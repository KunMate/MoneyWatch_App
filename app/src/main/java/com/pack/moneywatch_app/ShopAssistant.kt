package com.pack.moneywatch_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ShopAssistant.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopAssistant : Fragment(){

    lateinit var linearLayout : LinearLayout
    lateinit var newItem : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_shop_assistant, container, false)
        /*linearLayout = view.findViewById(R.id.shoplinearlayout)
        newItem = view.findViewById(R.id.shopnewitem)
        newItem.setOnClickListener{  // jelenleg nem csinal semmit, context = ?
            val textView = TextView(context)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)

            textView.layoutParams = params
            textView.text = "omg megjelentem"
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            linearLayout.addView(textView)
        }*/
        return inflater.inflate(R.layout.fragment_shop_assistant, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShopAssistant.
         */
       /* @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShopAssistant().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }


}