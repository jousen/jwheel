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
    int choiceYear = 2021;
    int choiceMonth = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        year = findViewById(R.id.wheel_view1);
        //设置选中文本的样式
        year.setTextSize(16);
        year.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        year.setData(getYearData());
        //设置选择文字后缀
        year.setSelectSuffix("年");
        //默认选中项(要选中的内容 - 起始内容 = position)
        year.initPosition(choiceYear - 1000);
        //监听选中内容
        year.setOnSelectListener((position, data) -> {
            Log.e("Select Position:", position + "|" + data);
            choiceYear = position + 1000;
        });

        month = findViewById(R.id.wheel_view2);
        //设置选中文本的样式
        month.setTextSize(16);
        month.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        month.setData(getMonthData());
        //默认选中项(要选中的内容 - 起始内容 = position)
        month.initPosition(choiceMonth - 1);
        //监听选中内容
        month.setOnSelectListener((position, data) -> {
            Log.e("Select Position:", position + "|" + data);
            choiceMonth = position + 1;
            day.setData(getDayData(getMaxDay(choiceYear, choiceMonth)));
        });

        day = findViewById(R.id.wheel_view3);
        //设置选中文本的样式
        day.setTextSize(16);
        day.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        day.setData(getDayData(getMaxDay(choiceYear, choiceMonth)));
        //默认选中项(要选中的内容 - 起始内容 = position)
        day.initPosition(1);
        //监听选中内容
        day.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
    }

    public List<String> getYearData() {
        List<String> data = new ArrayList<>();
        for (int i2 = 1000; i2 <= 3000; i2++) {
            data.add(String.valueOf(i2));
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

    public static List<String> getDayData(int maxDayOfMonth) {
        List<String> dayData = new ArrayList<>();
        for (int i3 = 1; i3 <= maxDayOfMonth; i3++) {
            if (i3 < 10) {
                dayData.add("0" + i3 + "日");
            } else {
                dayData.add(i3 + "日");
            }
        }
        return dayData;
    }

    public static int getMaxDay(int choiceYear, int choiceMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, choiceYear);
        calendar.set(Calendar.MONTH, (choiceMonth - 1));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}