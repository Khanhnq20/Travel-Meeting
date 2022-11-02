package com.example.travelfake.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TripSQLite {
    private  SQLiteOpenHelper sqLiteOpenHelper;
    public   static final String TABLE_NAME = "trip_management";
    public   static final String COLUMN_ID = "_id";
    public   static final String COLUMN_NAME = "trip_name";
    public   static final String COLUMN_DESTINATION = "trip_destination";
    public   static final String COLUMN_DATE = "trip_date";
    public   static final String COLUMN_REQUIRE = "trip_require";
    public   static final String COLUMN_DESCRIPTION = "trip_description";
    public TripSQLite(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public static void CreateTable(SQLiteDatabase sqLiteOpenHelper){
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESTINATION + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_REQUIRE + " BOOLEAN);";
        sqLiteOpenHelper.execSQL(query);
    }

    public static void DropTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long addTrip(String name,String destination, String dateOfTrip
            ,Boolean require,String description) throws Exception {
        if(this.sqLiteOpenHelper == null) throw new Exception("Cannot open db");
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_DESTINATION,destination);
        cv.put(COLUMN_DATE,dateOfTrip);
        cv.put(COLUMN_REQUIRE,require);
        cv.put(COLUMN_DESCRIPTION,description);
        long result= db.insertOrThrow(TABLE_NAME, null, cv);
        if(result == -1){
            throw new Exception("Cant add to Database");
        }
        return result;
    }
    public Cursor readData() throws Exception {
        if(this.sqLiteOpenHelper == null) throw new Exception("Cannot read database");
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        if(database != null){
            cursor = database.rawQuery(query,null);
        }
        System.out.println(cursor);
        return cursor;
    }

    public void updateTrip(String trip_id,String trip_name
            ,String trip_destination
            ,String trip_date, Boolean trip_require
            ,String trip_description) throws Exception{
        SQLiteDatabase db = this.sqLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,trip_name);
        cv.put(COLUMN_DESTINATION,trip_destination);
        cv.put(COLUMN_DATE,trip_date);
        cv.put(COLUMN_REQUIRE,trip_require);
        cv.put(COLUMN_DESCRIPTION,trip_description);

        long result = db.update(TABLE_NAME,cv
                ,COLUMN_ID + "=" + trip_id,null);
        if(result == -1){
            throw new Exception("Cant add to Database");
        }
    }
    public void deleteOneRow(String id) throws Exception{
        try{
            SQLiteDatabase db = this.sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + " = " + id, null);
            if(cursor.getCount() < 0){
                throw  new Exception("Not Find Item");
            }
            long result = db.delete(TABLE_NAME,"_id=?", new String[]{id});

            if(result == -1){
                throw new Exception("Cant delete from Database");
            }
        }
        catch(Exception e){
            throw e;
        }
    }
    public void deleteAllTrip() throws Exception{
        SQLiteDatabase db = this.sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    public Cursor searchTrip(String text) throws Exception{
        try{
            String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_NAME +" LIKE " +"\'%" +text+ "%\'";
            SQLiteDatabase database = this.sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(query,null);
            return cursor;
        }catch (Exception e){
            throw e;
        }
    }
}
