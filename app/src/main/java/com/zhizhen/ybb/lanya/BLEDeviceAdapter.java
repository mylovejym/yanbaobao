package com.zhizhen.ybb.lanya;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhizhen.ybb.R;

import java.util.List;

/**
 * Created by vonchenchen on 2016/1/29 0029.
 */
public class BLEDeviceAdapter extends BaseAdapter {

    private List<BluetoothDevice> mDataList;
    private Context mCtx;

    public BLEDeviceAdapter(Context ctx, List<BluetoothDevice> list){
        this.mDataList = list;
        this.mCtx = ctx;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = viewHolder.getRootView();
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.refreshView((BluetoothDevice)getItem(i));

        return view;
    }

    private class ViewHolder extends BaseWidgetHolder<BluetoothDevice>{

        TextView mNameText;
        TextView mAddressText;

        @Override
        public View initView() {
            View view = View.inflate(mCtx, R.layout.item_ble_device_info, null);
            mNameText = (TextView) view.findViewById(R.id.tv_name);
            mAddressText = (TextView) view.findViewById(R.id.tv_address);
            return view;
        }

        @Override
        public void refreshView(BluetoothDevice data) {
            mNameText.setText(data.getName());
            mAddressText.setText(data.getAddress());
        }
    }
}
