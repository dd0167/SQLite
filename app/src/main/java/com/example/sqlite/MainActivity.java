package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * The type Main activity.
 *
 * @author Dean David <dd0167@bs.amalnet.k12.il>
 * @version 1.0
 * @since 29/01/2021
 */
public class MainActivity extends AppCompatActivity {

    EditText et_name;
    Switch sw_active;
    EditText et_address;
    EditText et_phone_mobile;
    EditText et_phone_home;
    EditText et_name_mother;
    EditText et_phone_mother;
    EditText et_name_father;
    EditText et_phone_father;
    EditText et_subject;
    EditText et_quarter;
    EditText et_grade;
    SQLiteDatabase db;
    HelperDB hlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name=(EditText) findViewById(R.id.et_name);
        sw_active=(Switch) findViewById(R.id.sw_active);
        et_address=(EditText) findViewById(R.id.et_address);
        et_phone_mobile=(EditText) findViewById(R.id.et_phone_mobile);
        et_phone_home=(EditText) findViewById(R.id.et_phone_home);
        et_name_mother=(EditText) findViewById(R.id.et_name_mother);
        et_phone_mother=(EditText) findViewById(R.id.et_phone_mother);
        et_name_father=(EditText) findViewById(R.id.et_name_father);
        et_phone_father=(EditText) findViewById(R.id.et_phone_father);
        et_subject=(EditText) findViewById(R.id.et_subject);
        et_quarter=(EditText) findViewById(R.id.et_quarter);
        et_grade=(EditText) findViewById(R.id.et_grade);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
    }

    /**
     * Studentsdata.
     * Keeps all information about the student
     * @param view the view
     */
    public void studentsdata(View view) {
        String name, address, name_mother, name_father, sphone_mobile, sphone_home, sphone_mother, sphone_father, active;
        int phone_mobile, phone_home, phone_mother, phone_father;

        name = et_name.getText().toString();
        address = et_address.getText().toString();
        name_mother = et_name_mother.getText().toString();
        name_father = et_name_father.getText().toString();
        sphone_mobile = et_phone_mobile.getText().toString();
        sphone_home = et_phone_home.getText().toString();
        sphone_mother = et_phone_mother.getText().toString();
        sphone_father = et_phone_father.getText().toString();
        phone_mobile = Integer.parseInt(sphone_mobile);
        phone_home = Integer.parseInt(sphone_home);
        phone_mother = Integer.parseInt(sphone_mother);
        phone_father = Integer.parseInt(sphone_father);
        if (sw_active.isChecked()) {
            active = "True";
        }
        else active = "False";
        if (name.equals("") || address.equals("") || name_mother.equals("") || name_father.equals("") || active.equals(""))
        {
            Toast.makeText(this, "Enter all the required information!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put(Students.NAME, name);
            cv.put(Students.ADDRESS, address);
            cv.put(Students.NAME_MOTHER, name_mother);
            cv.put(Students.NAME_FATHER, name_father);
            cv.put(Students.ACTIVE, active);
            cv.put(Students.MOBILE_PHONE, phone_mobile);
            cv.put(Students.HOME_PHONE, phone_home);
            cv.put(Students.MOTHER_PHONE, phone_mother);
            cv.put(Students.FATHER_PHONE, phone_father);

            db = hlp.getWritableDatabase();
            db.insert(Students.TABLE_STUDENTS, null, cv);
            db.close();

            et_name.setText("");
            sw_active.setText("");
            et_address.setText("");
            et_phone_mobile.setText("");
            et_phone_home.setText("");
            et_name_mother.setText("");
            et_phone_mother.setText("");
            et_name_father.setText("");
            et_phone_father.setText("");
        }
    }

    /**
     * Gradesdata.
     * Keeps all information about the grades
     * @param view the view
     */
    public void gradesdata(View view) {
        String subject,quarter,sgrade;
        int grade;

        subject=et_subject.getText().toString();
        quarter=et_quarter.getText().toString();
        sgrade=et_grade.getText().toString();
        grade= Integer.parseInt(sgrade);

        if (subject.equals("") || quarter.equals("") || sgrade.equals(""))
        {
            Toast.makeText(this, "Enter all the required information!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put(Grades.SUBJECT, subject);
            cv.put(Grades.QUARTER, quarter);
            cv.put(Grades.GRADE, grade);

            db = hlp.getWritableDatabase();
            db.insert(Grades.TABLE_GRADES, null, cv);
            db.close();

            et_subject.setText("");
            et_quarter.setText("");
            et_grade.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        String st=item.getTitle().toString();
        if (st.equals("Data In"))
        {
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if (st.equals("Watch Tables"))
        {
            Intent in=new Intent(this,Watchtables.class);
            startActivity(in);
        }
        else if (st.equals("Sort Tables"))
        {
            Intent in=new Intent(this,Sorttables.class);
            startActivity(in);
        }
        else if (st.equals("Credits"))
        {
            Intent in=new Intent(this,CreditsActivity.class);
            startActivity(in);
        }
        return true;
    }
}