package com.pack.moneywatch_app.balance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.pack.moneywatch_app.R

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var addbtn : Button
    private lateinit var closeBtn : ImageButton
    private lateinit var nameInput : EditText
    private lateinit var amountInput : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_transaction)
        addbtn = findViewById(R.id.idAddnewTransactionBtn)
        closeBtn = findViewById(R.id.idTransactionCloseBtn)
        addbtn.setOnClickListener{
            val label = nameInput.text.toString()
            try {
                val amount = amountInput.text.toString().toInt()
            } catch(e: NumberFormatException){
                Toast.makeText(this, "Adja meg az összeget", Toast.LENGTH_SHORT).show()
            }
            if(label.isEmpty())
                Toast.makeText(this, "Adja meg a költés nevét", Toast.LENGTH_SHORT).show()
        }
        closeBtn.setOnClickListener{
            finish()
        }
    }
}