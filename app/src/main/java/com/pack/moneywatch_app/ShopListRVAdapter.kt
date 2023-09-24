package com.pack.moneywatch_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopListRVAdapter (var list: List<ShopListItems>,
                         val shopListItemClickInterface: ShopListItemClickInterface)  : RecyclerView.Adapter<ShopListRVAdapter.ShopListViewHolder>() {
    inner class ShopListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTV = itemView.findViewById<TextView>(R.id.idTVItemName)
        val quantityTV = itemView.findViewById<TextView>(R.id.idTVQuantity)
        val priceTV = itemView.findViewById<TextView>(R.id.idTVPrice)
        val amountTV = itemView.findViewById<TextView>(R.id.idTVTotalAmt)
        val deleteTV = itemView.findViewById<ImageView>(R.id.idTVDelete)

    }



    interface ShopListItemClickInterface{
        fun onItemClick(shopListItems: ShopListItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shoplist_rv_item, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        holder.nameTV.text = list[position].itemName
        holder.quantityTV.text = list[position].itemAmount.toString()
        holder.priceTV.text = list[position].itemPrice.toString()
        val itemTotal : Float = list[position].itemPrice * list[position].itemAmount
        holder.amountTV.text = itemTotal.toString()
        holder.deleteTV.setOnClickListener{
            shopListItemClickInterface.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}