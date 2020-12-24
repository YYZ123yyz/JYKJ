package www.jykj.com.jykj_zxyl.capitalpool.weiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.Objects;
import www.jykj.com.jykj_zxyl.R;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-23 21:03
 */
public class CommonPayPwdCheckDialog extends Dialog {

    private View mRootView;
    private Context mContext;
    private View ivCloseBtn;
    private TextView mTvPayMount;
    private PasswordEditText passwordEditText;
    private OnCompleteListener onCompleteListener;

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public CommonPayPwdCheckDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
    }

    /**
     * 初始化页面
     * @param context 上下文
     */
    private void init(Context context){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_pay_pwd_confirm, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        addListener();
    }

    private void initView(View view) {
        ivCloseBtn = view.findViewById(R.id.iv_close_btn);
        mTvPayMount = view.findViewById(R.id.tv_pay_mount);
        passwordEditText = view.findViewById(R.id.password_et);
    }


    /**
     * 设置数据
     * @param payMount 金额
     */
    public void setData(String payMount){
        mTvPayMount.setText(payMount);
        passwordEditText.setText("");
    }


    /**
     * 添加监听
     */
    private void addListener() {
        ivCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonPayPwdCheckDialog.this.dismiss();
            }
        });

        passwordEditText.setOnCompleteListener(password -> {
            String s = passwordEditText.getText().toString();
            if (onCompleteListener != null) {
                onCompleteListener.onTextPwdComplete(s);
            }
        });
    }


   public interface OnCompleteListener{

        void onTextPwdComplete(String pwd);
    }

}
