package www.jykj.com.jykj_zxyl.capitalpool.activity;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.UserAccountAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountPresenter;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAccountActivity extends AbstractMvpBaseActivity<UserAccountContract.View
        , UserAccountPresenter> implements UserAccountContract.View {

    @BindView(R.id.recycleview)
    RecyclerView myRecyleview;
    @BindView(R.id.ali_iv_msg)
    ImageView aliMsgIv;
    @BindView(R.id.weicha_iv_msg)
    ImageView weicatMsgIv;
    private JYKJApplication mApp;
    private ArrayList<WithdrawTypelListBean> dataList;
    private UserAccountAdapter userAccountAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_account;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        myRecyleview.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();


        userAccountAdapter = new UserAccountAdapter(R.layout.item_account, dataList);
        userAccountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setClick(i == position);
                }

            }
        });
        myRecyleview.setAdapter(userAccountAdapter);
    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getDoctorInfo(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        return RetrofitUtil.encodeParam(stringStringHashMap);
    }


    @OnClick({R.id.weichat_rela, R.id.ali_layout, R.id.addbank_card_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weichat_rela:
                Intent intent = new Intent(UserAccountActivity.this, CollectionCodeActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.ali_layout:
                Intent intent1 = new Intent(UserAccountActivity.this, CollectionCodeActivity.class);
                intent1.putExtra("type", 2);
                startActivity(intent1);
                break;
            case R.id.addbank_card_layout:
                startActivity(new Intent(UserAccountActivity.this, AddBankcardActivity.class));
                break;

        }

    }

    @Override
    public void getNodata() {
        myRecyleview.setVisibility(View.GONE);
        aliMsgIv.setVisibility(View.GONE);
        weicatMsgIv.setVisibility(View.GONE);
    }

    @Override
    public void getDataSucess(List<WithdrawTypelListBean> data) {
        if (dataList != null && dataList.size() > 0) {
            dataList.clear();
        }
        if (data != null) {
            dataList.addAll(data);
            userAccountAdapter.notifyDataSetChanged();
        }

    }
}

