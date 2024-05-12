package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var db : ExpensesDatabaseHelper
    private lateinit var expensesAdapter: ExpensesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        db = ExpensesDatabaseHelper(this)
        expensesAdapter = ExpensesAdapter(db.getAllExpenses(),this)

        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expensesRecyclerView.adapter = expensesAdapter


        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddExpenses::class.java)
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error starting Add_Expenses activity: ${e.message}")
             }
         }

      }
    override fun onResume(){
        super.onResume()
        expensesAdapter.refreshData(db.getAllExpenses())
        }
    }
