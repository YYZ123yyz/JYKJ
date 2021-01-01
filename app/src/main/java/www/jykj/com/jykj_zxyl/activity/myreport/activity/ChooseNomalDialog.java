package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import entity.basicDate.ProvideBasicsRegion;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.CreateUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorUnionInviteMemberActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.UpdateUnionActivity;
import www.jykj.com.jykj_zxyl.activity.myself.UserCenterActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.widget.OnWheelChangedListener;
import www.jykj.com.jykj_zxyl.util.widget.WheelView;
import www.jykj.com.jykj_zxyl.util.widget.adapters.ArrayWheelAdapter;
import www.jykj.com.jykj_zxyl.util.widget.adapters.BirthdayWheelAdapter;

public class ChooseNomalDialog extends Dialog implements
        android.view.View.OnClickListener {
    private JYKJApplication mApp;

    private Context context;
    private WheelView mViewProvince;
    private WheelView mViewCity;

    private TextView mBtnConfirm;

    private int intoActivity;
    private ArrayList<String> mDepList;
    private ArrayList<String> mDisList;
    private int mType = 0;

    public ChooseNomalDialog(Context context) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.mApp = (JYKJApplication) context.getApplicationContext();
        setCanceledOnTouchOutside(false);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        initView(context);

    }


    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.choose_nomal, null);

        mViewProvince = (WheelView) view.findViewById(R.id.id_department);
        mViewCity = (WheelView) view.findViewById(R.id.id_disease);

        mBtnConfirm = (TextView) view.findViewById(R.id.btn_confirm);


        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setUpListener();
        setUpData(context);
        setContentView(view);
    }

    private void setUpListener() {


        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData(Context context) {

        // 设置可见条目数量
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);


        if (mType == 1) {
            mViewCity.setVisibility(View.VISIBLE);
            updateAreas();
        } else {
            mViewCity.setVisibility(View.GONE);
        }
        updateCities();

    }

    private void updateAreas() {
        if (mDisList != null) {
            mViewProvince.setViewAdapter(new BirthdayWheelAdapter<String>(context, mDisList));
            mViewProvince.setCurrentItem(0);
        }
    }

    private void updateCities() {
        if (mDepList != null) {
            mViewProvince.setViewAdapter(new BirthdayWheelAdapter<String>(context, mDepList));
            mViewProvince.setCurrentItem(0);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult(context);
                break;
            default:
                break;
        }

    }

    public void setType(int type) {
        this.mType = type;
    }


    public void setData(ArrayList<String> depList, ArrayList<String> diseaseList) {
        mDepList = depList;
        mDisList = diseaseList;
    }


    private void showSelectedResult(Context context) {


        this.dismiss();
    }


}
