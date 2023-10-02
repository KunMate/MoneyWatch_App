package com.pack.moneywatch_app.shopAssistant

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.*
import org.w3c.dom.Text
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 * Use the [ShopAssistant.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopAssistant : Fragment(), ShopListRVAdapter.ShopListItemClickInterface {

    lateinit var shopItemsRV: RecyclerView
    lateinit var shopAddFAB: FloatingActionButton
    lateinit var shopList: List<ShopListItems>
    lateinit var shopListViewModel: ShopListViewModel
    lateinit var shopListRVAdapter: ShopListRVAdapter
    lateinit var shopSum : TextView
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
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(requireContext()))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory)[ShopListViewModel::class.java]
        shopListViewModel.getAllShopListItems().observe(viewLifecycleOwner, Observer {
            shopListRVAdapter.list = it
            shopListRVAdapter.notifyDataSetChanged()
            updateSum()
        })
        shopAddFAB.setOnClickListener {
            openDialog()
        }
        updateSum()
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
                    Toast.makeText(requireContext(), "Nem elég adat", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
    }

    fun updateSum(){
        val sum = shopListViewModel.getTotalCost().toFloat().roundToInt()
        shopSum.text = sum.toString() + " Ft"
    }
}