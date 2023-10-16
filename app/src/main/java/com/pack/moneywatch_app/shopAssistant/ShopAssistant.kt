package com.pack.moneywatch_app.shopAssistant

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.*
import com.pack.moneywatch_app.balance.AddTransactionActivity
import org.w3c.dom.Text
import kotlin.math.roundToInt


class ShopAssistant : Fragment(), ShopListRVAdapter.ShopListItemClickInterface {

    private lateinit var shopItemsRV: RecyclerView
    private lateinit var shopAddFAB: FloatingActionButton
    private lateinit var shopList: List<ShopListItems>
    private lateinit var shopListViewModel: ShopListViewModel
    private lateinit var shopListRVAdapter: ShopListRVAdapter
    private lateinit var shopSum : TextView
    private lateinit var addToBalBtn : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_assistant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopItemsRV = view.findViewById(R.id.idRVItems)
        shopAddFAB = view.findViewById(R.id.idFABAdd)
        shopSum = view.findViewById(R.id.idlistsum)
        shopList = ArrayList<ShopListItems>()
        shopListRVAdapter = ShopListRVAdapter(shopList, this)
        shopItemsRV.layoutManager = LinearLayoutManager(view.context)
        shopItemsRV.adapter = shopListRVAdapter
        addToBalBtn = view.findViewById(R.id.idsaveToBalanceBtn)
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(requireContext()))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory)[ShopListViewModel::class.java]
        shopListViewModel.getAllShopListItems().observe(viewLifecycleOwner) {
            shopListRVAdapter.list = it
            shopListRVAdapter.notifyDataSetChanged()
            updateSum()
        }
        shopAddFAB.setOnClickListener {
            openDialog()
        }
        updateSum()
        addToBalBtn.setOnClickListener{
            if(shopListViewModel.getItemCount() == 0)
                Toast.makeText(requireContext(), "Üres listát nem lehet menteni", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(requireContext(), AddTransactionActivity::class.java)
                val sum = getSum()
                intent.putExtra("cost", sum)
                startActivityForResult(intent, 1)
            }
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                shopListViewModel.delete(shopListRVAdapter.list[viewHolder.adapterPosition])
                shopListRVAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                updateSum()
            }
        }
        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(shopItemsRV)
    }

    private fun openDialog() {
        shopItemsRV.layoutManager = LinearLayoutManager(context)
        shopItemsRV.adapter = shopListRVAdapter
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(requireContext()))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory)[ShopListViewModel::class.java]
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.shoplist_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEditItemAmount)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName: String = itemEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()
            val itemQuantity: String = itemQuantityEdt.text.toString()
            try {
                val qty: Float = itemQuantity.toFloat()
                val pr: Int = itemPrice.toInt()
                if (itemName.isNotEmpty()) {
                    val items = ShopListItems(itemName, qty, pr)
                    shopListViewModel.insert(items)
                    Toast.makeText(requireContext(), "Termék hozzáadva", Toast.LENGTH_SHORT).show()
                    shopListRVAdapter.notifyDataSetChanged()
                    updateSum()
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Nincs elég adat", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Rossz szám adat", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    override fun onItemClick(shopListItems: ShopListItems) {
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(requireContext()))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory)[ShopListViewModel::class.java]
        shopListViewModel.delete(shopListItems)
        shopListRVAdapter.notifyDataSetChanged()
        updateSum()
        Toast.makeText(requireContext(), "Termék eltávolítva", Toast.LENGTH_SHORT).show()
    }

    override fun loadItem(shopListItems: ShopListItems) {
        shopItemsRV.layoutManager = LinearLayoutManager(context)
        shopItemsRV.adapter = shopListRVAdapter
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(requireContext()))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory)[ShopListViewModel::class.java]
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.shoplist_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEditItemAmount)
        itemEdt.setText(shopListItems.itemName)
        itemPriceEdt.setText(shopListItems.itemPrice.toString())
        itemQuantityEdt.setText(shopListItems.itemAmount.toString())
        addBtn.setText("Frissítés")
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName: String = itemEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()
            val itemQuantity: String = itemQuantityEdt.text.toString()
            try {
                if (itemName.isNotEmpty()) {
                    shopListItems.itemName = itemName
                    shopListItems.itemPrice = itemPrice.toInt()
                    shopListItems.itemAmount =  itemQuantity.toFloat()
                    shopListViewModel.update(shopListItems)
                    Toast.makeText(requireContext(), "Termék frissítve", Toast.LENGTH_SHORT).show()
                    shopListRVAdapter.notifyDataSetChanged()
                    updateSum()
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Nincs elég adat", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Rossz szám adat", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    fun updateSum(){
        val sum : Int
        if(shopListViewModel.getItemCount() == 0)
        {
            sum = 0
        }
        else
            sum = shopListViewModel.getTotalCost().toFloat().roundToInt()
        shopSum.text = sum.toString() + " Ft"
    }
    private fun getSum() : Int{
        val sum : Int
        if(shopListViewModel.getItemCount() == 0)
        {
            sum = 0
        }
        else
            sum = shopListViewModel.getTotalCost().toFloat().roundToInt()
        return sum
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            shopListViewModel.wipeItems()
        }
    }
}