package com.example.expensetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.ActivityAddexpensesBinding

class AddExpenses : AppCompatActivity() {
    private lateinit var binding: ActivityAddexpensesBinding
    private lateinit var db: ExpensesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            binding = ActivityAddexpensesBinding.inflate(layoutInflater)
            setContentView(binding.root)

            db = ExpensesDatabaseHelper(this)

            binding.saveButton.setOnClickListener {
                val title = binding.titleEditText.text.toString()
                val content = binding.contentEditText.text.toString()
                val expenses = Expenses(0, title, content)
                db.insertExpenses(expenses)
                finish()
                Toast.makeText(this, "Expenses Saved", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading AddExpenses activity", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if an error occurs
        }
    }
}
