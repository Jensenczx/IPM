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
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(mToolbar);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("zhejiushi");
        WebView mWebView = (WebView)findViewById(R.id.essay_activity_webview);
        mWebView.setEnabled(true);
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.loadUrl("http://www.cnblogs.com/menlsh/p/3139498.html");
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.essay_activity_floatbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });

    }

    private void skip(){
        Intent intent = new Intent();
        intent.setClass(this,CommentActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_essay, menu);
        return true;
    }

}
