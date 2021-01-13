package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.appointment.data.DataUtil;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawDetBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;

public class WithdrawDetAdapter extends BaseQuickAdapter<WithdrawDetBean, BaseViewHolder> {

    public WithdrawDetAdapter(int layoutResId, @Nullable List<WithdrawDetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawDetBean item) {

        helper.setText(R.id.rate,"¥"+item.getPlatformService())
                .setText(R.id.service,"¥"+item.getPlatformServiceCost())
                .setText(R.id.pay_rate,"¥"+item.getPlatformPersonTax())
                .setText(R.id.pay,"¥"+item.getPlatformPersonTaxCost())
                .setText(R.id.Initiate_time,""+ DateUtils.getDateToStringYYYMMDDHHMM(item.getWithdrawalDateTime()))
                .setText(R.id.carryout_time,""+DateUtils.getDateToStringYYYMMDDHHMM(item.getWithdrawalDateTime()))
                .setText(R.id.price,"¥"+item.getCashMoney())
        ;
        int withdrawalState = item.getWithdrawalState();
        helper.setText(R.id.status,item.getWithdrawalStateName());



    }
}
