package com.jousen.plugin.jwheel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class WheelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        WheelView year = findViewById(R.id.wheel_view1);
        //设置选中文本的样式
        year.setTextSize(16);
        year.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        year.setData(getYearData());
        //开启声音
        year.enableSound();
        //默认选中项(要选中的内容 - 起始内容 = position)
        year.initPosition(2021 - 1000);
        //监听选中内容
        year.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));

        WheelView month = findViewById(R.id.wheel_view2);
        //设置选中文本的样式
        month.setTextSize(16);
        month.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        month.setData(getMonthData());
        //默认选中项(要选中的内容 - 起始内容 = position)
        month.initPosition(3 - 1);
        //监听选中内容
        month.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
    }

    public List<String> getYearData() {
        List<String> data = new ArrayList<>();
        for (int i2 = 1000; i2 <= 3000; i2++) {
            data.add(i2 + "年");
        }
        return data;
    }

    public static List<String> getMonthData() {
        List<String> monthData = new ArrayList<>();
        for (int i2 = 1; i2 <= 12; i2++) {
            if (i2 < 10) {
                monthData.add("0" + i2 + "月");
            } else {
                monthData.add(i2 + "月");
            }
        }
        return monthData;
    }
}