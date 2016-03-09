package com.example.chenjensen.ipm.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.EssayActivity;
import com.example.chenjensen.ipm.adapter.EssayListviewAdapter;
import com.example.chenjensen.ipm.entity.EssayEntity;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.util.DisplayHelper;

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

    public void setSRLEnabled(boolean enable){
        ((MainActivity) mActivity).setmSwipeRefreshLayout(enable);
    }

    public void skip2EssayActivity(){
        Intent intent = new Intent();
        intent.setClass(mActivity, EssayActivity.class);
        startActivity(intent);
    }

    public void initData(){
        //初始化文章列表数据
        EssayEntity entity = new EssayEntity();
        mList = new ArrayList<EssayEntity>();
        for(int i=0; i<10; i++)
            mList.add(entity);
        mEssayAdapter = new EssayListviewAdapter(mActivity,mList,R.layout.item_essay_listview);
        //初始化headview数据
        mHeadView = mInflater.inflate(R.layout.header_view_list_class,null);
        mHeadviewIv = (ImageView)mHeadView.findViewById(R.id.headview_listview_class_imageview);
        mHeadviewTv = (TextView)mHeadView.findViewById(R.id.headervie_listview_class_textview);
        mHeadviewIv.setImageBitmap(BitmapHelper.decodeBitmapFromResource(getResources(),R.drawable.show1,DisplayHelper.w_screen,200));
        mHeadviewTv.setText("心理学专栏");
    }


}
