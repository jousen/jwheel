package com.jousen.plugin.jwheel;

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
    private int selectTextSize = 16;
    private int selectTextColor = Color.BLACK;
    private int unSelectTextSize = 14;
    private int unSelectTextColor = Color.GRAY;

    public void setSelectTextStyle(int selectTextSize, int selectTextColor) {
        this.selectTextSize = selectTextSize;
        this.selectTextColor = selectTextColor;
    }

    public void setUnSelectTextStyle(int unSelectTextSize, int unSelectTextColor) {
        this.unSelectTextSize = unSelectTextSize;
        this.unSelectTextColor = unSelectTextColor;
    }

    public void setData(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public String selectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
        return items.get(selectPosition);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wheel_items, parent, false);
        return new VH(v);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.text.setText(items.get(position));
        if (position == selectPosition) {
            holder.text.setTextSize(selectTextSize);
            holder.text.setTextColor(selectTextColor);
        } else {
            holder.text.setTextSize(unSelectTextSize);
            holder.text.setTextColor(unSelectTextColor);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public final TextView text;

        public VH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
