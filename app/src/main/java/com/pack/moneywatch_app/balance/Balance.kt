package com.pack.moneywatch_app.balance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Balance : Fragment() {
    private lateinit var transactions: List<BalanceTransaction>
    private lateinit var balanceTransactionAdapter: BalanceTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var balanceRV: RecyclerView
    private lateinit var balanceLeft: TextView
    private lateinit var budget: TextView
    private lateinit var expense: TextView
    private lateinit var newItemBtn: FloatingActionButton
    private lateinit var balancedb: BalanceDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        balanceRV = view.findViewById(R.id.idBalanceRecycler)
        balanceLeft = view.findViewById(R.id.idBalanceRemaining)
        budget = view.findViewById(R.id.idBalanceBudget)
        expense = view.findViewById(R.id.idBalanceExpense)
        newItemBtn = view.findViewById(R.id.idBalanceAddTransactionBtn)
        transactions = arrayListOf()
        balanceTransactionAdapter = BalanceTransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(requireContext())
        balancedb = Room.databaseBuilder(
            requireContext(),
            BalanceDatabase::class.java,
            "transactions"
        ).build()
        balanceRV.apply {
            adapter = balanceTransactionAdapter
            layoutManager = linearLayoutManager
        }

        newItemBtn.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = balancedb.transactionDao().getAll()
            activity?.runOnUiThread {
                updateBoard()
                balanceTransactionAdapter.setData(transactions)
            }
        }
    }

    private fun updateBoard() {
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val expensesum = totalAmount - budgetAmount
        budget.text = budgetAmount.toString() + " Ft"
        expense.text = expensesum.toString() + " Ft"
        balanceLeft.text = totalAmount.toString() + " Ft"
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}