package www.jykj.com.jykj_zxyl.activity.home.tjhz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.ApplicationAuditRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZNoFinishRecycleAdapter;

/**
 * 添加患者==>申请审核
 */
    public class ApplicationAuditActivity extends AppCompatActivity {

        private             Context                     mContext;
        private             RecyclerView                mApplicationList;                  //申请审核

        private             LinearLayoutManager         layoutManager;
        private             ApplicationAuditRecycleAdapter mApplicationAuditRecycleAdapter;       //适配器
        private             List<HZIfno>                mHZEntyties = new ArrayList<>();            //所有数据

        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationaudit);
        mContext = this;
        initLayout();
    }

    /**
     * 初始化界面
     */
    private void initLayout() {
        mApplicationList = (RecyclerView) this.findViewById(R.id.rv_activityApplicationAudit_applicationList);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mApplicationList.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mApplicationList.setHasFixedSize(true);
        //创建并设置Adapter
        mApplicationAuditRecycleAdapter = new ApplicationAuditRecycleAdapter(mHZEntyties,mContext);
        mApplicationList.setAdapter(mApplicationAuditRecycleAdapter);
        mApplicationAuditRecycleAdapter.setOnItemClickListener(new ApplicationAuditRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,ApplicationAuditDealActivity.class));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }



    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityAddPatient_applicationAudit:
                    break;

            }
        }
    }


}
