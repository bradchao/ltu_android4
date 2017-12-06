package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View view){
        new Thread(){
            @Override
            public void run() {
                connectToLtu();
            }
        }.start();

    }

    private void connectToLtu(){

        try {
            // 1. URL : protocol:// Hostname or ip /.resources...
            URL url = new URL("http://www.ltu.edu.tw/");
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            conn.connect();;
            InputStream in =  conn.getInputStream();
            int b;
            while ((b = in.read()) != -1){
                Log.v("brad", "" + (char)b);
            }
            in.close();
            Log.v("brad", "OK");

        }catch (Exception ee){
            Log.v("brad", ee.toString());
        }
    }

    public void test2(View view){

    }
    public void test3(View view){

    }

}
