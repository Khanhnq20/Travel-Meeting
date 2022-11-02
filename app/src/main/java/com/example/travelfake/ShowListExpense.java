package com.example.travelfake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelfake.Entity.Expense;
import com.example.travelfake.Entity.ExpenseSQLite;
import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowListExpense extends AppCompatActivity {
    FloatingActionButton create_expense_button;
    RecyclerView recyclerView;
    int tripId;
    ExpenseAdapter expenseAdapter;
    MyDatabaseHelper sqLiteOpenHelper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_expense);
        sqLiteOpenHelper = new MyDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView_expense);
        imageView = findViewById(R.id.expense_noData);
        create_expense_button = findViewById(R.id.create_button_expense);
        tripId = getIntent().getIntExtra(UpdateActivity.TRIP_ID_INTENT, 0);
        List<Expense> expenseList = getExpenseData(tripId);
        expenseAdapter= new ExpenseAdapter();
        expenseAdapter.setListExpense(expenseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expenseAdapter);

        create_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowListExpense.this,AddExpenseActivity.class);
                intent.putExtra(UpdateActivity.TRIP_ID_INTENT, tripId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Expense> expenseList = getExpenseData(tripId);
        expenseAdapter.setListExpense(expenseList);
    }

    List<Expense> getExpenseData(int tripId){
        List<Expense> expenseList = new ArrayList<>();
        ExpenseSQLite database = new ExpenseSQLite(this.sqLiteOpenHelper);
        Cursor cursor = database.readData(tripId);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            imageView.setVisibility(View.VISIBLE);
            return expenseList;
        }else{
            imageView.setVisibility(View.GONE);
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseSQLite.COLUMN_ID));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseSQLite.COLUMN_TYPE));
                String amount = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseSQLite.COLUMN_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseSQLite.COLUMN_DATE));
                Expense expense = new Expense(id,type,amount,date);
                expenseList.add(expense);
            }
            return expenseList;
        }
    }
}