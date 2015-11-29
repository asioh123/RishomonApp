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
import android.widget.Button;
import android.widget.EditText;

import com.example.assy.rishomon.classSql.Person;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    String activeUser;
    EditText inputItem;
    EditText inputPrice;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        inputItem=(EditText)findViewById(R.id.inputItem);
        inputPrice=(EditText)findViewById(R.id.inputPrice);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        Intent i = getIntent();
        activeUser = i.getStringExtra("value");
        setTitle(activeUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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

    public void connectDataListItem()
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

            Cursor c = myDatabase.rawQuery("SELECT * FROM listitem", null);

            int idInbex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");
            int itemIndex = c.getColumnIndex("item");
            int priceIndex = c.getColumnIndex("price");

            c.moveToFirst();

            while (c!=null){

                Log.i("enter",c.getString(nameIndex));
                Log.i("enter2",Float.toString(c.getFloat(priceIndex)));

                c.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if(v==btnAdd)
        {
            connectDataListItem();
            Intent i = new Intent(getApplicationContext(), InPersonListActivity.class);
            i.putExtra("value", activeUser.toString());
            startActivity(i);
        }
    }
}
