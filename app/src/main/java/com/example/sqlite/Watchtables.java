package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sqlite.Grades.KEY_ID_G;
import static com.example.sqlite.Grades.TABLE_GRADES;
import static com.example.sqlite.Students.KEY_ID;
import static com.example.sqlite.Students.TABLE_STUDENTS;

public class Watchtables extends AppCompatActivity implements AdapterView.OnItemClickListener{

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ListView lv_tables, lv_records;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;
    int table_choise;
    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchtables);

        lv_tables=(ListView)findViewById(R.id.lv_tables);
        lv_records=(ListView)findViewById(R.id.lv_records);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lv_tables.setOnItemClickListener(this);
        lv_tables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_records.setOnItemClickListener(this);
        lv_records.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        table_choise=0;

        String[] tables={TABLE_STUDENTS,TABLE_GRADES};
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lv_tables.setAdapter(adp);
    }

    /**
     * Displays the information according to the user's choice
     * @param view the view
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (parent == lv_tables) {
            tbl = new ArrayList<>();
            table_choise = position + 1;
            if (table_choise != 0) {
                db = hlp.getReadableDatabase();
                if (table_choise == 1) {
                    crsr = db.query(TABLE_STUDENTS, null, null, null, null, null, null);
                    int col1 = crsr.getColumnIndex(Students.KEY_ID);
                    int col2 = crsr.getColumnIndex(Students.NAME);
                    int col3 = crsr.getColumnIndex(Students.ADDRESS);
                    int col4 = crsr.getColumnIndex(Students.MOBILE_PHONE);
                    int col5 = crsr.getColumnIndex(Students.ACTIVE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String name = crsr.getString(col2);
                        String address = crsr.getString(col3);
                        int mobilephone = crsr.getInt(col4);
                        String active = crsr.getString(col5);
                        if (active.equals("True"))
                        {
                            String tmp = "" + key + ", " + name + ", " +address + ", " + mobilephone;
                            tbl.add(tmp);
                        }
                        crsr.moveToNext();
                    }
                } else {
                    crsr = db.query(TABLE_GRADES, null, null, null, null, null, null);
                    int col1 = crsr.getColumnIndex(Students.KEY_ID);
                    int col2 = crsr.getColumnIndex(Grades.SUBJECT);
                    int col3 = crsr.getColumnIndex(Grades.GRADE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String sub = crsr.getString(col2);
                        int gra = crsr.getInt(col3);
                        String tmp = "" + key + ", " + sub + ", " + gra;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                }
                crsr.close();
                db.close();
                adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                lv_records.setAdapter(adp);
            } else {
                Toast.makeText(this, "Choose table first !", Toast.LENGTH_LONG).show();
            }
        } else {
            String strtmp = tbl.get(position);
            adb = new AlertDialog.Builder(this);
            adb.setTitle("Are you sure ?");
            adb.setMessage("Are you sure you want to delete " + strtmp);
            adb.setPositiveButton("Yes !", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db = hlp.getWritableDatabase();
                    if (table_choise == 1) {
                        db.delete(TABLE_STUDENTS, KEY_ID+"=?", new String[]{Integer.toString(position + 1)});
                    } else {
                        db.delete(TABLE_GRADES, KEY_ID+"=?", new String[]{Integer.toString(position + 1)});
                    }
                    db.close();
                    tbl.remove(position);
                    adp.notifyDataSetChanged();
                }
            });
            adb.setNeutralButton("Cancel !", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();
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