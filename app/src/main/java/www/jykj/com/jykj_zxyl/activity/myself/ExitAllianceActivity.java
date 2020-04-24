package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.AllianceEntity;
import entity.PBInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.adapter.ExitAllianceRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MyPBRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 退出联盟
 */
public class ExitAllianceActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 ExitAllianceActivity        mActivity;
    private                 RecyclerView                mAllianceRecycleView;             //联盟列表
    private                 LinearLayoutManager         layoutManager;
    private                 ExitAllianceRecycleAdapter  mExitAllianceRecycleAdapter;       //适配器
    private                 List<AllianceEntity>        mAllianceEntitys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_exitalliance);
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
        mAllianceRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityExitAlliance_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mAllianceRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mAllianceRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mExitAllianceRecycleAdapter = new ExitAllianceRecycleAdapter(mAllianceEntitys,mContext,mActivity);
        mAllianceRecycleView.setAdapter(mExitAllianceRecycleAdapter);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_myAccount_mySurplus:
                    //我的余额
                    Intent intent = new Intent();
                    intent.setClass(mContext,MySurplusActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textView_activityLogin_userRegistTextView:
                    //用户注册
                    intent = new Intent();
                    intent.setClass(mContext,UseRegistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_activityLogin_LoginButton:
                    //登录
                    intent = new Intent();
                    intent.setClass(mContext,MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {
        AllianceEntity allianceEntity = new AllianceEntity();
        allianceEntity.setAllianceName("李飞的联盟");
        allianceEntity.setAllianceRemark("省第三人民医院");
        mAllianceEntitys.add(allianceEntity);

        AllianceEntity allianceEntity01 = new AllianceEntity();
        allianceEntity01.setAllianceName("张三的联盟");
        allianceEntity01.setAllianceRemark("昆医附二院");
        mAllianceEntitys.add(allianceEntity01);

        mExitAllianceRecycleAdapter.setDate(mAllianceEntitys);
        mExitAllianceRecycleAdapter.notifyDataSetChanged();
    }



}
