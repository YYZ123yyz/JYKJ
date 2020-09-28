package www.jykj.com.jykj_zxyl.activity.home.mypatient.fillindetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ViewInteractOrderMedicalRecordDetailBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.CircleImageView;

/**
 * 医生填写详情
 */
public class FillindetailsActivity extends AbstractMvpBaseActivity<FillindetailsContract.View,
        FillindetailsPresenter> implements FillindetailsContract.View {
    @BindView(R.id.ri_back)
    RelativeLayout riBack;
    @BindView(R.id.lin_chief)
    LinearLayout linChief;
    @BindView(R.id.lin_history)
    LinearLayout linHistory;
    @BindView(R.id.lin_past)
    LinearLayout linPast;
    @BindView(R.id.lin_examination)
    LinearLayout linExamination;
    @BindView(R.id.lin_look)
    LinearLayout linLook;
    @BindView(R.id.lin_suggest)
    LinearLayout linSuggest;
    @BindView(R.id.treatment)
    TextView treatment;
    @BindView(R.id.lin_suggest_1)
    LinearLayout linSuggest1;
    @BindView(R.id.lin_checklist)
    LinearLayout linChecklist;
    @BindView(R.id.dispaly_fullpayment)
    ImageView dispalyFullpayment;
    /*@BindView(R.id.price)
    TextView price;*/
    @BindView(R.id.share)
    Button share;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.lin_prescriptionnote)
    LinearLayout linPrescriptionnote;
    private String gennder;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout diagFlow; //临床诊断
    @BindView(R.id.flowlayout_check)
    TagFlowLayout checkFlow;//检查检验
    @BindView(R.id.flowlayout_prescr)
    TagFlowLayout prescrFlow;//处方笺
    @BindView(R.id.lin_chief_msg)
    LinearLayout chiefMsg;
    @BindView(R.id.dispaly_Chief)
    ImageView ivChief;
    @BindView(R.id.userHead)
    CircleImageView userHead;//头像
    @BindView(R.id.userName)
    TextView patientName;//患者名
    @BindView(R.id.usergendder)
    TextView userGennder;//性别
    @BindView(R.id.userage)
    TextView userAger;//年龄
    @BindView(R.id.userDoctro)
    TextView userDoc;//医生名
    @BindView(R.id.userdepartment)
    TextView userdepartMent;//诊疗号
    @BindView(R.id.department)
    TextView departMent;//诊室
    @BindView(R.id.time)
    TextView time;//时间
    @BindView(R.id.chief)
    TextView chiefTv;//主诉
    @BindView(R.id.medicalhistory)
    TextView medicalHistoryTv;//现病史
    @BindView(R.id.past)
    TextView pastTv;//既往病史
    @BindView(R.id.examination)
    TextView examinationTv;//过敏史
    @BindView(R.id.physical)
    TextView lookTv;//查体
    @BindView(R.id.lin_medicalhistoryf_msg)
    LinearLayout linMedicalhistoryfMsg;
    @BindView(R.id.dispaly_medicalhistory)
    ImageView displayMedicalhistory;
    @BindView(R.id.lin_past_msg)
    LinearLayout linPastMsg;
    @BindView(R.id.dispaly_past)
    ImageView displayPast;
    @BindView(R.id.lin_examination_msg)
    LinearLayout linExaminationMsg;
    @BindView(R.id.dispaly_examination)
    ImageView displayExamination;
    @BindView(R.id.lin_look_msg)
    LinearLayout linLokMsg;
    @BindView(R.id.dispaly_physical)
    ImageView displayLook;
    @BindView(R.id.lin_suggest_msg)
    LinearLayout linSuggestMsg;
    @BindView(R.id.dispaly_treatment)
    ImageView displaySuggest;
  /*  @BindView(R.id.prescr_part)
    LinearLayout linPrescr;*/
  /*  @BindView(R.id.drug_recycle)
    RecyclerView drugRecycleview;*/
    @BindView(R.id.into_check)
    ImageView intoCheck;
    @BindView(R.id.lin_confirm)
    LinearLayout linConfig;
    @BindView(R.id.lin_fullpayment)
    LinearLayout linOnyShare;
    private JYKJApplication mApp;
    private String recordId;
    private LayoutInflater mInflater;
    private static final int DRUG_TYPE_NOMAL = 0;
    private static final int DRUG_TYPE_START = 1;
    private static final int DRUG_TYPE_END = 2;
    private String patientCode;
    private String patientName1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fillindetails;
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
    protected void initData() {
        super.initData();
        mPresenter.sendSearchFillindetailsRequest(mApp.loginDoctorPosition, "1", patientCode, patientName1, recordId);
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        Intent intent = getIntent();
        recordId = intent.getStringExtra("recordId");
        SharedPreferences mSharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        patientCode = mSharedPreferences.getString("patientCode", "");
        patientName1 = mSharedPreferences.getString("patientName", "");
    }

    @Override
    public void getSearchFillindetailsResult(ViewInteractOrderMedicalRecordDetailBean viewInteractOrderMedicalRecordDetailBeans) {
       if(viewInteractOrderMedicalRecordDetailBeans!=null){
           Glide.with(FillindetailsActivity.this).load(viewInteractOrderMedicalRecordDetailBeans.getPatientLogoUrl()).into(userHead);

           patientName.setText(viewInteractOrderMedicalRecordDetailBeans.getPatientName());
           userGennder.setText(viewInteractOrderMedicalRecordDetailBeans.getPatientGender() == 0 ? "未知" : (viewInteractOrderMedicalRecordDetailBeans.getPatientGender() == 1 ? "男" : "女"));
           switch (viewInteractOrderMedicalRecordDetailBeans.getPatientGender()) {
               case 0:
                   gennder = "未知";
                   break;
               case 1:
                   gennder = "男";
                   break;
               case 2:
                   gennder = "女";
                   break;
           }
           recordId = viewInteractOrderMedicalRecordDetailBeans.getRecordCode();
           userGennder.setText(gennder);
           userAger.setText(String.valueOf(viewInteractOrderMedicalRecordDetailBeans.getPatientAge()));
           userDoc.setText(viewInteractOrderMedicalRecordDetailBeans.getDoctorName());
           userdepartMent.setText(viewInteractOrderMedicalRecordDetailBeans.getTreatmentCardNum());
           departMent.setText(viewInteractOrderMedicalRecordDetailBeans.getDepartmentSecondName());
           time.setText(DateUtils.getStringTimeOfYMD(viewInteractOrderMedicalRecordDetailBeans.getCreateDate()));

           chiefTv.setText(viewInteractOrderMedicalRecordDetailBeans.getChiefComplaint());
           medicalHistoryTv.setText(viewInteractOrderMedicalRecordDetailBeans.getHistoryNew());
           pastTv.setText(viewInteractOrderMedicalRecordDetailBeans.getHistoryPast());
           examinationTv.setText(viewInteractOrderMedicalRecordDetailBeans.getFlagHistoryAllergy() == 0 ? "无" : viewInteractOrderMedicalRecordDetailBeans.getHistoryAllergy());
           lookTv.setText(viewInteractOrderMedicalRecordDetailBeans.getMedicalExamination());
           diagFlow.setAdapter(getTagAdapter(viewInteractOrderMedicalRecordDetailBeans.getDiagnosisName(), diagFlow));
           checkFlow.setAdapter(getTagAdapter(viewInteractOrderMedicalRecordDetailBeans.getInspectionName(), checkFlow));


           if (viewInteractOrderMedicalRecordDetailBeans.getFlagSendMedicalRecord() == 0) { //未确认
               prescrFlow.setVisibility(View.VISIBLE);
            //   linPrescr.setVisibility(View.GONE);
               prescrFlow.setAdapter(getTagAdapter(viewInteractOrderMedicalRecordDetailBeans.getDrugName(), prescrFlow));
               intoCheck.setVisibility(View.GONE);
               linConfig.setVisibility(View.VISIBLE);
               linOnyShare.setVisibility(View.GONE);
           } else { //已确认
               prescrFlow.setVisibility(View.GONE);
          //     linPrescr.setVisibility(View.VISIBLE);
               intoCheck.setVisibility(View.VISIBLE);
               linConfig.setVisibility(View.GONE);
               linOnyShare.setVisibility(View.VISIBLE);
               List<ViewInteractOrderMedicalRecordDetailBean.InteractOrderPrescribeListBean> interactOrderPrescribeList = viewInteractOrderMedicalRecordDetailBeans.getInteractOrderPrescribeList();
               List<ViewInteractOrderMedicalRecordDetailBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfoBeans = new ArrayList<>();
               for (int i = 0; i < interactOrderPrescribeList.size(); i++) {

                   List<ViewInteractOrderMedicalRecordDetailBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfo = interactOrderPrescribeList.get(i).getPrescribeInfo();
                   if (prescribeInfo.size() > 1) { //有一组
                       for (int j = 0; j < prescribeInfo.size(); j++) {
                           if (j == 0) { //一组开头
                               prescribeInfo.get(j).setType(DRUG_TYPE_START);
                           } else if (j == (prescribeInfo.size()) - 1) {//一组结尾
                               prescribeInfo.get(j).setType(DRUG_TYPE_END);
                           } else {//中间
                               prescribeInfo.get(j).setType(DRUG_TYPE_NOMAL);
                           }
                       }
                   } else { //没有一组
                       prescribeInfo.get(0).setType(DRUG_TYPE_NOMAL);
                   }
                   prescribeInfoBeans.addAll(prescribeInfo);
               }
            /*   MedicalRecordDrugAdapter medicalRecordDrugAdapter = new MedicalRecordDrugAdapter(R.layout.item_record_drug, prescribeInfoBeans);
               drugRecycleview.setAdapter(medicalRecordDrugAdapter);*/


           }


       }
    }

    @OnClick({R.id.ri_back,R.id.confirm, R.id.download, R.id.lin_chief, R.id.lin_history,R.id.lin_prescriptionnote,
            R.id.lin_past, R.id.lin_examination, R.id.lin_look, R.id.lin_suggest, R.id.lin_checklist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ri_back:
                finish();
                break;
            case R.id.lin_chief:
                clickAndSome(chiefMsg, ivChief);
                break;
            case R.id.lin_history:
                clickAndSome(linMedicalhistoryfMsg, displayMedicalhistory);
                break;
            case R.id.lin_past:
                clickAndSome(linPastMsg, displayPast);
                break;
            case R.id.lin_examination:
                clickAndSome(linExaminationMsg, displayExamination);
                break;
            case R.id.lin_look:
                clickAndSome(linLokMsg, displayLook);
                break;
            case R.id.lin_suggest:
                clickAndSome(linSuggestMsg, displaySuggest);
                break;
        }
    }
    private void clickAndSome(LinearLayout vis, ImageView ani) {
        vis.setVisibility(vis.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (vis.getVisibility() == View.VISIBLE) {
            showAnimation(ani);
        } else {
            endAnimation(ani);
        }
    }
    private List<String> dealData(String msg) {
        ArrayList<String> strings = new ArrayList<>();
        if (msg.contains(",")) {
            String[] split = msg.split(",");
            strings.addAll(Arrays.asList(split));
        } else {
            strings.add(msg);
        }
        return strings;
    }

    private TagAdapter<String> getTagAdapter(String msg, TagFlowLayout checkFlow) {
        return new TagAdapter<String>(dealData(msg)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                        checkFlow, false);
                tv.setText(s);
                return tv;
            }
        };
    }

    @Override
    public void showLoading(int code) {

    }

}