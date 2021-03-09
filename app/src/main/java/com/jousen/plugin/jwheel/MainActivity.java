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

        List<String> list = new ArrayList<>();
        for (int i = 2000; i < 2030; i++) {
            list.add(i + "年");
        }
        WheelView wheelView = findViewById(R.id.wheel_view);
        //设置选中文本的样式
        wheelView.setSelectTextStyle(18, Color.BLUE);
        //设置非选中文本的样式
        wheelView.setUnSelectTextStyle(16, Color.GRAY);
        //设置内容
        wheelView.setData(list);
        //监听选中内容
        wheelView.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
    }
}