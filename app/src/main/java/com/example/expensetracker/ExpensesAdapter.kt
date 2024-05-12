package com.example.expensetracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ExpensesAdapter(private var expenses: List<Expenses>, context: Context) : RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    private val db: ExpensesDatabaseHelper = ExpensesDatabaseHelper(context)


    class ExpensesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_item, parent, false)
        return ExpensesViewHolder(view)
    }

    override fun getItemCount(): Int = expenses.size

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val expense = expenses[position]
        holder.titleTextView.text = expense.title
        holder.contentTextView.text = expense.content

        holder.updateButton.setOnClickListener{
            val intent = Intent (holder.itemView.context,UpdateExpenses::class.java).apply {
                putExtra("expenses_id",expense.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteExpenses(expense.id)
            refreshData(db.getAllExpenses())
            Toast.makeText(holder.itemView.context,"Expense Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newExpenses: List<Expenses>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}
