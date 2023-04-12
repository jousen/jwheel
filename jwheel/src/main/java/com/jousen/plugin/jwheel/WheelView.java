package com.jousen.plugin.jwheel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WheelView extends RecyclerView {
    private final LinearLayoutManager linearLayoutManager;
    private final WheelAdapter wheelAdapter;
    private OnSelectListener onSelectListener;
    //item数据
    private int itemSize = 0;//控件总数量
    private List<String> items;
    private int itemHeight = 0;//控件高度
    private int currentSelect = -1;
    private float textSize = 16;
    private int selectColor = Color.BLACK;
    private int unSelectColor = Color.GRAY;
    private String selectSuffix = "";

    public WheelView(@NonNull Context context) {
        this(context, null);
    }

    public WheelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //布局器初始化
        linearLayoutManager = new LinearLayoutManager(context, VERTICAL, false);
        setLayoutManager(linearLayoutManager);
        //确保居中显示
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(this);
        //初始化数据
        wheelAdapter = new WheelAdapter();
        setAdapter(wheelAdapter);
        //初始化布局
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = getMeasuredHeight();//
                int childCount = getChildCount();//可见item数量
                itemHeight = height / childCount;
                int padding = (height - itemHeight) / 2;
                WheelView.this.setPadding(0, padding, 0, padding);
                //若有初始选择的item，则滑动过去
                if (currentSelect >= 0) {
                    scrollToItem(currentSelect);
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (linearLayoutManager == null || items.size() == 0) {
                return;
            }
            int first = linearLayoutManager.findFirstVisibleItemPosition();
            int last = linearLayoutManager.findLastVisibleItemPosition();
            int select = (last - first) / 2 + first;
            if (select < 0) {
                select = 0;
            }
            currentSelect = select;
            onSelectListener.onSelect(currentSelect, items.get(currentSelect));
            wheelAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置选中项监听
     *
     * @param onSelectListener OnSelectListener
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    /**
     * item 字体大小
     *
     * @param textSize float
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * item 字体颜色
     *
     * @param selectColor int
     * @param unSelectColor int
     */
    public void setTextColor(int selectColor, int unSelectColor) {
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
    }

    /**
     * item 选中项文字后缀
     *
     * @param selectSuffix String
     */
    public void setSelectSuffix(String selectSuffix) {
        this.selectSuffix = selectSuffix;
    }

    /**
     * 设置数据
     *
     * @param items List<String>
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> items) {
        this.items = items;
        this.itemSize = items.size();
        //数据变少后，若选择的末尾一个，则重新选择
        if (currentSelect >= itemSize) {
            currentSelect = itemSize - 1;
            scrollToItem(currentSelect);
        }
        wheelAdapter.notifyDataSetChanged();
    }

    /**
     * 初始位置
     *
     * @param position int
     */
    public void initPosition(int position) {
        if (position < 0) {
            return;
        }
        //若recyclerview未绘制，则先保存位置
        if (itemHeight <= 0) {
            currentSelect = position;
            return;
        }
        scrollToItem(position);
    }

    private void scrollToItem(int position) {
        if (itemSize < 0) {
            return;
        }
        //若初始设置位置超过item数组index，则滑动到最后一位
        int scrollPosition = position;
        if (position >= itemSize) {
            scrollPosition = itemSize - 1;
        }
        //initPosition为负值和数组为空的情况判断
        if (scrollPosition < 0) {
            scrollPosition = 0;
        }
        //滑动过去后，再平移一小部分，让linearSnapHelper微调位置
        scrollToPosition(scrollPosition);
        smoothScrollBy(0, itemHeight / 4);
    }

    /**
     * 适配器部分
     */
    class WheelAdapter extends RecyclerView.Adapter<VH> {
        public WheelAdapter() {
            super();
            items = new ArrayList<>();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wheel_items, parent, false);
            return new VH(v, textSize);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull VH holder) {
            super.onViewAttachedToWindow(holder);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            if (position == currentSelect) {
                String text = items.get(position) + selectSuffix;
                holder.text.setText(text);
                holder.text.setTextColor(selectColor);
            } else {
                holder.text.setText(items.get(position));
                holder.text.setTextColor(unSelectColor);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    /**
     * view holder部分
     */
    static class VH extends RecyclerView.ViewHolder {
        public final TextView text;

        public VH(@NonNull View itemView, float textSize) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text.setTextSize(textSize);
        }
    }
}