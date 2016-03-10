package com.example.chenjensen.ipm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.chenjensen.ipm.AppConfig;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.imageloader.BitmapHelper;
import com.example.chenjensen.ipm.imageloader.ImageLoader;
import com.example.chenjensen.ipm.net.MyWebViewClient;
import com.example.chenjensen.ipm.util.DisplayHelper;

public class EssayActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String mUrl;
    private String mTitle;
    private String mPageUrl;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        getData();
        initView();
    }

    public void getData(){
        Intent intent = getIntent();
        mPageUrl = intent.getStringExtra(AppConfig.ESSAY_PAGE_URL);
        mBitmap = ImageLoader.getInstance().loadBitmap(mPageUrl, DisplayHelper.w_screen, 230);
        mTitle = intent.getStringExtra(AppConfig.ESSAY_TITLE);
        mUrl = intent.getStringExtra(AppConfig.ESSAY_CONTENT_URL);
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.essay_activity_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip2MainActivity();
            }
        });
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle(mTitle);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandtextview);
        Drawable d = getResources().getDrawable(R.drawable.show1);
        if(mBitmap!=null){
            d = new BitmapDrawable(getResources(), mBitmap);
        }
        mCollapsingToolbarLayout.setBackgroundDrawable(d);
        WebView mWebView = (WebView)findViewById(R.id.essay_activity_webview);
        mWebView.setEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
         // 开启database storage API功能
         mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadUrl(mUrl);
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.essay_activity_floatbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip2CommentActivity();
            }
        });
    }

    private void skip2CommentActivity(){
        Intent intent = new Intent();
        intent.setClass(this,CommentActivity.class);
        startActivity(intent);
    }

    private void skip2MainActivity(){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_essay, menu);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
    }
}
