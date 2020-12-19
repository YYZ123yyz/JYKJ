package www.jykj.com.jykj_zxyl.personal.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import android.view.View;


import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.RefrecenmapBean;
import www.jykj.com.jykj_zxyl.mypatient.adapter.ReferenceMapAdapter;
import www.jykj.com.jykj_zxyl.mypatient.adapter.RiskNomalAdapter;
import www.jykj.com.jykj_zxyl.personal.ReferenceContract;
import www.jykj.com.jykj_zxyl.personal.ReferencePresenter;
import www.jykj.com.jykj_zxyl.util.widget.MyLinearManger;


public class ReferenceMapActivity extends AbstractMvpBaseActivity<ReferenceContract.View
        , ReferencePresenter> implements ReferenceContract.View {

    @BindView(R.id.man_recycle)
    RecyclerView manRecycleview;
    @BindView(R.id.women_recycle)
    RecyclerView womenRecyeleview;
    private JYKJApplication mApp;
    private ArrayList<RefrecenmapBean> manBeans;
    private ArrayList<RefrecenmapBean> womenBeans;
    private ReferenceMapAdapter manAdapter;
    private ReferenceMapAdapter womenAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_referencemap;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        MyLinearManger linearLayoutManager = new MyLinearManger(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        manRecycleview.setLayoutManager(linearLayoutManager);

        MyLinearManger linearLayoutManager1 = new MyLinearManger(this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager1.setScrollEnabled(false);
        womenRecyeleview.setLayoutManager(linearLayoutManager1);
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getReferenceData(getParams(0));
        manBeans = new ArrayList<>();
        womenBeans = new ArrayList<>();


        manAdapter = new ReferenceMapAdapter(R.layout.item_referencemap, manBeans, this);
        manRecycleview.setAdapter(manAdapter);

        manAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("点击啊啊啊啊    "+position);
                for (int i = 0; i < manBeans.size(); i++) {
                    manBeans.get(i).setClick(i == position);
                }
                manAdapter.notifyDataSetChanged();
            }
        });

        womenAdapter = new ReferenceMapAdapter(R.layout.item_referencemap, womenBeans, this);
        womenRecyeleview.setAdapter(womenAdapter);
    }

    @OnClick({R.id.submit,R.id.set_signtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_signtv:
                startActivity(new Intent(ReferenceMapActivity.this,WarningActivity.class));
                break;
            case R.id.submit:

                break;
        }

    }

    private String getParams(int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginUserPosition", ParameUtil.loginDoctorPosition);
        if (type == 0) {
            map.put("doctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        } else {

        }


        return RetrofitUtil.encodeParam(map);
    }

    @Override
    public void getManDataSucess(ArrayList<RefrecenmapBean> manBean) {
        LogUtils.e("xxxxx"+manBean.size());
        manBeans.addAll(manBean);
        manAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWomenDataSucess(ArrayList<RefrecenmapBean> womenBean) {
        LogUtils.e("xxxxx"+womenBean.size());
        womenBeans.addAll(womenBean);
        womenAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMsg(String msg) {

    }
}

