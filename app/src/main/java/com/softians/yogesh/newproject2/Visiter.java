package com.softians.yogesh.newproject2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Visiter extends ActionBarActivity {

    Button visiter, update, logout,showfeedback;

    Bundle bundle = null;

    public static int count=0;

    public String myString1,ph="no data";

    public static String k="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiter);

        Toolbar myToolbar1 = (Toolbar) findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar1);

        visiter = (Button) findViewById(R.id.btnVisiter);
        update = (Button) findViewById(R.id.btnUpdate);
        logout = (Button) findViewById(R.id.btnLogout);
        showfeedback= (Button) findViewById(R.id.btnshow);

        myString1 =Config.EmailId;

        String url55=Config.DATA_PHONE2+myString1;

 //====================================================================================================================
        StringRequest stringRequest = new StringRequest(url55, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                    JSONObject collegeData = result.getJSONObject(0);
                    ph = collegeData.getString(Config.KEY_C);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (error instanceof NetworkError)
                        {
                            Toast.makeText(Visiter.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(Visiter.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(Visiter.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(Visiter.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(Visiter.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(Visiter.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }

                        //Toast.makeText(Update.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

      RequestQueue requestQueue55 = Volley.newRequestQueue(getApplicationContext());
        requestQueue55.add(stringRequest);

        //====================================================================================================================

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count=1;
                Intent intent = new Intent(Visiter.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString1);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });


        showfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Visiter.this,Display.class);
                Bundle bundle=new Bundle();
                bundle.putString("cemail3",myString1);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        visiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Visiter.this, Feedback.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail2", myString1);
                bundle.putString("cno", ph);
                it.putExtras(bundle);
                startActivity(it);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent it = new Intent(Visiter.this, SignInActivity.class);

                startActivity(it);
                finish();
            }
        });

    }

    //=============================

    public void onBackPressed() {


        Log.d("back button", "back button pressed");
        AlertDialog.Builder ad1=new AlertDialog.Builder(this);
        ad1.setMessage("Are you sure you want to Exit ?");
        ad1.setCancelable(false);

        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {


            }
        });

        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                //android.os.Process.killProcess(android.os.Process.myPid());

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);

            }
        });
        AlertDialog alert=ad1.create();
        alert.show();

    }

//====================================================================================================


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


            int id = item.getItemId();


        if (id == R.id.action_settings)
        {
            //String name5= Visiter.k;

            Intent intent=new Intent(Visiter.this,Suggestion.class);

                Bundle bundle = new Bundle();
                bundle.putString("cemail5", myString1);
                intent.putExtras(bundle);

                startActivity(intent);



            return true;
        }
        //************************************************************************************
        if (id == R.id.action_settings1)
        {

            Intent intent=new Intent(Visiter.this,Aboutus.class);
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_settings2)
        {
             Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.softianstech.my&hl=en"));
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings3)
        {
            openalert(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//===========================================================Toolbar End=========================================
public void openalert(int view){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    alertDialogBuilder.setTitle("Please Contact Us on");
    alertDialogBuilder.setMessage("    softianstechnologies@gmail.com \n    7709697799");

    alertDialogBuilder.setPositiveButton("Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}

}
