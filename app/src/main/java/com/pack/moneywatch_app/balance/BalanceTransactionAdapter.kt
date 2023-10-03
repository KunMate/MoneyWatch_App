package com.pack.moneywatch_app.balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pack.moneywatch_app.R

class BalanceTransactionAdapter(private val transactions: ArrayList<BalanceTransaction>) : RecyclerView.Adapter<BalanceTransactionAdapter.BalanceTransactionViewHolder>() {
    class BalanceTransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label : TextView = view.findViewById(R.id.idTransactionLabel)
        val amount : TextView = view.findViewById(R.id.idTransactionAmount)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BalanceTransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        return BalanceTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: BalanceTransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.amount.context
        if(transaction.amount > 0) {
            holder.amount.text = "+" + transaction.amount.toString() + " Ft"
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green_bright60))
        }
        else{
            holder.amount.text = transaction.amount.toString() + " Ft"
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        holder.label.text = transaction.label
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

}