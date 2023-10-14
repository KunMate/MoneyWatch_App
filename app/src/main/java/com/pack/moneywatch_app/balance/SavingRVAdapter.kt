package com.pack.moneywatch_app.balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pack.moneywatch_app.R

class SavingRVAdapter(
    var list: List<SavingGoal>,
    val savingItemClickInterface: SavingItemClickInterface
) : RecyclerView.Adapter<SavingRVAdapter.SavingViewHolder>() {
    inner class SavingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val savingName = itemView.findViewById<TextView>(R.id.idSavingItemName)
        val itemCost = itemView.findViewById<TextView>(R.id.idSavingCost)
        val itemProgress = itemView.findViewById<SeekBar>(R.id.idSavingProgressBar)
        val completeSaving = itemView.findViewById<Button>(R.id.idSavingCompleteSavingBtn)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.idSavingDelete)
    }


    interface SavingItemClickInterface {
        fun onItemClick(savingGoal: SavingGoal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.saving_rv_item, parent, false)
            return SavingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingViewHolder, position: Int) {
        holder.savingName.text = list[position].itemName
        holder.itemCost.text = list[position].itemPrice.toString()
        holder.deleteBtn.setOnClickListener{
            savingItemClickInterface.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}