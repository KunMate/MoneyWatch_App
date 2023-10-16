package com.pack.moneywatch_app.savings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.room.Room
import com.pack.moneywatch_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNewSavingActivity : AppCompatActivity() {
    private lateinit var savingName : EditText
    private lateinit var savingAmount : EditText
    private lateinit var addBtn : Button
    private lateinit var closeBtn : ImageButton
    private lateinit var saving : SavingGoal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_saving)
        supportActionBar?.hide()
        savingName = findViewById(R.id.idSavingNameInput)
        savingAmount = findViewById(R.id.idSavingAmountInput)
        addBtn = findViewById(R.id.idSavingFinish)
        closeBtn = findViewById(R.id.idSavingCloseBtn)
        addBtn.setOnClickListener{
            try {
                val sName = savingName.text.toString()
                val sAmount = savingAmount.text.toString().toInt()
                if(sName.isEmpty())
                    Toast.makeText(this, "Nincs megadott cím", Toast.LENGTH_SHORT).show()
                else {
                    saving = SavingGoal(sName, sAmount)
                    insert(saving)
                }
            }catch(e: NumberFormatException)
            {
                Toast.makeText(this, "Nem megfelelő adatok", Toast.LENGTH_SHORT).show()
            }
        }
        closeBtn.setOnClickListener{
            finish()
        }
    }
    private fun insert(saving : SavingGoal){
        val savingDatabase = Room.databaseBuilder(
            this,
            SavingDatabase::class.java,
            "saving.db"
        ).build()
        GlobalScope.launch {
            savingDatabase.getSavingDao().insert(saving)
            setResult(RESULT_OK)
            finish()
        }
    }
}
