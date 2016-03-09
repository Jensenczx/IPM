package com.example.chenjensen.ipm.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.net.MyWebViewClient;

public class EssayActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        initView();
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
        mCollapsingToolbarLayout.setTitle("如果这个标题非常的长长长长度");
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandtextview);
        mCollapsingToolbarLayout.setBackgroundResource(R.drawable.show1);
        WebView mWebView = (WebView)findViewById(R.id.essay_activity_webview);
        mWebView.setEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.loadDataWithBaseURL("https://segmentfault.com/u/jensenczx", "<a href=\"/user/note\"></a>","text/html","utf-8",null);
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

}
