package com.timemoneywaste.flames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);



//it help to go main of the application automatically

    new Handler().postDelayed(new Runnable(){
    @Override
    public void run(){

        final Intent mainintent=new Intent(homepage.this,MainActivity.class);
                homepage.this.startActivity(mainintent);
                homepage.this.finish();
            }


        },4000); //This page will be displayed only for 4 seconds


    }

}
