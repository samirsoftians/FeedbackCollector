package com.softians.yogesh.newproject2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Lenovo on 4/17/2017.
 */

public class Update2 extends Activity
{

    String name5 = "no data";
    String bname5 = "no data";
    String address5 = "no data";
    String contact5 = "no data";
    String email5 = "no data";
    String password5 = "no data";
    String business5 = "Business Type";
    String city5 = "City";
    int refresh=0;
    String myString2,btname;
    Bundle bundle = null;
    String insertUrl5=Config.DATA_UPDATE;

    private JSONArray result;//**************************new***DECLARED HERE
    private JSONArray result2;//**************************new***DECLARED HERE
    private ArrayList<String> students;//************new***Declared here*************************
    private ArrayList<String> students2;//************new***Declared here*************************

    private EditText Password1, Cpass1, Name1, Bname1, Address1;
    private TextView TvEmailAddrss1;
    private EditText EtPhoneNumber1;

    Button ud;
    Spinner Spinner3,city2;
    public static String isp="Internet Service Provider";
    RequestQueue requestQueue;
    String insertUrl1 = Config.DATA_INSERT;
    String insertUrl6 = Config.DATA_CHECK;
    int i;
    Button signup;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
//================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Spinner3 = (Spinner)findViewById(R.id.spinner3);
        city2= (Spinner) findViewById(R.id.spinner4);
        students = new ArrayList<String>();
        students2 = new ArrayList<String>();
        getData();
        getData2();


        ud = (Button) findViewById(R.id.update);
        Name1 = (EditText) findViewById(R.id.uName);
        Bname1 = (EditText) findViewById(R.id.uBName);
        Address1 = (EditText) findViewById(R.id.uAddress);
       // TvEmailAddrss1 = (EditText) findViewById(R.id.etEmail);
        EtPhoneNumber1 = (EditText) findViewById(R.id.uContactNo);
        Cpass1 = (EditText) findViewById(R.id.utComPass);
        Password1 = (EditText) findViewById(R.id.utPass);
        // business_type = (EditText) findViewById(R.id.btype);

        bundle = this.getIntent().getExtras();
        myString2 = bundle.getString("cemail1");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //******************************************Retriving Data From Server to insert in the edit text************************************************************************************
        String url = Config.DATA_URL+myString2;//EtEmailAddrss1.getText().toString().trim();
        if(Visiter.count==1)//Checking the value of count from Visiter class
        {
            //******************************************Starting PROGRESS DIALOG BOX******************************
            final ProgressDialog myPd_ring= ProgressDialog.show(Update2.this, "Please wait", "Retriving Data.....", true);
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

                }
            }).start();
//******************************************************Ending Progress Dialog box**************************************
            StringRequest stringRequest841 = new StringRequest(url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    myPd_ring.dismiss();
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                            if (error instanceof NetworkError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ServerError)
                            {
                                Toast.makeText(Update2.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof AuthFailureError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ParseError)
                            {
                                Toast.makeText(Update2.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                            }
                            else if (error instanceof NoConnectionError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof TimeoutError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }

                            //Toast.makeText(Update.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                            Config.refresh++;

                            if(Config.refresh>2)
                            {
                                Refresh();
                            }
                            else
                            {
                                Intent intent = new Intent(Update2.this, Update2.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cemail1", myString2);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }


                        }
                    });

            RequestQueue requestQueue841 = Volley.newRequestQueue(this);
            requestQueue841.add(stringRequest841);
//*************************************************Retriving Ends Here*****************************************************************************************************
        }
        //********************************Business type spinner starts here*****************
        Spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
            {

                String selected_val=Spinner3.getSelectedItem().toString();

                if(selected_val.equals("Others"))
                {
                    open2(view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });
//*************************************BUSINESS TYPE SPINNER ENDS HERE***********************************
        //******************City spinner Starts here****************************
        //*********************************new set on click listener*****************
        city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
            {

                String selected_val2=city2.getSelectedItem().toString();

                if(selected_val2.equals("Others"))
                {
                    open3(view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });

        //******************************City spinner ends here**************************


        if(Config.city.equals("City") || Config.b.equals("Business Type"))
        {
            Config.refresh++;

            if(Config.refresh>2)
            {
                Refresh();
            }
            else
            {
                Intent intent = new Intent(Update2.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        ud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //******************Making all the static variable null************
                Config.b="Business Type";
                Config.city="City";
                Config.n="";
                Config.a="";
                Config.e="";
                Config.p="";
                Config.c="";
                Config.pa="";
                Config.busname="";
                //***************************Ending the the block****************
                int a=0;
                //spinner.getSelectedItem().toString().trim()=="Business Type"
                if (Spinner3.getSelectedItem().toString().equals("Business Type"))
                {
                    Toast.makeText(Update2.this, "Please Select any one Business Type", Toast.LENGTH_SHORT).show();
                    a++;
                }
                if (Spinner3.getSelectedItem().toString().equals("Others"))
                {
                    Toast.makeText(Update2.this, "Please Select any one Business Type", Toast.LENGTH_SHORT).show();
                    a++;
                }
                if (city2.getSelectedItem().toString().equals("City"))
                {
                    // spinner.setError("Please Select any one Business_Type");
                    Toast.makeText(Update2.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }
                if (city2.getSelectedItem().toString().equals("Others"))
                {
                    // spinner.setError("Please Select any one Business_Type");
                    Toast.makeText(Update2.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }
                String phone= EtPhoneNumber1.getText().toString();

                if (city2.getSelectedItem().toString().trim()=="City")
                {
                    Toast.makeText(Update2.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }

//==========================================================================
                final String pass = Password1.getText().toString();
                if (!isValidPassword(pass)) {
                    Password1.setError("Password length should be greater than 4 character");

                    // Toast.makeText(SignUpActivity.this, "Paswword lenght should be greater than 6 char", Toast.LENGTH_SHORT).show();
                    a++;

                }

                //========================================================================

                String strPass1 = Password1.getText().toString();
                String strPass2 = Cpass1.getText().toString();

                if (!strPass1.equals(strPass2))
                {
                    Cpass1.setError("Password not matched");

                    //  Toast.makeText(SignUpActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                    a++;
                }


                //===================================================

                String Number = EtPhoneNumber1.getText().toString();

                if (EtPhoneNumber1.getText().toString().length() < 10 || Number.length() >= 12) {

                    EtPhoneNumber1.setError("Please enter valid phone number");
                    // Toast.makeText(SignUpActivity.this,"Please enter valid phone number",Toast.LENGTH_SHORT).show();
                    a++;
                    // am_checked=0;
                }
                //==========================================================================
                if (Name1.getText().toString().trim().equals(""))
                {

                    Name1.setError("Please Enter Name");

                    // Toast.makeText(SignUpActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    a++;

                }
                if (Bname1.getText().toString().trim().equals(""))
                {
                    Bname1.setError("Please Enter BusinessName");

                    // Toast.makeText(SignUpActivity.this, "Please enter Busieness_Name", Toast.LENGTH_SHORT).show();
                    a++;

                }

                if (Address1.getText().toString().trim().equals(""))
                {

                    Address1.setError("Please Enter Address");

                    //Toast.makeText(SignUpActivity.this, "Please enter Address", Toast.LENGTH_SHORT).show();
                    a++;

                }

                //=================================================================================================
                if(a==0)
                {
                    final ProgressDialog myPd_ring= ProgressDialog.show(Update2.this, "Please wait", "Retriving Data.....", true);
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

                        }
                    }).start();

                    //*************************************************************************************************************

                    //***************************************Here Updation is done to the server***************************************************
                    StringRequest stringRequest8411 = new StringRequest(Request.Method.POST, insertUrl5, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            myPd_ring.dismiss();

                            System.out.println(response.toString());

                            Toast.makeText(Update2.this,"Update Successfully", Toast.LENGTH_LONG).show();

                            Intent i=new Intent(Update2.this,SignInActivity.class);
                            startActivity(i);
                            finish();

                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (error instanceof NetworkError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ServerError)
                            {
                                Toast.makeText(Update2.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof AuthFailureError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ParseError)
                            {
                                Toast.makeText(Update2.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                            }
                            else if (error instanceof NoConnectionError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof TimeoutError)
                            {
                                Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }

                        }
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String,String> parameters  = new HashMap<String, String>();



                            parameters.put("owner_name", Name1.getText().toString());
                            parameters.put("business_name", Bname1.getText().toString());
                            parameters.put("address", Address1.getText().toString());
                            parameters.put("contact_no", EtPhoneNumber1.getText().toString());
                            parameters.put("email", myString2);
                            parameters.put("password", Password1.getText().toString());
                            parameters.put("business_type", Spinner3.getSelectedItem().toString());
                            parameters.put("city", city2.getSelectedItem().toString());
                            return parameters;
                        }
                    };
                    RequestQueue requestQueue8411 = Volley.newRequestQueue(Update2.this);
                    requestQueue8411.add(stringRequest8411);}
                     Visiter.count=0;

            }

        });
    }
    private boolean isValidPassword(String pass)
    {
        if (pass != null && pass.length() > 4) {
            return true;
        }
        return false;

    }

    private void getData()
    {
        final ProgressDialog myPd_ring= ProgressDialog.show(Update2.this, "Please wait", "Uploading Data.....", true);
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

            }
        }).start();

        StringRequest stringRequest = new StringRequest(Config.getbusiness,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JSONObject j = null;
                        try
                        {
                          //  Toast.makeText(Update2.this, response, Toast.LENGTH_SHORT).show();

                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            getStudents(result);

                            myPd_ring.dismiss();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //myPd_ring.dismiss();
                        if (error instanceof NetworkError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(Update2.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(Update2.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        Config.refresh++;

                        if(Config.refresh>2)
                        {
                                Refresh();
                        }
                        else
                        {
                            Intent intent = new Intent(Update2.this, Update2.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("cemail1", myString2);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getStudents(JSONArray j)
    {

        students.add("Business Type");
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);



                if(json.getString("business_type").equals("Doctor") || json.getString("business_type").equals("Hotel") ||
                        json.getString("business_type").equals("Restaurant")|| json.getString("business_type").equals("Real Estate")
                        || json.getString("business_type").equals("Hospital")|| json.getString("business_type").equals("Classes")
                        || json.getString("business_type").equals("Education") || json.getString("business_type").equals("Entertainment")
                        || json.getString("business_type").equals("Fitness") || json.getString("business_type").equals("Internet Service Provider")
                        || json.getString("business_type").equals("Repair") || json.getString("business_type").equals("Showroom")
                        || json.getString("business_type").equals("Saloon") || json.getString("business_type").equals("Travels"))
                {

                }
                else
                {

                    students.add(json.getString("business_type"));//Config.TAG_USERNAME

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        students.add("Hotel");
        students.add("Restaurant");
        students.add("Real Estate");
        students.add("Hospital");
        students.add("Classes");
        students.add("Doctor");
        students.add("Education");
        students.add("Entertainment");
        students.add("Fitness");
        students.add("Internet Service Provider");
        students.add("Repair");
        students.add("Saloon");
        students.add("Travels");
        students.add("Showroom");
        students.add("Others");
        Spinner3.setAdapter(new ArrayAdapter<String>(Update2.this, android.R.layout.simple_spinner_dropdown_item, students));
        //*********************************************************************
        Spinner3.setSelection(students.indexOf(Config.b));
        Name1.setText(Config.n);
        Bname1.setText(Config.busname);
        Address1.setText(Config.a);
//        TvEmailAddrss1.setText(Config.e);
        EtPhoneNumber1.setText(Config.p);
        Cpass1.setText(Config.c);
        Password1.setText(Config.pa);
        //*****************************************************************************************************
    }
    //*************************************SECOND METHODS ENDS HERE*************************
    public void open2(View view)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(Update2.this);
        View rootView = layoutInflater.inflate(R.layout.dialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update2.this);
        alertDialogBuilder.setView(rootView);

        final EditText yourEditText = (EditText) rootView.findViewById(R.id.etComments);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//***********************************************Volley Starts Here*****************************

                       String name = yourEditText.getText().toString().substring(0,1).toUpperCase() + yourEditText.getText().toString().substring(1).toLowerCase();
                        Config.b=yourEditText.getText().toString();
                        Config.city=city2.getSelectedItem().toString();//*************************Here to perform
                        Config.n=Name1.getText().toString();
                        Config.a=Address1.getText().toString();
                        Config.e= myString2;//TvEmailAddrss1.getText().toString();
                        Config.p=EtPhoneNumber1.getText().toString();
                        Config.c=Cpass1.getText().toString();
                        Config.pa=Password1.getText().toString();
                        Config.busname=Bname1.getText().toString();

                        Visiter.count=0;
                        students.add(yourEditText.getText().toString());
                        Spinner3.setAdapter(new ArrayAdapter<String>(Update2.this, android.R.layout.simple_spinner_dropdown_item, students));
                        Spinner3.setSelection(students.indexOf(yourEditText.getText().toString()));

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Spinner3.setSelection(students.indexOf("Business Type"));
                                dialog.cancel();

                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        //*******************************Allert Ends Here********************************
    }
    //**********************************************Allert Dialog box for city spinner Stars here*********************
    public void open3(View view)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(Update2.this);
        View rootView = layoutInflater.inflate(R.layout.dialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update2.this);
        alertDialogBuilder.setView(rootView);

        final EditText yourEditText2 = (EditText) rootView.findViewById(R.id.etComments);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//***********************************************Volley Starts Here*****************************

                        String name2 = yourEditText2.getText().toString().substring(0,1).toUpperCase() + yourEditText2.getText().toString().substring(1).toLowerCase();
                        Config.city=yourEditText2.getText().toString();
                        Config.b=Spinner3.getSelectedItem().toString();
                        Config.n=Name1.getText().toString();
                        Config.a=Address1.getText().toString();
                        Config.e=myString2;//TvEmailAddrss1.getText().toString();
                        Config.p=EtPhoneNumber1.getText().toString();
                        Config.c=Cpass1.getText().toString();
                        Config.pa=Password1.getText().toString();
                        Config.busname=Bname1.getText().toString();

                        Visiter.count=0;

                        students2.add(yourEditText2.getText().toString());

                        city2.setAdapter(new ArrayAdapter<String>(Update2.this, android.R.layout.simple_spinner_dropdown_item, students2));


                        city2.setSelection(students2.indexOf(yourEditText2.getText().toString()));

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                city2.setSelection(students2.indexOf("City"));


                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        //*******************************Allert Ends Here********************************
    }

    //************************************Allert Dialog box ends for city spinner***********************************************************
//********************************************Get data fro spinner city**********************************
    private void getData2()
    {
        final ProgressDialog myPd_ring= ProgressDialog.show(Update2.this, "Please wait", "Retriving Data.....", true);
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

            }
        }).start();

        StringRequest stringRequest = new StringRequest(Config.getcity,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JSONObject j = null;
                        try
                        {
                            //Toast.makeText(Update2.this, response, Toast.LENGTH_SHORT).show();

                            j = new JSONObject(response);
                            result2 = j.getJSONArray("result");
                            getStudents2(result2);

                            myPd_ring.dismiss();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
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
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(Update2.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(Update2.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(Update2.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }

                        Config.refresh++;

                        if(Config.refresh>2)
                        {
                            Refresh();
                        }
                        else
                        {
                            Intent intent = new Intent(Update2.this, Update2.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("cemail1", myString2);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    // *************************Get data for spinner city ends heere************************************************
    //**************************Spinner for city is function here**************************
    private void getStudents2(JSONArray j)
    {
        students2.add("City");
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);


                if(json.getString("city").equals("Pune") || json.getString("city").equals("Mumbai") ||
                        json.getString("city").equals("Delhi")|| json.getString("city").equals("Chennai")
                        )
                {

                }
                else
                {
                    students2.add(json.getString("city"));
                }
//                students2.add(json.getString("city"));//Config.TAG_USERNAME
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        students2.add("Mumbai");
        students2.add("Pune");
        students2.add("Delhi");
        students2.add("Chennai");
        students2.add("Others");
        city2.setAdapter(new ArrayAdapter<String>(Update2.this, android.R.layout.simple_spinner_dropdown_item, students2));
        //*********************************************************************


        city2.setSelection(students2.indexOf(Config.city));

        Name1.setText(Config.n);
        Bname1.setText(Config.busname);
        Address1.setText(Config.a);
       // TvEmailAddrss1.setText(Config.e);
        EtPhoneNumber1.setText(Config.p);
        Cpass1.setText(Config.c);
        Password1.setText(Config.pa);
//*****************************************************************************************************

    }
    //*************************************SECOND METHODS ENDS HERE*************************



    //*************************End of retrival of city spinner*************************


    //**********************************************Getting data from server and filling to the edit text********
    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name5 = collegeData.getString(Config.KEY_N);
            bname5 = collegeData.getString(Config.KEY_B);
            address5 = collegeData.getString(Config.KEY_A);
            contact5 = collegeData.getString(Config.KEY_C);
            email5 = collegeData.getString(Config.KEY_E);// we dont require email but we get it from the server
            password5 = collegeData.getString(Config.KEY_P);
            business5 = collegeData.getString(Config.KEY_BUS);
            city5 = collegeData.getString(Config.KEY_CITY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        city2.setSelection(students2.indexOf(city5));
        Spinner3.setSelection(students.indexOf(business5));
        Name1.setText(name5);
        Bname1.setText(bname5);
        Address1.setText(address5);
        EtPhoneNumber1.setText(contact5);
        Password1.setText(password5);
        Config.city=city5;
        Config.b=business5;
        Config.n=Name1.getText().toString();
        Config.a=Address1.getText().toString();
        Config.e=myString2;//TvEmailAddrss1.getText().toString();
        Config.p=EtPhoneNumber1.getText().toString();
        Config.c=Cpass1.getText().toString();
        Config.pa=Password1.getText().toString();
        Config.busname=Bname1.getText().toString();
//**********************************Not Working Code******************
//**************************************************************************
        if(Name1.getText().toString().equals(""))
        {Config.refresh++;
            if(Config.refresh>2)
            {
                Refresh();
            }
            else
            {
                Intent intent = new Intent(Update2.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString2);
                intent.putExtras(bundle);
                startActivity(intent);
            }



        }
        if(Config.city.equals("City") || Config.b.equals("Business Type"))
        {
            Config.refresh++;

            if(Config.refresh>2)
            {
                Refresh();
            }
            else
            {
                Intent intent = new Intent(Update2.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
//===================================================================
    }
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setMessage("Click to Refresh or Exit");
        alertDialogBuilder2.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Update2.this, Visiter.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
        alertDialogBuilder2.setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Config.refresh=0;
                Intent intent = new Intent(Update2.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString2);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //************************************************************
        AlertDialog alertDialog2 = alertDialogBuilder2.create();
        alertDialog2.show();


    }
    //***********************************************Ending HERE*************************************************
    public void Refresh(){
        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setMessage("Please Check your Internet Connection");

        alertDialogBuilder2.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                       // finish();

                        Intent intent = new Intent(Update2.this, Visiter.class);


                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });

        alertDialogBuilder2.setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Config.refresh=0;
                Intent intent = new Intent(Update2.this, Update2.class);
                Bundle bundle = new Bundle();
                bundle.putString("cemail1", myString2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog2 = alertDialogBuilder2.create();
        alertDialog2.show();
    }
    //******************************************************************************
}
