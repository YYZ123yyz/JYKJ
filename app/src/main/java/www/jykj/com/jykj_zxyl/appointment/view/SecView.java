package www.jykj.com.jykj_zxyl.appointment.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.appointment.listener.MyItemClickListener;

public class SecView {

    private Context context;
    private MyItemClickListener listener;

    public SecView(Context context) {
        this.context = context;
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public View secView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_clinic_sec, null);
        TextView tvPriceDefaultBtn =  view.findViewById(R.id.tv_price_default_btn);
        TextView tvPriceDescBtn =  view.findViewById(R.id.tv_price_desc_btn);
        TextView tvPriceAscBtn =  view.findViewById(R.id.tv_price_asc_btn);
        tvPriceDefaultBtn.setOnClickListener(new mClick(""));
        tvPriceDescBtn.setOnClickListener(new mClick("2"));
        tvPriceAscBtn.setOnClickListener(new mClick("1"));
        return view;
    }

    private class mClick implements View.OnClickListener {

        String string;

        private mClick(String string) {
            this.string = string;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, 2, string);
        }
    }

}
