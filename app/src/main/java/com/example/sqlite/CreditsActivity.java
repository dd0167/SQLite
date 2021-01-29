package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void click(View view) {
        finish();
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