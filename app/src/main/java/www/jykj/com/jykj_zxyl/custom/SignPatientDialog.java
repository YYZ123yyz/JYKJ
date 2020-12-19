package www.jykj.com.jykj_zxyl.custom;


import android.app.Dialog;
import android.content.Context;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;


public class SignPatientDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private TextView ageTv;
    private EditText etHigh;
    private EditText etLow;
    private EditText etThre;


    public SignPatientDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(false);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
    }

    /**
     * 初始化页面
     *
     * @param context 上下文
     */
    private void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.popup_sign_patient, null);
        setContentView(mRootView);
        initView();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 初始化View
     */
    private void initView() {

        mRootView.findViewById(R.id.choose_age).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListen.chooseAge();
            }
        });
        etHigh = mRootView.findViewById(R.id.et_high);
        etLow = mRootView.findViewById(R.id.et_low);
        etThre = mRootView.findViewById(R.id.et_thre);

        ageTv = mRootView.findViewById(R.id.age_tv);
        mRootView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etHigh.getText().toString().trim()) || TextUtils.isEmpty(etLow.getText().toString().trim()) || TextUtils.isEmpty(etThre.getText().toString().trim())) {
                    ToastUtils.showShort("请填写数据");
                    return;
                }
                if (Double.parseDouble(etHigh.getText().toString().trim()) > 300) {
                    ToastUtils.showShort("高压值不能大于300");
                    return;
                }
                if (Double.parseDouble(etLow.getText().toString().trim()) > 200) {
                    ToastUtils.showShort("高压值不能大于200");
                    return;
                }
                if (Double.parseDouble(etThre.getText().toString().trim()) > 50) {
                    ToastUtils.showShort("高压值不能大于50");
                    return;
                }

                mListen.setDataSure(ageTv.getText().toString().trim(), etHigh.getText().toString().trim(), etLow.getText().toString().trim(), etThre.getText().toString().trim());
            }
        });
        mRootView.findViewById(R.id.tv_dissmiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setAge(String age) {
        ageTv.setText(age);
    }

    public void clearData() {
        ageTv.setText("");
        etHigh.getText().clear();
        etLow.getText().clear();
        etThre.getText().clear();
    }

    private onChooseAge mListen;

    public void setOnChooseAgeListen(onChooseAge listen) {
        this.mListen = listen;
    }

    public interface onChooseAge {
        void chooseAge();

        void setDataSure(String age, String high, String low, String thres);
    }
}
