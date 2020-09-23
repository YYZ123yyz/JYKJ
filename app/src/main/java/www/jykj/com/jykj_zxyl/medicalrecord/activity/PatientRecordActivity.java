package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordPresenter;
import www.jykj.com.jykj_zxyl.util.CircleImageView;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * Created by G on 2020/9/19 9:52
 */
public class PatientRecordActivity
        extends AbstractMvpBaseActivity<PatientRecordContract.View, PatientRecordPresenter>
        implements PatientRecordContract.View {
    @BindView(R.id.txt_main_title)
    TextView tittle;
    @BindView(R.id.lin_chief_msg)
    LinearLayout chiefMsgLin;
    @BindView(R.id.dispaly_Chief)
    ImageView ivChief;
    @BindView(R.id.dispaly_medicalhistory)
    ImageView ivHistory;
    @BindView(R.id.dispaly_past)
    ImageView ivPast;
    @BindView(R.id.dispaly_physical)
    ImageView ivLook;
    @BindView(R.id.dispaly_treatment)
    ImageView ivSuggest;
    @BindView(R.id.dispaly_examination)
    ImageView ivExamination;
    @BindView(R.id.docName) //医生名
    TextView docName;
    @BindView(R.id.department) //科室
    TextView departmentName;
    @BindView(R.id.userDepartment) //诊疗号
    TextView diaOrder;
    @BindView(R.id.time) //时间
    TextView diaTime;
    @BindView(R.id.input_chief_state)
    ImageView chiefState;//主诉填写状态
    @BindView(R.id.input_history_state)
    ImageView historyState;  //现病史状态
    @BindView(R.id.input_past_state)
    ImageView pastState; //既往病史状态
    @BindView(R.id.input_look_state)
    ImageView lookState;//查体转态
    @BindView(R.id.input_diag_state)
    ImageView diagState;//临床诊断
    //    @BindView(R.id.input_aller_state)
//    ImageView allerState;//过敏史状态
    @BindView(R.id.radio_yes)
    RadioButton radioYes;
    @BindView(R.id.radio_no)
    RadioButton radioNo;
    @BindView(R.id.examination_radio)
    RadioGroup examinationRadio;
    @BindView(R.id.input_suggest_state)
    ImageView suggestState;//治疗建议状态
    @BindView(R.id.input_check_state)
    ImageView checkState;//检查检验状态
    @BindView(R.id.lin_medicalhistoryf_msg)
    LinearLayout historyMsg;
    @BindView(R.id.lin_past_msg)
    LinearLayout pastMsg;
    @BindView(R.id.lin_look_msg)
    LinearLayout lookMsg;
    @BindView(R.id.lin_examination_msg)
    LinearLayout examinationMsg;
    @BindView(R.id.lin_suggest_masg)
    LinearLayout suggestMsg;
    @BindView(R.id.chief)
    TextView chiefTv;
    @BindView(R.id.medicalhistory)
    TextView medicalHistoryTv;
    @BindView(R.id.past)
    TextView pastTv;
//    @BindView(R.id.physical)
//    TextView lookTv;
    @BindView(R.id.examination)
    TextView examinationTv;
//    @BindView(R.id.treatment)
//    TextView suggestTv;
    @BindView(R.id.ed_chief)
    EditText chiefEt;
    @BindView(R.id.ed_history)
    EditText newEt;
    @BindView(R.id.ed_past)
    EditText pastEt;
    @BindView(R.id.ed_look)
    EditText lookEt;
    @BindView(R.id.ed_examination)
    EditText ed_examinationEt;
    @BindView(R.id.ed_suggest)
    EditText suggestEt;
    @BindView(R.id.patientName)
    TextView paientName;
    @BindView(R.id.usergendder)
    TextView userGendder;
    @BindView(R.id.userage)
    TextView userAge;
    @BindView(R.id.userHead)
    CircleImageView headView;
    private String gendder;
    private String orderCode;//订单Id
    private String patientCode;//患者code
    private String patientName;//患者名称
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderCode = extras.getString("orderCode");
            patientCode=extras.getString("patientCode");
            patientName=extras.getString("patientName");
        }

    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_patient_record;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        tittle.setText("病历");
    }

    @Override
    protected void initData() {
        super.initData();
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        mPresenter.getPatientRecord(s);
    }

    @OnClick({R.id.lin_chief, R.id.lin_history, R.id.lin_past, R.id.lin_look, R.id.lin_examination,
            R.id.lin_suggest, R.id.confirm, R.id.download,R.id.left_image_id
            ,R.id.ll_prescription_notes_root,R.id.ll_inspection_root
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_chief:
                clickAndSome(chiefMsgLin, ivChief);
                break;
            case R.id.lin_history:
                clickAndSome(historyMsg, ivHistory);
                break;
            case R.id.lin_past:
                clickAndSome(pastMsg, ivPast);
                break;
            case R.id.lin_look:
                clickAndSome(lookMsg, ivLook);
                break;
            case R.id.lin_examination:
                clickAndSome(examinationMsg, ivExamination);
                break;
            case R.id.lin_suggest:
                clickAndSome(suggestMsg, ivSuggest);
                break;
            case R.id.confirm: //保存
                saveData();
                break;
            case R.id.download: //发送
                HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
                hashMap.put("orderCode", orderCode);
                String s = RetrofitUtil.encodeParam(hashMap);
                mPresenter.sendPatientRecord(s);
                break;
            case R.id.left_image_id:
                finish();
                break;
            case R.id.ll_prescription_notes_root:
                Bundle prescriptionBundle=new Bundle();
                prescriptionBundle.putString("patientCode",patientCode);
                prescriptionBundle.putString("patientName",patientName);
                prescriptionBundle.putString("orderId",orderCode);
                startActivity(PrescriptionNotesListActivity.class,prescriptionBundle);
                break;
            case R.id.ll_inspection_root:
                Bundle inspectionBundle=new Bundle();
                inspectionBundle.putString("patientCode",patientCode);
                inspectionBundle.putString("patientName",patientName);
                inspectionBundle.putString("orderId",orderCode);
                startActivity(InspectionOrderListActivity.class,inspectionBundle);

                break;
                default:

        }
    }

    private void saveData() {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode", orderCode);

        hashMap.put("operType", "0");
        hashMap.put("chiefComplaint", TextUtils.isEmpty(chiefEt.getText().toString().trim()) ? "无" : chiefEt.getText().toString().trim());
        hashMap.put("historyNew", TextUtils.isEmpty(newEt.getText().toString().trim()) ? "无" : newEt.getText().toString().trim());
        hashMap.put("historyPast", TextUtils.isEmpty(pastEt.getText().toString().trim()) ? "无" : pastEt.getText().toString().trim());
        hashMap.put("flagHistoryAllergy", "1");
        hashMap.put("historyAllergy", TextUtils.isEmpty(ed_examinationEt.getText().toString().trim()) ? "无" : ed_examinationEt.getText().toString().trim());
        hashMap.put("medicalExamination", TextUtils.isEmpty(lookEt.getText().toString().trim()) ? "无" : lookEt.getText().toString().trim());
        hashMap.put("treatmentProposal", TextUtils.isEmpty(suggestEt.getText().toString().trim()) ? "无" : suggestEt.getText().toString().trim());
        String s = RetrofitUtil.encodeParam(hashMap);
        mPresenter.savePatientMedicalRecord(s);

    }

    private void clickAndSome(LinearLayout vis, ImageView ani) {
        vis.setVisibility(vis.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (vis.getVisibility() == View.VISIBLE) {
            showAnimation(ani);
        } else {
            endAnimation(ani);
        }
    }


    public void showAnimation(View view) {
        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, centerX,
                centerY);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void endAnimation(View view) {

        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, centerX,
                centerY);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    @Override
    public void getPatientRecordDet(PatientRecordDetBean det) {
        docName.setText(det.getDoctorName());
        departmentName.setText(det.getDepartmentSecondName());
        diaOrder.setText(det.getTreatmentCardNum());
        diaTime.setText(DateUtils.getDateToYYYYMMDD(det.getCreateDate()));

//        chiefState.setVisibility(det.getFlagWriteChiefComplaint() == 1 ? View.VISIBLE : View.GONE);
        chiefState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        historyState.setVisibility(det.getFlagWriteHistoryNew() == 1 ? View.VISIBLE : View.GONE);
        historyState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        pastState.setVisibility(det.getFlagWriteHistoryPast() == 1 ? View.VISIBLE : View.GONE);
        pastState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        lookState.setVisibility(det.getFlagWriteMedicalExamination() == 1 ? View.VISIBLE : View.GONE);
        lookState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        diagState.setVisibility(det.getFlagWriteDiagnosis() == 1 ? View.VISIBLE : View.GONE);
//        diagState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        allerState.setVisibility(det.getFlagWriteHistoryAllergy() == 1 ? View.VISIBLE : View.GONE);
        examinationRadio.check(det.getFlagWriteHistoryAllergy() == 1 ? R.id.radio_yes : R.id.radio_no);
//        suggestState.setVisibility(det.getFlagWriteTreatmentProposal() == 1 ? View.VISIBLE : View.GONE);
        suggestState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);
//        checkState.setVisibility(det.getFlagWriteInspection() == 1 ? View.VISIBLE : View.GONE);
        checkState.setImageResource(det.getFlagWriteChiefComplaint() == 1 ? R.mipmap.iv_filled : R.mipmap.iv_noinput);

        chiefEt.setText(det.getChiefComplaint());
        newEt.setText(det.getHistoryNew());
        pastEt.setText(det.getHistoryPast());
        ed_examinationEt.setText(det.getHistoryAllergy());
//        lookTv.setText(det.getMedicalExamination());
        suggestEt.setText(det.getTreatmentProposal());
        paientName.setText(det.getPatientName());

        chiefTv.setText(TextUtils.isEmpty(det.getPatientChiefComplaint()) ? "无" : det.getPatientChiefComplaint());
        medicalHistoryTv.setText(TextUtils.isEmpty(det.getPatientHistoryNew()) ? "无" : det.getPatientHistoryNew());
        pastTv.setText( TextUtils.isEmpty(det.getPatientHistoryPast()) ? "无" : det.getPatientHistoryPast());
        examinationTv.setText(TextUtils.isEmpty(det.getPatientHistoryAllergy()) ? "无" : det.getPatientHistoryAllergy());
        switch (det.getPatientGender()) {
            case 0:
                gendder ="未知";
                break;
            case 1:
                gendder ="男";
                break;
            case 2:
                gendder ="女";
                break;
        }
        userGendder.setText(gendder);
        userAge.setText(String.valueOf(det.getPatientAge()));
        Glide.with(PatientRecordActivity.this).load(det.getPatientLogoUrl()).into(headView);
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void dealDataSucess(String msg) {
        ToastUtils.showShort(msg);
    }
}
