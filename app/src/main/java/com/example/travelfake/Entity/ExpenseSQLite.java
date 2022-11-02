package com.example.travelfake.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseSQLite {
    private SQLiteOpenHelper sqLiteOpenHelper;
    public   static final String TABLE_NAME = "my_expense";
    public   static final String COLUMN_ID = "expense_id";
    public   static final String COLUMN_TYPE = "expense_type";
    public   static final String COLUMN_AMOUNT = "expense_amount";
    public   static final String COLUMN_DATE = "expense_timeOfExpense";
    public static final String COLUMN_TRIP_ID = "trip_id";

    public ExpenseSQLite(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public static void CreateTable(SQLiteDatabase sqLiteOpenHelper){
        String query =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYPE + " TEXT, " +
                    COLUMN_AMOUNT + " DOUBLE, " +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TRIP_ID + " INTEGER, " +
                    "FOREIGN KEY("+ COLUMN_TRIP_ID +") REFERENCES "
                    + TripSQLite.TABLE_NAME +
                    "(" + TripSQLite.COLUMN_ID + ") ON DELETE CASCADE" + ");";
        sqLiteOpenHelper.execSQL(query);
    }
    public static void DropTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long addExpense(String type,String amount, String dateOfExpense, int tripId) throws Exception {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TYPE,type);
        cv.put(COLUMN_AMOUNT,amount);
        cv.put(COLUMN_DATE,dateOfExpense);
        cv.put(COLUMN_TRIP_ID, tripId);
        long result= db.insertOrThrow(TABLE_NAME,null,cv);
        if(result == -1){
            throw new Exception("Cant add to Database");
        }
        return result;
    }

    public Cursor readData(int tripId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TRIP_ID + " = " + tripId;

        try {
            Cursor cursor = this.sqLiteOpenHelper.getReadableDatabase().rawQuery(query, null);
            return cursor;
        }
        catch(Exception e){
            return null;
        }
    }

}
