package com.example.chenjensen.ipm.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.net.MyWebViewClient;

public class EssayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        initView();
    }

    public void initView(){
        Toolbar mToolbar = (Toolbar)findViewById(R.id.essay_activity_toolbar);
        mToolbar.setNavigationIcon(R.drawable.toolbar_menu);
        setSupportActionBar(mToolbar);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("zhejiushi");
        WebView mWebView = (WebView)findViewById(R.id.essay_activity_webview);
        mWebView.setEnabled(true);
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.loadUrl("http://www.cnblogs.com/menlsh/p/3139498.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_essay, menu);
        return true;
    }

}
