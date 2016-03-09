package com.example.chenjensen.ipm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.chenjensen.ipm.AppConfig;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.entity.ColumnEntity;

import java.util.List;

/**
 * Created by chenjensen on 16/2/27.
 */
public class ColumnListAdapter extends BaseAdapter {

    private List<ColumnEntity> mList;
    private Context mContext;
    private int mResourceID;
    private LayoutInflater mInflater;
    private ViewHolder mHolder;
    private static final int PAGE_LIST_NAME_TEXTVIEW = R.id.item_pagelist_textview;
    private static final int PAGE_LIST_ADD_BUTTON = R.id.item_pagelist_button;
    private static final int DRAWBLE_ADDED = R.drawable.added_page_list;

    class ViewHolder{
        private TextView tv;
        private Button btn;
    }

    public ColumnListAdapter(Context context, List<ColumnEntity> list, int resourceID){
        mList = list;
        mContext = context;
        mResourceID = resourceID;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceID,parent,false);
            mHolder.tv = (TextView)convertView.findViewById(PAGE_LIST_NAME_TEXTVIEW);
            mHolder.btn = (Button)convertView.findViewById(PAGE_LIST_ADD_BUTTON);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)convertView.getTag();
        }
        final ColumnEntity entity = mList.get(position);
        mHolder.tv.setText(entity.getName());
        if(entity.getIsFollow()==1)
            mHolder.btn.setBackgroundResource(DRAWBLE_ADDED);
        else
            mHolder.btn.setBackgroundResource(R.drawable.add_page_list);
        mHolder.btn.setFocusable(false);
        mHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.setIsFollow(entity.getName(),Math.abs(entity.getIsFollow()-1));
                entity.setIsFollow(Math.abs(entity.getIsFollow()-1));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
