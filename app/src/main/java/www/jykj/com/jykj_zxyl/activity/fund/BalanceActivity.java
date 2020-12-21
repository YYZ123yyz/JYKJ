package www.jykj.com.jykj_zxyl.activity.fund;

import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;

/**
 * Description:余额
 *
 * @author: qiuxinhai
 * @date: 2020-12-19 15:24
 */
public class BalanceActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_recharge_btn)
    TextView tvRechargeBtn;
    @BindView(R.id.tv_amount_money)
    TextView tvAmountMoney;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_balance;
    }


    @Override
    protected void initView() {
        super.initView();
    }


}
