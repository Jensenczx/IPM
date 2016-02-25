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

public class StartActivity extends BaseActivity {

    private View mView;
    private Animation mAnimation;
    private final int TIME = 800;

    public static final int VIEW_ID = R.layout.activity_start;
    public static final int ANIM_ID = R.anim.alpha;
    public static final Class ACTIVITY_MAIN_CLASS_NAME = MainActivity.class;
    //public static final Class ACTIVITY_GUIDE_CLASS_NAME = GuideActivity.class;

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
                skipActivity(ACTIVITY_MAIN_CLASS_NAME);
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
