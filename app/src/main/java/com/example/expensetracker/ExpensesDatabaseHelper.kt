package com.example.expensetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpensesDatabaseHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "expensesapp.db"
        private const val DATABASE_VERSION = 1
        private  const val TABLE_NAME = "allexpenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private  const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertExpenses(expenses: Expenses){
        val db = writableDatabase
        val values = ContentValues(). apply {
            put(COLUMN_TITLE,expenses.title)
            put(COLUMN_CONTENT,expenses.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllExpenses(): List<Expenses>{
        val expensesList = mutableListOf<Expenses>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val expenses = Expenses (id,title,content)
            expensesList.add(expenses)
        }
        cursor.close()
        db.close()
        return expensesList
    }

    fun updateExpenses(expenses: Expenses){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,expenses.title)
            put(COLUMN_CONTENT,expenses.content)
        }
        val whereClause = "$COLUMN_ID= ?"
        val whereArgs = arrayOf(expenses.id.toString())
        db.update(TABLE_NAME, values,whereClause, whereArgs)
        db.close()
    }

    fun getExpensesByID(expensesId:Int): Expenses{
       val db = readableDatabase
       val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $expensesId"
       val cursor = db.rawQuery(query,null)
       cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Expenses(id, title, content)
    }

    fun deleteExpenses(expensesId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(expensesId.toString())
        db.delete(TABLE_NAME,whereClause, whereArgs)
        db.close()
    }
}