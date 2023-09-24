package com.pack.moneywatch_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    lateinit var shopItemsRV : RecyclerView
    lateinit var shopAddFAB : FloatingActionButton
    lateinit var shopList : List<ShopListItems>
    lateinit var shopListViewModel : ShopListViewModel
    lateinit var shopListRVAdapter : ShopListRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Balance())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.balance -> replaceFragment(Balance())
                R.id.goals -> replaceFragment(Goals())
                R.id.shopassistant -> {
                    replaceFragment(ShopAssistant())
                }
                else -> {}
            }
            true
        }
    }
    private fun replaceFragment(fragment : Fragment){
        val FragmentManager = supportFragmentManager
        val fragmentTransaction = FragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.MainFrameLayout, fragment)
        fragmentTransaction.commit()
    }

    // shopassistant methods
   /* fun initAssistant(){
        shopItemsRV = findViewById(R.id.idRVItems)
        shopAddFAB = findViewById(R.id.idFABAdd)
        shopList = ArrayList<ShopListItems>()
        shopListRVAdapter = ShopListRVAdapter(shopList, this)
        shopItemsRV.layoutManager = LinearLayoutManager(this)
        shopItemsRV.adapter = shopListRVAdapter
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(this))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory).get(ShopListViewModel::class.java)
        shopListViewModel.getAllShopListItems().observe(this, Observer {
            shopListRVAdapter.list = it
            shopListRVAdapter.notifyDataSetChanged()
        })
        shopAddFAB.setOnClickListener{
            openDialog()
        }
    }*/

    /*fun openDialog(){
        shopItemsRV = findViewById(R.id.idRVItems)
        //shopList = ArrayList<ShopListItems>()
        //shopListRVAdapter = ShopListRVAdapter(shopList, this)
        shopItemsRV.layoutManager = LinearLayoutManager(this)
        shopItemsRV.adapter = shopListRVAdapter
        val shopListRepository = ShopListRepository(ShopListDatabase.invoke(this))
        val factory = ShopListViewModelFactory(shopListRepository)
        shopListViewModel = ViewModelProvider(this, factory).get(ShopListViewModel::class.java)
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.shoplist_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEditItemAmount)
        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }
        addBtn.setOnClickListener{
            val itemName: String = itemEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()
            val itemQuantity: String = itemQuantityEdt.text.toString()
            try {
                val qty: Float = itemQuantity.toFloat()
                val pr: Int = itemPrice.toInt()
                if(itemName.isNotEmpty() && itemPrice.isNotEmpty() &&itemQuantity.isNotEmpty()){
                    val items = ShopListItems(itemName, qty, pr)
                    shopListViewModel.insert(items)
                    Toast.makeText(applicationContext, "Item inserted successfully", Toast.LENGTH_SHORT).show()
                    shopListRVAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                }else{
                    Toast.makeText(applicationContext, "Insufficient data", Toast.LENGTH_SHORT).show()
                }
            }catch(e: NumberFormatException){
                Toast.makeText(applicationContext, "Wrong number input", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }*/

}