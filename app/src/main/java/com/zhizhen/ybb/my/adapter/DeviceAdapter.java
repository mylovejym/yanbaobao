package com.zhizhen.ybb.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhen.ybb.R;
import com.zhizhen.ybb.bean.DeviceInfo;
import com.zhizhen.ybb.bean.MyBLEDevice;
import com.zhizhen.ybb.my.interFace.BindingDeviceOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MyBLEDevice> mItemBeans = new ArrayList<>();
    private BindingDeviceOnClickListener bindingDeviceOnClickListener;

    public DeviceAdapter(Context context, List<MyBLEDevice> mItemBeans) {
        mInflater = LayoutInflater.from(context);
        this.mItemBeans = mItemBeans;
    }

    public void refresh(List<MyBLEDevice> mItemBeans) {
        this.mItemBeans = mItemBeans;
        this.notifyDataSetChanged();
    }

    public void setBindingDeviceOnClickListener(BindingDeviceOnClickListener bindingDeviceOnClickListener) {
        this.bindingDeviceOnClickListener = bindingDeviceOnClickListener;
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
            convertView = mInflater.inflate(R.layout.device_item, null);
            holder.item_tv_name = (TextView) convertView.findViewById(R.id.txt_device_name);
            holder.item_tv_state = (TextView) convertView.findViewById(R.id.txt_device_state);
            holder.item_bt = (Button) convertView.findViewById(R.id.bt_device);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_tv_name.setText(mItemBeans.get(position).getName());
//        holder.item_tv_state.setText(mItemBeans.get(position).getDeviceState().equals("1") ? "已绑定" : "未绑定");
//        holder.item_bt.setText(mItemBeans.get(position).getDeviceState().equals("1") ? "解除绑定" : "绑定设备");
        holder.item_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bindingDeviceOnClickListener.onBindingOnClickListener(v, position, mItemBeans.get(position).getDeviceState());
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView item_tv_name;
        TextView item_tv_state;
        Button item_bt;
    }
}
