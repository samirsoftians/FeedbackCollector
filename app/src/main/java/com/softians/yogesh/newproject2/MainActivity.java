package com.softians.yogesh.newproject2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar ab=getSupportActionBar();
//        ab.hide();


        /*A handler is basically a message queue. You post a message to it, and it will eventually process it by calling its run method and passing the message to it.
         Since these run calls will always occur in the order of messages received on the same thread, it allows you to serialize events.
         Serialization in java is a mechanism of writing the state of an object into a byte stream.*/


        new Handler().postDelayed
                (
                new Runnable()//it will kepp the app on on pause state for 3000 sec
                 {
                    @Override
                     public void run()
                        {
                        Intent sc=new Intent(MainActivity.this,SignInActivity.class);
                         startActivity(sc);
                        }
                 },
                3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();//Declared so that when it reaches on on pause state after 3000 sec then it will finish the app
    }
}
