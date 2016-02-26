package com.example.chenjensen.ipm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.adapter.ViewPagerAdapter;
import com.example.chenjensen.ipm.base.BaseActivity;
import com.example.chenjensen.ipm.util.SharedPreferenceHelper;
import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private List<View> list;
    private ImageView[]ivArray;
    private int currentNum;
    private ViewPager mViewPager;
    private static final int VIEW_ONE_ID = R.layout.guide_view_one;
    private static final int VIEW_TWO_ID = R.layout.guide_view_two;
    private static final int VIEW_THREE_ID = R.layout.guide_view_three;
    private static final int VIEW_THREE_BUTTON_ID = R.id.guide_view_three_start_button;
    private static final int DOT_SELECTED_ID = R.drawable.dot_selected;
    private static final int DOT_UNSELECTED_ID = R.drawable.dot_unselected;
    private static final String IS_FIST_OPEN_KEY = "isfistopen";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initDot();
    }

    public void initView(){
        mViewPager = (ViewPager)findViewById(R.id.guide_viewpager);
        //添加view界面
        LayoutInflater mInflater = getLayoutInflater();
        View mOneView = mInflater.inflate(VIEW_ONE_ID, null);
        View mTwoView = mInflater.inflate(VIEW_TWO_ID, null);
        View mThreeView = mInflater.inflate(VIEW_THREE_ID,null);
        Button btnStart = (Button)mThreeView.findViewById(VIEW_THREE_BUTTON_ID);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordHasStarted();
                skip();
            }
        });
        list = new ArrayList<View>();
        list.add(mOneView);
        list.add(mTwoView);
        list.add(mThreeView);
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initDot(){
        LinearLayout mLinearLayout = (LinearLayout)findViewById(R.id.guide_dot_linearlayout);
        ivArray = new ImageView[3];
        for(int i=0; i<3; i++){
            ivArray[i] = (ImageView)mLinearLayout.getChildAt(i);
        }
        currentNum = 0;
        ivArray[currentNum].setImageResource(DOT_SELECTED_ID);
    }

    //设置当前圆点
    public void setCurrentDot(int position){
        if(position>=3||position<0)
            return;
        ivArray[position].setImageResource(DOT_SELECTED_ID);
        ivArray[currentNum].setImageResource(DOT_UNSELECTED_ID);
        currentNum = position;
    }

    public void recordHasStarted(){
        SharedPreferenceHelper.setBooleanValue(IS_FIST_OPEN_KEY,true);
    }

    public void skip(){
        Intent mIntent = new Intent();
        mIntent.setClass(this, MainActivity.class);
        startActivity(mIntent);
        this.finish();
    }


}
