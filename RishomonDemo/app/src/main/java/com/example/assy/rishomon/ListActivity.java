package com.example.assy.rishomon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.Map;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {


    public JSONArray users = null;

    String tempFirstName;
    String tempLastName;
    String tempName;

    // List view
     ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    ImageButton btnBack;
    ArrayList<Person> listPersons = new ArrayList<Person>();
    ArrayList<String> ValuesList = new ArrayList<String>();



    private ProgressDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("רשימות");

        btnBack=(ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sql users getData
        //connectDataPerson();

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        ApiReadData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
    public void onClick(View view) {
        if(view==btnBack)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }


    public void ApiReadData(){

        StringRequest stringRequest = new StringRequest(GlobalConstants.JSON_URL_READ_PERSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJsonPerson(response);
                        hidePDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        hidePDialog();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void parseJsonPerson(String json){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            Log.i("asi","asi");
            users = jsonObject.getJSONArray(GlobalConstants.JSON_ARRAY_PERSON);

            for(int i=0;i<users.length();i++){

                JSONObject jo = users.getJSONObject(i);
                listPersons.add(new Person(Integer.parseInt(jo.getString(GlobalConstants.KEY_ID)),jo.getString(GlobalConstants.KEY_FIRST_NAME),jo.getString(GlobalConstants.KEY_LAST_NAME),jo.getString(GlobalConstants.KEY_PHONE),jo.getString(GlobalConstants.KEY_ADRESS)));

            }

            //values to arr
            String[] Values = new String[listPersons.size()];

            for(int i=0;i<listPersons.size();i++)
            {
                Values[i] = listPersons.get(i).getFirstName() + " " + listPersons.get(i).getLastName();
            }

            lv = (ListView) findViewById(R.id.list_view);
            inputSearch = (EditText) findViewById(R.id.inputSearch);


            ValuesList.addAll( Arrays.asList(Values) );


            // Adding items to listview
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, ValuesList);
            lv.setAdapter(adapter);

            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    ListActivity.this.adapter.getFilter().filter(cs);
                }


                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setBackgroundColor(Color.parseColor("#C6F4FF"));
                    Object listItem = lv.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(), InPersonListActivity.class);
                    i.putExtra("value", listItem.toString());
                    startActivity(i);
                }
            });

            //listView clickLong
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int pos, long id) {
                    // TODO Auto-generated method stub

                    arg1.setBackgroundColor(Color.parseColor("#C6F4FF"));

                    new AlertDialog.Builder(ListActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("אתה בטוח!")
                            .setMessage("אתה בטוח שאתה רוצה למחוק?")
                            .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    tempFirstName = listPersons.get(pos).getFirstName();
                                    tempLastName = listPersons.get(pos).getLastName();
                                    tempName = tempFirstName + " " + tempLastName;

                                    ApiDeletePerson(tempFirstName,tempLastName,tempName);

                                    ValuesList.remove(pos);
                                    listPersons.remove(pos);

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

    public void ApiDeletePerson(final String firstName,final String lastName,final String name)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_DELETE_PERSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ListActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_FIRST_NAME,firstName);
                params.put(GlobalConstants.KEY_LAST_NAME,lastName);
                params.put(GlobalConstants.KEY_NAME,name);


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




    //connect to sqlite backup!!!
    public void connectDataPerson()
    {
        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS person(id INTEGER PRIMARY KEY AUTOINCREMENT,firstName VARCHAR,lastName VARCHAR,phone VARCHAR,adress VARCHAR)");

            Cursor c = myDatabase.rawQuery("SELECT * FROM person",null);

            int idInbex = c.getColumnIndex("id");
            int firstNameIndex = c.getColumnIndex("firstName");
            int lastNameIndex = c.getColumnIndex("lastName");
            int phoneIndex = c.getColumnIndex("phone");
            int adressIndex = c.getColumnIndex("adress");

            c.moveToFirst();

            while (c!=null){

                listPersons.add(new Person(c.getInt(idInbex),c.getString(firstNameIndex), c.getString(lastNameIndex), c.getString(phoneIndex), c.getString(adressIndex)));

                c.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }






}







