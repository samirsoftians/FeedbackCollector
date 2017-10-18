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

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignUpActivity extends Activity
{
    int counter=3,i;

    private JSONArray result;//**************************new***DECLARED HERE

    private JSONArray result2;//**************************new***DECLARED HERE

    private ArrayList<String> students;//************new***Declared here*************************

    private ArrayList<String> students2;//************new***Declared here*************************

    private EditText password,cpass,name,bname,address;
    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    Spinner spinner,city;

    public static String isp="Internet Service Provider";

    RequestQueue requestQueue;

    String insertUrl1 = Config.DATA_INSERT;

    String insertUrl6 = Config.DATA_CHECK;



    //********************************************For Sqlite DataBase---------------------
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    //------------------------------------------------------------------------------------

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
    //**************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        students = new ArrayList<String>();

        students2 = new ArrayList<String>();

        signup = (Button) findViewById(R.id.btnSingUp);

        name = (EditText) findViewById(R.id.etName);
        bname = (EditText) findViewById(R.id.etBName);
        address = (EditText) findViewById(R.id.etAddress);
        etEmailAddrss = (EditText) findViewById(R.id.etEmail);
        etPhoneNumber = (EditText) findViewById(R.id.etContactNo);
        cpass = (EditText) findViewById(R.id.etComPass);
        password = (EditText) findViewById(R.id.etPass);

        spinner = (Spinner)findViewById(R.id.spinner);

        city= (Spinner) findViewById(R.id.spinnercity);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        getData();

        getData2();

        //********************************Business type spinner starts here*****************
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
            {

                String selected_val=spinner.getSelectedItem().toString();

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
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
            {

                String selected_val2=city.getSelectedItem().toString();

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
        signup.setOnClickListener(new View.OnClickListener() {
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

                if (spinner.getSelectedItem().toString().equals("Business Type"))
                {

                    Toast.makeText(SignUpActivity.this, "Please Select any one Business Type", Toast.LENGTH_SHORT).show();
                    a++;
                }
                if (spinner.getSelectedItem().toString().equals("Others"))
                {

                    Toast.makeText(SignUpActivity.this, "Please Select any one Business Type", Toast.LENGTH_SHORT).show();
                    a++;
                }

                if (city.getSelectedItem().toString().equals("City"))
                {

                    Toast.makeText(SignUpActivity.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }
                if (city.getSelectedItem().toString().equals("Others"))
                {

                    Toast.makeText(SignUpActivity.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }


                String email = etEmailAddrss.getText().toString();
               String phone= etPhoneNumber.getText().toString();

                if (city.getSelectedItem().toString().trim()=="City")
                {
                    Toast.makeText(SignUpActivity.this, "Please Select any one City", Toast.LENGTH_SHORT).show();
                    a++;
                }

                if(!checkEmail(email)) {

                    etEmailAddrss.setError("Invalid Email address");

                    a++;

                }

                //***************************************************************************************
//==========================================================================
                final String pass = password.getText().toString();
                if (!isValidPassword(pass)) {
                    password.setError("Password length should be greater than 4 character");
                    a++;
                }
                //========================================================================
                String strPass1 = password.getText().toString();
                String strPass2 = cpass.getText().toString();

                if (!strPass1.equals(strPass2))
                {
                    cpass.setError("Password not matched");


                    a++;
                }
                //===================================================
                String Number = etPhoneNumber.getText().toString();

                if (etPhoneNumber.getText().toString().length() < 10 || Number.length() >= 12) {

                    etPhoneNumber.setError("Please enter valid phone number");

                    a++;
                }
                    //==========================================================================
                if (name.getText().toString().trim().equals(""))
                {
                    name.setError("Please Enter Name");
                    a++;
                }

                if (bname.getText().toString().trim().equals(""))
                {
                    bname.setError("Please Enter BusinessName");
                    a++;
                }

                if (address.getText().toString().trim().equals(""))
                {
                    address.setError("Please Enter Address");
                    a++;
                }

                //=================================================================================================
                if(a==0)
                {
                    final ProgressDialog myPd_ring= ProgressDialog.show(SignUpActivity.this, "Please wait", "Retriving Data.....", true);
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

                    StringRequest request1 = new StringRequest(Request.Method.POST, insertUrl6, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            myPd_ring.dismiss();

                            if(response.trim().equals("success"))
                            {
//************************************************Storing in sqlite database **********************

                             etEmailAddrss.setError("Email already Exist");
                            }
                            else
                            {//*************************************************************************
                                StringRequest request = new StringRequest(Request.Method.POST, insertUrl1, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        myPd_ring.dismiss();

                                        System.out.println(response.toString());
                                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                                        startActivity(i);
                                        finish();

                                    }
                            }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error)
                                    {
                                        myPd_ring.dismiss();
                                        if (error instanceof NetworkError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                                        }
                                        else if (error instanceof ServerError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                                        }
                                        else if (error instanceof AuthFailureError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                        }
                                        else if (error instanceof ParseError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                                        }
                                        else if (error instanceof NoConnectionError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                        }
                                        else if (error instanceof TimeoutError)
                                        {
                                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                                        }
                                        counter++;
                                    }
                                    {

                                    }
                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> parameters = new HashMap<String, String>();
                                        parameters.put("owner_name", name.getText().toString());
                                        parameters.put("business_name", bname.getText().toString());
                                        parameters.put("address", address.getText().toString());
                                        parameters.put("contact_no", etPhoneNumber.getText().toString());
                                        parameters.put("email", etEmailAddrss.getText().toString());
                                        parameters.put("password", password.getText().toString());
                                        parameters.put("business_type", spinner.getSelectedItem().toString());
                                        parameters.put("city", city.getSelectedItem().toString());

                                        return parameters;
                                    }
                                };
                                requestQueue.add(request);

                                //*******************************************************************************************
                            }
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            myPd_ring.dismiss();
                            if (error instanceof NetworkError)
                            {
                                Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ServerError)
                            {
                                Toast.makeText(SignUpActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof AuthFailureError)
                            {
                                Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof ParseError)
                            {
                                Toast.makeText(SignUpActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                            }
                            else if (error instanceof NoConnectionError)
                            {
                                Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }
                            else if (error instanceof TimeoutError)
                            {
                                Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                            }

                            counter++;


                        }
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String,String> parameters  = new HashMap<String, String>();
                            parameters.put("email",etEmailAddrss.getText().toString());

                            return parameters;
                        }
                    };
                    requestQueue.add(request1);
                }

            }
        });
    }
//***********************************************************************************************************************************
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    //***********************************************************************************
    private boolean isValidPassword(String pass)
    {
        if (pass != null && pass.length() > 4) {
            return true;
        }
        return false;

    }

    //=================================================================

    public void onBackPressed() {

        Refresh3();
    }
    //=================================================================
    //name.getText().toString(),bname.getText().toString(),address.getText().toString(),etPhoneNumber.getText().toString(),password.getText().toString(),Constants.type
    //**************************Calling methoad to store in sqlite*********
//    private void saveNameToLocalStorage(String name,String busi,String add,String phon,String emai,String pass,String btype, int status) {
//        //editTextName.setText("");
//        db.addName(name,busi,add,phon,emai,pass,btype, status);
////        Name n = new Name(name, status);
////        names.add(n);
//        //refreshList();
//    }
    //**********************************GET DATA METHOD CALLED HERE**************************************
    private void getData()
    {
        final ProgressDialog myPd_ring= ProgressDialog.show(SignUpActivity.this, "Please wait", "Uploading Data.....", true);
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
                        myPd_ring.dismiss();
                        if (error instanceof NetworkError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(SignUpActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(SignUpActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }

                                counter++;

                        if(counter>2)
                        {
                           // myPd_ring.dismiss();
                            Refresh3();
                        }
                        else
                        {
                            Intent intent=new Intent(SignUpActivity.this,SignUpActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        for (int i = 0; i < j.length(); i++)
        {
            try
            {
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
            } catch (JSONException e)
            {
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
        spinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
        spinner.setSelection(students.indexOf(Config.b));
        name.setText(Config.n);
        bname.setText(Config.busname);
        address.setText(Config.a);
        etEmailAddrss.setText(Config.e);
        etPhoneNumber.setText(Config.p);
        cpass.setText(Config.c);
        password.setText(Config.pa);
    }
    //*************************************SECOND METHODS ENDS HERE*************************
    public void open2(View view)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(SignUpActivity.this);
        View rootView = layoutInflater.inflate(R.layout.dialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
        alertDialogBuilder.setView(rootView);

        final EditText yourEditText = (EditText) rootView.findViewById(R.id.etComments);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

//***********************************************Volley Starts Here*****************************
                        String name3 = yourEditText.getText().toString().substring(0,1).toUpperCase() + yourEditText.getText().toString().substring(1).toLowerCase();

                        Config.b=yourEditText.getText().toString();
                        //Config.b=yourEditText.getText().toString();
                        Config.n=name.getText().toString();
                        Config.a=address.getText().toString();
                        Config.e=etEmailAddrss.getText().toString();
                        Config.p=etPhoneNumber.getText().toString();
                        Config.c=cpass.getText().toString();
                        Config.pa=password.getText().toString();
                        Config.busname=bname.getText().toString();

                        students.add(yourEditText.getText().toString());
                        spinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
                        spinner.setSelection(students.indexOf(yourEditText.getText().toString()));
                        //*********************************Spiiner Starts Here*************************************


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                spinner.setSelection(students.indexOf("Business Type"));
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
        LayoutInflater layoutInflater = LayoutInflater.from(SignUpActivity.this);
        View rootView = layoutInflater.inflate(R.layout.dialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
        alertDialogBuilder.setView(rootView);

        final EditText yourEditText2 = (EditText) rootView.findViewById(R.id.etComments);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//***********************************************Volley Starts Here*****************************
                        String name3 = yourEditText2.getText().toString().substring(0,1).toUpperCase() + yourEditText2.getText().toString().substring(1).toLowerCase();
                        Config.city=yourEditText2.getText().toString();
                        Config.n=name.getText().toString();
                        Config.a=address.getText().toString();
                        Config.e=etEmailAddrss.getText().toString();
                        Config.p=etPhoneNumber.getText().toString();
                        Config.c=cpass.getText().toString();
                        Config.pa=password.getText().toString();
                        Config.busname=bname.getText().toString();

                        students2.add(yourEditText2.getText().toString());

                        city.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, students2));

                        city.setSelection(students2.indexOf(yourEditText2.getText().toString()));

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                city.setSelection(students2.indexOf("city"));


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
        final ProgressDialog myPd_ring= ProgressDialog.show(SignUpActivity.this, "Please wait", "Retriving Data.....", true);
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
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(SignUpActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(SignUpActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(SignUpActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        counter++;

                        if(counter>2)
                        {
                            Refresh3();
                        }
                        else {
                            Intent intent=new Intent(SignUpActivity.this,SignUpActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        students2.add("city");
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


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        students2.add("Mumbai");
        students2.add("Pune");
        students2.add("Delhi");
        students2.add("Chennai");
        students2.add("Others");

        city.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, students2));
        city.setSelection(students2.indexOf(Config.city));
        name.setText(Config.n);
        bname.setText(Config.busname);
        address.setText(Config.a);
        etEmailAddrss.setText(Config.e);
        etPhoneNumber.setText(Config.p);
        cpass.setText(Config.c);
        password.setText(Config.pa);}
    //*************************************SECOND METHODS ENDS HERE*************************
    //*************************End of retrival of city spinner*************************
    public void Refresh3(){
        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setMessage("Please Check Your Internet Connection");
        alertDialogBuilder2.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });

        alertDialogBuilder2.setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //************************************************************
        AlertDialog alertDialog2 = alertDialogBuilder2.create();
        alertDialog2.show();
    }

}

