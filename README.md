# jwheel

**Android wheel picker base on RecyclerView.** 

**Android 滚动选择组件，基于RecyclerView**

------

## 1、Feature 特性

- Support Android 5.0+       Android 5.0以上系统版本支持
- Support Only AndroidX    只支持 AndroidX



<img src="https://github.com/jousen/jwheel/blob/main/Screenshot_1615261871.png" style="zoom:25%;" />



## 2、Import 依赖

1、Add the JitPack maven repository to the list of repositories 将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)

**build.gradle**

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2、Add dependencies 

[![](https://jitpack.io/v/jousen/jwheel.svg)](https://jitpack.io/#jousen/jwheel)

```
dependencies {
    implementation 'com.github.jousen:jwheel:2.8'
}
```

## 3、Usage 使用

##### 1、Use in xml 在xml文件定义如下

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#EDEDED">
    
    <com.jousen.plugin.jwheel.WheelView
        android:id="@+id/wheel_view"
        android:layout_width="match_parent"
        android:layout_height="500dp"
        android:clipToPadding="false"
        android:elevation="10dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
        
    <View
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:layout_margin="10dp"
        android:background="#99FFFFFF"
        app:layout_constraintBottom_toBottomOf="@+id/wheel_view"
        app:layout_constraintTop_toTopOf="@+id/wheel_view" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

------

##### 2、Init in Activity 在代码里初始化

```
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
        //若默认选中2021年，则position应该为 2021 - 2000（数组首位）
        findViewById(R.id.button).setOnClickListener(v -> wheelView.selectPosition(2021 - 2000));
        //监听选中内容
        wheelView.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
```



# Licenses

```
Copyright 2021 jousen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```