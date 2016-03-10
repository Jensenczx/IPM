package com.example.chenjensen.ipm.fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chenjensen.ipm.AppConfig;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.LoginActivity;
import com.example.chenjensen.ipm.adapter.ColumnListAdapter;
import com.example.chenjensen.ipm.entity.ColumnEntity;
import com.example.chenjensen.ipm.entity.UserEntity;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.imageloader.ImageLoader;
import com.example.chenjensen.ipm.net.GsonRequest;
import com.example.chenjensen.ipm.net.VolleyManager;
import com.example.chenjensen.ipm.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawLayoutFragment extends Fragment {

    private static final String TAG = "DRAWLAYOUTFRAGMENT";
    private static final int DRAWLAYOUT_VIEW_ID = R.layout.drawlayout_view_content;
    private static final int LISTVIEW_VIEW_ID = R.id.drawer_layout_item_listview;
    private static final int ITEM_VIEW_LAYOUT = R.layout.item_column_listview;
    private static final String USER_INFO_URL = "";
    private static final String COLUMN_LIST_URL = "";
    private ListView mListView;
    private ColumnListAdapter mAdapter;
    private List<ColumnEntity> mList;
    private View mView;
    private CircleImageView mCircleImageView;
    private TextView mHomeTv;
    private TextView mUserNameTv;
    private TextView mUserIntrdTv;
    private Activity mActivity;
    private VolleyManager volleyManager;
    private UserEntity mUserEntity;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                updateTheUserInfo();
                AppConfig.saveUserInfo(mUserEntity);
                ToastUtil.showToast("加载");
            }
        }
    };

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
                Bundle bundle = new Bundle();
                bundle.putString(AppConfig.COLUMN_NAME,AppConfig.COLUMN_ARRAY[position]);
                openTheColumnFragment(bundle);
                closeDraw();
            }
        });
        mListView.setAdapter(mAdapter);
        mCircleImageView = (CircleImageView)mView.findViewById(R.id.profile_image);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppConfig.isLogin())
                    skipToLoginPage();
                else
                    closeDraw();
            }
        });
        mHomeTv = (TextView)mView.findViewById(R.id.drawer_layout_home_textview);
        mHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTheHomeFragment(null);
                closeDraw();
            }
        });
        mUserNameTv = (TextView)mView.findViewById(R.id.drawer_layout_profile_name_textview);
        mUserIntrdTv = (TextView)mView.findViewById(R.id.drawer_layout_profile_introduce_textview);
        mCircleImageView.setImageBitmap(BitmapHelper.decodeBitmapFromResource(getResources()
                , R.drawable.ic_photo, 72, 72));
        updateTheUserInfo();
    }

    public void updateTheUserInfo(){
        if(mUserEntity!=null){
            ImageLoader.getInstance().bindBitmap(mUserEntity.getPhoto(), mCircleImageView, 72, 72);
            mUserNameTv.setText(mUserEntity.getName());
            mUserIntrdTv.setText(mUserEntity.getIntroduce());
        }
    }

    public void getUserData(){
        mUserEntity = AppConfig.getUserInfo();
        if(mUserEntity==null){
            if(!AppConfig.isNetworkAvailable())
                ToastUtil.showToast(R.string.toast_net_work_not_avaiable);
            else
                getUserDataFromNetwork();
        }
    }

    public void getUserDataFromNetwork(){
        String url = "http://115.28.174.38:5000/";
        volleyManager = VolleyManager.getSingleInstance();
        GsonRequest<UserEntity> gsonRequest = new GsonRequest<UserEntity>(url, UserEntity.class, new Response.Listener<UserEntity>() {
            @Override
            public void onResponse(UserEntity response) {
                mUserEntity = response;
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.sendToTarget();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        volleyManager.addRequest(gsonRequest);

    }

    public void openTheHomeFragment(Bundle bundle){
        if(((MainActivity)getActivity()).getcurID()==1)
        ((MainActivity)getActivity()).replaceFragment(bundle);
    }

    public void openTheColumnFragment(Bundle bundle){
        ((MainActivity)getActivity()).replaceFragment(bundle);
    }

    public void getData(){
        getUserData();
        mList = new ArrayList<ColumnEntity>();
        for(int i=0; i<AppConfig.COLUMN_ARRAY.length; i++){
            String tmp = AppConfig.COLUMN_ARRAY[i];
            ColumnEntity entity = new ColumnEntity();
            entity.setIsFollow(AppConfig.isFollow(tmp));
            entity.setName(tmp);
            mList.add(entity);
        }
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
