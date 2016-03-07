package com.example.chenjensen.ipm.fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.LoginActivity;
import com.example.chenjensen.ipm.adapter.ColumnListAdapter;
import com.example.chenjensen.ipm.entity.PageListEntity;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.util.SharedPreferenceHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawLayoutFragment extends Fragment {

    private static final int DRAWLAYOUT_VIEW_ID = R.layout.drawlayout_view_content;
    private static final int LISTVIEW_VIEW_ID = R.id.drawer_layout_item_listview;
    private static final int ITEM_VIEW_LAYOUT = R.layout.item_column_listview;
    private ListView mListView;
    private ColumnListAdapter mAdapter;
    private List<PageListEntity> mList;
    private View mView;
    private CircleImageView mCircleImageView;
    private TextView mHomeTv;
    private TextView mUserNameTv;
    private TextView mUserIntrdTv;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(DRAWLAYOUT_VIEW_ID,container,false);
        mActivity = getActivity();
        getData();
        initView();
        return mView;
    }

    public void initView(){
        mListView = (ListView)mView.findViewById(LISTVIEW_VIEW_ID);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeDraw();
            }
        });
        mListView.setAdapter(mAdapter);
        mCircleImageView = (CircleImageView)mView.findViewById(R.id.profile_image);
        mCircleImageView.setImageBitmap(BitmapHelper.decodeBitmapFromResource(getResources()
                ,R.drawable.ic_photo,72,72));
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!SharedPreferenceHelper.getBooleanValue("islogin"))
                   skipToLoginPage();
                else
                   closeDraw();
            }
        });
        mHomeTv = (TextView)mView.findViewById(R.id.drawer_layout_home_textview);
        mHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDraw();
            }
        });
        mUserNameTv = (TextView)mView.findViewById(R.id.drawer_layout_profile_name_textview);
        mUserIntrdTv = (TextView)mView.findViewById(R.id.drawer_layout_profile_introduce_textview);
    }

    public void getData(){
        mList = new ArrayList<PageListEntity>();
        PageListEntity mEntity = new PageListEntity();
        mEntity.setIsFollow(1);
        mEntity.setName("新闻");
        for(int i=0; i<5; i++)
        mList.add(mEntity);
        mAdapter = new ColumnListAdapter(getActivity(),mList,ITEM_VIEW_LAYOUT);
    }

    public void closeDraw(){
        ((MainActivity)mActivity).closeMenu();
    }


    public void skipToLoginPage(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}
