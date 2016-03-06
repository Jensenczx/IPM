package com.example.chenjensen.ipm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.adapter.CommentAdapter;
import com.example.chenjensen.ipm.entity.CommentEntity;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private List<CommentEntity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.comment_activity_toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("评论");
        setSupportActionBar(mToolbar);
        mListView = (ListView)findViewById(R.id.comment_activity_listview);
        mList = new ArrayList<CommentEntity>();
        CommentEntity entity = new CommentEntity();
        for(int i=0; i<5; i++)
            mList.add(entity);
        CommentAdapter adapter = new CommentAdapter(this,mList,R.layout.item_comment_listview);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
