package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.AddPatientAdapter;

/**
 * 添加患者
 */
public class AddPatientActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private AddPatientAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        initView();
        initListener();
    }

    private void initView(){
        llBack = findViewById(R.id.ll_back);
        mRecyclerView = findViewById(R.id.rv_paint_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AddPatientAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
