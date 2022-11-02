package com.example.travelfake;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Fragment {

    private RecyclerView recyclerView;
    private List<Trip> tripList;
    private TripAdapter tripAdapter;
    EditText search_input;
    MyDatabaseHelper myDatabaseHelper;
    Button search_button;

    public SearchActivity() {}

    public static SearchActivity newInstance(String param1, String param2) {
        SearchActivity fragment = new SearchActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_input = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.recyclerView_search);
        search_button = view.findViewById(R.id.search_button);

        myDatabaseHelper = new MyDatabaseHelper(view.getContext());
        tripList = new ArrayList<>();
        tripAdapter = new TripAdapter();

        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TripSQLite tripSQLite = new TripSQLite(myDatabaseHelper);
                    String query = search_input.getText().toString().trim();
                    tripList = new ArrayList<>();
                    Cursor cursor = tripSQLite.searchTrip(query);
                    if(cursor.getCount() < 1){
                        Toast.makeText(getContext(), "We don't have data", Toast.LENGTH_SHORT).show();
                    }
                    while (cursor.moveToNext()){
                        String id = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_NAME));
                        String destination = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_DESTINATION));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_DATE));
                        Integer require = cursor.getInt(cursor.getColumnIndexOrThrow(TripSQLite.COLUMN_REQUIRE));
                        Trip foundTrip = new Trip(Integer.parseInt(id),name,destination,date,null,require == 1);
                        tripList.add(foundTrip);
                    }
                    tripAdapter.setListTrip(tripList);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_activity, container, false);
    }
}