package com.softians.yogesh.newproject2;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends ActionBarActivity
{
    EditText Email4,Password4;
    RequestQueue requestQueue;
    Button Login,Register;
    TextView Fp;
    String email;
    String myString4="";
    Bundle bundle = null;
    public static String name5="No user";
    String insertUrl3=Config.LOGIN;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        //*********************Making all the static value empty so that fresh enty can be made*********************
        Config.b="";
        Config.city="";
        Config.n="";
        Config.busname="";
        Config.a="";
        Config.e="";
        Config.p="";
        Config.c="";
        Config.pa="";
        //*********************************************************************************************
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //*************************
        Email4= (EditText) findViewById(R.id.etUserName);
        Password4= (EditText) findViewById(R.id.etPass);
        Fp= (TextView) findViewById(R.id.forgot);
        Login= (Button) findViewById(R.id.btnSingIn);
        Register= (Button)findViewById(R.id.btnSingUp);



        //requestQueue = Volley.newRequestQueue(getApplicationContext());
        Fp.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
             Intent i = new Intent(SignInActivity.this,Forgetpass.class);
             startActivity(i);
         }
        });
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int a=0;
                email=Email4.getText().toString();
                name5=email;
                if (Email4.getText().toString().trim().equals(""))
                {
                    Email4.setError("Please Enter Email");
                    a++;
                }

                if (Password4.getText().toString().trim().equals(""))
                {
                    Password4.setError("Please Enter Password");
                    a++;
                }

                if(a==0)
                {


                    final ProgressDialog myPd_ring= ProgressDialog.show(SignInActivity.this, "", "Please Wait.....", true);
                    myPd_ring.setCancelable(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try
                            {
                                //Thread.sleep(10000);
                            }catch(Exception e){

                            }
                            //myPd_ring.dismiss();
                        }
                    }).start();

//********************************************************************************
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl3,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    myPd_ring.dismiss();
                                    if(response.trim().equals("Login Successful"))
                                    {
                                        Toast.makeText(SignInActivity.this,"Login Successful", Toast.LENGTH_LONG).show();

                                        Config.EmailId=Email4.getText().toString();
                                        Intent intent=new Intent(SignInActivity.this,Visiter.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("cemail", Email4.getText().toString());
                                        intent.putExtras(bundle);

                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                     {
                                        Toast.makeText(SignInActivity.this,response,Toast.LENGTH_LONG).show();
                                     }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    myPd_ring.dismiss();
                                    if (error instanceof NetworkError)
                                    {
                                        Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                                    }
                                    else if (error instanceof ServerError)
                                    {
                                        Toast.makeText(SignInActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                                    }
                                    else if (error instanceof AuthFailureError)
                                    {
                                        Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                    }
                                    else if (error instanceof ParseError)
                                    {
                                        Toast.makeText(SignInActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                                    }
                                    else if (error instanceof NoConnectionError)
                                    {
                                        Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                    }
                                    else if (error instanceof TimeoutError)
                                    {
                                        Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                    }

                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String,String> parameters  = new HashMap<String, String>();
                            parameters.put("email",Email4.getText().toString());
                            parameters.put("password",Password4.getText().toString());


                            return parameters;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
                //******************************************************************************

            }
        });
//===============================================================================================================================
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            }
        });
    }


    public void onBackPressed() {

        //super.onBackPressed();
        Log.d("back button", "back button pressed");
        AlertDialog.Builder ad1=new AlertDialog.Builder(this);
        ad1.setMessage("Are you sure you want to exit ?");
        ad1.setCancelable(false);


        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {


            }
        });

        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

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

    //************************************Menu Bar************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//*********************************************************************************************
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings)
        {
            open(id);
            return true;
        }
        //************************************************************************************
        if (id == R.id.settings1)
        {

            Intent intent=new Intent(SignInActivity.this,Aboutus.class);
            // Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.softianstech.my&hl=en"));
            startActivity(intent);
            return true;

        }
        if (id == R.id.settings2)
        {
           // Intent intent=new Intent(SignInActivity.this,WebSite.class);
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.softianstech.my&hl=en"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //***********************************************************
    public void open(int view){
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

