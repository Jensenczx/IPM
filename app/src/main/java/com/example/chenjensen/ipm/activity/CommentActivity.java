package com.example.chenjensen.ipm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.adapter.CommentAdapter;
import com.example.chenjensen.ipm.entity.CommentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CommentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private List<CommentEntity> mList;
    private String mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.comment_activity_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.action_comment));
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip2EssayActivity();
            }
        });
        mListView = (ListView)findViewById(R.id.comment_activity_listview);
        mList = new ArrayList<CommentEntity>();
        CommentEntity entity = new CommentEntity();
        for(int i=0; i<5; i++)
            mList.add(entity);
        CommentAdapter adapter = new CommentAdapter(this,mList,R.layout.item_comment_listview);
        mListView.setAdapter(adapter);
    }

    public void skip2EssayActivity(){
        Intent intent = new Intent();
        intent.setClass(this,EssayActivity.class);
        startActivity(intent);
        finish();
    }

    public void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_comment);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input_view
                ,null);
        builder.setView(view);
        final EditText editText = (EditText)view.findViewById(R.id.dialog_edittext);
        builder.setPositiveButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.dialog_send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mComment = editText.getText().toString();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_write_comment)
            showInputDialog();
        return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

}
