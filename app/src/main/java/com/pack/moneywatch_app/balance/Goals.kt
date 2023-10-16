package com.pack.moneywatch_app.balance

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pack.moneywatch_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Goals : Fragment(), SavingRVAdapter.SavingItemClickInterface {

    lateinit var goalsItemRV : RecyclerView
    lateinit var addGoal : FloatingActionButton
    lateinit var savings : List<SavingGoal>
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
        savings = ArrayList()
        savingRVAdapter = SavingRVAdapter(savings, this)
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
        updateSavingBalance()
        addGoal.setOnClickListener{
            val intent = Intent(requireContext(), AddNewSavingActivity::class.java)
            startActivityForResult(intent, 10)
        }
    }

    override fun onItemClick(savingGoal: SavingGoal) {
        val savingRepository = SavingRepository(SavingDatabase(requireContext()))
        val factory = SavingViewModelFactory(savingRepository)
        savingViewModel = ViewModelProvider(this,factory)[SavingViewModel::class.java]
        savingViewModel.delete(savingGoal)
    }

    override fun getRatio(savingGoal: SavingGoal): Int {
        val sum = -1 * balancedb.transactionDao().getSavingBalance()
        val value : Int = ((sum.toString().toFloat() / savingGoal.itemPrice) * 100).toInt()
        return if (value > 100)
            100
        else
            value
    }

    override fun savingFinish(savingGoal: SavingGoal) {
        goalsItemRV.layoutManager = LinearLayoutManager(requireContext())
        goalsItemRV.adapter = savingRVAdapter
        val savingRepository = SavingRepository(SavingDatabase(requireContext()))
        val factory = SavingViewModelFactory(savingRepository)
        savingViewModel = ViewModelProvider(this,factory)[SavingViewModel::class.java]
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.goals_finish_dialog)
        val buttonYes = dialog.findViewById<Button>(R.id.idSavingFinishYes)
        val buttonNo = dialog.findViewById<Button>(R.id.idSavingFinishNo)
        buttonNo.setOnClickListener{
            dialog.dismiss()
        }
        buttonYes.setOnClickListener{
            val goal = BalanceTransaction(0, "cél teljesítés", -1*savingGoal.itemPrice, "egyéb", "")
            val savingBack = BalanceTransaction(0, savingGoal.itemName + " (levont cél)", savingGoal.itemPrice, "Megtakarítás", "")
            balancedb.transactionDao().insertAll(savingBack)
            balancedb.transactionDao().insertAll(goal)
            savingViewModel.delete(savingGoal)
            updateSavingBalance()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun updateSavingBalance(){
        val sum = -1 * balancedb.transactionDao().getSavingBalance()
        savingAvailable.text = sum.toString() + " Ft"
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == 10){
            val savingRepository = SavingRepository(SavingDatabase(requireContext()))
            val factory = SavingViewModelFactory(savingRepository)
            savingViewModel = ViewModelProvider(this,factory)[SavingViewModel::class.java]
            savingViewModel.getAllItems().observe(viewLifecycleOwner){
                savingRVAdapter.list = it
                savingRVAdapter.notifyDataSetChanged()
            }
        }
    }
}