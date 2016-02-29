package com.example.chenjensen.ipm;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private static final int TOOBAR_LOGO_RES_ID = R.drawable.toolbar_menu;
    private ActionBarDrawerToggle mDrawerToggle;

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
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name,R.string.hello_world);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
