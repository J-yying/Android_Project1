package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button bt1;
    private ImageView img1;

    private ArrayList<String> urls;
    private int curpos = 0;//当前显示张数
    private int page =1;
    private PictureLoad load ;
    private ArrayList<Sister> data;
    private SisterApi sisterApi;
    private SisterTask sisterTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load = new PictureLoad();
        sisterApi = new SisterApi();
        initDate();
        initUI();
    }

    private void initDate() {
//        urls = new ArrayList<>();
//        urls.add("https://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
//        urls.add("https://ww3.sinaimg.cn/large/610dc034jw1f6gcxc1t7vj20hs0hsgo1.jpg");
//        urls.add("https://ww4.sinaimg.cn/large/610dc034jw1f6f5ktcyk0j20u011hacg.jpg");
//        urls.add("https://ww1.sinaimg.cn/large/610dc034jw1f6e1f1qmg3j20u00u0djp.jpg");
//        urls.add("https://ww3.sinaimg.cn/large/610dc034jw1f6aipo68yvj20qo0qoaee.jpg");
//        urls.add("https://ww3.sinaimg.cn/large/610dc034jw1f69c9e22xjj20u011hjuu.jpg");
//        urls.add("https://ww3.sinaimg.cn/large/610dc034jw1f689lmaf7qj20u00u00v7.jpg");
//        urls.add("https://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg");
//        urls.add("https://ww2.sinaimg.cn/large/610dc034jw1f65f0oqodoj20qo0hntc9.jpg");
//        urls.add("https://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");

        data = new ArrayList<>();
//        new SisterTask(page).execute();
    }
    private void  initUI(){
        img1 = (ImageView)findViewById(R.id.imageView);
        bt1 = (Button) findViewById(R.id.button);

        bt1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if(data!=null && !data.isEmpty()){
                    if(curpos >= 9){
                        curpos = 0;
                    }
                    Log.d("guokejian", "onClick: " + data.get(curpos).getCoverImageUrl());
                    load.load(img1,data.get(curpos).getCoverImageUrl());
                    curpos++;
             }
            break;
        }
    }

    private class SisterTask extends AsyncTask<Void,Void,ArrayList<Sister>> {

        public SisterTask() {
        }

        @Override
        protected ArrayList<Sister> doInBackground(Void... voids) {
            return sisterApi.fetchSister();
        }

        @Override
        protected void onPostExecute(ArrayList<Sister> sisters) {
            super.onPostExecute(sisters);
            data.clear();
            Log.d("guokejian", "onPostExecute: "+sisters.isEmpty());
            data.addAll(sisters);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            sisterTask = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
