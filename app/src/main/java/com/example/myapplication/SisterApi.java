package com.example.myapplication;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @Class: ${NAME}
 * @Description:
 * @author: BG235144/AMOSCXY
 * @Date: ${DATE}
 */
public class SisterApi {

    private static final String TAG = "Network";
//    private static final String BASE_URL = "https://gank.io/api/data/福利/";
    private static final String OTHER_URL = "https://gank.io/api/v2/categories/Girl";

//    public ArrayList<Sister> fetchSister(int count ,int page){
//        ArrayList<Sister> sisters = new ArrayList<>();
////        String fetchUrl = BASE_URL + count + "/" + page;
//        String fetchUrl = OTHER_URL;
//
//        try {
//            URL url = new URL(fetchUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(10000);
//            int code = conn.getResponseCode();
//            Log.d(TAG, "ResponseCode(): " + code);
//            if (code == 200){
//                InputStream inputStream = conn.getInputStream();
//                byte[] data = readFromStream(inputStream);
//                String result = new String(data,"UTF-8");
//                sisters = parseSister(result);
//            }else{
//                Log.d(TAG, "请求失败，错误码："+code);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sisters;
//    }

    /*
    请求网址，返回JSON
     */
    public ArrayList<Sister> fetchSister(){
        ArrayList<Sister> sisters = new ArrayList<>();
//        String fetchUrl = BASE_URL + count + "/" + page;
        String fetchUrl = OTHER_URL;

        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            int code = conn.getResponseCode();
            Log.d("guokejian", "ResponseCode(): " + code);
            if (code == 200){
                InputStream inputStream = conn.getInputStream();
                byte[] data = readFromStream(inputStream);
                String result = new String(data,"UTF-8");
                sisters = parseSister(result);
                Log.d("guokejian", "result: "+sisters.isEmpty());
            }else{
                Log.d(TAG, "请求失败，错误码："+code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisters;
    }

    /*
    解析Json
     */
    private ArrayList<Sister> parseSister(String content) throws Exception {
        ArrayList<Sister> sisters = new ArrayList<>();
        JSONObject object = new JSONObject(content);
//        JSONArray array  = object.getJSONArray("results");
        JSONArray array  = object.getJSONArray("data");
        Log.d("guokejian", "lenth: "+ array.length());
        for (int i=0;i < array.length();i++){
            Sister sister = new Sister();
            JSONObject results;
            results = (JSONObject) array.get(i);
            Log.d("guokejian", "results: "+results);
            sister.set_id(results.getString("_id"));
//            sister.setCoverImageUrl(results.getString("coverImageUrl"));
            //原网址无法使用使用固定Url
            sister.setCoverImageUrl("https://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
            sister.setDesc(results.getString("desc"));
            sister.setType(results.getString("type"));
            sister.setTitle(results.getString("title"));
//            sister.setStatus(results.getString("status"));
            sisters.add(sister);
            Log.d("guokejian", "parseSister: "+i);

        }
        Log.d("guokejian", "return: ");
        return sisters;

    }

    /*
    读取流中的数据的方法
     */
    private byte[] readFromStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int length = -1;
        while((length = inputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,length);
        }
        inputStream.close();
//        outputStream.close();
        return outputStream.toByteArray();
    }
}
