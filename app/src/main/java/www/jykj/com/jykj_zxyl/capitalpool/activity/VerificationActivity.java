package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawPresenter;

public class VerificationActivity extends AbstractMvpBaseActivity<VerificationContract.View
        , VerificationPresenter> implements VerificationContract.View {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.phone_et)
    TextView etPhone;
    @BindView(R.id.send_ms_tv)
    TextView sendMsTv;
    @BindView(R.id.id_num_tv)
    EditText etIdNum;
    @BindView(R.id.ver_code)
    EditText etVerCode;
    private JYKJApplication mApp;
    private Timer mTimer;
    private TimerTask mTask;
    private int mInitVCodeTime = 60;
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 10:
                    mInitVCodeTime--;
                    sendMsTv.setText(mInitVCodeTime + "");
                    sendMsTv.setClickable(false);
                    break;
                case 11:
                    sendMsTv.setText("重新获取");
                    sendMsTv.setClickable(true);
                    mInitVCodeTime = 60;
                    mTimer.cancel();
                    mTask.cancel();
                    break;
            }
        }
    };
    private String usephone;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        usephone = SPUtils.getInstance().getString("usephone");
        String phonenum = usephone.substring(0, 3) + "****" + usephone.substring(7, 11);
        etPhone.setText(phonenum);
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
    }

    @OnClick({R.id.next_tv, R.id.send_ms_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_tv:
                checkInfo();

                break;
            case R.id.send_ms_tv:
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("请先完善个人信息");
                    return;
                }
                startTask();
                mPresenter.sendMs(getParams());

                break;
        }

    }

    private void checkInfo() {

        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.showShort("请输入名字");
            return;
        }
        if (TextUtils.isEmpty(etIdNum.getText().toString())) {
            ToastUtils.showShort("请输入身份证");
            return;
        }
        if (TextUtils.isEmpty(etVerCode.getText().toString())) {
            ToastUtils.showShort("请填写验证码");
            return;
        }
        mPresenter.checkAccount(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());//mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());//mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
        stringStringHashMap.put("phone", usephone);//mType
        stringStringHashMap.put("idNumber", etIdNum.getText().toString().trim());//mType
        stringStringHashMap.put("captcha", etVerCode.getText().toString().trim());//mType



        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    private void startTask() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mInitVCodeTime > 0) {
                    mHandler.sendEmptyMessage(10);

                } else {
                    mHandler.sendEmptyMessage(11);
                }
            }
        };
        mTimer.schedule(mTask, 0, 1000);
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void checkSucess() {
        ToastUtils.showShort("验证成功");
        startActivity(new Intent(VerificationActivity.this, ModifyIinforActivity.class));
        finish();
    }
}


