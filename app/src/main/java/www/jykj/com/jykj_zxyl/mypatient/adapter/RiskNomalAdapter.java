package www.jykj.com.jykj_zxyl.mypatient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;

public class RiskNomalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RiskNomalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.now_state);
    }
}
