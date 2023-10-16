package com.pack.moneywatch_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pack.moneywatch_app.balance.Balance
import com.pack.moneywatch_app.savings.Goals
import com.pack.moneywatch_app.databinding.ActivityMainBinding
import com.pack.moneywatch_app.shopAssistant.ShopAssistant

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Balance())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.balance -> replaceFragment(Balance())
                R.id.goals -> replaceFragment(Goals())
                R.id.shopassistant -> {
                    replaceFragment(ShopAssistant())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val FragmentManager = supportFragmentManager
        val fragmentTransaction = FragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.MainFrameLayout, fragment)
        fragmentTransaction.commit()
    }


}