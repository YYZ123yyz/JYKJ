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
        TextView tvBtnOne = (TextView) view.findViewById(R.id.btn_one);
        TextView tvBtnTwo = (TextView) view.findViewById(R.id.btn_two);
        tvBtnOne.setOnClickListener(new mClick("第一个"));
        tvBtnTwo.setOnClickListener(new mClick("第二个"));
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
