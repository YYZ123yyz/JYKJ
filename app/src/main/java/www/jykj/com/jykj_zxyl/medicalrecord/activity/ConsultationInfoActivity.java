package www.jykj.com.jykj_zxyl.medicalrecord.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InteractPatientInterrogation;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.InterrogationDetailsContract;
import www.jykj.com.jykj_zxyl.medicalrecord.InterrogationDetailsPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.ConsultationImagesAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.utils.ConvertUtils;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.PhotoDialog;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:问诊资料
 *
 * @author: qiuxinhai
 * @date: 2020-09-25 10:20
 */
public class ConsultationInfoActivity extends AbstractMvpBaseActivity<
        InterrogationDetailsContract.View, InterrogationDetailsPresenter> implements
        InterrogationDetailsContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_gender)
    TextView tvUserGender;
    @BindView(R.id.tv_user_age)
    TextView tvUserAge;
    @BindView(R.id.tv_blood_abnormal_date)
    TextView tvBloodAbnormalDate;
    @BindView(R.id.tv_hypertension_history)
    TextView tvHypertensionHistory;
    @BindView(R.id.tv_family_disease_history)
    TextView tvFamilyDiseaseHistory;
    @BindView(R.id.tv_measure_instrument)
    TextView tvMeasureInstrument;
    @BindView(R.id.tv_measure_mode)
    TextView tvMeasureMode;
    @BindView(R.id.tv_high_pressure)
    TextView tvHighPressure;
    @BindView(R.id.tv_low_pressure)
    TextView tvLowPressure;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_chief_complaint)
    TextView tvChiefComplaint;
    @BindView(R.id.tv_chief_complaint_process)
    TextView tvChiefComplaintProcess;
    @BindView(R.id.tv_other_disease)
    TextView tvOtherDisease;
    @BindView(R.id.tv_allergy_history)
    TextView tvAllergyHistory;
    @BindView(R.id.rv_imageView)
    RecyclerView rvImageView;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private String orderCode;//订单code
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private JYKJApplication mApp;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderCode= extras.getString("orderCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_consultation_info;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        //设置toolBar
        setToolBar();
        //初始化loading
        initLoadingAndRetryManager();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("问诊资料");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.sendInterrogationDetailRequest(orderCode,this);
    }


    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(scrollView);
        mLoadingLayoutManager.setRetryListener(v ->
                mPresenter.sendInterrogationDetailRequest(orderCode,this));


    }

    @Override
    public void showLoading(int code) {
        mLoadingLayoutManager.showLoading();
    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }

    @Override
    public void showRetry() {
        mLoadingLayoutManager.showError();
    }

    @Override
    public void getInterrogationDetailResult(InteractPatientInterrogation
                                                         interactPatientInterrogation) {


        setDetialData(interactPatientInterrogation);
        mLoadingLayoutManager.showContent();
    }

    /**
     * 设置详情数据
     * @param interactPatientInterrogation 问诊详情数据
     */
    @SuppressLint("DefaultLocale")
    private void setDetialData(InteractPatientInterrogation interactPatientInterrogation){
        tvUserName.setText(interactPatientInterrogation.getPatientName());
        tvUserPhone.setText(interactPatientInterrogation.getPatientLinkPhone());
        int gender = interactPatientInterrogation.getGender();
        if (gender==1) {
            tvUserGender.setText("男");
        }else if(gender==2){
            tvUserGender.setText("女");
        }
        tvUserAge.setText(interactPatientInterrogation.getBirthday());
        tvBloodAbnormalDate.setText(
                DateUtils.getDateToYYYYMMDD(
                        interactPatientInterrogation.getBloodPressureAbnormalDate()));
        int flagHtnHistory = interactPatientInterrogation.getFlagHtnHistory();
        if (flagHtnHistory==1) {
            tvHypertensionHistory.setText("是");
        }else{
            tvHypertensionHistory.setText("否");
        }
        int flagFamilyHtn = interactPatientInterrogation.getFlagFamilyHtn();
        if (flagFamilyHtn==1) {
            tvFamilyDiseaseHistory.setText("是");
        }else{
            tvFamilyDiseaseHistory.setText("否");
        }
        tvMeasureInstrument.setText(interactPatientInterrogation.getMeasureInstrumentName());
        tvMeasureMode.setText(interactPatientInterrogation.getMeasureModeName());
        tvHighPressure.setText(String.format("%d mmHg",
                interactPatientInterrogation.getHighPressureNum()));
        tvLowPressure.setText(String.format("%d mmHg",
                interactPatientInterrogation.getLowPressureNum()));
        tvHeartRate.setText(String.format("%d 次/分钟",
                interactPatientInterrogation.getHeartRateNum()));

        String chiefComplaint = interactPatientInterrogation.getChiefComplaint();
        tvChiefComplaint.setText(StringUtils.isNotEmpty(chiefComplaint)?chiefComplaint:"暂无");
        String historyNew = interactPatientInterrogation.getHistoryNew();
        tvChiefComplaintProcess.setText(StringUtils.isNotEmpty(historyNew)?historyNew:"暂无");
        String historyPast = interactPatientInterrogation.getHistoryPast();
        tvOtherDisease.setText(StringUtils.isNotEmpty(historyPast)?historyPast:"暂无");
        String historyAllergy = interactPatientInterrogation.getHistoryAllergy();
        tvAllergyHistory.setText(StringUtils.isNotEmpty(historyAllergy)?historyAllergy:"暂无");
        String interrogationImgArray = interactPatientInterrogation.getInterrogationImgArray();
        List<String> stringList = ConvertUtils.convertStrToList(interrogationImgArray);
        if (!CollectionUtils.isEmpty(stringList)) {
            setImageData(stringList);
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
        }


    }

    /**
     * 设置图片数据
     * @param imageUrls url列表
     */
    private void setImageData(List<String> imageUrls){
        FullyGridLayoutManager mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvImageView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvImageView.setHasFixedSize(true);
        ConsultationImagesAdapter consultationImagesAdapter=
                new ConsultationImagesAdapter(imageUrls,this);
        consultationImagesAdapter.setData(imageUrls);
        rvImageView.setAdapter(consultationImagesAdapter);
        consultationImagesAdapter.setOnItemClickListener(
                new ConsultationImagesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                PhotoDialog photoDialog = new PhotoDialog(ConsultationInfoActivity.this,
                        R.style.PhotoDialog);
                photoDialog.setDate(ConsultationInfoActivity.this, mApp
                        , ConvertUtils.convertListToBasicsImg(imageUrls), position);
                photoDialog.show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}
