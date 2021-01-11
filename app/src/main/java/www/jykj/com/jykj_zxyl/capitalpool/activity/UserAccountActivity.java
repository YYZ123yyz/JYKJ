package www.jykj.com.jykj_zxyl.capitalpool.activity;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.UserAccountAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountPresenter;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.weichat_tv)
    TextView weichatTv;
    @BindView(R.id.ali_tv)
    TextView aliTv;
    private JYKJApplication mApp;
    private ArrayList<WithdrawTypelListBean> dataList;
    private UserAccountAdapter userAccountAdapter;
    private String weichatId;
    private String aliId;
    private BaseToolBar toolbar;
    private ImageButton imageButtonE;
    private String weChatCollectionFilePath = "";
    private String alipayCollectionFilePath = "";
    private WithdrawTypelListBean aliBean;
    private WithdrawTypelListBean weichatBean;

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
        userAccountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_unbind:
                        Intent intent = new Intent(UserAccountActivity.this, ModifyIinforActivity.class);
                        intent.putExtra("bankcardCode", dataList.get(position).getBankcardCode());
                        intent.putExtra("type", 3);
                        startActivity(intent);
                        break;
                }
            }
        });
        myRecyleview.setAdapter(userAccountAdapter);
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.toolbar);
        setToolBar();
    }

    private void setToolBar() {
        toolbar.setMainTitle("账户");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        //add
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(UserAccountActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getDoctorInfo(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        return RetrofitUtil.encodeParam(stringStringHashMap);
    }


    @OnClick({R.id.weichat_rela, R.id.ali_layout, R.id.addbank_card_layout, R.id.weicha_iv_msg, R.id.ali_iv_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weichat_rela:
                Intent intent = new Intent(UserAccountActivity.this, CollectionCodeActivity.class);
                intent.putExtra("type", 1);
                if (weichatTv.getText().toString().equals("微信收款码")) {
                    intent.putExtra("img", weChatCollectionFilePath);
                    intent.putExtra("id", weichatBean.getWeChatCollectionFileCode());
                }
                startActivity(intent);
                break;
            case R.id.ali_layout:
                Intent intent1 = new Intent(UserAccountActivity.this, CollectionCodeActivity.class);
                intent1.putExtra("type", 2);
                if (aliTv.getText().toString().equals("支付宝收款码")) {
                    intent1.putExtra("img", alipayCollectionFilePath);
                    intent1.putExtra("id", aliBean.getAlipayCollectionFileCode());
                }
                startActivity(intent1);
                break;
            case R.id.addbank_card_layout:
                startActivity(new Intent(UserAccountActivity.this, AddBankcardActivity.class));
                break;
            case R.id.weicha_iv_msg://解绑微信

                Intent intent0 = new Intent(UserAccountActivity.this, ModifyIinforActivity.class);
                intent0.putExtra("bankcardCode", weichatBean.getBankcardCode());
                intent0.putExtra("type", 3);
                startActivity(intent0);


                break;
            case R.id.ali_iv_msg://解绑支付宝

                Intent intent2 = new Intent(UserAccountActivity.this, ModifyIinforActivity.class);
                intent2.putExtra("bankcardCode", aliBean.getBankcardCode());
                intent2.putExtra("type", 3);
                startActivity(intent2);


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

    @Override
    public void getWeiChat(WithdrawTypelListBean weichatBean) {
        this.weichatBean = weichatBean;
        weichatId = weichatBean.getIdNumber();
        weChatCollectionFilePath = weichatBean.getWeChatCollectionFilePath();
        weichatTv.setText("微信收款码");
        weicatMsgIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getAli(WithdrawTypelListBean aliBean) {
        this.aliBean = aliBean;
        aliId = aliBean.getIdNumber();
        alipayCollectionFilePath = aliBean.getAlipayCollectionFilePath();
        aliTv.setText("支付宝收款码");
        aliMsgIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getEmpeyData(int type) {
        if (type == 1) {//微信
            weichatTv.setText("请绑定");
            weicatMsgIv.setVisibility(View.GONE);
        } else {//支付宝
            aliTv.setText("请绑定");
            aliMsgIv.setVisibility(View.GONE);
        }
    }
}

