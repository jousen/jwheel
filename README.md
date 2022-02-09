# jwheel

**Android 滚动选择组件，基于RecyclerView**

**Android wheel picker base on RecyclerView.** 

------

## 1、特性 Feature 

- Android 5.0以上系统版本支持 Support Android 5.0+ 
- 只支持 AndroidX  Support Only AndroidX 

<img src="https://github.com/jousen/jwheel/blob/main/1.png" />

## 2、依赖 Import 

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
    implementation 'com.github.jousen:jwheel:4.0'
}
```

## 3、使用 Usage 

##### 1、Use in xml 在xml文件定义如下

中间项若需装饰，可使用ConstraintLayout布局将一个view设置为WheelView的中间

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
        android:layout_height="40dp"
        android:layout_margin="10dp"
        android:background="#99FFFFFF"
        app:layout_constraintBottom_toBottomOf="@+id/wheel_view"
        app:layout_constraintTop_toTopOf="@+id/wheel_view" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

------

##### 2、Init in Activity 在代码里初始化

```
        WheelView year = findViewById(R.id.wheel_view1);
        //设置选中文本的样式
        year.setTextSize(16);
        year.setTextColor(Color.BLUE, Color.GRAY);
        //设置内容
        year.setData(getYearData());
        //设置选择文字后缀
        year.setSelectSuffix("年");
        //默认选中项(要选中的内容 - 起始内容 = position)
        year.initPosition(2021 - 1000);
        //监听选中内容
        year.setOnSelectListener((position, data) -> Log.e("Select Position:", position + "|" + data));
```



# Licenses

```
Copyright 2022 jousen

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