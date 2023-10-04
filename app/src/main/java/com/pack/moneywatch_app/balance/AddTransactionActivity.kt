package com.pack.moneywatch_app.balance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.pack.moneywatch_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var addbtn: Button
    private lateinit var closeBtn: ImageButton
    private lateinit var nameInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var categorySpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val categories = resources.getStringArray(R.array.Categories)
        setContentView(R.layout.activity_add_transaction)
        addbtn = findViewById(R.id.idAddnewTransactionBtn)
        closeBtn = findViewById(R.id.idTransactionCloseBtn)
        nameInput = findViewById(R.id.idNameInput)
        amountInput = findViewById(R.id.idAmountInput)
        categorySpinner = findViewById(R.id.idCategorySpinner)
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.adapter = adapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // categories[position] = kivalasztott spinner
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nem tortenhet ilyen
            }

        }
        addbtn.setOnClickListener {
            val description = nameInput.text.toString()
            val amount: Int
            if (description.isEmpty())
                Toast.makeText(this, "Adja meg a költés nevét", Toast.LENGTH_SHORT).show()
            try {
                amount = amountInput.text.toString().toInt()
                if (description.isNotEmpty()) {
                    val transaction = BalanceTransaction(0, description, amount, "", "")
                    insert(transaction)
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

    private fun insert(transaction: BalanceTransaction) {
        val balancedb = Room.databaseBuilder(
            this,
            BalanceDatabase::class.java,
            "transactions"
        ).build()

        GlobalScope.launch {
            balancedb.transactionDao().insertAll(transaction)
            finish()
        }
    }
}