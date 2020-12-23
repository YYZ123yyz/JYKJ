package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.BalanceContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.BalancePresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.AddDrugInfoActivity2;

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
    @BindView(R.id.tv_amount_money)
    TextView tvAmountMoney;
    @BindView(R.id.rl_content_root)
    RelativeLayout rlContentRoot;
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
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(SmallChangeActivity.class,null);

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
}
