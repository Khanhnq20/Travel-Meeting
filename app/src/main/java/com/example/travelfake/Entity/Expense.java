package com.example.travelfake.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Expense {
    private int expense_id;
    private String expense_type,expense_amount,expense_timeOfExpense;
    public Expense(int expense_id, String expense_type, String expense_amount, String expense_timeOfExpense) {
        this.expense_id = expense_id;
        this.expense_type = expense_type;
        this.expense_amount = expense_amount;
        this.expense_timeOfExpense = expense_timeOfExpense;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public String getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(String expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getExpense_timeOfExpense() {
        return expense_timeOfExpense;
    }

    public void setExpense_timeOfExpense(String expense_timeOfExpense) {
        this.expense_timeOfExpense = expense_timeOfExpense;
    }
}
