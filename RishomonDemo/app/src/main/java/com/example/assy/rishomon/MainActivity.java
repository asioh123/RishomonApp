package com.example.assy.rishomon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.assy.rishomon.classSql.Person;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnList;
    Button btnReports;
    Button btnNewFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnList = (Button)findViewById(R.id.btnList);
        btnReports = (Button)findViewById(R.id.btnReports);
        btnNewFile = (Button)findViewById(R.id.btnNewfile);

        btnList.setOnClickListener(this);
        btnReports.setOnClickListener(this);
        btnNewFile.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==btnList)
        {
            Log.i("btnList","new");
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            startActivity(intent);
        }
        else if(v==btnNewFile)
        {
            Log.i("btnNewFile","new");
            Intent intent = new Intent(getApplicationContext(),NewPersonActivity.class);
            startActivity(intent);
        }
        else if (v==btnReports)
        {
            Log.i("btnNewFile","new");
            Intent intent = new Intent(getApplicationContext(),ReportsActivity.class);
            startActivity(intent);
        }

    }

    public void deleteTable()
    {
        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person",MODE_PRIVATE,null);

            myDatabase.execSQL("DROP TABLE listitem");



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
