package com.example.chenjensen.ipm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.chenjensen.ipm.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mAccountEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;
    private Button mRegisterBtn;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView(){
        mAccountEt = (EditText)findViewById(R.id.login_activity_account_edittext);
        mPasswordEt = (EditText)findViewById(R.id.login_activity_password_edittext);
        mLoginBtn = (Button)findViewById(R.id.login_activity_login_button);
        mToolbar = (Toolbar)findViewById(R.id.login_activity_toolbar);
        mRegisterBtn = (Button)findViewById(R.id.login_activity_register_button);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle(getResources().getString(R.string.text_login));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

}
