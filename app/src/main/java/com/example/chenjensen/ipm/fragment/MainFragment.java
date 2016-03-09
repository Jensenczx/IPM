package com.example.chenjensen.ipm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.EssayActivity;
import com.example.chenjensen.ipm.adapter.EssayListviewAdapter;
import com.example.chenjensen.ipm.adapter.HeaderViewPagerAdapter;
import com.example.chenjensen.ipm.entity.EssayEntity;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.util.DisplayHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private View mView;
    private ListView mListView;
    private LayoutInflater mInflater;
    private View mHeadView;
    private EssayListviewAdapter mEssayAdapter;
    private HeaderViewPagerAdapter mHeadViewPagerAdapter;
    private Activity mActivity;
    private List<EssayEntity> mList;
    private List<ImageView> mImageViewList;
    private ViewPager mHeadViewViewPager;
    private TextView mHeaderViewTV;
    private LinearLayout mHeaderViewLL;
    private int currentItem=0;
    private boolean isStop = false;
    private static final int UPDATE_VIEWPAGE_MSG_WHAT = 2;

    /** Banner文字描述数组 */
    private String[] bannerTextDescArray = {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来了，再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送", "热血屌丝的反杀"
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

    public void setSRLEnabled(boolean enable){
        ((MainActivity) mActivity).setmSwipeRefreshLayout(enable);
    }

    public void skip2EssayActivity(){
        Intent intent = new Intent();
        intent.setClass(mActivity, EssayActivity.class);
        startActivity(intent);
    }

    public void refreshViewPager(){
        mHeaderViewLL.getChildAt(currentItem).setEnabled(false);
        // 根据索引设置那个点被选中
        currentItem = (currentItem+1) % (mImageViewList.size());
        mHeaderViewLL.getChildAt(currentItem).setEnabled(true);
        mHeadViewViewPager.setCurrentItem(currentItem);
    }

    public void initData(){
        //初始化文章列表数据
        EssayEntity entity = new EssayEntity();
        mList = new ArrayList<EssayEntity>();
        for(int i=0; i<10; i++)
            mList.add(entity);
        mEssayAdapter = new EssayListviewAdapter(mActivity,mList,R.layout.item_essay_listview);
        //初始化headview数据
        mImageViewList = new ArrayList<ImageView>();
        int[] imageIDs = new int[] {
                R.drawable.show1,
                R.drawable.show2,
                R.drawable.show1,
        };
        //初始化HeadView
        mHeadView = mInflater.inflate(R.layout.header_view_listview_home,null);
        mHeadViewViewPager = (ViewPager)mHeadView.findViewById(R.id.headview_viewpager);
        mHeaderViewTV = (TextView)mHeadView.findViewById(R.id.headerview_title_textview);
        mHeaderViewLL = (LinearLayout)mHeadView.findViewById(R.id.headerview_dot_group_ll);
        ImageView imageView = null;
        View dot = null;
        LinearLayout.LayoutParams params = null;
        for (int id : imageIDs) {
            imageView = new ImageView(mActivity);
            imageView.setImageBitmap(BitmapHelper.decodeBitmapFromResource(getResources(), id, DisplayHelper.w_screen, 200));
            //imageView.setBackgroundResource(id);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(imageView);

            // 每循环一次添加一个点到线行布局中
            dot = new View(mActivity);
            dot.setBackgroundResource(R.drawable.dot_bg_selector);
            params = new LinearLayout.LayoutParams(20, 20);
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
    }

    @Override
    public void onStart() {
        startPlay();
        super.onStart();
    }

    @Override
    public void onStop() {
        isStop = true;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startPlay() {
        new Thread(){
            @Override
            public void run() {
                while(!isStop){
                    try{
                        sleep(3000);
                        Message msg = ((MainActivity)mActivity).mHandler.obtainMessage();
                        msg.what = UPDATE_VIEWPAGE_MSG_WHAT;
                        msg.sendToTarget();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }



}
