package com.example.chenjensen.ipm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.base.BaseActivity;
import com.example.chenjensen.ipm.util.SharedPreferenceHelper;

public class StartActivity extends BaseActivity {

    private View mView;
    private Animation mAnimation;
    private static final int VIEW_ID = R.layout.activity_start;
    private static final int ANIM_ID = R.anim.alpha;
    private static final Class ACTIVITY_MAIN_CLASS_NAME = MainActivity.class;
    private static final Class ACTIVITY_GUIDE_CLASS_NAME = GuideActivity.class;
    private static final String IS_FIRST_OPEN_KEY = "isfirstopen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = View.inflate(this,VIEW_ID,null);
        setContentView(mView);
        playAnimation();
    }

    private void playAnimation(){
        mAnimation = AnimationUtils.loadAnimation(this, ANIM_ID);
        mView.startAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstOpen = SharedPreferenceHelper.getBooleanValue(IS_FIRST_OPEN_KEY);
                if (isFirstOpen) {
                    skipActivity(ACTIVITY_MAIN_CLASS_NAME);
                }else{
                    skipActivity(ACTIVITY_GUIDE_CLASS_NAME);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    private void skipActivity(Class mClass){
        Intent mIntent = new Intent();
        mIntent.setClass(this,mClass);
        startActivity(mIntent);
        this.finish();
    }

}
