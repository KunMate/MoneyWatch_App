package com.pack.moneywatch_app.balance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.pack.moneywatch_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionDetailsActivity : AppCompatActivity() {
    private lateinit var updateBtn: Button
    private lateinit var closeBtn: ImageButton
    private lateinit var nameInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var captionText: EditText
    private lateinit var deleteBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categories = resources.getStringArray(R.array.Categories)
        setContentView(R.layout.activity_transaction_details)
        supportActionBar?.hide()
        updateBtn = findViewById(R.id.idAddnewTransactionBtn)
        closeBtn = findViewById(R.id.idTransactionCloseBtn)
        nameInput = findViewById(R.id.idNameInput)
        amountInput = findViewById(R.id.idAmountInput)
        categorySpinner = findViewById(R.id.idCategorySpinner)
        captionText = findViewById(R.id.idDescriptionInput)
        deleteBtn = findViewById(R.id.idRemoveTransactionBtn)
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.adapter = adapter
        var transaction = intent.getSerializableExtra("transaction") as BalanceTransaction
        nameInput.setText(transaction.description)
        amountInput.setText(transaction.amount.toString())
        val spinnerPos = categories.indexOf(transaction.category)
        if(spinnerPos == -1) {
            categorySpinner.setSelection(1)
        }
        else
            categorySpinner.setSelection(spinnerPos)
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nem tortenik meg
            }
        }
        captionText.setText(transaction.expinfo)

        deleteBtn.setOnClickListener{
            delete(transaction)
            finish()
        }

        updateBtn.setOnClickListener {
            val title = nameInput.text.toString()
            val description = captionText.text.toString()
            val selectedCategory = categories[categorySpinner.selectedItemId.toInt()]
            val amount: Int
            if (title.isEmpty())
                Toast.makeText(this, "Adja meg a költés nevét", Toast.LENGTH_SHORT).show()
            try {
                amount = amountInput.text.toString().toInt()
                if (title.isNotEmpty()) {
                    val updatedTransaction =
                        BalanceTransaction(transaction.id, title, amount, selectedCategory, description)
                    update(updatedTransaction)
                } else {
                    Toast.makeText(this, "Nincs elég adat", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Adja meg az összeget", Toast.LENGTH_SHORT).show()
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun delete(transaction: BalanceTransaction) {
        val balancedb = Room.databaseBuilder(
            this,
            BalanceDatabase::class.java,
            "transactions"
        ).build()
        GlobalScope.launch {
            balancedb.transactionDao().delete(transaction)
        }
    }

    private fun update(transaction: BalanceTransaction) {
        val balancedb = Room.databaseBuilder(
            this,
            BalanceDatabase::class.java,
            "transactions"
        ).build()

        GlobalScope.launch {
            balancedb.transactionDao().update(transaction)
            finish()
        }
    }

}