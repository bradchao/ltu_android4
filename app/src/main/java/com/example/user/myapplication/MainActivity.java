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
            URL url = new URL("http://www.ltu.edu.tw/ezfiles/0/1000/img/44/110042000.jpg");
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

    }

}
