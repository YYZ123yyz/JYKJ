package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import www.jykj.com.jykj_zxyl.R;

public class MyReportDialog extends Dialog {

    private LinearLayout disease_class;
    private LinearLayout department_class;

    private OnClickListener onClickListener;
    private TextView commit;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyReportDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.myreport_layout);

        //疾病类型
        disease_class = findViewById(R.id.disease_class);
        //科室选择
        department_class = findViewById(R.id.department_class);
        //确认
        commit = findViewById(R.id.commit);
        addListener();
    }

    private void addListener() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.onClickSucessBtn();
                }
            }
        });

        disease_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.disease();
                }
            }
        });


        department_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.department();
                }
            }
        });
    }

    public interface OnClickListener{
        //提交
        void onClickSucessBtn();
        //疾病选择
        void disease();
        //科室选择
        void department();
    }




}
