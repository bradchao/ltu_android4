package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView img;
    private StringBuffer sb;
    private UIHandler handler;
    private  Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();
        tv = (TextView)findViewById(R.id.tv);
        img = (ImageView)findViewById(R.id.img);

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
            conn.connect();

            InputStream in =  conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line = null; sb = new StringBuffer();
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
            in.close();

            handler.sendEmptyMessage(0);

            Log.v("brad", "OK");

        }catch (Exception ee){
            Log.v("brad", ee.toString());
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0:
                    tv.setText(sb);
                    break;
                case 1:
                    img.setImageBitmap(bmp);
                    break;
            }
        }
    }

    public void test2(View view){
        new Thread(){
            @Override
            public void run() {
                getImage();
            }
        }.start();
    }

    private void getImage(){
        try {
            URL url = new URL("https://ezgo.coa.gov.tw/Uploads/opendata/BuyItem/APPLY_D/20151026161106.jpg");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            bmp = BitmapFactory.decodeStream(conn.getInputStream());
            handler.sendEmptyMessage(1);

            Log.v("brad", "OK2");
        }catch (Exception e){
            Log.v("brad", e.toString());
        }

    }

    public void test3(View view){
        new Thread(){
            @Override
            public void run() {
                getOpenData();
            }
        }.start();
    }

    private void getOpenData(){
        try {
            // 1. URL : protocol:// Hostname or ip /.resources...
            URL url = new URL("http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvAgriculturalProduce.aspx");
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            conn.connect();

            InputStream in =  conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line = null; sb = new StringBuffer();
            while ((line = br.readLine()) != null){
                //Log.v("brad", line);
                sb.append(line + "\n");
            }
            in.close();
            parseJSON(sb.toString());
            Log.v("brad", "OK");

        }catch (Exception ee){
            Log.v("brad", ee.toString());
        }
    }

    private void parseJSON(String json){
        try {
            JSONArray root = new JSONArray(json);
            Log.v("brad", "count:" + root.length());

            for (int i=0; i<root.length(); i++){
                JSONObject rec = root.getJSONObject(i);
                String name = rec.getString("Name");
                String address = rec.getString("SalePlace");
                String tel = rec.getString("ContactTel");
                Log.v("brad", name + ":" + address + ":" + tel);

            }


        } catch (JSONException e) {
            Log.v("brad", e.toString());
        }
    }


    public void test4(View view){
        new Thread(){
            @Override
            public void run() {
                try {
                    // 1. URL : protocol:// Hostname or ip /.resources...
                    //URL url = new URL("http://120.108.137.125/brad2.php?x=100&y=33");
                    URL url = new URL("http://10.0.2.2/brad2.php?x=100&y=33");  // 10.0.2.2 => 127.0.0.1 alias => VM
                    HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                    conn.connect();

                    InputStream in =  conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String line = null; sb = new StringBuffer();
                    while ((line = br.readLine()) != null){
                        Log.v("brad", line);
                        sb.append(line + "\n");
                    }
                    in.close();
                    //parseJSON(sb.toString());
                    Log.v("brad", "OK");

                }catch (Exception ee){
                    Log.v("brad", ee.toString());
                }
            }
        }.start();
    }


}
