package www.jykj.com.jykj_zxyl.mypatient.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.dialog.ColumnWheelDialog;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.appointment.data.DataUtil;
import www.jykj.com.jykj_zxyl.custom.SignPatientDialog;
import www.jykj.com.jykj_zxyl.mypatient.adapter.RiskNomalAdapter;
import www.jykj.com.jykj_zxyl.mypatient.contract.RedRiskContract;
import www.jykj.com.jykj_zxyl.mypatient.presenter.RedRiskPresenter;
import www.jykj.com.jykj_zxyl.personal.activity.StateDetActivity;

public class RedHighRiskFragment extends AbstractMvpBaseFragment<RedRiskContract.View,
        RedRiskPresenter> implements RedRiskContract.View {

    @BindView(R.id.all_recy)
    RecyclerView mRecycleview;
    private SignPatientDialog signPatientDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_red_highrisk;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            strings.add("xx");
        }
        RiskNomalAdapter riskNomalAdapter = new RiskNomalAdapter(R.layout.item_risk, strings);
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
                }
            }
        });
        riskNomalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //showpop
                showSetPop();

            }
        });
    }

    private void showSetPop() {
        if (signPatientDialog ==null){
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

            }
        });
    }

    private void showChoosedTimesDialog(){
        ColumnWheelDialog<WheelItem, WheelItem, WheelItem, WheelItem, WheelItem>
                columnWheelDialog=new ColumnWheelDialog<>(getActivity());
        columnWheelDialog.show();
        columnWheelDialog.setShowTitle(false);
        columnWheelDialog.setClickTipsWhenIsScrolling("");
        columnWheelDialog.setCancelButton("取消", null);
        columnWheelDialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            String showText = item0.getShowText();
            if (signPatientDialog.isShowing()){
                signPatientDialog.setAge(showText);
            }
            return false;
        });

        WheelItem[] wheelItems = DataUtil.convertStrToWheelArry(getDayStrList());

        columnWheelDialog.setItems(wheelItems,null,null,null,null);
        columnWheelDialog.setSelected(0
                ,0,0,0,0);
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
}
