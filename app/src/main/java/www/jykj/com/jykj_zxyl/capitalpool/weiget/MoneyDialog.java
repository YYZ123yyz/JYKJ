package www.jykj.com.jykj_zxyl.capitalpool.weiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.appointment.dialog.AddSignalSourceDialog;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawTypeAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.util.StringUtils;

public class MoneyDialog extends Dialog {

    private View mPopView;
    private WithdrawCostBean mData;

    private PasswordEditText passwordEditText;
    private Context mContext;
    private TextView platformService;
    private TextView platformServiceCost;
    private TextView platformPersonTax;
    private TextView platformPersonTaxCost;

    private TextView moneyTv;


    public MoneyDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(false);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
    }

    /**
     * 初始化页面
     * @param context 上下文
     */
    private void init(Context context){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_money, null);
        setContentView(mPopView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        initView();
    }


    private void initView(){
        mPopView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        moneyTv = mPopView.findViewById(R.id.money_tv);
        platformService = mPopView.findViewById(R.id.platformService);
        platformServiceCost = mPopView.findViewById(R.id.platformServiceCost);
        platformPersonTax = mPopView.findViewById(R.id.platformPersonTax);
        platformPersonTaxCost = mPopView.findViewById(R.id.platformPersonTaxCost);
        passwordEditText = mPopView.findViewById(R.id.password_et);
        passwordEditText.setOnCompleteListener(new PasswordEditText.OnPasswordCompleteListener() {
            @Override
            public void onComplete(String password) {
                myDevChoose.onDevChoose(password);
            }
        });
    }



    public void setData(WithdrawCostBean data) {
        mData = data;
        initData();
    }



    private void initData() {
        if (mData !=null){
            platformService.setText(mData.getPlatformService());
            platformServiceCost.setText(mData.getPlatformServeCost());
            platformPersonTax.setText(mData.getPlatformPersonTax());
            platformPersonTaxCost.setText(mData.getPlatformPersonTaxCost());
            moneyTv.setText(mData.getMoney());
        }
    }

    private onDevChoose myDevChoose;

    public void setOnDevChoose(onDevChoose listen) {
        this.myDevChoose = listen;
    }

    public interface onDevChoose {
        void onDevChoose(String password);
    }



}
