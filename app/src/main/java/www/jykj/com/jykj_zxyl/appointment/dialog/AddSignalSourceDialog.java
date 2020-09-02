package www.jykj.com.jykj_zxyl.appointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.util.StringUtils;

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
    private TextView tvTime;
    private TextView tvType;
    private boolean isShowSignalType=true;
    private OnClickDialogListener onClickDialogListener;



    public void setOnClickDialogListener(OnClickDialogListener onClickDialogListener) {
        this.onClickDialogListener = onClickDialogListener;
    }
    public AddSignalSourceDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(false);//禁止点击空白区域消失
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
        if(isShowSignalType){
            rlChooseType.setVisibility(View.VISIBLE);
        }else{
            rlChooseType.setVisibility(View.GONE);
        }
        edInput=view.findViewById(R.id.ed_input);
        tvSaveBtn=view.findViewById(R.id.tv_save_btn);
        tvTime=view.findViewById(R.id.tv_time);
        tvType=view.findViewById(R.id.tv_type);
    }

    public void setAppointTime(String startTime,String endTime){
        if (StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)) {
            tvTime.setText(startTime+"-"+endTime);
        }else{
            tvTime.setText("请选择放号时间");
        }

    }

    public void setSignalType(BaseReasonBean baseReasonBean){
        if (baseReasonBean!=null) {
            tvType.setText(baseReasonBean.getAttrName());
        }else{
            tvType.setText("请选择号源类型");
        }

    }

    public void setSignalNum(String signalNum){
        if (StringUtils.isNotEmpty(signalNum)) {
            edInput.setText(signalNum);
        }else{
            edInput.setText("");
        }

    }
    public void setShowSignalType(boolean showSignalType) {
        isShowSignalType = showSignalType;
        if(isShowSignalType){
            rlChooseType.setVisibility(View.VISIBLE);
        }else{
            rlChooseType.setVisibility(View.GONE);
        }
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
                if (onClickDialogListener!=null) {
                    onClickDialogListener.onClickEnsure();
                }
            }
        });

        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onClickDialogListener!=null) {
                    onClickDialogListener.onSignalChange(s.toString());
                }
            }
        });
        rlChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDialogListener!=null) {
                    onClickDialogListener.onClickChooseTime();
                }
            }
        });
        rlChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDialogListener!=null) {
                    onClickDialogListener.onClickChooseType();
                }
            }
        });
    }

    public interface OnClickDialogListener{


        void onClickChooseTime();

        void onClickChooseType();

        void onSignalChange(String signalSourceNum);

        void onClickEnsure();

    }

}
