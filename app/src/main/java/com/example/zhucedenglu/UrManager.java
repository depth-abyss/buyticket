package com.example.zhucedenglu;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by zhangliang on 2017-9-4.
 */

public class UrManager {
    //final  public  static  String server="http://work.nyfzgroup.com:8080";
    final  public  static  String server="http://10.0.2.2:8080";//  本机服务器
    public static HttpURLConnection getHttpURLConnection(String url,String method){
        HttpURLConnection mHttpURLConnection=null;
        try {
            URL mUrl=new URL(url);
            mHttpURLConnection=(HttpURLConnection)mUrl.openConnection();
            //设置链接超时时间
            mHttpURLConnection.setConnectTimeout(15000);
            //设置读取超时时间
            mHttpURLConnection.setReadTimeout(15000);
            //设置请求参数
            mHttpURLConnection.setRequestMethod(method);
            //添加Header
            mHttpURLConnection.setRequestProperty("Connection","Keep-Alive");
            //接收输入流
            mHttpURLConnection.setDoInput(true);
            //传递参数时需要开启,如果连接方式为POST  DoOutPut 为 true，连接方式为GET   DoOutPut 为 false
            mHttpURLConnection.setDoOutput(method.toUpperCase().equals("POST"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mHttpURLConnection ;
    }
    public static  String httpUrlConnectionGet(String url) {
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = getHttpURLConnection(server+url,"GET");
        try {
            //获取返回数据
            mHttpURLConnection.connect();
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            String respose = converStreamToString(mInputStream);

            Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
            mInputStream.close();
            return  respose;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
    public static  String httpUrlConnectionPost(String url,ContentValues postParams) {
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = getHttpURLConnection(server+url,"POST");
        try {
            StringBuilder mStringBuilder=new StringBuilder();
            for (Map.Entry<String, Object> entry : postParams.valueSet()){
                if(!TextUtils.isEmpty(mStringBuilder)){
                    mStringBuilder.append("&");
                }
                mStringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                mStringBuilder.append("=");
                mStringBuilder.append(URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
            }
            // 上传数据
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter
                    (mHttpURLConnection.getOutputStream(),"UTF-8"));
            writer.write(mStringBuilder.toString());
            writer.flush();
            writer.close();
            //获取返回数据
            mHttpURLConnection.connect();
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            String respose = converStreamToString(mInputStream);
            Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
            mInputStream.close();
            return  respose;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
    private static String converStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        String respose = sb.toString();
        return respose;
    }

    public static Bitmap getHttpBitmap(String url) {

        URL myFileUrl = null;

        Bitmap bitmap = null;

        try {

            Log.d(TAG, url);

            myFileUrl = new URL(url);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setConnectTimeout(0);

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return bitmap;

    }
}