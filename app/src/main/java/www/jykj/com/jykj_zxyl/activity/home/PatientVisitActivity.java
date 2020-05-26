package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.PaintVisitAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 患者就诊
 */
public class PatientVisitActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private PaintVisitAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_visit);
        ActivityUtil.setStatusBarMain(PatientVisitActivity.this);

        initView();
        initListener();
    }

    private void initView(){
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_paint_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PaintVisitAdapter();
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
