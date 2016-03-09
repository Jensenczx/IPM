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
        private ImageView userLikeIv;
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
        final ViewHolder mHolder;
        if(convertView==null){
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceID,parent,false);
            mHolder.userPhoto = (ImageView)convertView.findViewById(R.id.user_photo);
            mHolder.userName = (TextView)convertView.findViewById(R.id.user_name);
            mHolder.userLike = (TextView)convertView.findViewById(R.id.user_like);
            mHolder.userComment = (TextView)convertView.findViewById(R.id.user_comment);
            mHolder.userLikeIv = (ImageView)convertView.findViewById(R.id.user_like_image);
            convertView.setTag(mHolder);
        }
        else{
            mHolder = (ViewHolder)convertView.getTag();
        }
        mHolder.userName.setText("name");
        mHolder.userComment.setText("，这是一个高质量的技术干货分享社区，web前端、Android、iOS、设计资源和产品，满足你的学习欲望。\n" +
                "\n" +
                "这篇文章因为是台湾人写的，语言风格很别致。本文在原文的基础上做了一些微调（主要是繁体字的问题）。\n" +
                "\n" +
                "今年(2014) 的 google i/o 发表令多数人为之一亮的 material design，而 google 也从「google i/o 2014」 开始，大家也陆陆续续地看到其更新的 android app 皆套用了这个设计介面。当然，这个设计介面著实让大家感到惊艳外" +
                "，更让 android 开发者开始担心未来 app 的界面处理了。\n");
        mHolder.userLike.setText("12");
        mHolder.userLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.userLikeIv.setImageResource(R.drawable.ic_like);
            }
        });
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
