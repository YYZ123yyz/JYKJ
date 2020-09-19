package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordPresenter;

/**
 * Created by G on 2020/9/19 9:52
 */
public class PatientRecordActivity
        extends AbstractMvpBaseActivity<PatientRecordContract.View, PatientRecordPresenter>
        implements PatientRecordContract.View {

    @BindView(R.id.lin_chief_msg)
    LinearLayout chiefMsgLin;
    @BindView(R.id.dispaly_Chief)
    ImageView ivChief;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_patient_record;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initData() {
        super.initData();
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode","2c534ac9d97245e480315b9e3804f459");
        String s = RetrofitUtil.encodeParam(hashMap);
        mPresenter.getPatientRecord(s);
    }

    @OnClick({R.id.lin_chief})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.lin_chief:
                chiefMsgLin.setVisibility(chiefMsgLin.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                if (chiefMsgLin.getVisibility() ==View.VISIBLE ){
                    showAnimation(ivChief);
                }else{
                    endAnimation(ivChief);
                }
                break;
        }
    }



    public void showAnimation(View view) {

        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, centerX,
                centerY);
        rotateAnimation.setDuration(200 );
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void endAnimation(View view) {

        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, centerX,
                centerY);
        rotateAnimation.setDuration(200 );
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }
}
