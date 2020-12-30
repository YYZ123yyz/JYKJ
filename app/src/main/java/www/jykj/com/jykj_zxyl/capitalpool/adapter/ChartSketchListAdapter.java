package www.jykj.com.jykj_zxyl.capitalpool.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.ChartSketchBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-24 11:22
 */
public class ChartSketchListAdapter extends BaseQuickAdapter<AccountStisticInfoBean.AccountDoctorIncomeOutListBean, BaseViewHolder> {
    public ChartSketchListAdapter(int layoutResId, @Nullable List<AccountStisticInfoBean.AccountDoctorIncomeOutListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountStisticInfoBean.AccountDoctorIncomeOutListBean item) {
        ImageView ivSketchIcon = helper.getView(R.id.iv_sketch_icon);

        TextView tvSketchName = helper.getView(R.id.tv_sketch_name);
        ivSketchIcon.setBackgroundColor(Color.parseColor(item.getIncomeOutTypeRGB().replace("0x","#")));

        tvSketchName.setText(item.getIncomeOutTypeName());


    }


}
