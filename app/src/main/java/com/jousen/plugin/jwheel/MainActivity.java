package com.jousen.plugin.jwheel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WheelView wheelView = findViewById(R.id.wheel_view);
        //设置选中文本的样式
        wheelView.setSelectTextStyle(16, Color.BLUE);
        //设置非选中文本的样式
        wheelView.setUnSelectTextStyle(16, Color.GRAY);
        //设置内容
        wheelView.setData(getMonthData());
        //监听选中内容
        wheelView.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
        //若默认选中8点，则应该为 8 - 1（数组首位）
        findViewById(R.id.button).setOnClickListener(v -> {
            wheelView.setSelectItem(8-1);
        });
    }

    public List<String> getMonthData() {
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