package com.example.assy.rishomon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assy.rishomon.classSql.Item;
import com.example.assy.rishomon.tools.GlobalConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReportsAllActivity extends AppCompatActivity implements View.OnClickListener {




    public JSONArray items = null;



    ProgressDialog pDialog;
    ImageButton btnBack;
    String activeUser;
    Float total = 0f;
    TextView txtTottal;



    ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    ArrayList<Item> listItems = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_all);

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize activity
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.listViewInPerson);
        txtTottal = (TextView) findViewById(R.id.txtTotal);

        //get value from ActivityList
        Intent i = getIntent();
        activeUser = i.getStringExtra("value");
        setTitle(activeUser);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        //sql users getData
        //connectDataItems();
        ApiReadData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_person_list, menu);
        return true;
    }


    @Override
    public void onClick(View v) {

        if (v == btnBack) {
            Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
            startActivity(intent);
        }
    }


    public void ApiReadData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_READ_REPORTS_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJsonlistItem(response);
                        hidePDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportsAllActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        hidePDialog();
                    }
                }){
        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void parseJsonlistItem(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            Log.i("asi", "asi");
            items = jsonObject.getJSONArray(GlobalConstants.JSON_ARRAY_LIST_ITEM);

            for (int i = 0; i < items.length(); i++) {

                JSONObject jo = items.getJSONObject(i);
                listItems.add(new Item(Integer.parseInt(jo.getString(GlobalConstants.KEY_ID)), jo.getString(GlobalConstants.KEY_NAME), jo.getString(GlobalConstants.KEY_DATE), jo.getString(GlobalConstants.KEY_ITEM), Float.parseFloat(jo.getString(GlobalConstants.KEY_PRICE))));
                total += (Float.parseFloat(jo.getString(GlobalConstants.KEY_PRICE)));
            }

            //values to arr
            String[] Values = new String[listItems.size()];
            for (int index = 0; index < listItems.size(); index++) {
                Values[index] = "מוצר: " + listItems.get(index).getItem() + "\n" + "מחיר: " + listItems.get(index).getPrice() + "\n" + "תאריך: " + listItems.get(index).getDate();
            }
            final ArrayList<String> ValuesList = new ArrayList<String>();
            ValuesList.addAll(Arrays.asList(Values));


            // Adding items to listview
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, ValuesList);
            lv.setAdapter(adapter);

            //set the color text of txtTotal
            if (total < 0) {
                txtTottal.setText(Float.toString(total));
                txtTottal.setTextColor(getResources().getColor(R.color.red));
            } else {
                txtTottal.setText(Float.toString(total));
                txtTottal.setTextColor(Color.parseColor("#00FF00"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }
}