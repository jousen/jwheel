package com.jousen.example.jwheel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jousen.plugin.jwheel.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelActivity extends AppCompatActivity {
    WheelView year;
    WheelView month;
    WheelView day;
    int yearStart = 2000;
    int choiceYear = 2022;
    int choiceMonth = 1;
    int choiceDay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        //当前日期
        Calendar calendar = Calendar.getInstance();
        choiceYear = calendar.get(Calendar.YEAR);
        choiceMonth = calendar.get(Calendar.MONTH) + 1;
        choiceDay = calendar.get(Calendar.DAY_OF_MONTH);

        year = findViewById(R.id.wheel_view1);
        //设置选中文本的样式
        year.setTextSize(18);
        year.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        year.setData(getYearData());
        //设置选择文字后缀
        year.setSelectSuffix("年");
        //默认选中项(要选中的内容 - 起始内容 = position)
        year.initPosition(choiceYear - yearStart);
        //监听选中内容
        year.setOnSelectListener((position, data) -> {
            choiceYear = position + yearStart;
            Log.d("JWheel:", choiceYear + "-" + choiceMonth + "-" + choiceDay);
        });

        month = findViewById(R.id.wheel_view2);
        //设置选中文本的样式
        month.setTextSize(18);
        month.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        month.setData(getMonthData());
        //设置选择文字后缀
        month.setSelectSuffix("月");
        //默认选中项(要选中的内容 - 起始内容 = position)
        month.initPosition(choiceMonth - 1);
        //监听选中内容
        month.setOnSelectListener((position, data) -> {
            choiceMonth = position + 1;
            Log.d("JWheel:", choiceYear + "-" + choiceMonth + "-" + choiceDay);
            day.setData(getDayData());
        });

        day = findViewById(R.id.wheel_view3);
        //设置选中文本的样式
        day.setTextSize(18);
        day.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        day.setData(getDayData());
        //设置选择文字后缀
        day.setSelectSuffix("日");
        //默认选中项(要选中的内容 - 起始内容 = position)
        day.initPosition(choiceDay - 1);
        //监听选中内容
        day.setOnSelectListener((position, data) -> {
            choiceDay = position + 1;
            Log.d("JWheel:", choiceYear + "-" + choiceMonth + "-" + choiceDay);
        });
    }

    private List<String> getYearData() {
        List<String> data = new ArrayList<>();
        for (int i2 = yearStart; i2 <= 2100; i2++) {
            data.add(String.valueOf(i2));
        }
        return data;
    }

    private List<String> getMonthData() {
        List<String> monthData = new ArrayList<>();
        for (int i2 = 1; i2 <= 12; i2++) {
            monthData.add(String.valueOf(i2));
        }
        return monthData;
    }

    private List<String> getDayData() {
        List<String> dayData = new ArrayList<>();
        for (int i3 = 1; i3 <= getMaxDay(); i3++) {
            dayData.add(String.valueOf(i3));
        }
        return dayData;
    }

    private int getMaxDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, choiceYear);
        calendar.set(Calendar.MONTH, (choiceMonth - 1));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}