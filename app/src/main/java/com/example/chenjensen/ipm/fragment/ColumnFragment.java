package com.example.chenjensen.ipm.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chenjensen.ipm.AppConfig;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.EssayActivity;
import com.example.chenjensen.ipm.adapter.EssayListviewAdapter;
import com.example.chenjensen.ipm.entity.EssayEntity;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.net.GsonRequest;
import com.example.chenjensen.ipm.net.VolleyManager;
import com.example.chenjensen.ipm.util.DisplayHelper;
import com.example.chenjensen.ipm.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColumnFragment extends Fragment {

    private View mView;
    private ListView mListView;
    private LayoutInflater mInflater;
    private View mHeadView;
    private EssayListviewAdapter mEssayAdapter;
    private Activity mActivity;
    private List<EssayEntity> mList;
    private ImageView mHeadviewIv;
    private TextView mHeadviewTv;
    private VolleyManager mVolleyManager;
    private EssayEntity[] mEssayArray;
    private Bundle mBundle;
    private final static int UPDATE_ESSAY_LISTVIEW = 401;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_ESSAY_LISTVIEW:updateListView();break;
                default:break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mActivity = getActivity();
        mView = inflater.inflate(R.layout.fragment_main,null);
        initListView();
        return mView;
    }

    public void initListView(){
        initData();
        mListView = (ListView)mView.findViewById(R.id.fragment_main_listview);
        mListView.addHeaderView(mHeadView);
        mListView.setAdapter(mEssayAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mListView != null && mListView.getChildCount() > 0) {
                    boolean enable = (firstVisibleItem == 0) && (mListView.getChildAt(firstVisibleItem).getTop() == 0);
                    setSRLEnabled(enable);
                }

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                skip2EssayActivity();
            }
        });
    }
    public void updateListView(){
        for(int i=0; i<mEssayArray.length; i++)
            mList.add(mEssayArray[i]);
        mEssayAdapter.notifyDataSetChanged();
    }


    public void setSRLEnabled(boolean enable){
        ((MainActivity) mActivity).setmSwipeRefreshLayout(enable);
    }

    public void skip2EssayActivity(){
        Intent intent = new Intent();
        intent.setClass(mActivity, EssayActivity.class);
        startActivity(intent);

    }

    public void getDataFromNetWork(){
        mBundle = getArguments();
        String url="http://115.28.174.38:5000/";
        mVolleyManager = VolleyManager.getSingleInstance();
        GsonRequest<EssayEntity[]> request = new GsonRequest<EssayEntity[]>(url,EssayEntity[].class,new Response.Listener<EssayEntity[]>() {
            @Override
            public void onResponse(EssayEntity[] response) {
                mEssayArray = response;
                Message msg = mHandler.obtainMessage();
                msg.what = UPDATE_ESSAY_LISTVIEW;
                msg.sendToTarget();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.toString());
                ToastUtil.showToast(error.toString());
            }
        });
        mVolleyManager.addRequest(request);
    }

    public void initData(){
        getDataFromNetWork();
        //初始化文章列表数据
        EssayEntity entity = new EssayEntity();
        mList = new ArrayList<EssayEntity>();
        mEssayAdapter = new EssayListviewAdapter(mActivity,mList,R.layout.item_essay_listview);
        //初始化headview数据
        mHeadView = mInflater.inflate(R.layout.header_view_list_class,null);
        mHeadviewIv = (ImageView)mHeadView.findViewById(R.id.headview_listview_class_imageview);
        mHeadviewTv = (TextView)mHeadView.findViewById(R.id.headervie_listview_class_textview);
        mHeadviewIv.setImageBitmap(BitmapHelper.decodeBitmapFromResource(getResources(), R.drawable.show1, DisplayHelper.w_screen, 200));
        mHeadviewTv.setText(mBundle.getString(AppConfig.COLUMN_NAME));
    }


}
