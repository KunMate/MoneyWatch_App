package com.pack.moneywatch_app.shopAssistant

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.pack.moneywatch_app.R
import com.pack.moneywatch_app.balance.TransactionDetailsActivity
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class ShopListRVAdapter (var list: List<ShopListItems>,
                         private val shopListItemClickInterface: ShopListItemClickInterface)
    : RecyclerView.Adapter<ShopListRVAdapter.ShopListViewHolder>() {
    inner class ShopListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTV = itemView.findViewById<TextView>(R.id.idTVItemName)
        val quantityTV = itemView.findViewById<TextView>(R.id.idTVQuantity)
        val priceTV = itemView.findViewById<TextView>(R.id.idTVPrice)
        val amountTV = itemView.findViewById<TextView>(R.id.idTVTotalAmt)
        val deleteTV = itemView.findViewById<ImageView>(R.id.idTVDelete)

    }



    interface ShopListItemClickInterface{
        fun onItemClick(shopListItems: ShopListItems)
        fun loadItem(shopListItems: ShopListItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shoplist_rv_item, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        holder.nameTV.text = list[position].itemName
        holder.quantityTV.text = list[position].itemAmount.toString()
        holder.priceTV.text = list[position].itemPrice.toString() + " Ft"
        val itemTotal : Int = (list[position].itemPrice * list[position].itemAmount).roundToInt()
        holder.amountTV.text = itemTotal.toString() + " Ft"
        holder.deleteTV.setOnClickListener{
            shopListItemClickInterface.onItemClick(list[position])
        }
        holder.itemView.setOnClickListener{
            shopListItemClickInterface.loadItem(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}