package com.example.expensetracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensetracker.databinding.ActivityUpdateExpensesBinding

class UpdateExpenses : AppCompatActivity() {


    private lateinit var binding:ActivityUpdateExpensesBinding
    private lateinit var db:ExpensesDatabaseHelper
    private var expensesId: Int = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ExpensesDatabaseHelper(this)

        expensesId = intent.getIntExtra("expenses_id",-1)
        if (expensesId == -1){
            finish()
            return
        }

        val expenses = db.getExpensesByID(expensesId)
        binding.updatetitleEditText.setText(expenses.title)
        binding.updatecontentEditText.setText(expenses.content)

        binding.updatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val updateExpenses = Expenses(expensesId,newTitle,newContent)
            db.updateExpenses(updateExpenses)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }

    }
}