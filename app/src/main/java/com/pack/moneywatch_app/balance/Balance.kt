package com.pack.moneywatch_app.balance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
    private lateinit var typeSpinner : Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categories = resources.getStringArray(R.array.Categories_balance)
        var selectedCat : String
        balanceRV = view.findViewById(R.id.idBalanceRecycler)
        balanceLeft = view.findViewById(R.id.idBalanceRemaining)
        budget = view.findViewById(R.id.idBalanceBudget)
        expense = view.findViewById(R.id.idBalanceExpense)
        newItemBtn = view.findViewById(R.id.idBalanceAddTransactionBtn)
        transactions = arrayListOf()
        balanceTransactionAdapter = BalanceTransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(requireContext())
        typeSpinner = view.findViewById(R.id.idBalanceSetTypeShow)
        val spinnerAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        typeSpinner.adapter = spinnerAdapter
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

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> fetchAll()
                    1 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                    2 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                    3 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                    4 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                    5 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                    6 -> {
                        selectedCat = categories[typeSpinner.selectedItemId.toInt()]
                        fetchCategory(selectedCat)
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nem tortenhet ilyen
            }

        }
    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = balancedb.transactionDao().getAll()
            activity?.runOnUiThread {
                balanceTransactionAdapter.setData(transactions)
            }
            updateBoard()
        }
    }
    private fun fetchCategory(category : String) {
        GlobalScope.launch {
            transactions = balancedb.transactionDao().getCategoryType(category)
            activity?.runOnUiThread {
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
        updateBoard()
    }
}