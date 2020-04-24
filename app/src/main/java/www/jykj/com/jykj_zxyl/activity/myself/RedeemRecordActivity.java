package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.RedeemProductInfo;
import entity.RedeemRecordEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MyIntegralInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.RedeemRecordRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 积分兑换记录
 */
public class RedeemRecordActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 RedeemRecordActivity        mActivity;
    private                 TextView                    mDetail;                        //明细
    private                 TextView                    mRedeemRecord;                  //积分兑换
    private                 RecyclerView                mRedeemRecordRecycleView;         //积分兑换记录列表
    private                 LinearLayoutManager         layoutManager;
    private                 RedeemRecordRecycleAdapter  mRedeemRecordRecycleAdapter;       //适配器
    private                 List<RedeemRecordEntity>        mRedeemProductInfos = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentself_redeemrecord);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }



    /**
     * 初始化布局
     */
    private void initLayout() {
        mRedeemRecordRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityMyRedeemRecord_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRedeemRecordRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRedeemRecordRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mRedeemRecordRecycleAdapter = new RedeemRecordRecycleAdapter(mRedeemProductInfos,mContext,mActivity);
        mRedeemRecordRecycleView.setAdapter(mRedeemRecordRecycleAdapter);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_myIntegralInfo_detail:
                    Intent intent = new Intent();
                    intent.setClass(RedeemRecordActivity.this,MyIntegralActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_activityMyIntegralInfo_redeemRecord:
                    intent = new Intent();
                    intent.setClass(RedeemRecordActivity.this,RedeemRecordActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }


    private void setData() {
        for (int i = 0; i < 20; i++)
        {
            RedeemRecordEntity redeemRecordEntity = new RedeemRecordEntity();
            redeemRecordEntity.setmRedeemName("汇仁肾宝片");
            redeemRecordEntity.setmRedeemExplain("汇仁肾宝片的功效，要具体判断患者是否用这个药。其实汇仁肾宝片它是中成药，中成药这一类都应该讲究辨证施，治辨证施治就是根据引起性功能下降，性欲下降到底是肾阴虚还是肾阳虚，所以根据肾阴虚肾阳虚的情况要具体用药，决定用药的中药的成分，所以至于功效好不好，要具体判断患者从中医理论...");
            redeemRecordEntity.setmRedeemPrice("6000");
            mRedeemProductInfos.add(redeemRecordEntity);
        }
        mRedeemRecordRecycleAdapter.setDate(mRedeemProductInfos);
        mRedeemRecordRecycleAdapter.notifyDataSetChanged();

    }



}
