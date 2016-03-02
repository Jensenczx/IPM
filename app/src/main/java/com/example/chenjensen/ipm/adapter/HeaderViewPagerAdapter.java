package com.example.chenjensen.ipm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.chenjensen.ipm.base.MyApplication;

import java.util.List;

/**
 * Created by chenjensen on 16/2/26.
 */
public class HeaderViewPagerAdapter extends PagerAdapter{

    private List<ImageView> mImageViews;

    public HeaderViewPagerAdapter(List<ImageView> mImageViews){
        this.mImageViews = mImageViews;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViews.get(position % mImageViews.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mImageViews.get(position % mImageViews.size());
        // 为每一个page添加点击事件
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), "Page 被点击了", Toast.LENGTH_SHORT).show();
            }

        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ImageView)object;
    }
}
