package com.example.assy.rishomon.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assy.rishomon.InPersonListActivity;
import com.example.assy.rishomon.ItemsActivity;
import com.example.assy.rishomon.R;
import com.example.assy.rishomon.tools.CustomGrid;
import com.example.assy.rishomon.tools.GlobalConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by assy on 26/11/2015.
 */
public class SalesFragment extends Fragment implements View.OnClickListener {

    String activeUser;

    GridView grid;
    String[] web = {

            "פיצה+ברד גדול",
            "פיצה+ברד קטן",
            "פיצה+טרופית",
            "לחם שום+3",
            "2 מגשים L",
            "2 מגשים XL",


    } ;
    int[] imageId = {
            R.drawable.work,
            R.drawable.work,
            R.drawable.work,
            R.drawable.work,
            R.drawable.work,
            R.drawable.work,

    };
    String[] price = {
            "14",
            "12",
            "11",
            "12",
            "82",
            "110",

    };
    int Total=0;

    TextView totalPrice;
    Button btnOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sales_fragment, container, false);


        ItemsActivity activity = (ItemsActivity)getActivity();
        activeUser = activity.getMyData();
        btnOk=(Button)v.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);


        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId,price);
        grid=(GridView)v.findViewById(R.id.grid);
        totalPrice=(TextView)v.findViewById(R.id.total_price);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), web[+position] + " " + price[position], Toast.LENGTH_SHORT).show();

                // database
                ApiInsertDataListItem(web[position], price[position]);

                // sqlite
                SqlInsertDataListItem(web[position], price[position]);


                Total -= Integer.parseInt(price[position]);
                totalPrice.setText(String.valueOf(Total));
            }
        });

        return v;


    }

    @Override
    public void onClick(View view) {
        if(view==btnOk)
        {
            Intent i = new Intent(getActivity(), InPersonListActivity.class);
            i.putExtra("value", activeUser);
            startActivity(i);
        }
    }


    public void SqlInsertDataListItem(String inputItem , String inputPrice )
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());


        try{
            SQLiteDatabase myDatabase = getActivity().openOrCreateDatabase("Person", android.content.Context.MODE_PRIVATE ,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS listitem(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,date VARCHAR,item VARCHAR,price FLOAT)");

            myDatabase.execSQL("INSERT INTO listitem(name,date,item,price) VALUES (" + "'" + activeUser + "'," + "'" +  formattedDate+ "',"  + "'" +  inputItem+ "'," + "'" +  "-"+inputPrice+ "'" +")");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ApiInsertDataListItem(final String inputItem , final String inputPrice)
    {
        //get date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        final String name = activeUser.trim();
        final String date = formattedDate;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.JSON_URL_LIST_ITEM_FRAG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(GlobalConstants.KEY_NAME,name);
                params.put(GlobalConstants.KEY_DATE,date);
                params.put(GlobalConstants.KEY_ITEM,inputItem);
                params.put(GlobalConstants.KEY_PRICE,inputPrice);
                return params;
            }

        };



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
