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
        for (int i = 1; i < 24; i++) {
            list.add(i + "点");
        }
        WheelView wheelView = findViewById(R.id.wheel_view);
        //设置选中文本的样式
        wheelView.setSelectTextStyle(18, Color.BLUE);
        //设置非选中文本的样式
        wheelView.setUnSelectTextStyle(16, Color.GRAY);
        //设置内容
        wheelView.setData(list);
        //若默认选中8点，则应该为 8 - 1（数组首位）
        wheelView.setSelectItem(8 - 1);
        //监听选中内容
        wheelView.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
    }
}