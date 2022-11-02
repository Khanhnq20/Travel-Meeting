package com.example.travelfake;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {
    EditText name_input,destination_input, dateOfTrip_input,description_input;
    RadioGroup require_input;
    Button add_button;
    Boolean radio_require;
    SQLiteOpenHelper sqLiteOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        name_input = findViewById(R.id.name_input);
        add_button = findViewById(R.id.add_button);
        destination_input = findViewById(R.id.destination_input);
        dateOfTrip_input = findViewById(R.id.dateOfTrip_input);
        description_input = findViewById(R.id.description_input);
        require_input = findViewById(R.id.radio_check);
        sqLiteOpenHelper = new MyDatabaseHelper(this);

        dateOfTrip_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        require_input.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radio_require = i == R.id.radio_yes;
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!validateName()){return;}
                    if(!validateDestination()){return;}
                    if(!validateDate()){return;}
                    if(!validateRequire()){return;}
                    TripSQLite tripSQLite = new TripSQLite(sqLiteOpenHelper);
                    tripSQLite.addTrip(name_input.getText().toString().trim(),
                            destination_input.getText().toString().trim(),
                            dateOfTrip_input.getText().toString().trim(),
                            radio_require,
                            description_input.getText().toString().trim()
                    );
                    AddTripActivity.super.onBackPressed();
                    Toast.makeText(AddTripActivity.this
                            ,"Add Successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(AddTripActivity.this
                            , e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateName(){
        String val = name_input.getEditableText().toString().trim();
        if(val.isEmpty()){
            name_input.setError("Field can not be empty");
            return false;
        }else {
            name_input.setError(null);
            return true;
        }
    }

    private boolean validateDestination(){
        String val = destination_input.getEditableText().toString().trim();
        if(val.isEmpty()){
            destination_input.setError("Field can not be empty");
            return false;
        }else {
            destination_input.setError(null);
            return true;
        }
    }

    private boolean validateDate() throws ParseException {
        String val = dateOfTrip_input.getEditableText().toString().trim();
        if(val.isEmpty()){
            dateOfTrip_input.setError("Field can not be empty");
            return false;
        }
        else {
            dateOfTrip_input.setError(null);
            return true;
        }
    }

    private boolean validateRequire(){
        if(require_input.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please Select The Require Risks Assessment", Toast.LENGTH_SHORT).show();
            return false;
        }else {
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
                dateOfTrip_input.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        datePickerDialog.show();
    }
}