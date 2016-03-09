package com.example.chenjensen.ipm.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.activity.LoginActivity;

/**
 */
public class LoginFragment extends Fragment {
    private View mView;
    private EditText mAccountEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;
    private Button mRegisterBtn;
    private String mAccount;
    private String mPassword;
    private Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login,null);
        mActivity = getActivity();
        initView();
        return mView;
    }

    public void initView(){
        mAccountEt = (EditText)mView.findViewById(R.id.login_fragment_account);
        mPasswordEt = (EditText)mView.findViewById(R.id.login_fragment_password);
        mLoginBtn = (Button)mView.findViewById(R.id.login_fragment_login_button);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRegisterBtn = (Button)mView.findViewById(R.id.login_fragment_register_button);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip2RegisterFragment();
            }
        });
    }

    public void skip2RegisterFragment(){
        ((LoginActivity)mActivity).skip2RegisterFragment();
    }


    public void getLoginInfo(){

    }


}
