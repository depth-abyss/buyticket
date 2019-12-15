package com.example.zhucedenglu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{
    public ListView lv;
    public ArrayList<Map<String,Object>>  list=new ArrayList<Map<String,Object>>();
    //
    protected boolean useThemestatusBarColor = false;
    protected boolean useStatusBarColor = true;

    protected void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    //定义状态栏沉浸的东西



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setStatusBar();
        init();





    }


    private PiaoAdapter list_item;

    private void init(){
        list.clear();
        lv=(ListView)findViewById(R.id.lv);
        list_item=new PiaoAdapter();
        lv.setAdapter(list_item);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/servlet/Getinfos")
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    jsonJX(date);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jsonJX(String date){
            try {
                JSONObject jsonObject = new JSONObject(date);
                JSONArray jsonArray = jsonObject.getJSONArray("infos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        final String time = Object.getString("time");
                        String money = Object.getString("money");
                        Log.e("name",time);
                        Log.e("money",money);
                        Map<String,Object> map = new HashMap<String,Object>();
                        try {
                            map.put("time",time);
                            map.put("money",money);
                            list.add(map);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessageDelayed(1,100);
                        }


            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    list_item.notifyDataSetChanged();
                    break;
            }
        }
    };



    public class PiaoAdapter extends BaseAdapter{

        @Override
        public int getCount(){ return list.size(); }
        @Override
        public Object getItem(int position){return list.get(position);}
        @Override
        public long getItemId(int position){return position;}

        @Override
        public View getView(final int position, View converView, ViewGroup parent){
            ViewHolder viewHolder = new ViewHolder();

            if (converView == null){
                converView = getLayoutInflater().inflate(R.layout.shop_item,null);
                viewHolder.Time = (TextView)converView.findViewById(R.id.shijian);
                viewHolder.Money = (TextView)converView.findViewById(R.id.jiage);

                converView.setTag(viewHolder);



            }else {
                viewHolder = (ViewHolder) converView.getTag();
            }
            viewHolder.Time.setText(list.get(position).get("time").toString());
            viewHolder.Money.setText(list.get(position).get("money").toString());

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(ShopActivity.this,"购买成功",Toast.LENGTH_LONG).show();
                }
            });
            return converView;
        }
    }



    final static class ViewHolder{
        TextView Time;
        TextView Money;

    }



    @Override
    public void onClick(View view){

    }

}


