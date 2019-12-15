package com.example.zhucedenglu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zhucedenglu.base.BaseActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InActivity extends BaseActivity {

    private CityPicker cityPicker;
    private CityPicker cityPicker1;
    private DatePicker datePicker;
    private EditText editText;
    private Button shi;
    private Button da;
    private Button shop;
    private ImageButton sy;
    private ImageButton gr;


    //调用城市选择器

    //一个按钮一个城市选择器
    public void initCityPicker(){
        cityPicker = new CityPicker.Builder(InActivity.this)
                .textSize(20)
                .title("城市选择")
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];
                String city = citySelected[1];
                shi.setText(province + city);
            }

            @Override
            public void onCancel() {

            }
        });

        cityPicker1 = new CityPicker.Builder(InActivity.this)
                .textSize(20)
                .title("城市选择")
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        cityPicker1.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];
                String city = citySelected[1];
                da.setText(province + city);
            }

            @Override
            public void onCancel() {

            }
        });
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        setStatusBar();


        shi = (Button)findViewById(R.id.button3);
        shi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCityPicker();
                cityPicker.show();
            }
        });
        da = (Button)findViewById(R.id.button);
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCityPicker();
                cityPicker1.show();
            }
        });
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        editText = (EditText)findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                datePicker.setVisibility(View.VISIBLE);
            }
        });
        datePicker.init(2019, 11, 11, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号");
                editText.setText(format.format(calendar.getTime()));
                datePicker.setVisibility(View.INVISIBLE);
            }
        });

        shop=(Button)findViewById(R.id.goupiao);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });


    }

    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.gr:
                open(MyActivity.class);
                break;

        }
    }



    }

