package com.example.chenjensen.ipm.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chenjensen.ipm.MainActivity;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.fragment.LoginFragment;
import com.example.chenjensen.ipm.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FragmentManager mManager;
    private Fragment loginFragment;
    private Fragment registerFragment;
    private String mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mManager = getSupportFragmentManager();
        loginFragment = new LoginFragment();
        mManager.beginTransaction().replace(R.id.login_activity_content,
                loginFragment).commit();
        initView();
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.login_activity_toolbar);
        mToolbar.setTitle("登陆");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip2MainActivity();
            }
        });
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    public void skip2MainActivity(){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void skip2RegisterFragment(){
        if(registerFragment==null)
            registerFragment = new RegisterFragment();
        mManager.beginTransaction().replace(R.id.login_activity_content,registerFragment).commit();
        mToolbar.setTitle("注册");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

}
