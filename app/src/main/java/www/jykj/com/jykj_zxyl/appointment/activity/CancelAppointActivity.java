package www.jykj.com.jykj_zxyl.appointment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.jykj.bean.OrderMessage;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CancelAppointResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_dialog.BaseReasonDialog;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.activity.SmallChangeActivity;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:取消预约
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 18:49
 */
public class CancelAppointActivity extends BaseActivity {
    @BindView(R.id.txt_left_title)
    TextView txtLeftTitle;
    @BindView(R.id.left_image_id)
    ImageButton leftImageId;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.txt_right_title)
    TextView txtRightTitle;
    @BindView(R.id.right_image_search)
    ImageButton rightImageSearch;
    @BindView(R.id.right_image_id)
    ImageButton rightImageId;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_cancel_contract)
    TextView tvCancelContract;
    @BindView(R.id.rl_cancel_contract)
    RelativeLayout rlCancelContract;
    @BindView(R.id.ed_cancel_contract_desc)
    EditText edCancelContractDesc;
    @BindView(R.id.tv_submit_btn)
    TextView tvSubmitBtn;
    private BaseReasonDialog baseReasonDialog;
    private List<BaseReasonBean> baseReasonBeans;
    private PatientInfoBean currentPatientInfoBean;
    private BaseReasonBean currentBaseReasonBean;
    private JYKJApplication mApp;
    private CancelAppointResultBean cancelAppointResultBean;
    private ImageButton imageButtonE;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mApp = (JYKJApplication) getApplication();
        baseReasonDialog=new BaseReasonDialog(this,"解约原因");
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            baseReasonBeans= ((List<BaseReasonBean>) extras.getSerializable("baseReasonBeans"));
            currentPatientInfoBean=(PatientInfoBean)extras.getSerializable("currentPatientInfoBean");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cancel_appoint;
    }

    @Override
    protected void initView() {
        super.initView();
        imageButtonE = findViewById(R.id.right_image_search);
        setToolBar();
        addListener();
    }


    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("取消预约");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightSearchTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(
                        CancelAppointActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlCancelContract.setOnClickListener(v -> {
            baseReasonDialog.show();
            baseReasonDialog.setData(baseReasonBeans);
        });
        baseReasonDialog.setOnClickItemListener(cancelContractBean -> {
            currentBaseReasonBean=cancelContractBean;
            tvCancelContract.setText(cancelContractBean.getAttrName());
        });
        tvSubmitBtn.setOnClickListener(v -> {
            if(currentBaseReasonBean==null){
                ToastUtils.showToast("请选择解约原因");
                return;
            }
            String remark = edCancelContractDesc.getText().toString();
            if (!StringUtils.isNotEmpty(remark)) {
                ToastUtils.showToast("解约描述不能为空");
                return;
            }

            sendCancelAppointRequest(currentPatientInfoBean,currentBaseReasonBean);

        });
    }




    private void sendCancelAppointRequest(PatientInfoBean currentPatientInfoBean,BaseReasonBean baseReasonBean){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("reserveCode",currentPatientInfoBean.getReserveCode());
        hashMap.put("reserveRosterDateCode",currentPatientInfoBean.getReserveRosterDateCode());
        hashMap.put("mainDoctorCode",currentPatientInfoBean.getMainDoctorCode());
        hashMap.put("mainDoctorName",currentPatientInfoBean.getMainDoctorName());
        hashMap.put("mainPatientCode",currentPatientInfoBean.getMainPatientCode());
        hashMap.put("mainPatientName",currentPatientInfoBean.getMainPatientName());
        hashMap.put("version","0");
        hashMap.put("cancelReserveCode",baseReasonBean.getAttrCode());
        hashMap.put("cancelReserveName",baseReasonBean.getAttrName());
        hashMap.put("cancelReserveRemark",edCancelContractDesc.getText().toString());
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operCancelReservePatientDoctorInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                showLoading("",null);
            }

            @Override
            public void hideLoadingView() {
                dismissLoading();

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode == 1) {
                    String resJsonData = baseBean.getResJsonData();
                    if (StringUtils.isNotEmpty(resJsonData)) {
                        cancelAppointResultBean =
                                GsonUtils.fromJson(resJsonData, CancelAppointResultBean.class);
                        startJumpChatActivity(currentPatientInfoBean,cancelAppointResultBean);
                        setResult(1001);
                    }
                    CancelAppointActivity.this.finish();
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                ToastUtils.showToast(s);

            }

            @Override
            protected String setTag() {
                return super.setTag();
            }
        });
    }



    /**
     * 跳转IM
     * @param currentPatientInfoBean 患者信息
     */
    private void startJumpChatActivity(PatientInfoBean currentPatientInfoBean,CancelAppointResultBean cancelAppointResultBean){

        Intent intent = new Intent(this, ChatActivity.class);
        //患者
        intent.putExtra("userCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("userName", currentPatientInfoBean.getMainPatientName());
        //医生
        intent.putExtra("usersName", currentPatientInfoBean.getMainDoctorName());
        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //URL
        intent.putExtra("doctorUrl", currentPatientInfoBean.getPatientLogoUrl());
        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
        intent.putExtra("patientCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("patientSex", currentPatientInfoBean.getPatientSex());

        long reserveConfigStart = currentPatientInfoBean.getReserveConfigStart();
        String appointTime = DateUtils.getDateToStringYYYMMDDHHMM(reserveConfigStart);
        String cancelTime = DateUtils.getStringTimeOfYYYYMMDDHHMM(cancelAppointResultBean.getUpdateDate());
        int treatmentType = currentPatientInfoBean.getTreatmentType();
        String treatmentTypeValue="";
        if(treatmentType==1){
            treatmentTypeValue="一次性就诊";
        }else if(treatmentType==2){
            treatmentTypeValue="签约就诊";
        }
        Bundle bundle = new Bundle();
        OrderMessage appointment = new OrderMessage(mApp.mViewSysUserDoctorInfoAndHospital.getUserName(),
                mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(),
                currentPatientInfoBean.getReserveCode(),
                currentPatientInfoBean.getSignCode(), appointTime,
                cancelTime, currentPatientInfoBean.getReserveProjectName()
                , treatmentTypeValue, "2", "appointment");
        appointment.setReserveCode(currentPatientInfoBean.getReserveCode());
        bundle.putSerializable("orderMsg",
                appointment);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
