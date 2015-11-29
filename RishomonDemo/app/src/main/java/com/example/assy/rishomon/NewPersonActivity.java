package com.example.assy.rishomon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assy.rishomon.tools.GlobalConstants;

import java.sql.SQLData;
import java.util.HashMap;
import java.util.Map;

public class NewPersonActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout mainLayout;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputPhone;
    EditText inputAdress;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mainLayout.setOnClickListener(this);
        inputFirstName = (EditText)findViewById(R.id.inputFristName);
        inputLastName = (EditText)findViewById(R.id.inputLastName);
        inputPhone = (EditText)findViewById(R.id.inputPhone);
        inputAdress = (EditText)findViewById(R.id.inputAdress);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_person, menu);
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
        if(v==btnOk)
        {

            sqlInsertData();
            ApiInsertData();


            Log.i("btnList","new");
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(v==mainLayout)
        {
            InputMethodManager method = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            method.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }



    public void sqlInsertData()
    {
        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS person(id INTEGER PRIMARY KEY AUTOINCREMENT,firstName VARCHAR,lastName VARCHAR,phone VARCHAR,adress VARCHAR)");

            myDatabase.execSQL("INSERT INTO person(firstName,lastName,phone,adress) VALUES (" + "'" + inputFirstName.getText() + "'," + "'" +  inputLastName.getText()+ "'," + "'" +
                    inputPhone.getText() + "'," + "'" +  inputAdress.getText()+ "'" +")");



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ApiInsertData()
    {




        final String firstName = inputFirstName.getText().toString().trim();
        final String lastName = inputLastName.getText().toString().trim();
        final String phone = inputPhone.getText().toString().trim();
        final String adress = inputAdress.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_INSERT_PERSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NewPersonActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewPersonActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_FIRST_NAME,firstName);
                params.put(GlobalConstants.KEY_LAST_NAME,lastName);
                params.put(GlobalConstants.KEY_PHONE, phone);
                params.put(GlobalConstants.KEY_ADRESS, adress);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

