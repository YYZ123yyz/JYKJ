package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.BalanceContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.BalancePresenter;

/**
 * Description:余额
 *
 * @author: qiuxinhai
 * @date: 2020-12-19 15:24
 */
public class BalanceActivity extends AbstractMvpBaseActivity<BalanceContract.View,
        BalancePresenter> implements BalanceContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_recharge_btn)
    TextView tvRechargeBtn;
    @BindView(R.id.tv_withdrawal_btn)
    TextView tvWithDrawalBtn;
    @BindView(R.id.tv_amount_money)
    TextView tvAmountMoney;
    @BindView(R.id.rl_content_root)
    RelativeLayout rlContentRoot;
    @BindView(R.id.tv_forget_pwd_btn)
    TextView tvForgetPwdBtn;
    @BindView(R.id.tv_modify_pwd_btn)
    TextView tvModifyPwdBtn;
    private AccountBalanceBean accountBalanceBean;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    @Override
    protected int setLayoutId() {
        return R.layout.activity_balance;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        initLoadingAndRetryManager();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.sendSearchAccountDoctorAssetsInfoRequest(this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("余额");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleText("零钱明细");
        toolbar.setRightTitleSearchBtnVisible(false);
        toolbar.setRightTitleClickListener(v -> startActivity(SmallChangeActivity.class,null));
        tvRechargeBtn.setOnClickListener(v -> {
            if (accountBalanceBean!=null) {
                String isbinding = accountBalanceBean.getIsbinding();
                if (isbinding.equals("1")) {
                    startActivity(RechargeActivity.class,null,1000);
                }else{
                    startActivity(UserAccountActivity.class,null,1001);
                }
            }
        });
        tvWithDrawalBtn.setOnClickListener(v -> {
            if (accountBalanceBean!=null) {
                String isbinding = accountBalanceBean.getIsbinding();
                if (isbinding.equals("1")) {
                    startActivity(WithdrawDetActivity.class,null,1000);
                }else{
                    startActivity(UserAccountActivity.class,null,1001);
                }
            }

        });

        tvForgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvModifyPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(rlContentRoot);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchAccountDoctorAssetsInfoRequest(this);

        });
        mLoadingLayoutManager.showLoading();

    }

    @Override
    public void showLoading(int code) {
        if (code == 101) {
            showLoading("", null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getSearchAccountBalanceResult(AccountBalanceBean accountBalanceBean) {
        mLoadingLayoutManager.showContent();
        this.accountBalanceBean=accountBalanceBean;
        setData(accountBalanceBean);
    }

    /**
     * 设置数据
     * @param accountBalanceBean 账户余额信息
     */
    private void setData(AccountBalanceBean accountBalanceBean){
        tvAmountMoney.setText(String.format("¥ %s", accountBalanceBean.getBalanceMoney()));
    }

    @Override
    public void getSearchAccountBalanceError() {
        mLoadingLayoutManager.showError();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
