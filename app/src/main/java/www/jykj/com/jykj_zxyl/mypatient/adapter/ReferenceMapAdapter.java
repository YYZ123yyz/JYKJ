package www.jykj.com.jykj_zxyl.mypatient.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.custom.RefrecenmapBean;

public class ReferenceMapAdapter extends BaseQuickAdapter<RefrecenmapBean, BaseViewHolder> {
    private Context mContext ;

    public ReferenceMapAdapter(int layoutResId, @Nullable List<RefrecenmapBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefrecenmapBean item) {
        EditText etHigh =helper.getView(R.id.et_high);
        EditText etLow =helper.getView(R.id.et_low);
        EditText etThre =helper.getView(R.id.et_thre);
        if (item.isClick()){

            etHigh.requestFocus();
            etHigh.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));
            etLow.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));
            etThre.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));

        }else {
            etHigh.clearFocus();
            etHigh.setBackground(null);
            etLow.setBackground(null);
            etThre.setBackground(null);
        }


        helper.setText(R.id.tv_age,item.getAgeStart() +"-"+item.getAgeEnd())
                .setText(R.id.et_high,String.valueOf(item.getHighNum()))
                .setText(R.id.et_low,String.valueOf(item.getLowNum()))
                .setText(R.id.et_thre,String.valueOf(item.getGradeFloatingValue()));

    }
}
