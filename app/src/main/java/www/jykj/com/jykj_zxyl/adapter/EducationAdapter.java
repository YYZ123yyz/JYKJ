package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import entity.EducationBean;
import www.jykj.com.jykj_zxyl.R;

public class EducationAdapter extends BaseQuickAdapter<EducationBean , BaseViewHolder> {
    public EducationAdapter(int layoutResId, @Nullable List<EducationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationBean item) {
            helper.setText(R.id.num,item.getNum());
    }
}
