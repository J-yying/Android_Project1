package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class PictureLoad {
    private ImageView loadimg;
    private String imgUrl;
    private byte[] picByte;

    // 5/16/21 to complete Handle

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                if(picByte != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte,0,picByte.length);
                    loadimg.setImageBitmap(bitmap);
                }
            }
        }
    };

    public void load(ImageView loadimg,String imgUrl){
        this.loadimg = loadimg;
        this.imgUrl = imgUrl;

        Drawable drawable = loadimg.getDrawable();
        if(drawable != null && drawable instanceof BitmapDrawable){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            if(bitmap != null && !bitmap.isRecycled()){
                bitmap.isRecycled();
            }
        }

        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(imgUrl);
                HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                if(urlConnection.getResponseCode() == 200){
                    InputStream inputStream = urlConnection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = -1;
                    while((length = inputStream.read(bytes)) != -1){
                        outputStream.write(bytes,0,length);
                    }
                    picByte = outputStream.toByteArray();
                    inputStream.close();
                    outputStream.close();
                    handler.sendEmptyMessage(0x123);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}
