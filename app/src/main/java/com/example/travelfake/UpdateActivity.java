package com.example.travelfake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.travelfake.Entity.TripSQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateActivity extends AppCompatActivity {
    EditText name_input,destination_input, dateOfTrip_input,description_input;
    RadioGroup require_input;
    Button expenses_button;
    Button update_button;
    TripSQLite tripSQLite;
    MyDatabaseHelper sqLiteOpenHelper;
    Boolean radio_require,trip_require;
    int _id;

    String trip_name,trip_destination,trip_date,trip_description;

    public static String TRIP_ID_INTENT = "tripID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        sqLiteOpenHelper = new MyDatabaseHelper(this);
        name_input = findViewById(R.id.name_input2);
        destination_input = findViewById(R.id.destination_input2);
        dateOfTrip_input = findViewById(R.id.dateOfTrip_input2);
        expenses_button = findViewById(R.id.expenses_button);
        require_input = findViewById(R.id.radio_check2);
        description_input = findViewById(R.id.description_input2);
        update_button = findViewById(R.id.update_button);

        expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this,ShowListExpense.class);
                intent.putExtra(TRIP_ID_INTENT, _id);
                startActivity(intent);
            }
        });

        dateOfTrip_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        require_input.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radio_require = i == R.id.radio_yes2;
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!validateName()){return;}
                    if(!validateDestination()){return;}
                    if(!validateDate()){return;}
                    if(!validateRequire()){return;}
                    TripSQLite trip_data = new TripSQLite(sqLiteOpenHelper);
                    trip_data.updateTrip(
                            String.valueOf(_id)
                            ,name_input.getText().toString().trim()
                            ,destination_input.getText().toString().trim()
                            ,dateOfTrip_input.getText().toString().trim()
                            ,radio_require
                            ,description_input.getText().toString().trim()
                    );
                    onBackPressed();
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("_id") && getIntent().hasExtra("trip_name")
                && getIntent().hasExtra("trip_destination") && getIntent().hasExtra("trip_date")
                && getIntent().hasExtra("trip_description") && getIntent().hasExtra("trip_require")
        ){
            _id = getIntent().getIntExtra(TripAdapter.TRIP_ID_INTENT, 0);
            trip_name = getIntent().getStringExtra(TripAdapter.TRIP_NAME_INTENT);
            trip_destination = getIntent().getStringExtra(TripAdapter.TRIP_DESTINATION_INTENT);
            trip_date = getIntent().getStringExtra(TripAdapter.TRIP_DATE_INTENT);
            trip_require = getIntent().getBooleanExtra(TripAdapter.TRIP_REQUIRE_INTENT, false);
            trip_description= getIntent().getStringExtra(TripAdapter.TRIP_DESCRIPTION_INTENT);

            name_input.setText(trip_name);
            destination_input.setText(trip_destination);
            dateOfTrip_input.setText(trip_date);
            require_input.check(trip_require ?  R.id.radio_yes2 : R.id.radio_no2);
            description_input.setText(trip_description);

        }else {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trash,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_one_row){
            try {
                TripSQLite tripSQLite1 = new TripSQLite(sqLiteOpenHelper);
                tripSQLite1.deleteOneRow(String.valueOf(_id));
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }catch (Exception e){
                Toast.makeText(this, "Cant delete", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
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