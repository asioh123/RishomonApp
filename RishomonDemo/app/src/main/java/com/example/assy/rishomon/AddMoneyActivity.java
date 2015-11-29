package com.example.assy.rishomon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assy.rishomon.tools.GlobalConstants;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener {


    public JSONArray items = null;

    String activeUser;
    TextView inputItem;
    EditText inputReceipt;
    EditText inputPrice;
    Button btnAdd;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        inputItem=(TextView)findViewById(R.id.inputItem);
        inputPrice=(EditText)findViewById(R.id.inputPrice);
        inputReceipt=(EditText)findViewById(R.id.inputReceipt);
        btnAdd=(Button)findViewById(R.id.btnOk);
        btnAdd.setOnClickListener(this);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        Intent i = getIntent();
        activeUser = i.getStringExtra("value");
        setTitle(activeUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_money, menu);
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
        if(v==btnAdd)
        {

            //check if empty
            if(inputPrice.getText().toString().trim().length()!= 0)
            {
                //sqlite backup
                SqlAddDataListItem();

                //database backup
                ApiInsertDataListItem();
            }

            //check if empty
            if(inputReceipt.getText().toString().trim().length()!= 0)
            {
                //sqlite backup
                SqlPayDataListItem();

                //database backup
                ApiInsertDataPaidItem();
            }

            Intent i = new Intent(getApplicationContext(), InPersonListActivity.class);
            i.putExtra("value", activeUser.toString());
            startActivity(i);
        }
        else if(v==btnBack)
        {
            Intent i = new Intent(getApplicationContext(), InPersonListActivity.class);
            i.putExtra("value", activeUser.toString());
            startActivity(i);
        }
    }

    public void ApiInsertDataListItem()
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        final String name = activeUser.trim();
        final String item = inputItem.getText().toString();
        final String date = formattedDate;
        final String price = inputPrice.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_INSERT_LIST_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddMoneyActivity.this, "שולם", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddMoneyActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_NAME,name);
                params.put(GlobalConstants.KEY_DATE,date);
                params.put(GlobalConstants.KEY_ITEM,item);
                params.put(GlobalConstants.KEY_PRICE,price);
                return params;
            }

        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void ApiInsertDataPaidItem()
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        final String name = activeUser.trim();
        final String kod = inputReceipt.getText().toString();
        final String date = formattedDate;
        final String price = inputPrice.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_INSERT_PAID_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddMoneyActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_NAME,name);
                params.put(GlobalConstants.KEY_DATE,date);
                params.put(GlobalConstants.KEY_KOD,kod);
                params.put(GlobalConstants.KEY_PRICE,price);
                return params;
            }

        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }











//////////////////////////////////////////////////////////////SQLITE BACKUP/////////////////////////////////////////////////////////////////

    public void SqlAddDataListItem()
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());
        Log.i("thisDate", formattedDate);

        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS listitem(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,date VARCHAR,item VARCHAR,price FLOAT)");

            myDatabase.execSQL("INSERT INTO listitem(name,date,item,price) VALUES (" + "'" + activeUser + "'," + "'" +  formattedDate+ "',"  + "'" +  inputItem.getText()+ "'," + "'" +  inputPrice.getText()+ "'" +")");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void SqlPayDataListItem()
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());


        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Person",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS paid(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,date VARCHAR,kod VARCHAR,price FLOAT)");

            myDatabase.execSQL("INSERT INTO paid(name,date,kod,price) VALUES (" + "'" + activeUser + "'," + "'" +  formattedDate+ "',"  + "'" +  inputReceipt.getText()+ "'," + "'" +  inputPrice.getText()+ "'" +")");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
