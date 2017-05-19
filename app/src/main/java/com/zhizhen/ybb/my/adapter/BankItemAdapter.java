package com.zhizhen.ybb.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhen.ybb.R;

import java.util.ArrayList;
import java.util.List;

public class BankItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> mItemBeans = new ArrayList<>();
    private String sampling;

    public BankItemAdapter(Context context, String sampling) {
        mInflater = LayoutInflater.from(context);
        this.sampling = sampling + "s";
        for (int i = 1; i <= 10; i++) {
            mItemBeans.add(i + "s");
        }
    }

    public void refresh(String sampling) {
        this.sampling = sampling + "s";
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemBeans != null ? mItemBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.sampling_item, null);
            holder.item_tv = (TextView) convertView.findViewById(R.id.txt_sampling);
            holder.item_img = (ImageView) convertView.findViewById(R.id.image_sampling);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_tv.setText(mItemBeans.get(position));
        if (mItemBeans.get(position).equals(sampling)) {
            holder.item_img.setVisibility(View.VISIBLE);
        } else {
            holder.item_img.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView item_img;
        TextView item_tv;
    }
}
