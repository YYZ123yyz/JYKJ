package www.jykj.com.jykj_zxyl.appointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;

/**
 * Description:添加号源数量
 *
 * @author: qiuxinhai
 * @date: 2020-08-26 17:21
 */
public class AddSignalSourceDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private ImageView ivCloseBtn;
    private RelativeLayout rlChooseTime;
    private RelativeLayout rlChooseType;
    private EditText edInput;
    private TextView tvSaveBtn;
    public AddSignalSourceDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
        addListener();
    }

    /**
     * 初始化页面
     * @param context 上下文
     */
    private void init(Context context){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_add_signal_source, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 初始化View
     * @param view 根布局
     */
    private void initView(View view){
        ivCloseBtn=view.findViewById(R.id.iv_close_btn);
        rlChooseTime=view.findViewById(R.id.rl_choose_time);
        rlChooseType=view.findViewById(R.id.rl_choose_type);
        edInput=view.findViewById(R.id.ed_input);
        tvSaveBtn=view.findViewById(R.id.tv_save_btn);
    }
    /**
     * 添加监听
     */
    private void addListener(){
        ivCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSignalSourceDialog.this.dismiss();
            }
        });
        tvSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
