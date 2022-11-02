package com.example.travelfake.Entity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Trip {

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_destination() {
        return trip_destination;
    }

    public void setTrip_destination(String trip_destination) {
        this.trip_destination = trip_destination;
    }

    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getTrip_description() {
        return trip_description;
    }

    public void setTrip_description(String trip_description) {
        this.trip_description = trip_description;
    }

    public Boolean getTrip_require() {
        return trip_require;
    }

    public void setTrip_require(Boolean trip_require) {
        this.trip_require = trip_require;
    }

    private int trip_id;
    private String trip_name,trip_destination,trip_date,trip_description;
    private Boolean trip_require;


    public Trip(int trip_id, String trip_name, String trip_destination, String trip_date, String trip_description, Boolean trip_require) {
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.trip_destination = trip_destination;
        this.trip_date = trip_date;
        this.trip_description = trip_description;
        this.trip_require = trip_require;
    }
}