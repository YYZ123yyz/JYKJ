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

import com.blankj.utilcode.util.LogUtils;
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
import www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter.DesecAdapter;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter.DevAdapter;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myself.UserCenterActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.widget.OnWheelChangedListener;
import www.jykj.com.jykj_zxyl.util.widget.WheelView;
import www.jykj.com.jykj_zxyl.util.widget.adapters.ArrayWheelAdapter;
import www.jykj.com.jykj_zxyl.util.widget.adapters.BirthdayWheelAdapter;

public class ChooseDepDialog extends Dialog implements
        android.view.View.OnClickListener, OnWheelChangedListener {
    private JYKJApplication mApp;
    private CreateUnionActivity mCreateActivity;
    private JoinDoctorsUnionActivity mJoinUnionActivity;
    private DoctorUnionInviteMemberActivity mDoctorUnionInviteMemberActivity;
    private UpdateUnionActivity mUpdateUnionActivity;
    private UserCenterActivity mUserCenterActivity;


    /**
     * 市
     */
    protected List<DepartmentListBean.HospitalDepartmentListBean> mCityList = new ArrayList<>();

    /**
     * 区
     */
    protected List<ProvideBasicsRegion> mDistList = new ArrayList<>();


    private ArrayList<DepartmentListBean> mfDta;
    private ArrayList<DepartmentListBean.HospitalDepartmentListBean> msData;


    /**
     * 当前省
     */
    protected DepartmentListBean mCurrentProviceName;
    /**
     * 当前市
     */
    protected ProvideBasicsRegion mCurrentCityName;


    private Context context;
    private WheelView mViewProvince;
    private WheelView mViewCity;

    private TextView mBtnConfirm;

    private List<DepartmentListBean> mData;

    public ChooseDepDialog(Context context) {
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
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mViewCity.setVisibility(View.VISIBLE);
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
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件


        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData(Context context) {
        mfDta = new ArrayList<DepartmentListBean>();
        msData = new ArrayList<DepartmentListBean.HospitalDepartmentListBean>();


        mViewProvince.setViewAdapter(new DevAdapter<DepartmentListBean>(context, mfDta
        ));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);


        updateCities(context);

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

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities(context);
        }
    }


    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities(Context context) {

        LogUtils.e("xxxxxxxxxxxxxxxxxxx  mfDta  "+mfDta.size());
        LogUtils.e("xxxxxxxxxxxxxxxxxxx  msData  "+msData.size());
        int pCurrent = mViewProvince.getCurrentItem();
        if (mfDta .size() ==0 ){
            return;
        }
        mCurrentProviceName = mfDta.get(pCurrent);
        List<DepartmentListBean.HospitalDepartmentListBean> list = new ArrayList<>();


        for (int i = 0; i < msData.size(); i++) {
            if (msData.get(i).getParentId() == (mCurrentProviceName.getHospitalDepartmentId())) {
                list.add(msData.get(i));
            }
        }
        mCityList = list;
        mViewCity
                .setViewAdapter(new DesecAdapter<DepartmentListBean.HospitalDepartmentListBean>(context, list));
        mViewCity.setCurrentItem(0);
        mViewCity.setVisibleItems(7);

    }

    private void showSelectedResult(Context context) {
        myListen.chooseListen();

        this.dismiss();
    }

    public void setData(ArrayList<DepartmentListBean> fDta, ArrayList<DepartmentListBean.HospitalDepartmentListBean> sData) {
        mfDta.addAll(fDta);
        msData.addAll(sData);
        updateCities(context);

    }


    private onDepChoose myListen;

    public void setOnDepChoose(onDepChoose listen){
        this.myListen = listen;
    }

    public interface onDepChoose{
        void chooseListen();
    }
}
