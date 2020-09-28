package www.jykj.com.jykj_zxyl.app_base.base_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;

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
    private OnClickListener onClickListener;
    public static final int CHIEF_COMPLAINT_TYPE=1;//主诉
    public static final int HISTORY_NEW_TYPE=2;//现病史
    public static final int HISTORY_PAST_TYPE=3;//既往史
    public static final int HISTORY_ALLERGY_TYPE=4;//过敏史
    public static final int MEDICAL_EXAMINATION_TYPE=5;//查体
    public static final int CLINICAL_DIAGNOSIS_TYPE=6;//临床诊断
    public static final int TREATMENTPROPOSAL_TYPE=7;//治疗建议
    public static final int INSPECTION_TYPE=8;//检查检验
    public static final int PRESCRIPTION_NOTES_TYPE=9;//处方笺


    private int contentType;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHIEF_COMPLAINT_TYPE, HISTORY_NEW_TYPE, HISTORY_PAST_TYPE,MEDICAL_EXAMINATION_TYPE,
            CLINICAL_DIAGNOSIS_TYPE,HISTORY_ALLERGY_TYPE,TREATMENTPROPOSAL_TYPE,INSPECTION_TYPE,PRESCRIPTION_NOTES_TYPE})
    public @interface ContentType {

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

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


    /**
     * 更新谁
     * @param inputType 输入类型
     * @param title 标题
     * @param fromContent 来源内容
     * @param inputContent 填写内容
     */
    public void updateData(@ContentType int inputType,String title
            ,String fromContent,String inputContent){
        if (isShowing()) {
            contentType=inputType;
            tvDialogTitle.setText(title);
            tvDialogContent.setText(StringUtils.isNotEmpty(fromContent)?fromContent:"未填写");
            edInputContent.setText(inputContent);
            if (inputType==TREATMENTPROPOSAL_TYPE
                    ||inputType==MEDICAL_EXAMINATION_TYPE) {
                tvDialogContent.setVisibility(View.GONE);
            }else{
                tvDialogContent.setVisibility(View.VISIBLE);
            }
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
                if (TextUtils.isEmpty(edInputContent.getText().toString())) {
                    ToastUtils.showToast("填写内容不能为空");
                    return;
                }
                if (onClickListener!=null) {
                    onClickListener.onClickSaveBtn(edInputContent.getText().toString(),contentType);
                }
                MedcalRecordDialog.this.dismiss();
            }
        });
    }


    public interface OnClickListener{

        void onClickSaveBtn(String msg,@ContentType int contentType);
    }

}
