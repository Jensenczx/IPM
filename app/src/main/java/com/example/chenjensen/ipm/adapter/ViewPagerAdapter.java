package com.example.chenjensen.ipm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenjensen on 16/2/25.
 */
public class ViewPagerAdapter extends PagerAdapter{

    private List<View> mList;

    public ViewPagerAdapter(List<View> list){
        mList = list;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View mView = mList.get(position);
        container.addView(mView);
        return mView;
    }
}
