package com.example.assy.rishomon;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ReportsActivity  extends AppCompatActivity implements View.OnClickListener {

    Button btnReportsDay;
    Button btnReportsAll;
    Button btnReportsPay;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        btnReportsDay = (Button) findViewById(R.id.btnReportsDay);
        btnReportsAll = (Button) findViewById(R.id.btnReportsAll);
        btnReportsPay = (Button) findViewById(R.id.btnReportsPay);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnReportsDay.setOnClickListener(this);
        btnReportsAll.setOnClickListener(this);
        btnReportsPay.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);
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
        if (v == btnReportsDay) {
            Log.i("btnList", "new");
            Intent intent = new Intent(getApplicationContext(), ReportDayActivity.class);
            startActivity(intent);
        } else if (v == btnReportsAll) {
            Log.i("btnNewFile", "new");
            Intent intent = new Intent(getApplicationContext(), ReportsAllActivity.class);
            startActivity(intent);
        } else if (v == btnReportsPay) {
            Log.i("btnNewFile", "new");
            Intent intent = new Intent(getApplicationContext(), ReportPayActivity.class);
            startActivity(intent);
        } else if (v == btnBack) {
            Log.i("btnNewFile", "new");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
