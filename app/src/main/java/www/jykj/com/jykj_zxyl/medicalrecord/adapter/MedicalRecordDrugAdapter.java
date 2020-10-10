package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;


/**
 * Created by G on 2020/9/22 17:10
 */
public class MedicalRecordDrugAdapter extends BaseQuickAdapter<PatientRecordDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean, BaseViewHolder> {
    public MedicalRecordDrugAdapter(int layoutResId, @Nullable List<PatientRecordDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientRecordDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean item) {

        helper.setText(R.id.name,item.getDrugName())
                .setText(R.id.speci,item.getSpecName())
                .setText(R.id.quantity,String.valueOf(item.getDrugAmount()))
                .setText(R.id.price,String.valueOf(item.getDrugPrice()));
        ImageView type = (ImageView) helper.getView(R.id.type);
        switch (item.getType()) {
            case 0:
                type.setVisibility(View.INVISIBLE);
                break;
            case 1:
                type.setVisibility(View.VISIBLE);
                type.setImageResource(R.mipmap.iv_code_shang);
                break;
            case 2:
                type.setVisibility(View.VISIBLE);
                type.setImageResource(R.mipmap.iv_code_xia);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)type.getLayoutParams();
                layoutParams.gravity= Gravity.BOTTOM;
                type.setLayoutParams(layoutParams);
                break;
        }

    }
}