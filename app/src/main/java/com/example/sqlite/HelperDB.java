package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.sqlite.Grades.GRADE;
import static com.example.sqlite.Grades.QUARTER;
import static com.example.sqlite.Grades.SUBJECT;
import static com.example.sqlite.Grades.TABLE_GRADES;
import static com.example.sqlite.Students.ACTIVE;
import static com.example.sqlite.Students.ADDRESS;
import static com.example.sqlite.Students.FATHER_PHONE;
import static com.example.sqlite.Students.HOME_PHONE;
import static com.example.sqlite.Students.KEY_ID;
import static com.example.sqlite.Students.MOBILE_PHONE;
import static com.example.sqlite.Students.MOTHER_PHONE;
import static com.example.sqlite.Students.NAME;
import static com.example.sqlite.Students.NAME_FATHER;
import static com.example.sqlite.Students.NAME_MOTHER;
import static com.example.sqlite.Students.TABLE_STUDENTS;

class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbsqlite.db";
    private static final int DATABASE_VERSION = 1;

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    String strCreate, strDelete;

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+TABLE_STUDENTS;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+ACTIVE+" BOOLEAN,";
        strCreate+=" "+ADDRESS+" TEXT,";
        strCreate+=" "+MOBILE_PHONE+" INTEGER,";
        strCreate+=" "+HOME_PHONE+" INTEGER,";
        strCreate+=" "+NAME_MOTHER+" TEXT,";
        strCreate+=" "+MOTHER_PHONE+" INTEGER,";
        strCreate+=" "+NAME_FATHER+" TEXT,";
        strCreate+=" "+FATHER_PHONE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_GRADES;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+SUBJECT+" TEXT,";
        strCreate+=" "+QUARTER+" TEXT,";
        strCreate+=" "+GRADE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
