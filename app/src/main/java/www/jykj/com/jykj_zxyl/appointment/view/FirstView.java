package www.jykj.com.jykj_zxyl.appointment.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.appointment.listener.MyItemClickListener;

public class FirstView {

    private Context context;
    private MyItemClickListener listener;

    public FirstView(Context context) {
        this.context = context;
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public View firstView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_clinic_first, null);
        TextView tvDateDescBtn = view.findViewById(R.id.tv_date_desc_btn);
        TextView tvDateAscBtn =  view.findViewById(R.id.tv_date_asc_btn);
        TextView tvDateDefaultBtn = view.findViewById(R.id.tv_date_default_btn);
        tvDateDescBtn.setOnClickListener(new mClick(""));
        tvDateAscBtn.setOnClickListener(new mClick("2"));
        tvDateDefaultBtn.setOnClickListener(new mClick("1"));
        return view;
    }

    private class mClick implements View.OnClickListener {

        String string;

        private mClick(String string) {
            this.string = string;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, 1, string);
        }
    }

}
