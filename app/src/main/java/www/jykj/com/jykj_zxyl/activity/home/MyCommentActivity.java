package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MyCommentAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class MyCommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyCommentAdapter myCommentAdapter;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        ActivityUtil.setStatusBarMain(MyCommentActivity.this);

        initView();
        initListener();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comment);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        myCommentAdapter = new MyCommentAdapter();
        mRecyclerView.setAdapter(myCommentAdapter);
    }

    private void initListener(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
