package www.jykj.com.jykj_zxyl.personal.activity;


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
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);  //垂直
        linearLayoutManager.setScrollEnabled(false);
        manRecycleview.setLayoutManager(linearLayoutManager);

        MyLinearManger linearLayoutManager1 = new MyLinearManger(this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);  //垂直
        linearLayoutManager1.setScrollEnabled(false);
        womenRecyeleview.setLayoutManager(linearLayoutManager1);
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
//        mPresenter.getReferenceData(getParams(0));
        ArrayList<RefrecenmapBean> strings = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            RefrecenmapBean refrecenmapBean = new RefrecenmapBean();
            strings.add(refrecenmapBean);
        }
        ReferenceMapAdapter riskNomalAdapter = new ReferenceMapAdapter(R.layout.item_referencemap, strings, this);
        manRecycleview.setAdapter(riskNomalAdapter);

        riskNomalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("点击啊啊啊啊    "+position);
                for (int i = 0; i < strings.size(); i++) {
                    strings.get(i).setClick(i == position);
                }
                riskNomalAdapter.notifyDataSetChanged();
            }
        });

        ReferenceMapAdapter riskNomalAdapter1 = new ReferenceMapAdapter(R.layout.item_referencemap, strings, this);
        womenRecyeleview.setAdapter(riskNomalAdapter1);
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {

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

}

