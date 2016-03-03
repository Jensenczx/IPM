package com.example.chenjensen.ipm;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chenjensen.ipm.activity.EssayActivity;
import com.example.chenjensen.ipm.adapter.EssayListviewAdapter;
import com.example.chenjensen.ipm.adapter.HeaderViewPagerAdapter;
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
    //ListView 头部图片集合
    private List<ImageView> mImageViewList;
    /**ListView头部适配器*/
    private HeaderViewPagerAdapter mHeadViewPagerAdapter;
    private TextView mHeaderViewTV;
    /** 小圆点的父控件 */
    private LinearLayout mHeaderViewLL;
    /**ListView 头部布局*/
    private View mHeadView;
    /**ListView 头部布局中ViewPager*/
    private ViewPager mHeadViewViewPager;
    private int currentItem=0;
    /** Banner文字描述数组 */
    private String[] bannerTextDescArray = {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来了，再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送", "热血屌丝的反杀"
    };

    /** Banner滚动线程是否销毁的标志，默认不销毁 */
    private boolean isStop = false;

    /** Banner的切换下一个page的间隔时间 */
    private long scrollTimeOffset = 5000;

    private Handler mHandler = new Handler(){

    };


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
                (new Handler()).postDelayed(new Runnable() {
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
        //ListView初始化和相关设置
        mListView = (ListView)findViewById(R.id.main_activity_listview);
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
                    mSwipeRefreshLayout.setEnabled(enable);
                }

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                skip();
            }
        });

    }

    public void initData(){
        //初始化文章列表数据
        EssayEntity entity = new EssayEntity();
        mList = new ArrayList<EssayEntity>();
        for(int i=0; i<10; i++)
            mList.add(entity);
        mEssayAdapter = new EssayListviewAdapter(this,mList,R.layout.item_essay_listview);
        //初始化headview数据
        mImageViewList = new ArrayList<ImageView>();
        int[] imageIDs = new int[] {
                R.drawable.show1,
                R.drawable.show2,
                R.drawable.show1,
        };
        //初始化HeadView
        LayoutInflater mInflater = LayoutInflater.from(this);
        mHeadView = mInflater.inflate(R.layout.header_view_listview,null);
        mHeadViewViewPager = (ViewPager)mHeadView.findViewById(R.id.headview_viewpager);
        mHeaderViewTV = (TextView)mHeadView.findViewById(R.id.headerview_title_textview);
        mHeaderViewLL = (LinearLayout)mHeadView.findViewById(R.id.headerview_dot_group_ll);
        ImageView imageView = null;
        View dot = null;
        LayoutParams params = null;
        for (int id : imageIDs) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            mImageViewList.add(imageView);
            // 每循环一次添加一个点到线行布局中
            dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot_bg_selector);
            params = new LayoutParams(20, 20);
            params.leftMargin = 10;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            mHeaderViewLL.addView(dot); // 向线性布局中添加"点"
        }
        mHeadViewPagerAdapter = new HeaderViewPagerAdapter(mImageViewList);
        mHeadViewViewPager.setAdapter(mHeadViewPagerAdapter);
        mHeaderViewTV.setText(bannerTextDescArray[0]);
        mHeaderViewLL.getChildAt(0).setEnabled(true);
        mHeadViewViewPager.setCurrentItem(currentItem);
        mHeadViewViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPositon = position % mImageViewList.size();
                // 根据索引设置图片的描述
                mHeaderViewTV.setText(bannerTextDescArray[newPositon]);
                // 把上一个点设置为被选中
                mHeaderViewLL.getChildAt(currentItem).setEnabled(false);
                // 根据索引设置那个点被选中
                mHeaderViewLL.getChildAt(newPositon).setEnabled(true);
                // 新索引赋值给上一个索引的位置
                currentItem = newPositon;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startPlay();
    }

    private void startPlay() {
        isStop = false;
        mHandler.postDelayed(task, 3000);
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (!isStop) {
                currentItem = (currentItem+1) % (mImageViewList.size());
                mHeadViewViewPager.setCurrentItem(currentItem);
                mHandler.postDelayed(task, 5000);
            }
        }
    };

    private void skip(){
        Intent intent = new Intent();
        intent.setClass(this, EssayActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        isStop = true;
        super.onDestroy();
    }
}
