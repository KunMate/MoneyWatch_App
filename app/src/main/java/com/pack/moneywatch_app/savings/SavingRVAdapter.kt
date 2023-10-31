package com.pack.moneywatch_app.savings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.pack.moneywatch_app.R

class SavingRVAdapter(
    var list: List<SavingGoal>,
    val savingItemClickInterface: SavingItemClickInterface
) : RecyclerView.Adapter<SavingRVAdapter.SavingViewHolder>() {
    inner class SavingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val savingName = itemView.findViewById<TextView>(R.id.idSavingItemName)
        val itemCost = itemView.findViewById<TextView>(R.id.idSavingCost)
        val itemProgress = itemView.findViewById<ProgressBar>(R.id.idSavingProgressBar)
        val completeSaving = itemView.findViewById<Button>(R.id.idSavingCompleteSavingBtn)
        val deleteBtn = itemView.findViewById<ImageView>(R.id.idSavingDelete)
        val mainbalance = itemView.findViewById<TextView>(R.id.idSavingAvailable)
    }


    interface SavingItemClickInterface {
        fun onItemClick(savingGoal: SavingGoal)
        fun getRatio(savingGoal: SavingGoal) : Int
        fun savingFinish(savingGoal: SavingGoal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.saving_rv_item, parent, false)
        return SavingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingViewHolder, position: Int) {
        val context = holder.completeSaving.context
        holder.savingName.text = list[position].itemName
        holder.itemCost.text = list[position].itemPrice.toString() + " Ft"
        holder.deleteBtn.setOnClickListener {
            savingItemClickInterface.onItemClick(list[position])
        }
        val prog = savingItemClickInterface.getRatio(list[position])
        holder.itemProgress.progress = prog
        holder.completeSaving.setOnClickListener{
            if(holder.itemProgress.progress != 100)
            {
                Toast.makeText(context, "Nincs még elegendő megtakarítás", Toast.LENGTH_SHORT).show()
            }
            else {
                savingItemClickInterface.savingFinish(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}