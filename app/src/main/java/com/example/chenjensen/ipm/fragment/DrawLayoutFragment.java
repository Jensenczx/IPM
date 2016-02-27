package com.example.chenjensen.ipm.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.adapter.PageListAdapter;
import com.example.chenjensen.ipm.entity.PageListEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawLayoutFragment extends Fragment {

    private static final int DRAWLAYOUT_VIEW_ID = R.layout.drawlayout_view_content;
    private static final int LISTVIEW_VIEW_ID = R.id.drawer_layout_item_listview;
    private static final int ITEM_VIEW_LAYOUT = R.layout.item_pagelist;
    private ListView mListView;
    private PageListAdapter mAdapter;
    private List<PageListEntity> mList;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(DRAWLAYOUT_VIEW_ID,container,false);
        initData();
       initView();
        return mView;
    }

    public void initView(){
        mListView = (ListView)mView.findViewById(LISTVIEW_VIEW_ID);
        mListView.setAdapter(mAdapter);

    }

    public void initData(){
        getData();
        mAdapter = new PageListAdapter(getActivity(),mList,ITEM_VIEW_LAYOUT);
    }

    public void getData(){
        mList = new ArrayList<PageListEntity>();
        PageListEntity mEntity = new PageListEntity();
        mEntity.setIsFollow(1);
        mEntity.setName("新闻");
        for(int i=0; i<5; i++)
        mList.add(mEntity);
    }


}
