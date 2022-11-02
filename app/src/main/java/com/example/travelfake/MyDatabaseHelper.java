package com.example.travelfake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.travelfake.Entity.Expense;
import com.example.travelfake.Entity.ExpenseSQLite;
import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    public   static final String DATABASE_NAME = "M-Expense.db";
    private   static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TripSQLite.CreateTable(db);
        ExpenseSQLite.CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        TripSQLite.DropTable(db);
        ExpenseSQLite.DropTable(db);
        onCreate(db);
    }
}
