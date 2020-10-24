package com.example.covidpoland;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


import org.json.JSONException;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    private String jsonResponse;

    private String urlJsonObj = "https://coronavirus-19-api.herokuapp.com/countries/Poland";

    private RequestQueue rq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);
        System.setProperty("http.keepAlive", "false");

        txt1 = (TextView) findViewById(R.id.txtView1);
        txt2 = (TextView) findViewById(R.id.txtView2);

        Button btn1 = (Button) findViewById(R.id.butQuit);
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Kończymy...",
                        Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
            }
        });


        Button btn2 = (Button) findViewById(R.id.butRef);
        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                GetData();
            }
        });

        GetData();
   }

    private void GetData()
    {
        rq = Volley.newRequestQueue(MainActivity.this);

        Toast.makeText(getApplicationContext(),
                "Pobieram dane...",
                Toast.LENGTH_LONG).show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("CovidPolandApp", response.toString());

                try {

                    String tdCases = response.getString("todayCases");
                    String tddeaths = response.getString("todayDeaths");
                    jsonResponse = "Swieże dane : " + response.getString("country") + "\n\n";
                    jsonResponse += "Dzisiaj przypadków : " + tdCases + "\n\n";
                    jsonResponse += "Dzisiaj umarło : " + tddeaths + "\n\n";
                    jsonResponse += "Ogółem przypadków : " + response.getString("cases") + "\n\n";
                    jsonResponse += "Ogółem zgonów : " + response.getString("deaths") + "\n\n";
                    jsonResponse += "Ilość w krytycznym stanie : " + response.getString("critical") + "\n\n";
                    txt1.setText(jsonResponse);
                    txt2.setText("Przeprowadzonych testów : " + response.getString("totalTests") + "\n\n"
                            + "Ozdrowieńców : " + response.getString("recovered") + "\n\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("CovidPolandApp", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonObjReq);

    }
}



