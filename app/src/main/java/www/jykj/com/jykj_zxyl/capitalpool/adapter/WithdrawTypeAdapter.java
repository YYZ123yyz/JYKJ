package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class WithdrawTypeAdapter extends BaseQuickAdapter<WithdrawTypelListBean, BaseViewHolder> {
    public WithdrawTypeAdapter(int layoutResId, @Nullable List<WithdrawTypelListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawTypelListBean item) {
        String idNumber = item.getIdNumber();
        String substring = idNumber.substring(idNumber.length() - 4, idNumber.length());

        helper.setText(R.id.bank_card_id,item.getBankName()+substring);
    }
}
