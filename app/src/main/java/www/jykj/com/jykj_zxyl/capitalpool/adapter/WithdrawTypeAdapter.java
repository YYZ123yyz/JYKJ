package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import yyz_exploit.dialog.ImageView;

public class WithdrawTypeAdapter extends BaseQuickAdapter<WithdrawTypelListBean, BaseViewHolder> {
    public WithdrawTypeAdapter(int layoutResId, @Nullable List<WithdrawTypelListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawTypelListBean item) {
        String showMsg = "";

        if (item.getWithdrawalType() ==1){ //微信
            helper.setImageResource(R.id.iv_type_wei1,R.mipmap.iv_charge_weichat);
            helper.setText(R.id.bank_card_id, "微信");

        }else if (item.getWithdrawalType() ==2){//支付宝
            helper.setImageResource(R.id.iv_type_wei1,R.mipmap.iv_charge_ali);
            helper.setText(R.id.bank_card_id, "支付宝");
        }else {//银行卡
            if (item.getIdNumber() != null && item.getBankName() != null) {
                String idNumber = item.getIdNumber();
                String substring = idNumber.substring(idNumber.length() - 4, idNumber.length());
                showMsg = item.getBankName() + substring;
            } else {
                showMsg = "银行卡";
            }
            helper.setImageResource(R.id.iv_type_wei1,R.mipmap.iv_withdraw_card);
            helper.setText(R.id.bank_card_id, showMsg);
        }

        helper.setGone(R.id.iv_choose,item.isClick());
    }
}
