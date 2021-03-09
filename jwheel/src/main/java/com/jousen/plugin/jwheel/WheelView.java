package com.jousen.plugin.jwheel;

import android.content.Context;
import android.util.AttributeSet;

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
    private int lastSelect = -1;

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

        post(() -> {
            int height = this.getMeasuredHeight();
            int padding = height / 2;
            WheelView.this.setPadding(0, padding, 0, padding);
        });
    }

    public void setSelectTextStyle(int selectTextSize, int selectTextColor) {
        wheelAdapter.setSelectTextStyle(selectTextSize, selectTextColor);
    }

    public void setUnSelectTextStyle(int unSelectTextSize, int unSelectTextColor) {
        wheelAdapter.setUnSelectTextStyle(unSelectTextSize, unSelectTextColor);
    }

    public void setData(List<String> items) {
        wheelAdapter.setData(items);
        if (items.size() > 0) {
            smoothScrollToPosition(0);
        }
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