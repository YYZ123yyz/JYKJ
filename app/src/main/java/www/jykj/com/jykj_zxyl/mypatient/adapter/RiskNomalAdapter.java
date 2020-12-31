package www.jykj.com.jykj_zxyl.mypatient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.mypatient.bean.WarningListBean;

public class RiskNomalAdapter extends BaseQuickAdapter<WarningListBean, BaseViewHolder> {
    public RiskNomalAdapter(int layoutResId, @Nullable List<WarningListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WarningListBean item) {
        helper.addOnClickListener(R.id.now_state);
        helper.addOnClickListener(R.id.set_state_tv);
        TextView stateTv = helper.getView(R.id.now_state);
        String stateMsg = "";
        stateTv.setTextColor(item.getColor());

        switch (item.getStateType()) {
            case 10:
                stateMsg = "低危";
                break;
            case 20:
                stateMsg = "中危";
                break;
            case 30:
                stateMsg = "高危";
                break;
            case 40:
                stateMsg = "很高危";
                break;

            case 50:
                stateMsg = "正常";
                break;
            case 0:
                stateMsg = "未设置";
                break;
        }
        stateTv.setText(stateMsg);

        LinearLayout state = helper.getView(R.id.part_0);
        TextView setStateTv = helper.getView(R.id.set_state_tv);
        if (item.getHighNum() == null) {
            setStateTv.setVisibility(View.VISIBLE);
            state.setVisibility(View.GONE);
        } else {
            setStateTv.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);

            helper.setText(R.id.tv_threshold, "预警阀值: " + item.getGradeFloatingValue())
                    .setText(R.id.tv_age, "年龄段: " + item.getAge())
                    .setText(R.id.tv_high_pre, "高压值: " + item.getHighNum() + "mmhg")
                    .setText(R.id.tv_label, "患者标签: " + item.getUserLabelSecondName())
                    .setText(R.id.tv_low_pre, "低压值: " + item.getLowNum() + "mmhg");


        }
        helper.setText(R.id.tv_name, item.getPatientName());


    }
}
