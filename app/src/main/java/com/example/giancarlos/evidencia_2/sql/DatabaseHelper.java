package com.example.giancarlos.evidencia_2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.giancarlos.evidencia_2.sql.CurpContract;
import com.example.giancarlos.evidencia_2.model.Curp;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "CurpManager.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + CurpContract.CurpEntry.TABLE_NAME + " (" +
                CurpContract.CurpEntry.COLUMN_CURP_LASTNAME1 + " TEXT NOT NULL, "+
                CurpContract.CurpEntry.COLUMN_CURP_LASTNAME2 + " TEXT NOT NULL, " +
                CurpContract.CurpEntry.COLUMN_CURP_NAME + " TEXT NOT NULL, " +
                CurpContract.CurpEntry.COLUMN_CURP_BIRTHDAY + " TEXT NOT NULL, " +
                CurpContract.CurpEntry.COLUMN_CURP_GENDER + " TEXT NOT NULL ," +
                CurpContract.CurpEntry.COLUMN_CURP_STATE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + CurpContract.CurpEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);
    }

    //Method to create beneficiary records
    public void addBeneficiary(Curp curp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CurpContract.CurpEntry.COLUMN_CURP_LASTNAME1, curp.getprimerApellido());
        values.put(CurpContract.CurpEntry.COLUMN_CURP_LASTNAME2, curp.getsegundoApellido());
        values.put(CurpContract.CurpEntry.COLUMN_CURP_NAME, curp.getnombre());
        values.put(CurpContract.CurpEntry.COLUMN_CURP_BIRTHDAY, curp.getnacimiento());
        values.put(CurpContract.CurpEntry.COLUMN_CURP_GENDER, curp.getsexo());
        values.put(CurpContract.CurpEntry.COLUMN_CURP_STATE, curp.getentidadFederativa());

        db.insert(CurpContract.CurpEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String name) {

        // array of columns to fetch
        String[] columns = {
                CurpContract.CurpEntry.COLUMN_CURP_LASTNAME1
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = CurpContract.CurpEntry.COLUMN_CURP_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {name};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(CurpContract.CurpEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public List<Curp> getAllCurp() {
        // array of columns to fetch
        String[] columns = {
                CurpContract.CurpEntry.COLUMN_CURP_LASTNAME1,
                CurpContract.CurpEntry.COLUMN_CURP_LASTNAME2,
                CurpContract.CurpEntry.COLUMN_CURP_NAME,
                CurpContract.CurpEntry.COLUMN_CURP_BIRTHDAY,
                CurpContract.CurpEntry.COLUMN_CURP_GENDER,
                CurpContract.CurpEntry.COLUMN_CURP_STATE
        };
        // sorting orders
        String sortOrder =
                CurpContract.CurpEntry.COLUMN_CURP_NAME + " ASC";
        List<Curp> curpList = new ArrayList<Curp>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CurpContract.CurpEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Curp curp = new Curp();
                curp.setprimerApellido(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_LASTNAME1)));
                curp.setsegundoApellido(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_LASTNAME2)));
                curp.setnombre(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_NAME)));
                curp.setnacimiento(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_BIRTHDAY)));
                curp.setsexo(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_GENDER)));
                curp.setentidadFederativa(cursor.getString(cursor.getColumnIndex(CurpContract.CurpEntry.COLUMN_CURP_STATE)));
                // Adding user record to list
                curpList.add(curp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return curpList;
    }

}
