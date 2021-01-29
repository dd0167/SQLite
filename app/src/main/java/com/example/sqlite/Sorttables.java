package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sqlite.Grades.TABLE_GRADES;
import static com.example.sqlite.Students.TABLE_STUDENTS;

/**
 * The type Sorttables.
 */
public class Sorttables extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lvtables, lvfld2sort, lvorted;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter<String> adpData;
    int table;
    String[] tables, fields;
    String tbl2sort;
    String[] columns = null;
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorttables);

        lvtables=(ListView)findViewById(R.id.lvtables);
        lvfld2sort=(ListView)findViewById(R.id.lvfld2sort);
        lvorted=(ListView)findViewById(R.id.lvsorted);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lvtables.setOnItemClickListener(this);
        lvtables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvfld2sort.setOnItemClickListener(this);
        lvfld2sort.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        table=-1;

        tables= new String[]{TABLE_STUDENTS, TABLE_GRADES};
        ArrayAdapter<String> adpTables=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lvtables.setAdapter(adpTables);
    }


    /**
     * Displays the information sorted according to the user's choice
     * @param view the view
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lvtables.equals(parent)) {
            table = position;
            if (table==0) {
                tbl2sort= TABLE_STUDENTS;
                fields= new String[]{Students.KEY_ID, Students.NAME, Students.ADDRESS, Students.MOBILE_PHONE};
            } else {
                tbl2sort= TABLE_GRADES;
                fields= new String[]{Students.KEY_ID, Grades.SUBJECT, Grades.GRADE};
            }
            ArrayAdapter<String> adpFields=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fields);
            lvfld2sort.setAdapter(adpFields);
        } else if (lvfld2sort.equals(parent)) {
            if (table != -1) {
                tbl = new ArrayList<>();
                db=hlp.getReadableDatabase();
                orderBy=fields[position];
                if (table == 0) {
                    crsr=db.query(TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                    int col1 = crsr.getColumnIndex(Students.KEY_ID);
                    int col2 = crsr.getColumnIndex(Students.NAME);
                    int col3 = crsr.getColumnIndex(Students.ADDRESS);
                    int col4 = crsr.getColumnIndex(Students.MOBILE_PHONE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String name = crsr.getString(col2);
                        String address = crsr.getString(col3);
                        int mobile_phone = crsr.getInt(col4);
                        String tmp = "" + key + ", " + name + ", " + address + ", " + mobile_phone;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                } else {
                    crsr=db.query(TABLE_GRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
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
                adpData = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                lvorted.setAdapter(adpData);
            } else {
                Toast.makeText(this, "Choose table first !", Toast.LENGTH_LONG).show();
            }
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