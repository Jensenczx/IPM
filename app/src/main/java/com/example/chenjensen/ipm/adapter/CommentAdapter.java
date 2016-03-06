package com.example.chenjensen.ipm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.entity.CommentEntity;
import java.util.List;

/**
 * Created by chenjensen on 16/3/3.
 */
public class CommentAdapter extends BaseAdapter {

    private List<CommentEntity> mlist;
    private Context mContext;
    private int mResourceID;
    private LayoutInflater mInflater;
    class ViewHolder{
        private TextView userName;
        private TextView userComment;
        private TextView userLike;
        private ImageView userPhoto;
    }

    public CommentAdapter(Context context,List<CommentEntity> list,int resourceID){
        mlist = list;
        mContext = context;
        mResourceID = resourceID;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if(convertView==null){
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceID,parent,false);
            mHolder.userPhoto = (ImageView)convertView.findViewById(R.id.user_photo);
            mHolder.userName = (TextView)convertView.findViewById(R.id.user_name);
            mHolder.userLike = (TextView)convertView.findViewById(R.id.user_like);
            mHolder.userComment = (TextView)convertView.findViewById(R.id.user_comment);
            convertView.setTag(mHolder);
        }
        else{
            mHolder = (ViewHolder)convertView.getTag();
        }
        mHolder.userName.setText("name");
        mHolder.userComment.setText("fdfdfdfdfdfdfdfdf");
        mHolder.userLike.setText("12");
        mHolder.userPhoto.setImageResource(R.drawable.ic_photo);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
