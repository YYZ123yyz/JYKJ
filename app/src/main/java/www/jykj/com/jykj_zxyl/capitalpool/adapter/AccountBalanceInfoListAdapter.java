package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceListInfoBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * Description:零钱明细适配器
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 18:59
 */
public class AccountBalanceInfoListAdapter extends BaseQuickAdapter<AccountBalanceListInfoBean
        .AccountDoctorBalanceInfoListBean, BaseViewHolder> {
    public AccountBalanceInfoListAdapter(int layoutResId,
                                         @Nullable List<AccountBalanceListInfoBean
                                                 .AccountDoctorBalanceInfoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,
                           AccountBalanceListInfoBean.AccountDoctorBalanceInfoListBean item) {
        TextView tvBillTitle = helper.getView(R.id.tv_bill_title);
        ImageView ivMoneySign = helper.getView(R.id.iv_money_sign);
        TextView tvDateTime = helper.getView(R.id.tv_date_time);
        TextView tvBillContent = helper.getView(R.id.tv_bill_content);
        TextView tvAmount = helper.getView(R.id.tv_amount);
        int changeType = item.getChangeType();
        int changeChildType = item.getChangeChildType();
        if (changeType==1) {
            ivMoneySign.setImageResource(R.mipmap.bg_return_money);
            if (changeChildType==11) {
                tvBillTitle.setText(String.format("%s -%s -%s", item.getChangeTypeName(), item.getOrderTypeName(), item.getSourceUserName()));
            }else{
                tvBillTitle.setText(item.getChangeTypeName());
            }

        }else if(changeType==2){
            ivMoneySign.setImageResource(R.mipmap.bg_pay_money);
            tvBillTitle.setText(String.format("%s  %s", item.getOrderTypeName(), item.getSourceUserName()));
        }
        tvBillContent.setText(item.getInfoDesc());
        String infoMoney = item.getInfoMoney();
        tvAmount.setText(infoMoney);
        tvDateTime.setText(DateUtils.getDateToStringYYYMMDDHHMM(item.getInfoDateTime()));

    }


}