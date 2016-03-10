package com.example.chenjensen.ipm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.entity.EssayEntity;
import com.example.chenjensen.ipm.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by chenjensen on 16/3/1.
 */
public class EssayListviewAdapter extends BaseAdapter{

    private List<EssayEntity> mList;
    private Context mContext;
    private int mResourceID;
    private ImageLoader mLoader;
    private LayoutInflater mInflater;
    class ViewHolder{
        private TextView tv;
        private ImageView iv;
    }

    public EssayListviewAdapter(Context context,List<EssayEntity> list,int resourceID){
        mList = list;
        mContext = context;
        mResourceID = resourceID;
        mInflater = LayoutInflater.from(context);
        mLoader = ImageLoader.getInstance();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if(convertView==null){
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceID,parent,false);
            mHolder.iv = (ImageView)convertView.findViewById(R.id.item_page_imageview);
            mHolder.tv = (TextView)convertView.findViewById(R.id.item_page_textview);
            convertView.setTag(mHolder);
        }
        else{
            mHolder = (ViewHolder)convertView.getTag();
        }
        EssayEntity entity = mList.get(position);
        if(entity!=null){
            mHolder.tv.setText(entity.getTitle());
            mLoader.bindBitmap(entity.getPage(),mHolder.iv,80,80);
        }
        return convertView;
    }
}
