package com.example.chenjensen.ipm;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import com.example.chenjensen.ipm.fragment.ColumnFragment;
import com.example.chenjensen.ipm.fragment.MainFragment;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_VIEWPAGE_MSG_WHAT = 2;
    private static final int TOOBAR_LOGO_RES_ID = R.drawable.ic_menu;
    private static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Fragment mHomeFragment;
    private Fragment mColumnFragment;
    private FragmentManager mFragmentManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout mContentFrameLayout;
    private long firstTime;
    private Snackbar sb;
    private int curID=0;


    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEWPAGE_MSG_WHAT:
                    refreshViewPager();
                default:break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mHomeFragment = new MainFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.main_activity_content_framelayout, mHomeFragment, HOME_FRAGMENT_TAG).commit();
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
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainFragment)mFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG)).getDataFromNetWork();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);

            }
        });
        mContentFrameLayout = (FrameLayout)findViewById(R.id.main_activity_content_framelayout);
    }

    public void refreshViewPager(){
        if(mFragmentManager!=null){
            if(((MainFragment)mFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG))!=null)
                ((MainFragment)mFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG)).refreshViewPager();
        }
    }

    public void closeMenu(){
        if(mDrawerLayout!=null)
        mDrawerLayout.closeDrawers();
    }

    public int getcurID(){
       return curID;
    }

    public void replaceFragment(Bundle bundle){
        if(curID==0){
            if(mColumnFragment == null){
                mColumnFragment = new ColumnFragment();
            }
            mColumnFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(
                    R.id.main_activity_content_framelayout,mColumnFragment).commit();
            curID = 1;
        }else{
            if(mHomeFragment==null){
                mHomeFragment = new MainFragment();
            }
            mFragmentManager.beginTransaction().replace(R.id.main_activity_content_framelayout,mHomeFragment).commit();
            curID = 0;
        }

    }

    public void setmSwipeRefreshLayout(boolean enable){
        mSwipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeMenu();
        } else if(curID==1){
            replaceFragment(null);
        } else{
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                sb = Snackbar.make(mContentFrameLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(R.color.black));
                sb.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sb.dismiss();
                    }
                });
                sb.setActionTextColor(getResources().getColor(R.color.white));
                sb.show();
                firstTime = secondTime;
            } else {
                finish();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
