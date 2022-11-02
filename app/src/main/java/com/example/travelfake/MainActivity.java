package com.example.travelfake;

import android.content.Intent;
import android.database.Cursor;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelfake.Entity.Trip;
import com.example.travelfake.Entity.TripSQLite;
import com.example.travelfake.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper sqLiteOpenHelper;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteOpenHelper = new MyDatabaseHelper(this);
        initNavigator();
    }

    private void initNavigator(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        navigateView(new ShowListTrip());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.page_1:
                    navigateView(new ShowListTrip());
                    break;

                case R.id.page_2:
                    navigateView(new SearchActivity());
                    break;
//                case R.id.page_3:
//                    navigateView(new UploadFragment());
//                    break;
            }

            return true;
        });
    }

    private void navigateView(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_main,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater_main = getMenuInflater();
        inflater_main.inflate(R.menu.menu_delete_all,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            try {
                TripSQLite tripSQLite1 = new TripSQLite(sqLiteOpenHelper);
                tripSQLite1.deleteAllTrip();
                recreate();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this, "Cant delete", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
}