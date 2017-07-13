package com.zhizhen.ybb.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.blog.www.guideview.Component;

/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {

    private int layout;

    private int x = 0, y = 0, anchor, fitPosition;

    @Override
    public View getView(LayoutInflater inflater) {

        LinearLayout ll = (LinearLayout) inflater.inflate(layout, null);
//        ll.setOnClickListener(view ->
//                Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show());
        return ll;
    }

    @Override
    public int getAnchor() {
        return anchor;
    }

    @Override
    public int getFitPosition() {
        return fitPosition;
    }

    @Override
    public int getXOffset() {
        return x;
    }

    @Override
    public int getYOffset() {
        return y;
    }

    public SimpleComponent setXOffset(int x) {
        this.x = x;
        return this;
    }

    public SimpleComponent setYOffset(int y) {
        this.y = y;
        return this;
    }

    public SimpleComponent setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public SimpleComponent setFitPosition(int fitPosition) {
        this.fitPosition = fitPosition;
        return this;
    }

    public SimpleComponent setLayout(int layout) {
        this.layout = layout;
        return this;
    }


}
