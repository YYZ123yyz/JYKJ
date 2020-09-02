package www.jykj.com.jykj_zxyl.appointment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.appointment.listener.MyItemClickListener;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-01 11:02
 */
public class ThirdView {
    private Context context;
    private MyItemClickListener listener;

    public ThirdView(Context context) {
        this.context = context;
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public View secView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_clinic_third, null);
        TextView tvReceivedDefaultBtn =  view.findViewById(R.id.tv_received_default_btn);
        TextView tvReceivedTreatmentBtn =  view.findViewById(R.id.tv_received_treatment_btn);
        TextView tvNotReceivedTreatmentBtn =  view.findViewById(R.id.tv_not_received_treatment_btn);
        tvReceivedDefaultBtn.setOnClickListener(new ThirdView.mClick(""));
        tvReceivedTreatmentBtn.setOnClickListener(new ThirdView.mClick("20"));
        tvNotReceivedTreatmentBtn.setOnClickListener(new ThirdView.mClick("10"));
        return view;
    }

    private class mClick implements View.OnClickListener {

        String string;

        private mClick(String string) {
            this.string = string;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, 3, string);
        }
    }
}
