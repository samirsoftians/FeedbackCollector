package com.softians.yogesh.newproject2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Aboutus extends Activity {
    private WebView webView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        //=====================================================================================================
        final ProgressDialog myPd_ring= ProgressDialog.show(Aboutus.this, "", "Please wait......", true);
        myPd_ring.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                try
                {
                    Thread.sleep(1000);
                }
                catch(Exception e){

                }
                myPd_ring.dismiss();
            }
        }).start();
        //================================================================================================================

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("http://www.softianstech.com/");
        webView.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {

            super.onBackPressed();
        }
    }
}
