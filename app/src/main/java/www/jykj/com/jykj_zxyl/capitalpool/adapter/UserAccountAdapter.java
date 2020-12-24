package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class UserAccountAdapter extends BaseQuickAdapter<WithdrawTypelListBean, BaseViewHolder> {
    public UserAccountAdapter(int layoutResId, @Nullable List<WithdrawTypelListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawTypelListBean item) {
        String showMsg = "";
        if (item.getIdNumber() != null && item.getBankName() != null) {
            String idNumber = item.getIdNumber();
            String substring = idNumber.substring(idNumber.length() - 4, idNumber.length());
            showMsg = item.getBankName() + "(" + substring + ")";
        } else {
            showMsg = "银行卡";
        }


        helper.setText(R.id.bank_card_id, showMsg);
        helper.addOnClickListener(R.id.iv_unbind);
    }
}
