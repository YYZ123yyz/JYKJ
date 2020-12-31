package www.jykj.com.jykj_zxyl.mypatient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.dialog.ColumnWheelDialog;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.data.DataUtil;
import www.jykj.com.jykj_zxyl.custom.SignPatientDialog;
import www.jykj.com.jykj_zxyl.mypatient.adapter.RiskNomalAdapter;
import www.jykj.com.jykj_zxyl.mypatient.bean.WarningDoctorSetPatientData;
import www.jykj.com.jykj_zxyl.mypatient.bean.WarningListBean;
import www.jykj.com.jykj_zxyl.mypatient.contract.RedRiskContract;
import www.jykj.com.jykj_zxyl.mypatient.presenter.RedRiskPresenter;
import www.jykj.com.jykj_zxyl.personal.activity.StateDetActivity;
import www.jykj.com.jykj_zxyl.personal.bean.SearchBean;
import www.jykj.com.jykj_zxyl.wxapi.PayInfoBean;

public class RedHighRiskFragment extends AbstractMvpBaseFragment<RedRiskContract.View,
        RedRiskPresenter> implements RedRiskContract.View {

    @BindView(R.id.all_recy)
    RecyclerView mRecycleview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tvNoData)
    TextView tvNodata;
    private SignPatientDialog signPatientDialog;
    private int rowNum = 10;
    private int pageNum = 1;
    private String patientName = "";
    private String ageStart = "";
    private String ageEnd = "";
    private JYKJApplication mApp;
    private ArrayList<WarningListBean> warningListBeans;
    private RiskNomalAdapter riskNomalAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_red_highrisk;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        super.initData();

        mApp = (JYKJApplication) getActivity().getApplication();
        mPresenter.getWarningList(getParams());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private String getParams() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        map.put("searchDoctorCode",  mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("searchFlagSigning", "1");
        map.put("rowNum", String.valueOf(rowNum));
        map.put("pageNum", String.valueOf(pageNum));
        map.put("stateType", 10);//
        map.put("patientName", patientName);
        map.put("ageStart", ageStart);
        map.put("ageEnd", ageEnd);

        return RetrofitUtil.encodeParam(map);
    }

    private String getCommitParams(ArrayList<WarningDoctorSetPatientData> warningDoctorSetPatientData){
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginUserPosition", ParameUtil.loginDoctorPosition);
        map.put("warningDoctorSetPatientData", warningDoctorSetPatientData);


        return RetrofitUtil.encodeParam(map);
    }



    @Override
    protected void initView(View view) {
        super.initView(view);
        warningListBeans = new ArrayList<>();
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));

        riskNomalAdapter = new RiskNomalAdapter(R.layout.item_risk, warningListBeans);
        mRecycleview.setAdapter(riskNomalAdapter);

        riskNomalAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //状态详情
                switch (view.getId()) {
                    case R.id.now_state:
                        Intent intent = new Intent(getActivity(), StateDetActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.set_state_tv:
                        showSetPop(position);
                        break;
                }
            }
        });
        riskNomalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //showpop

                showSetPop(position);
            }
        });


      refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
          @Override
          public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
              pageNum ++;
              mPresenter.getWarningList(getParams());
          }

          @Override
          public void onRefresh(@NonNull RefreshLayout refreshLayout) {
              pageNum =1;
              mPresenter.getWarningList(getParams());
          }
      });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(SearchBean msg) {
        patientName = msg.getName();
        ageStart =msg.getAgeStart();
        ageEnd = msg.getAgeEnd();
        pageNum =1;
        mPresenter.getWarningList(getParams());
    }


    private void showSetPop(int position ) {
        if (signPatientDialog == null) {
            signPatientDialog = new SignPatientDialog(getActivity());
        }
        signPatientDialog.show();

        signPatientDialog.setOnChooseAgeListen(new SignPatientDialog.onChooseAge() {
            @Override
            public void chooseAge() {
                showChoosedTimesDialog();
            }

            @Override
            public void setDataSure(String age, String high, String low, String thres) {
                //提交数据
                ArrayList<WarningDoctorSetPatientData> warningDoctorSetPatientData = new ArrayList<>();
                WarningDoctorSetPatientData warningDoctorSetPatientData1 = new WarningDoctorSetPatientData();
                warningDoctorSetPatientData1.setDoctorCode( mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                warningDoctorSetPatientData1.setDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                warningDoctorSetPatientData1.setFlagDataType("30");
                warningDoctorSetPatientData1.setGradeFloatingValue(warningListBeans.get(position).getGradeFloatingValue());
                warningDoctorSetPatientData1.setHighNum(high);
                warningDoctorSetPatientData1.setLowNum(low);
                warningDoctorSetPatientData1.setPatientCode(warningListBeans.get(position).getSetPatientWarningCode());
                warningDoctorSetPatientData1.setPatientName(warningListBeans.get(position).getPatientName());
                warningDoctorSetPatientData1.setSetPatientWarningCode(warningListBeans.get(position).getSetPatientWarningCode());

                warningDoctorSetPatientData.add(warningDoctorSetPatientData1);
                mPresenter.setPatientWarning(getCommitParams(warningDoctorSetPatientData));
            }
        });
    }

    private void showChoosedTimesDialog() {
        ColumnWheelDialog<WheelItem, WheelItem, WheelItem, WheelItem, WheelItem>
                columnWheelDialog = new ColumnWheelDialog<>(getActivity());
        columnWheelDialog.show();
        columnWheelDialog.setShowTitle(false);
        columnWheelDialog.setClickTipsWhenIsScrolling("");
        columnWheelDialog.setCancelButton("取消", null);
        columnWheelDialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            String showText = item0.getShowText();
            if (signPatientDialog.isShowing()) {
                signPatientDialog.setAge(showText);
            }
            return false;
        });

        WheelItem[] wheelItems = DataUtil.convertStrToWheelArry(getDayStrList());

        columnWheelDialog.setItems(wheelItems, null, null, null, null);
        columnWheelDialog.setSelected(0
                , 0, 0, 0, 0);
    }


    private List<String> getDayStrList() {
        List<String> list = new ArrayList<>();
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add(">90");
        return list;
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getListSucess(List<WarningListBean> data) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setColor(Color.parseColor("#FEA32C"));
        }
        if (refreshLayout.isRefreshing()){
            refreshLayout.finishRefresh();
        }
        LogUtils.e("xxxxxxxxxx"+data.size());

        if (pageNum == 1) {
            if (data.size() > 0) {
                warningListBeans.clear();
                warningListBeans.addAll(data);

                LogUtils.e("xxxxxxxxxx     sss   "+warningListBeans.size());

                riskNomalAdapter.notifyDataSetChanged();
                tvNodata.setVisibility(View.GONE);
                mRecycleview.setVisibility(View.VISIBLE);
            }else {
                tvNodata.setVisibility(View.VISIBLE);
                mRecycleview.setVisibility(View.GONE);
            }
        } else {
            if (data.size() > 0) {
                warningListBeans.addAll(data);
                riskNomalAdapter.notifyDataSetChanged();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }

    }
}
