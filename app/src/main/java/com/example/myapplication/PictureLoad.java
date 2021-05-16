package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;


public class PictureLoad {
    private ImageView loadimg;
    private String imgUrl;
    private byte[] picByte;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

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

        }
    };

}
