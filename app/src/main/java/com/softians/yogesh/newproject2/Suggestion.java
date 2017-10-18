package com.softians.yogesh.newproject2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 4/5/2017.
 */

public class Suggestion extends Activity {

    EditText sug;
    Button ss;
    String myString6,insertUrl58=Config.SUGGESTION;

    RequestQueue requestQueue58;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        myString6 = bundle.getString("cemail5");

        sug= (EditText) findViewById(R.id.suggest);

        ss= (Button) findViewById(R.id.ok);

        requestQueue58 = Volley.newRequestQueue(getApplicationContext());


        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sugges= sug.getText().toString();
//***************************************************Volley***********************************
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl58, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        // System.out.println(response.toString());

                        Toast.makeText(Suggestion.this, "Thanks for you suggestion", Toast.LENGTH_SHORT).show();

                        finish();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("sug", sug.getText().toString());
                        parameters.put("cemail", myString6);

                        return parameters;
                    }
                };
                requestQueue58.add(request);


                //************************Ending volley**********************************

            }
        });
    }
}
