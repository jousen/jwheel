package com.jousen.plugin.jwheel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WheelView extends RecyclerView {
    private final LinearLayoutManager linearLayoutManager;
    private final WheelAdapter wheelAdapter;
    private OnSelectListener onSelectListener;
    private int initPosition = -1;//初始位置
    private int itemCount = 0;//控件数量
    private int itemHeight = 0;//控件高度
    private int lastSelect = -1;//上次选择的item，相同item不再回调

    public WheelView(@NonNull Context context) {
        this(context, null);
    }

    public WheelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linearLayoutManager = new LinearLayoutManager(context, VERTICAL, false);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(this);
        wheelAdapter = new WheelAdapter();
        setAdapter(wheelAdapter);

        setHasFixedSize(true);
        setLayoutManager(linearLayoutManager);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = getMeasuredHeight();
                itemCount = getChildCount();
                itemHeight = height / itemCount;
                int padding = (height - itemHeight) / 2;
                WheelView.this.setPadding(0, padding, 0, padding);
                //若有初始选择的item，则滑动过去
                if (initPosition > 0 && initPosition <= itemCount) {
                    scrollToPosition(initPosition - 1);
                    smoothScrollBy(0, itemHeight);
                }
            }
        });
    }

    public void setSelectTextStyle(int selectTextSize, int selectTextColor) {
        wheelAdapter.setSelectTextStyle(selectTextSize, selectTextColor);
    }

    public void setUnSelectTextStyle(int unSelectTextSize, int unSelectTextColor) {
        wheelAdapter.setUnSelectTextStyle(unSelectTextSize, unSelectTextColor);
    }

    public void setData(List<String> items) {
        setData(items, 0);
    }

    public void setData(List<String> items, int initPosition) {
        wheelAdapter.setData(items);
        int size = items.size();
        if (size > 0 && initPosition <= size) {
            //初始设定位置小于3，直接平滑滚动过去
            if (initPosition < 3) {
                smoothScrollToPosition(initPosition);
                return;
            }
            scrollToPosition(initPosition - 1);
            smoothScrollToPosition(initPosition);
        }
    }

    public void setSelectItem(int position) {
        if (position < 0) {
            return;
        }
        if (position == 0) {
            smoothScrollToPosition(0);
            return;
        }
        if (itemHeight <= 0) {
            initPosition = position;
            return;
        }
        scrollToPosition(position - 1);
        smoothScrollBy(0, itemHeight);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (linearLayoutManager == null) {
                return;
            }
            int first = linearLayoutManager.findFirstVisibleItemPosition();
            int last = linearLayoutManager.findLastVisibleItemPosition();

            int selectPosition = (last - first) / 2 + first;
            if (selectPosition < 0) {
                selectPosition = 0;
            }
            if (lastSelect == selectPosition) {
                return;
            }
            lastSelect = selectPosition;
            String positionData = wheelAdapter.selectPosition(selectPosition);
            onSelectListener.onSelect(selectPosition, positionData);
        }
    }
}