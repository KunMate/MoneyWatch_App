package com.pack.moneywatch_app.balance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.R
import org.w3c.dom.Text


class Goals : Fragment(), SavingRVAdapter.SavingItemClickInterface {

    lateinit var goalsItemRV : RecyclerView
    lateinit var addGoal : FloatingActionButton
    lateinit var list : List<SavingGoal>
    lateinit var savingRVAdapter: SavingRVAdapter
    lateinit var savingViewModel: SavingViewModel
    lateinit var balancedb: BalanceDatabase
    lateinit var savingAvailable : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalsItemRV = view.findViewById(R.id.idSavingRecycler)
        addGoal = view.findViewById(R.id.idSavingAddBtn)
        list = ArrayList()
        savingRVAdapter = SavingRVAdapter(list, this)
        savingAvailable = view.findViewById(R.id.idSavingAvailable)
        goalsItemRV.layoutManager = LinearLayoutManager(requireContext())
        goalsItemRV.adapter = savingRVAdapter
        val savingRepository = SavingRepository(SavingDatabase(requireContext()))
        val factory = SavingViewModelFactory(savingRepository)
        savingViewModel = ViewModelProvider(this,factory)[SavingViewModel::class.java]
        savingViewModel.getAllItems().observe(viewLifecycleOwner){
            savingRVAdapter.list = it
            savingRVAdapter.notifyDataSetChanged()
        }
        balancedb = Room.databaseBuilder(
            requireContext(),
            BalanceDatabase::class.java,
            "transactions"
        ).allowMainThreadQueries().build()
        val sum = -1 * balancedb.transactionDao().getSavingBalance()
        savingAvailable.text = sum.toString() + " Ft"

    }

    override fun onItemClick(savingGoal: SavingGoal) {

    }

}