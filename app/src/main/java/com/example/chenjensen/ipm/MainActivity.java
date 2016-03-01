package com.example.chenjensen.ipm;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ListView;
import com.example.chenjensen.ipm.adapter.EssayListviewAdapter;
import com.example.chenjensen.ipm.entity.EssayEntity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private static final int TOOBAR_LOGO_RES_ID = R.drawable.toolbar_menu;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<EssayEntity> mList;
    private EssayListviewAdapter mEssayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_activity_drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.main_activity_toolbar);
        mToolbar.setNavigationIcon(TOOBAR_LOGO_RES_ID);
        mToolbar.setTitle("眼界");
        //设置支持actionbar
        setSupportActionBar(mToolbar);
        //用来通过actionbar控制导航栏
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name,R.string.hello_world);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.main_activity_swiperefrshlayout);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);
            }
        });
       initListView();
    }

    public void initListView(){
        initData();
        mListView = (ListView)findViewById(R.id.main_activity_listview);
        mListView.setAdapter(mEssayAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem==0)
                    mSwipeRefreshLayout.setEnabled(true);
                else
                    mSwipeRefreshLayout.setEnabled(false);
            }
        });

    }

    public void initData(){
        EssayEntity entity = new EssayEntity();
        mList = new ArrayList<EssayEntity>();
        for(int i=0; i<10; i++)
            mList.add(entity);
        mEssayAdapter = new EssayListviewAdapter(this,mList,R.layout.item_listview);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
