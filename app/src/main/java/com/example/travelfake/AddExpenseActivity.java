package com.example.travelfake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelfake.Entity.Expense;
import com.example.travelfake.Entity.ExpenseSQLite;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddExpenseActivity extends AppCompatActivity {
    Button add_expense_button;
    SQLiteOpenHelper sqLiteOpenHelper;
    TextView amount_input,dateOfExpense_input;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String item_chose;
    int tripId;
    String[] items = {"Travel","Food","Transport"};

    public String getItem_chose() {
        return item_chose;
    }
    
    public void setItem_chose(String item_chose) {
        this.item_chose = item_chose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        tripId = getIntent().getIntExtra(UpdateActivity.TRIP_ID_INTENT, 0);
        amount_input = findViewById(R.id.amount_input);
        dateOfExpense_input = findViewById(R.id.dateOfExpense_input);
        add_expense_button = findViewById(R.id.add_button_expense);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item_dropdown,items);
        autoCompleteTextView.setAdapter(adapterItems);

        dateOfExpense_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddExpenseActivity.this, "Item" + item,Toast.LENGTH_SHORT).show();
                setItem_chose(item);
            }
        });

        add_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sqLiteOpenHelper = new MyDatabaseHelper(view.getContext());
                    ExpenseSQLite expenseSQLite = new ExpenseSQLite(sqLiteOpenHelper);
                    if(!validateType()){return;}
                    if(!validateAmount()){return;}
                    if(!validateDate()){return;}
                    expenseSQLite.addExpense(getItem_chose(),amount_input.getText().toString().trim()
                            ,dateOfExpense_input.getText().toString().trim(),tripId);
                    AddExpenseActivity.super.onBackPressed();
                    Toast.makeText(AddExpenseActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(AddExpenseActivity.this, "Add Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateType(){
        if(getItem_chose() == null){
            Toast.makeText(AddExpenseActivity.this
                    , "You have not selected the Type"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAmount(){
        String val = amount_input.getEditableText().toString().trim();
        if(val.isEmpty()){
            amount_input.setError("Field can not be empty");
            return false;
        }else {
            amount_input.setError(null);
            return true;
        }
    }

    private boolean validateDate() throws ParseException {
        String val = dateOfExpense_input.getEditableText().toString().trim();
        if(val.isEmpty()){
            dateOfExpense_input.setError("Field can not be empty");
            return false;
        }
        else {
            dateOfExpense_input.setError(null);
            return true;
        }
    }

    private void selectDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateOfExpense_input.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        datePickerDialog.show();
    }
}