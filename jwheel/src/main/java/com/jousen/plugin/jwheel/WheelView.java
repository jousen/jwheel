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
    private int itemSize = 0;//控件数量
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
                //可见item数量
                int childCount = getChildCount();
                //防止除数为0
                if (childCount <= 0) {
                    return;
                }
                itemHeight = height / childCount;
                int padding = (height - itemHeight) / 2 + 1;
                WheelView.this.setPadding(0, padding, 0, padding);
                //若有初始选择的item，则滑动过去
                if (initPosition > 0) {
                    scrollToItem(initPosition);
                }
            }
        });
    }

    public void setTextSize(float textSize) {
        wheelAdapter.setTextSize(textSize);
    }

    public void setTextColor(int selectColor, int unSelectColor) {
        wheelAdapter.setTextColor(selectColor, unSelectColor);
    }

    public void setSelectSuffix(String selectSuffix) {
        wheelAdapter.setSelectSuffix(selectSuffix);
    }

    public void setData(List<String> items) {
        this.itemSize = items.size();
        wheelAdapter.setData(items);
    }

    @Deprecated
    public void enableSound() {
    }

    public void initPosition(int position) {
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
        scrollToItem(position);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    private void scrollToItem(int initPosition) {
        if (itemSize < 0) {
            return;
        }
        //若初始设置位置超过item数组index，则滑动到最后一位
        int scrollPosition = initPosition;
        if (initPosition >= itemSize) {
            scrollPosition = itemSize - 1;
        }
        //initPosition为负值和数组为空的情况判断
        if (scrollPosition < 0) {
            scrollPosition = 0;
        }
        //数组长度小于5，直接平滑滚动过去
        if (itemSize < 5 || scrollPosition == 0) {
            smoothScrollToPosition(scrollPosition);
            return;
        }
        //默认先直接到前一位，再平滑过去
        scrollToPosition(scrollPosition);
        smoothScrollBy(0, itemHeight / 4);
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
            String selectText = wheelAdapter.selectPosition(selectPosition);
            onSelectListener.onSelect(selectPosition, selectText);
        }
    }
}