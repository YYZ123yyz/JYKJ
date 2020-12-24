package www.jykj.com.jykj_zxyl.capitalpool.weiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
 * Description:支付确认弹框
 *
 * @author: qiuxinhai
 * @date: 2020-12-24 09:54
 */
public class CommonPayConfirmDialog  extends Dialog {
    private View mRootView;
    private Context mContext;
    private TextView mTvPayMount;
    private TextView mTvNotPayBtn;
    private TextView mTvPaidBtn;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommonPayConfirmDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_pay_confirm, null);
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

    private void initView(View view){
        mTvPayMount=view.findViewById(R.id.tv_pay_mount);
        mTvNotPayBtn=view.findViewById(R.id.tv_not_pay_btn);
        mTvPaidBtn=view.findViewById(R.id.tv_paid_btn);
    }

    /**
     * 设置数据
     * @param payMount 金额
     */
    public void setData(String payMount){
        mTvPayMount.setText(payMount);
    }


    private void addListener(){
        mTvNotPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.onClickNotPay();
                }
            }
        });
        mTvPaidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.onClickPaid();
                }
            }
        });
    }


    public interface OnClickListener{

        void onClickNotPay();


        void onClickPaid();
    }
}
