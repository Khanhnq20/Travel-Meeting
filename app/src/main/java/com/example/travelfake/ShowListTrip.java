package com.example.travelfake;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowListTrip extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton create_button;
    MyDatabaseHelper myDB;
    TripAdapter tripAdapter;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_trip_list,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Trip> trips = setDataArray();
        tripAdapter.setListTrip(trips);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        create_button = view.findViewById(R.id.create_button);
        imageView = view.findViewById(R.id.trip_noData);

        myDB = new MyDatabaseHelper(getContext());
        tripAdapter = new TripAdapter();

        List<Trip> trips = setDataArray();
        tripAdapter.setListTrip(trips);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivity(intent);
            }
        });
    }


    public List<Trip> setDataArray(){
        try {
            TripSQLite tripSQLite = new TripSQLite(myDB);
            Cursor cursor = tripSQLite.readData();
            List<Trip> trips = new ArrayList<>();

            if(cursor.getCount() == 0){
                Toast.makeText(this.getContext(), "No Data", Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.VISIBLE);
                return trips;
            }else {
                imageView.setVisibility(View.GONE);
                while (cursor.moveToNext()){
                    Boolean require;
                    int trip_id = cursor.getInt(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_ID));
                    String trip_name = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_NAME));
                    String trip_destination = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_DESTINATION));
                    String trip_date = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_DATE));
                    int trip_require = cursor.getInt(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_REQUIRE));
                    String trip_description = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_DESCRIPTION));
                    require = trip_require == 1;
                    trips.add(new Trip(trip_id, trip_name,trip_destination,trip_date,trip_description,require));
                }
                return trips;
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }
}