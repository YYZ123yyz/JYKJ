package www.jykj.com.jykj_zxyl.app_base.base_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import www.jykj.com.jykj_zxyl.app_base.R;

/**
 * Description:病例弹框
 *
 * @author: qiuxinhai
 * @date: 2020-09-21 15:26
 */
public class MedcalRecordDialog extends Dialog {

    private Context mContext;
    private ImageView ivCloseBtn;
    private View mRootView;
    private TextView tvDialogTitle;
    private TextView tvDialogContent;
    private EditText edInputContent;
    private TextView tvSaveBtn;
    public MedcalRecordDialog(@NonNull Context context) {
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
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_medcal_record, null);
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
     * @param view view
     */
    private void initView(View view){
        ivCloseBtn=view.findViewById(R.id.iv_close_btn);
        tvDialogTitle=view.findViewById(R.id.tv_dialog_title);
        tvDialogContent=view.findViewById(R.id.tv_dialog_content);
        edInputContent=view.findViewById(R.id.ed_input_content);
        tvSaveBtn=view.findViewById(R.id.tv_save);
    }


    public void updateData(String title,String content){
        if (isShowing()) {
            tvDialogTitle.setText(title);
            tvDialogContent.setText(content);
        }
    }

    /**
     * 添加监听
     */
    private void addListener(){

        ivCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedcalRecordDialog.this.dismiss();
            }
        });
        tvSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public interface OnClickListener{

    }

}
