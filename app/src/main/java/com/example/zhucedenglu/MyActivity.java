package com.example.zhucedenglu;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhucedenglu.base.BaseActivity;

public class MyActivity extends BaseActivity {

    private ImageButton sy;
    private ImageButton gr;
    private TextView gy;
    private TextView bb;
    private EditText my;
    private Button tj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setStatusBar();
        my=findViewById(R.id.my);
        tj = findViewById(R.id.tj);
        tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = my.getText().toString();
                my.setFocusable(false);
                my.setFocusableInTouchMode(false);
                my.setText(name);
                if (name.length()>0){
                    sendRequestWithHttpClient();
                    Toast.makeText(MyActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MyActivity.this,"请输入昵称后再提交",Toast.LENGTH_SHORT).show();
                }
            }
        });


        gy = (TextView)findViewById(R.id.gy);
        bb = (TextView)findViewById(R.id.bb);
        gy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyActivity.this,"学生设计-毕万斌",Toast.LENGTH_LONG).show();
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyActivity.this,"学生设计-1.0",Toast.LENGTH_LONG).show();
            }
        });


    }

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

    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.sy:
                open(InActivity.class);
                break;

        }
    }

    //弹出对话框询问是否退出


    private void sendRequestWithHttpClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ContentValues postParms = new ContentValues();
                    postParms.put("name",my.getText().toString());
                    String s = UrManager.httpUrlConnectionPost("/servlet/LoginServlet",postParms);
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
