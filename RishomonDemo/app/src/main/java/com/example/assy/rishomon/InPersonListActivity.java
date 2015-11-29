package com.example.assy.rishomon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assy.rishomon.classSql.Item;
import com.example.assy.rishomon.classSql.Person;
import com.example.assy.rishomon.tools.GlobalConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InPersonListActivity extends AppCompatActivity implements View.OnClickListener {



    public JSONArray items = null;
    Boolean deleteChack=false;


    ProgressDialog pDialog;
    ImageButton btnAdd;
    ImageButton btnBack;
    String activeUser;
    Float total=0f;
    TextView txtTottal;
    int tempSqlItemId;
    float tempSqlItemPrice;


    ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    ArrayList<Item> listItems = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_person_list);

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize activity
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            Intent intent = new Intent(getApplicationContext(),AddMoneyActivity.class);
            intent.putExtra("value", activeUser.toString());
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==btnAdd)
        {
            Log.i("btnAdd","new");
            Intent intent = new Intent(getApplicationContext(),ItemsActivity.class);
            intent.putExtra("value", activeUser.toString());
            startActivity(intent);
        }
        else if(v==btnBack)
        {
            Log.i("btnAdd", "new");
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            startActivity(intent);
        }
    }



    public void ApiReadData()
    {

        final String name = activeUser.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_READ_LIST_ITEM,
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
                        Toast.makeText(InPersonListActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        hidePDialog();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_NAME,name);
                return params;
            }

        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parseJsonlistItem(String json){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            Log.i("asi","asi");
            items = jsonObject.getJSONArray(GlobalConstants.JSON_ARRAY_LIST_ITEM);

            for(int i=0;i<items.length();i++){

                JSONObject jo = items.getJSONObject(i);
                listItems.add(new Item(Integer.parseInt(jo.getString(GlobalConstants.KEY_ID)),jo.getString(GlobalConstants.KEY_NAME), jo.getString(GlobalConstants.KEY_DATE),jo.getString(GlobalConstants.KEY_ITEM), Float.parseFloat(jo.getString(GlobalConstants.KEY_PRICE))));
                total+=(Float.parseFloat(jo.getString(GlobalConstants.KEY_PRICE)));
            }

            //values to arr
            String[] Values = new String[listItems.size()];
            for(int index=0;index<listItems.size();index++)
            {
                Values[index] =  "מוצר: " + listItems.get(index).getItem() + "\n" + "מחיר: " + listItems.get(index).getPrice() + "\n" + "תאריך: "+listItems.get(index).getDate();
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
            } else{
                txtTottal.setText(Float.toString(total));
                txtTottal.setTextColor(Color.parseColor("#00FF00"));
            }

            //listView clickLong
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int pos, long id) {
                    // TODO Auto-generated method stub

                    arg1.setBackgroundColor(Color.parseColor("#C6F4FF"));

                    new AlertDialog.Builder(InPersonListActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("אתה בטוח!")
                            .setMessage("אתה בטוח שאתה רוצה למחוק?")
                            .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    tempSqlItemId = listItems.get(pos).getId();
                                    tempSqlItemPrice = listItems.get(pos).getPrice();

                                    ApiDeleteDataListItem(String.valueOf(tempSqlItemId));

                                    if (deleteChack = true) {
                                        deleteDataItems();
                                    }


                                    ValuesList.remove(pos);
                                    listItems.remove(pos);
                                    total -= tempSqlItemPrice;

                                    if (total < 0) {
                                        txtTottal.setText(Float.toString(total));
                                        txtTottal.setTextColor(getResources().getColor(R.color.red));
                                    } else {
                                        txtTottal.setText(Float.toString(total));
                                        txtTottal.setTextColor(Color.parseColor("#00E235"));
                                    }

                                    lv.setAdapter(adapter);
                                    Log.v("long clicked", "pos: " + pos);

                                }
                            })
                            .setNegativeButton("לא", null)
                            .show();

                    return true;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void ApiDeleteDataListItem(final String itemId)
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());




        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_DELETE_LIST_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        deleteChack=true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        deleteChack=false;
                        Toast.makeText(InPersonListActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_ID,itemId);

                return params;
            }

        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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








///////////////////////////////////////////////////// SQLITE BACKUP ///////////////////////////////////////////////////////////////////////////////
    //sql function
    public void deleteDataItems()
    {
        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS listitem(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,date VARCHAR,item VARCHAR,price FLOAT)");

            Cursor c = myDatabase.rawQuery("DELETE FROM listitem WHERE id = '" + tempSqlItemId + "'", null);

            c.moveToFirst();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //sql function
    public void connectDataItems()
    {
        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS listitem(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,date VARCHAR,item VARCHAR,price FLOAT)");

            Cursor c = myDatabase.rawQuery("SELECT * FROM listitem WHERE TRIM(name) = '"+activeUser.trim()+"' ORDER BY id DESC", null);

            int idInbex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");
            int itemIndex = c.getColumnIndex("item");
            int priceIndex = c.getColumnIndex("price");

            c.moveToFirst();

            while (c!=null){

                listItems.add(new Item(c.getInt(idInbex),c.getString(nameIndex), c.getString(dateIndex), c.getString(itemIndex), c.getFloat(priceIndex)));
                total+=(c.getFloat(priceIndex));
                c.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
