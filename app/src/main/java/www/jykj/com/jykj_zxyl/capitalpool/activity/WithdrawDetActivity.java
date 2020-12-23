package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawDetAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetPresenter;
import www.jykj.com.jykj_zxyl.util.Util;

public class WithdrawDetActivity extends AbstractMvpBaseActivity<WithdrawDetContract.View
        , WithdrawDetPresenter> implements WithdrawDetContract.View {

    @BindView(R.id.my_recycleview)
    RecyclerView myRecyeleview;
    @BindView(R.id.tv_date)
    TextView dateTv;
    @BindView(R.id.withdrawState_tv)
    TextView stateTv;


    private JYKJApplication mApp;
    private String countPeriod = "";
    private String withdrawalState = "";
    private int pageNum = 0;
    private int rowNum = 10;
    private List<String> drugDosageNameList;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_withdraw_det;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        dateTv.setText(DateUtils.getDeviceTimeOfYM());
        countPeriod = DateUtils.getDeviceTimeOfYM();
        myRecyeleview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            strings.add("xx");
        }
        WithdrawDetAdapter withdrawDetAdapter = new WithdrawDetAdapter(R.layout.item_withdraw_det, strings);
        myRecyeleview.setAdapter(withdrawDetAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        drugDosageNameList = getDrugDosageNameList();
        mPresenter.getWithdrawDet(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        stringStringHashMap.put("withdrawalState", withdrawalState);
        stringStringHashMap.put("countPeriod", countPeriod);
        stringStringHashMap.put("rowNum", String.valueOf(pageNum));
        stringStringHashMap.put("pageNum", String.valueOf(rowNum));
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @OnClick({R.id.withdraw_tv, R.id.tv_date, R.id.withdrawState_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_tv:
                startActivity(new Intent(WithdrawDetActivity.this, UserAccountActivity.class));
                break;
            case R.id.tv_date:
                showCalendarDialog();
                break;
            case R.id.withdrawState_tv:
                showDrugDosageDialog();
                break;
        }
    }


    private void showCalendarDialog() {
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateTime = DateUtils.getDate(date);
                dateTv.setText(dateTime);
                countPeriod = dateTime;
                pageNum = 0;
                mPresenter.getWithdrawDet(getParams());
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt)).setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }

    private void showDrugDosageDialog() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    withdrawalState = drugDosageNameList.get(options1);
                    pageNum = 0;
                    mPresenter.getWithdrawDet(getParams());
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(drugDosageNameList,
                null, null);
        optionPickUnit.show();
    }

    private List<String> getDrugDosageNameList() {
        List<String> list = new ArrayList<>();
        list.add("已提交");
        list.add("审核中");
        list.add("打款中");
        list.add("已完成");
        return list;
    }
}


