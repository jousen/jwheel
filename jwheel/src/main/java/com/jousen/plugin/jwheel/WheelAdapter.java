package com.jousen.plugin.jwheel;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WheelAdapter extends RecyclerView.Adapter<WheelAdapter.VH> {
    private List<String> items;
    private int selectPosition = -1;
    private float textSize = 16;
    private int selectColor = Color.BLACK;
    private int unSelectColor = Color.GRAY;
    private String selectSuffix = "";

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int selectColor, int unSelectColor) {
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
    }

    public void setSelectSuffix(String selectSuffix) {
        this.selectSuffix = selectSuffix;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> items) {
        this.items = items;
        int size = items.size();
        if (selectPosition >= size) {
            selectPosition = size - 1;
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public String selectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
        return items.get(selectPosition);
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
        if (position == selectPosition) {
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

    public static class VH extends RecyclerView.ViewHolder {
        public final TextView text;

        public VH(@NonNull View itemView, float textSize) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text.setTextSize(textSize);
        }
    }
}
