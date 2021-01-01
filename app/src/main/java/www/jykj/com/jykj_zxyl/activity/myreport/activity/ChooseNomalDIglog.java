package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.MoneyDialog;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.PasswordEditText;

public class ChooseNomalDIglog extends Dialog {

    private View mPopView;
    private WithdrawCostBean mData;

    private PasswordEditText passwordEditText;
    private Context mContext;
    private TextView platformService;
    private TextView platformServiceCost;
    private TextView platformPersonTax;
    private TextView platformPersonTaxCost;

    private TextView moneyTv;


    public ChooseNomalDIglog(@NonNull Context context) {
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
        mPopView = inflater.inflate(R.layout.choose_nomal, null);
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

        ArrayList<String> strings = new ArrayList<>();
        strings.add("11111111");
        strings.add("222222222");
        strings.add("333333333");
        strings.add("44444444");
        strings.add("5555555555");

        Wheelview myWheel = mPopView.findViewById(R.id.wheel_view);
        LogUtils.e("xxxxx   "+myWheel);
//        myWheel.setOffset(1);
        myWheel.setItems(strings);
    }



    public void setData(WithdrawCostBean data) {
        mData = data;
        initData();
    }



    private void initData() {

    }

    private MoneyDialog.onDevChoose myDevChoose;

    public void setOnDevChoose(MoneyDialog.onDevChoose listen) {
        this.myDevChoose = listen;
    }

    public interface onDevChoose {
        void onDevChoose(String password);
    }



}
