package com.softians.yogesh.newproject2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.regex.Pattern;

/**
 * Created by Lenovo on 4/7/2017.
 */

public class Feedback extends Activity {

    int phoneno=0,emailid=0,client=0 ,exp,Names=0,b = 0,c,d=0;

    Context context;

    private EditText NAME, CONTACT, EMAIL, FEEDBACK;

    Button feedback;

    ImageButton GOOD, BAD;

    Animation shake,bounce;

    String feedback5="No Feedback";

    String eeeee="No Data",ccccc="No Data";

    String g,m,Message,myString3, RATING,cno,RESPONSE;

    Bundle bundle = null;

    RequestQueue requestQueue011;

    RequestQueue requestQueue013;

    String insertUrl212=Config.DATA_FEEDBACK;



    //***************************Variable declaration ending here*********************************
    //********************Email/phone validation code Starts*****************************************

    public final Pattern EMAIL_ADDRESS_PATTERN3 = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    public final Pattern PHONE_PATTERN = Pattern.compile("^[789]\\d{9}$");

    //********************Email/phone validation code end*****************************************




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        NAME = (EditText) findViewById(R.id.Name);
        CONTACT = (EditText) findViewById(R.id.ContactNo);
        EMAIL = (EditText) findViewById(R.id.Email);
        FEEDBACK = (EditText) findViewById(R.id.EditTextFeedbackBody);

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanim);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);// i did not bounce animation
        feedback = (Button) findViewById(R.id.btnFeedback);
        requestQueue013 = Volley.newRequestQueue(getApplicationContext());

        bundle = this.getIntent().getExtras();
        myString3 = bundle.getString("cemail2");
        cno = bundle.getString("cno");
        //**********************Image buttion  done over here****************************
        GOOD = (ImageButton) findViewById(R.id.happy);
        BAD = (ImageButton) findViewById(R.id.sad);

        GOOD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                GOOD.startAnimation(bounce);

                b++;// if good button clicked then b is incremented so that you cannout click again as will increment 2
                if (b==1) {
                    RATING = "good";
                    g="Thanks.!! Visit again...!!";
                }
                else
                {
                    Toast.makeText(Feedback.this, "You have allready choosen", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BAD.startAnimation(bounce);
                b++;//if good button clicked then b is incremented so that you cannout click again as will increment 2
                if (b==1)
                {
                    RATING = "bad";
                    g="Thanks.!! We will work on your issue..";
                }
                else
                {
                    Toast.makeText(Feedback.this, "You have allready choosen", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //*****************************Image buttion finishes************************************

        //*************************Volley linked******************************************

        requestQueue011 = Volley.newRequestQueue(getApplicationContext());

        //****************************Volley linking finishes******************************
        //*************************************Bundle ends here***************************************
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                eeeee=EMAIL.getText().toString();
                ccccc=CONTACT.getText().toString();
                client=0;
                emailid=0;
                phoneno=0;
                c=0;
                Names=0;
                //VARIABLE DEFINED TO CHECK EITHER PHONE OR EMAIL SHOULD BE ENTERED
                // Cphonemy String3
                if((EMAIL.getText().toString().equals(myString3)) || (CONTACT.getText().toString().equals(cno)))
                {
                    client++;
                }
//===========================SHAKING OF IMAGE WHEN NOT CLICKED===================================
                if (b==0)// if the image buttion on smilly is not pressed will shake the face
                {
                    GOOD.startAnimation(shake);
                    BAD.startAnimation(shake);

                    Toast.makeText(Feedback.this, "Please Click Me", Toast.LENGTH_SHORT).show();
                }

//========================================================================================
                int a = 0;//THESE VALIABLE FOR ERROR CHECKING DURING VALIDATION

                String email3 = EMAIL.getText().toString();

                if (EMAIL.getText().toString().trim().equals(""))
                {
                    c++;// INCREMENTED IF NOT EMAIL IS ENTERED
                }
                else if (!checkEmail3(email3))
                {
                    emailid++;
                    //c++;//IF EMAIL ADDRESS IS NOT VALIDATED THEN ALSO IT WILL BE INCREMENT AS VALUE GOES NOT INSERTED EMAIL
                    d++;//TO GET A PROPER MESSAGE IN ALLERT BOX
                    m= "Please Enter Valid Email";

                }
                if (NAME.getText().toString().trim().equals(""))
                {
                    NAME.startAnimation(shake);
                    a++;
                    Names++;
                }

                //***************************Phone number validation*********************
                String contact = CONTACT.getText().toString();
                if ((CONTACT.getText().toString().trim().equals("")))
                {
                    c++;//CONTACT NOT INSERTED THEN INCREMENTING C
                }
                else if (!checkcontact(contact))
                {
                    phoneno++;
                    //c++;// IF PHONE NUMBER IS NOT OK THEN ALSO INCREMTNING THE C
                    d++;//TO GET A PROPER MESSAGE IN ALLERT BOX
                    m= "Please Enter Valid Phone Number";
                }
                //***********************************************************************
//*******************************************************************************************
                if (a==0 && emailid==0 && phoneno==0 && c<2 && client==0)// && sss.equals("no")
                {
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl212, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            System.out.println(response.toString());

                            // Toast.makeText(Feedback2.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("name", NAME.getText().toString());
                            parameters.put("email", eeeee);
                            parameters.put("contact_no", ccccc);

                            //***************IF ELSE WHILE PROVIDING EMPTY FEEDBACK************************

                            if (FEEDBACK.getText().toString().trim().equals("")) {


                                parameters.put("feedback",feedback5 );// IF NO FEEDBACK GIVEN THEN WILL PUT NO FEEBBACK GIVEN


                            }

                            else
                            {
                                parameters.put("feedback", FEEDBACK.getText().toString());
                            }

                            //**************************IF ELSE BLOCK END***************************************************
                            parameters.put("cemail", myString3);
                            parameters.put("rating", RATING);

                            return parameters;
                        }
                    };
                    requestQueue011.add(request);

//***********************************************************************************************************************************
                }
                else
                {
                    if(client>0)
                    {


                        Message="Sorry you are Client";
                        open(v);
                    }
                    else if(exp==1)
                    {
                        Message="Sorry you are Client";
                    }
                    else if(emailid>0 && phoneno>0)
                    {
                        Message="Please enter valid Email and Phone no";

                        open(v);
                    }
                    else if (emailid>0 && phoneno==0)
                    {
                        Message="Please enter valid Email";
                        open(v);
                    }
                    else if(emailid==0 && phoneno>0)
                    {
                        Message="Please enter valid Phone no";
                        open(v);
                    }
                    else if(NAME.getText().toString().equals("") && EMAIL.getText().toString().equals("") && CONTACT.getText().toString().equals(""))
                    {
                        Message="Please Enter your Name,Email/Contact";
                        open(v);
                    }
                    else if(Names>0)
                    {
                        Message="Please Enter You Name";
                        open(v);

                    }

                    else
                    {
                        open2(v);
                    }



                }

//******************************************Closing Main if and else block here*****************************************************

                //****************************First Allert dialog box ********************************************
                if (a == 0 && b>0 && c<2 && emailid==0 && phoneno ==0 && client==0)
                {
                    AlertDialog.Builder ad1 = new AlertDialog.Builder(Feedback.this);
                    ad1.setMessage(g);
                    ad1.setCancelable(false);


                    ad1.setPositiveButton("Click Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            finish();
                        }
                    });
                    AlertDialog alert = ad1.create();
                    alert.show();
                }
//*************************************************First Allert box end**************************************
            }
        });
    }
////////////////////////////on create ends/////////////////////////////////////////////////////////////////////////////
    //*********************************Validation Methods***********************************************
    private boolean checkEmail3(String email) {
        return EMAIL_ADDRESS_PATTERN3.matcher(email).matches();
    }
    private boolean checkcontact(String contact)
    {
        return PHONE_PATTERN.matcher(contact).matches();
    }
    //*********************************Validation methods ends****************************************************
//******************************************On Back Pressed*******************************************************
    public void onBackPressed() {
        Log.d("back button", "back button pressed");
        AlertDialog.Builder ad1 = new AlertDialog.Builder(this);
        ad1.setMessage("Do you want leave this page...?");
        ad1.setCancelable(false);
        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
            }
        });
        AlertDialog alert = ad1.create();
        alert.show();

    }
//=====================================On back press end======================================================

    //********************************************Second Allert box*******************************************
    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);

        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
//***********************************Second Allert Ends*************************************************
    //*******************************Third allert Starts *************************************************

    public void open2(View view){
        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setMessage("Please enter your email or Contact no");

        alertDialogBuilder2.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog2 = alertDialogBuilder2.create();
        alertDialog2.show();
    }

//***********************************Third Allert Ends******************************************************
}
