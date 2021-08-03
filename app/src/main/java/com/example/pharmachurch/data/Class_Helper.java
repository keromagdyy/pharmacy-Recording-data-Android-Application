package com.example.pharmachurch.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pharmachurch.data.contantclass.*;


public class Class_Helper extends SQLiteOpenHelper {

    private static final String DBName = "PhChurch.db";

    public Class_Helper(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREAT_PATIENT_TABLE = "CREATE TABLE " + patienttable.TABLE_NAME + " (" +
                patienttable.COLUMN_PATIENT_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                patienttable.COLUMN_PATIENT_NAME + " TEXT NOT NULL);";

        String SQL_CREAT_MEDICINE_TABLE = "CREATE TABLE " + medicinetable.TABLE_NAME + " (" +
                medicinetable.COLUMN_MEDICINE_NAME + " TEXT NOT NULL, " +
                medicinetable.COLUMN_MEDICINE_DAY + " TEXT, "+
                medicinetable.COLUMN_MEDICINE_DATE + " DATE, " +
                medicinetable.COLUMN_MEDICINE_TIME + " DATETIME, " +
                medicinetable.COLUMN_MEDICINE_PATID_FOR + " INTEGER, " +
                medicinetable.COLUMN_MEDICINE_PATNAME_FOR + " TEXT, " +
                "FOREIGN KEY ("+medicinetable.COLUMN_MEDICINE_PATID_FOR+") REFERENCES "+patienttable.TABLE_NAME+"("+patienttable.COLUMN_PATIENT_CODE+")," +
                "FOREIGN KEY ("+medicinetable.COLUMN_MEDICINE_PATNAME_FOR+") REFERENCES "+patienttable.TABLE_NAME+"("+patienttable.COLUMN_PATIENT_NAME+")  );";


        db.execSQL(SQL_CREAT_PATIENT_TABLE);
        db.execSQL(SQL_CREAT_MEDICINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
