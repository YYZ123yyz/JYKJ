package www.jykj.com.jykj_zxyl.appointment.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.hyphenate.easeui.utils.CollectionUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyydjk.library.DropDownMenu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TimelyTreatmentBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.MyClinicDetialContract;
import www.jykj.com.jykj_zxyl.appointment.MyClinicDetialPresenter;
import www.jykj.com.jykj_zxyl.appointment.adapter.OneVisitPatientAdapter;
import www.jykj.com.jykj_zxyl.appointment.adapter.TimelyTreatmentAdapter;
import www.jykj.com.jykj_zxyl.appointment.dialog.AddSignalSourceDialog;
import www.jykj.com.jykj_zxyl.appointment.dialog.AppointTimeDialog;
import www.jykj.com.jykj_zxyl.appointment.dialog.AppointTypeDialog;
import www.jykj.com.jykj_zxyl.appointment.listener.MyItemClickListener;
import www.jykj.com.jykj_zxyl.appointment.view.FirstView;
import www.jykj.com.jykj_zxyl.appointment.view.SecView;
import www.jykj.com.jykj_zxyl.appointment.view.ThirdView;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:我的诊所
 *
 * @author: qiuxinhai
 * @date: 2020-08-26 18:12
 */
public class MyClinicDetialActivity extends AbstractMvpBaseActivity<MyClinicDetialContract.View,
        MyClinicDetialPresenter> implements MyItemClickListener,MyClinicDetialContract.View {
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
    @BindView(R.id.ll_sign_up_treatment)
    LinearLayout llSignUpTreatment;
    @BindView(R.id.iv_sign_up_treatment)
    ImageView ivSignUpTreatment;
    @BindView(R.id.ll_one_treatment)
    LinearLayout llOneTreatment;
    @BindView(R.id.iv_one_treatment)
    ImageView ivOneTreatment;
    @BindView(R.id.ll_timely_treatment)
    LinearLayout llTimelyTreatment;
    @BindView(R.id.iv_timely_treatment)
    ImageView ivTimelyTreatment;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.dr_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ll_more_right)
    LinearLayout llMoreRight;
    @BindView(R.id.ed_patient_name)
    EditText edPatientName;
    @BindView(R.id.tv_start_age)
    TextView tvStartAge;
    @BindView(R.id.tv_end_age)
    TextView tvEndAge;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_disease_type)
    TextView tvDiseaseType;
    @BindView(R.id.ed_patient_chief)
    EditText edPatientChief;
    @BindView(R.id.tv_cancel_btn)
    TextView tvCancelBtn;
    @BindView(R.id.tv_confirm_btn)
    TextView tvConfirmBtn;
    @BindView(R.id.rl_current_date_root)
    RelativeLayout rlCurrentDateRoot;
    @BindView(R.id.rl_timely_treatment_root)
    RelativeLayout rlTimelyTreatmentRoot;
    @BindView(R.id.rv_timely_list)
    RecyclerView rvTimelyList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout2)
    SmartRefreshLayout mRefreshLayout2;
    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    TextView tvNoData;
    private TimePickerView timePickerView;
    private RecyclerView rvList;
    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private int visitType=1;//就诊类型 1签约就诊 2一次性就诊 3及时就诊
    private String headers[] = {"预约日期", "价格","接诊状态","更多筛选"};
    private List<View> popupViews;
    private JYKJApplication mApp;
    private List<PatientInfoBean> mPatientInfoBeans;
    private List<TimelyTreatmentBean> mTimelyTreatmentBeans;
    private List<BaseReasonBean> baseReasonBeans;
    private List<BaseReasonBean> priceBaseReasonBeans;
    private List<BaseReasonBean> signalSourceTypeReasonBeans;
    private PatientInfoBean currentPatientInfoBean;
    private OneVisitPatientAdapter oneVisitPatientAdapter;
    private TimelyTreatmentAdapter timelyTreatmentAdapter;
    private AddSignalSourceDialog addSignalSourceDialog;
    private AppointTimeDialog appointTimeDialog;
    private AppointTypeDialog appointTypeDialog;
    private String appointStartTime;
    private String appointEndTime;
    private String startAge;
    private String endAge;
    private String reserveStatus="";
    private String dateSort="";
    private String priceSort="";
    private String treatmentType="2";
    private String priceRegion="";
    private String mStartTime;//开始时间
    private String mEndTime;//结束时间
    private BaseReasonBean currentBaseReasonBean;//号源类型
    private String mSignalSourceNum;//号源数量
    private String reserveDateRosterCode;//医生排班明细编号
    private int currentWeek=-1;
    private long currentTimes;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        popupViews=new ArrayList<>();
        mPatientInfoBeans=new ArrayList<>();
        baseReasonBeans=new ArrayList<>();
        priceBaseReasonBeans=new ArrayList<>();
        mTimelyTreatmentBeans=new ArrayList<>();
        signalSourceTypeReasonBeans=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_clinic_detial;
    }

    @Override
    protected void initView() {
        super.initView();
        addSignalSourceDialog=new AddSignalSourceDialog(this);
        appointTimeDialog=new AppointTimeDialog(this);
        appointTypeDialog=new AppointTypeDialog(this);
        mApp = (JYKJApplication) getApplication();
        setToolBar();
        //初始化popview
        initPopupView();
        //添加监听
        addListener();
        //设置选中状态
        setChooseStatus(visitType);
        //初始化一次性就诊适配器
        initOneVisitAdapter();
        //初始化及时就诊适配器
        initTimelyTreatmentAdapter();
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendGetSignalSourceTypeRequest("900060");
        mPresenter.sendCancelAppointReasonRequest("900061");
        mPresenter.sendPriceRegionReasonRequest("900058");
        mPresenter.sendSearchReservePatientDoctorInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                treatmentType,pageSize+"",pageIndex+"",
                edPatientName.getText().toString(),endAge,startAge,
                appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,this);

    }

    /**
     * 初始化一次性就诊适配器
     */
    private void initOneVisitAdapter(){
        oneVisitPatientAdapter=new OneVisitPatientAdapter(this,mPatientInfoBeans);
        oneVisitPatientAdapter.setOnClickItemListener(new OneVisitPatientAdapter.OnClickItemListener() {
            @Override
            public void onClickCancelAppointment(int pos) {
                currentPatientInfoBean = mPatientInfoBeans.get(pos);
                Bundle bundle=new Bundle();
                bundle.putSerializable("baseReasonBeans", (Serializable) baseReasonBeans);
                bundle.putSerializable("currentPatientInfoBean",currentPatientInfoBean);
                startActivity(CancelAppointActivity.class,bundle,100);

            }

            @Override
            public void onClickReceiveTreatment(int pos) {
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                mPresenter.sendOperConfirmReservePatientDoctorInfoRequest(
                        patientInfoBean.getReserveCode(),
                        patientInfoBean.getReserveRosterDateCode()
                        ,patientInfoBean.getMainDoctorCode(),
                        patientInfoBean.getMainDoctorName(),
                        patientInfoBean.getMainPatientCode(),
                        patientInfoBean.getMainPatientName(),"0",MyClinicDetialActivity.this);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(oneVisitPatientAdapter);
    }


    /**
     * 初始化及时就诊适配器
     */
    private void initTimelyTreatmentAdapter(){
        timelyTreatmentAdapter=new TimelyTreatmentAdapter(this,mTimelyTreatmentBeans);
        rvTimelyList.setLayoutManager(new LinearLayoutManager(this));
        rvTimelyList.setAdapter(timelyTreatmentAdapter);
        timelyTreatmentAdapter.setOnClickItemListener(new TimelyTreatmentAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {
                TimelyTreatmentBean timelyTreatmentBean = mTimelyTreatmentBeans.get(pos);
                String sourceType = timelyTreatmentBean.getSourceType();
                if (sourceType.equals("2")) {
                    mStartTime = timelyTreatmentBean.getStartTimes();
                    mEndTime= timelyTreatmentBean.getEndTimes();
                    int reserveType = timelyTreatmentBean.getReserveType();
                    currentBaseReasonBean = getCurrentReserveBean(reserveType);
                    mSignalSourceNum=timelyTreatmentBean.getReserveCount()+"";
                    currentWeek = timelyTreatmentBean.getWeek();
                    currentTimes = timelyTreatmentBean.getTimes();
                    reserveDateRosterCode=timelyTreatmentBean.getReserveDateRosterCode();
                    addSignalSourceDialog.show();
                    addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
                    addSignalSourceDialog.setSignalType(currentBaseReasonBean);
                    addSignalSourceDialog.setSignalNum(mSignalSourceNum);

                }

            }
        });
    }

    /**
     * 初始化popupView
     */
    private void initPopupView(){
        FirstView firstView = new FirstView(MyClinicDetialActivity.this);
        firstView.setListener(this);
        popupViews.add(firstView.firstView());

        SecView secView = new SecView(this);
        secView.setListener(this);
        popupViews.add(secView.secView());

        ThirdView thirdView = new ThirdView(this);
        thirdView.setListener(this);
        popupViews.add(thirdView.secView());


        SecView fourView = new SecView(this);
        fourView.setListener(this);
        popupViews.add(fourView.secView());


        @SuppressLint("InflateParams")
        View fifthView = LayoutInflater.from(this).inflate(R.layout.activity_one_visit, null);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        fifthView.setLayoutParams(lParams);
        rvList=fifthView.findViewById(R.id.rv_list);
        tvNoData=fifthView.findViewById(R.id.tv_no_data);
        mRefreshLayout=fifthView.findViewById(R.id.refreshLayout);

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, fifthView);

    }





    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的诊所");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 设置按钮状态
     * @param visitType 类型
     */
    private void setChooseStatus(int visitType) {
        switch (visitType) {
            case 1:
                rlTimelyTreatmentRoot.setVisibility(View.GONE);
                dropDownMenu.setVisibility(View.VISIBLE);
                llMoreRight.setVisibility(View.VISIBLE);
                rlCurrentDateRoot.setVisibility(View.GONE);
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment_press);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment);
                treatmentType="2";
                break;
            case 2:
                rlTimelyTreatmentRoot.setVisibility(View.GONE);
                dropDownMenu.setVisibility(View.VISIBLE);
                llMoreRight.setVisibility(View.VISIBLE);
                rlCurrentDateRoot.setVisibility(View.GONE);
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment_press);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment);
                treatmentType="1";
                break;
            case 3:
                rlTimelyTreatmentRoot.setVisibility(View.VISIBLE);
                dropDownMenu.setVisibility(View.GONE);
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment_press);
                llMoreRight.setVisibility(View.GONE);
                rlCurrentDateRoot.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    /**
     * 根据Id获取号源类型
     * @param reserveType 号源类型
     * @return BaseReasonBean
     */
    private BaseReasonBean getCurrentReserveBean(int reserveType){
        BaseReasonBean currentBaseReasonBean=null;
        if (!CollectionUtils.isEmpty(signalSourceTypeReasonBeans)) {
            for (BaseReasonBean baseReasonBean : signalSourceTypeReasonBeans) {
                int attrCode = baseReasonBean.getAttrCode();
                if (attrCode==reserveType) {
                    currentBaseReasonBean=baseReasonBean;
                    break;
                }
            }
        }
        return currentBaseReasonBean;

    }

    /**
     * 添加监听
     */
    private void addListener(){
        llSignUpTreatment.setOnClickListener(v -> {
            visitType=1;
            setChooseStatus(visitType);
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,this);
        });
        llOneTreatment.setOnClickListener(v -> {
            visitType=2;
            setChooseStatus(visitType);
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,this);
        });
        llTimelyTreatment.setOnClickListener(v -> {
            visitType=3;
            setChooseStatus(visitType);
            mPresenter.sendSearchReserveDoctorDateRosterInfoImmediateRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
        });
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            pageIndex=1;
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort
                    ,MyClinicDetialActivity.this);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageIndex++;
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                    MyClinicDetialActivity.this);
        });
        mRefreshLayout2.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout2.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout2.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.sendSearchReserveDoctorDateRosterInfoImmediateRequest(
                        mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                        MyClinicDetialActivity.this);
            }
        });

        llMoreRight.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));
        tvStartAge.setOnClickListener(v -> showChoosedAgeDialog(1));
        tvEndAge.setOnClickListener(v -> showChoosedAgeDialog(2));
        tvStartTime.setOnClickListener(v -> showChoosedTimeDialog(1));
        tvEndTime.setOnClickListener(v -> showChoosedTimeDialog(2));

        tvPrice.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(priceBaseReasonBeans)) {
                showChoosedPriceDialog(priceBaseReasonBeans);
            }

        });
        tvDiseaseType.setOnClickListener(v -> {

        });
        tvCancelBtn.setOnClickListener(v -> drawerLayout.closeDrawer(Gravity.RIGHT));
        tvConfirmBtn.setOnClickListener(v -> {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            pageIndex=1;
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                    MyClinicDetialActivity.this);

        });
        tvAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime=null;
                mEndTime=null;
                currentBaseReasonBean=null;
                mSignalSourceNum=null;
                reserveDateRosterCode=null;
                addSignalSourceDialog.show();
                addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
                addSignalSourceDialog.setSignalType(currentBaseReasonBean);
                addSignalSourceDialog.setSignalNum(mSignalSourceNum);
            }
        });
        addSignalSourceDialog.setOnClickDialogListener(new AddSignalSourceDialog.OnClickDialogListener() {
            @Override
            public void onClickChooseTime() {
                appointTimeDialog.show();
            }

            @Override
            public void onClickChooseType() {
                if (!CollectionUtils.isEmpty(signalSourceTypeReasonBeans)) {
                    appointTypeDialog.show();
                    appointTypeDialog.setData(signalSourceTypeReasonBeans);
                }

            }

            @Override
            public void onSignalChange(String signalSourceNum) {
                mSignalSourceNum=signalSourceNum;
            }

            @Override
            public void onClickEnsure() {
                if (currentWeek==-1) {
                    ToastUtils.showToast("请选择星期数");
                    return;
                }
                if (!StringUtils.isNotEmpty(mStartTime)) {
                    ToastUtils.showToast("请选择放号时间");
                    return;
                }
                if (currentBaseReasonBean==null) {
                    ToastUtils.showToast("请选择号源类型");
                    return;
                }

                if (!StringUtils.isNotEmpty(mSignalSourceNum)) {
                    ToastUtils.showToast("号源数量不能为空");
                    return;
                }

                addSignalSourceDialog.dismiss();
                mPresenter.sendOperUpdDoctorDateRosterInfoRequest(
                        mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                        mApp.mViewSysUserDoctorInfoAndHospital.getUserName()
                        ,mApp.mViewSysUserDoctorInfoAndHospital.getUserNameAlias(),
                        currentWeek+"",
                        currentBaseReasonBean.getAttrCode()+""
                        ,currentBaseReasonBean.getAttrName(),
                        DateUtils.getDateToYYYYMMDD(currentTimes),
                        mStartTime,mEndTime,mSignalSourceNum,"1",
                        reserveDateRosterCode,MyClinicDetialActivity.this
                );
            }
        });

        appointTimeDialog.setOnClickChoosedTimeListener((startTime, endTime) -> {

            mStartTime=startTime;
            mEndTime=endTime;
            if (addSignalSourceDialog.isShowing()) {
                addSignalSourceDialog.setAppointTime(startTime,endTime);
            }
        });
        appointTypeDialog.setOnChoosedItemListener(baseReasonBean -> {
            currentBaseReasonBean=baseReasonBean;
            if (addSignalSourceDialog.isShowing()) {
                addSignalSourceDialog.setSignalType(baseReasonBean);
            }
        });
    }

    /**
     * 预约选择时间弹框
     * @param type 1为预约开始时间 2为预约结束时间
     */
    private void showChoosedTimeDialog(int type) {
        timePickerView = new TimePickerBuilder(this, (date, v) -> {
            if (DateUtils.isLessThanCurrentDate(DateUtils.getDate(date))) {
                ToastUtils.showToast("预约日期不能小于当前日期");
                return;
            }
            if (type==1) {
                appointStartTime=DateUtils.getDate(date);
                tvStartTime.setText(appointStartTime);
            }else if(type==2){
                appointEndTime=DateUtils.getDate(date);
                tvEndTime.setText(appointEndTime);
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").build();
        timePickerView.show();
    }


    /**
     * 选择年龄范围弹框
     * @param type 1为开始年龄 2为结束年龄
     */
    private void showChoosedAgeDialog(int type) {
        List<String> stringList=new ArrayList<>();
        for(int i=0;i<100;i++) {
            stringList.add(i+1+"");
        }

        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
            if (type==1) {
                startAge=options1+"";
                tvStartAge.setText(startAge);
            }else if(type==2){
                endAge=options1+"";
                tvEndAge.setText(endAge);
            }
        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(stringList, null, null);
        optionPickUnit.show();
    }


    /**
     * 选择价格区间
     * @param list 价格区间列表
     */
    private void showChoosedPriceDialog(List<BaseReasonBean> list){
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    tvPrice.setText(list.get(options1).getAttrName());
                    priceRegion=list.get(options1).getAttrCode()+"";
                }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(convertDataToPriceType(list), null, null);
        optionPickUnit.show();
    }

    private List<String> convertDataToPriceType(List<BaseReasonBean> priceBaseReasonBeans){
        List<String> priceTypeList=new ArrayList<>();
        for (BaseReasonBean priceBaseReasonBean : priceBaseReasonBeans) {
            priceTypeList.add(priceBaseReasonBean.getAttrName());
        }
        return priceTypeList;
    }




    @Override
    public void onItemClick(View view, int postion, String string) {
        switch (postion){
            case 1:
                dateSort=string;
                break;
            case 2:
                priceSort=string;
                break;
            case 3:
                reserveStatus=string;
                break;
                default:
        }

        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        }
        pageIndex=1;
        mPresenter.sendSearchReservePatientDoctorInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                treatmentType,pageSize+"",pageIndex+"",
                edPatientName.getText().toString(),endAge,startAge,
                appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                MyClinicDetialActivity.this);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showLoading(int code) {
        if (code==100) {
            mRefreshLayout.autoRefresh();
        }else if(code==103){
            mRefreshLayout2.autoRefresh();
        }else if(code==101||code==104){
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getSearchReservePatientDoctorInfoResult(List<PatientInfoBean> patientInfoBeans) {
        if (pageIndex == 1) {
            mPatientInfoBeans.clear();
            mRefreshLayout.finishRefresh(500);
        }
        if (!CollectionUtils.isEmpty(patientInfoBeans)) {
            mPatientInfoBeans.addAll(patientInfoBeans);
            oneVisitPatientAdapter.setData(mPatientInfoBeans);
            oneVisitPatientAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
            tvNoData.setVisibility(View.GONE);
        } else {
            if (pageIndex == 1) {
                oneVisitPatientAdapter.setData(mPatientInfoBeans);
                oneVisitPatientAdapter.notifyDataSetChanged();
                tvNoData.setVisibility(View.VISIBLE);
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }


    }

    @Override
    public void getOperConfirmReservePatientDoctorInfoResult(boolean isSucess, String msg) {
        pageIndex=1;
        mPresenter.sendSearchReservePatientDoctorInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                treatmentType,pageSize+"",pageIndex+"",
                edPatientName.getText().toString(),endAge,startAge,
                appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                MyClinicDetialActivity.this);
    }

    @Override
    public void getOperCancelReservePatientDoctorInfoResult(boolean isSucess, String msg) {
        pageIndex=1;
        mPresenter.sendSearchReservePatientDoctorInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                treatmentType,pageSize+"",pageIndex+"",
                edPatientName.getText().toString(),endAge,startAge,
                appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                MyClinicDetialActivity.this);
    }

    @Override
    public void getCancelAppointReasonResult(List<BaseReasonBean> baseReasonBeans) {
        this.baseReasonBeans=baseReasonBeans;

    }

    @Override
    public void getPriceRegionReasonResult(List<BaseReasonBean> baseReasonBeans) {
        this.priceBaseReasonBeans=baseReasonBeans;
    }

    @Override
    public void getSignalSourceTypeResult(List<BaseReasonBean> baseReasonBeans) {
        this.signalSourceTypeReasonBeans=baseReasonBeans;
    }

    @Override
    public void getTimelyTreatmentListResult(List<TimelyTreatmentBean> timelyTreatmentBeans) {
        if (pageIndex == 1) {
            mTimelyTreatmentBeans.clear();
            mRefreshLayout2.finishRefresh(500);
        }
        if (!CollectionUtils.isEmpty(timelyTreatmentBeans)) {
            long times = timelyTreatmentBeans.get(0).getTimes();
            tvCurrentDate.setText(DateUtils.stampToDate(times));
            mTimelyTreatmentBeans.addAll(timelyTreatmentBeans);
            timelyTreatmentAdapter.setData(mTimelyTreatmentBeans);
            timelyTreatmentAdapter.notifyDataSetChanged();
            mRefreshLayout2.finishLoadMore();
            tvNoData2.setVisibility(View.GONE);
        } else {
            if (pageIndex == 1) {
                timelyTreatmentAdapter.setData(mTimelyTreatmentBeans);
                timelyTreatmentAdapter.notifyDataSetChanged();
                tvNoData2.setVisibility(View.VISIBLE);
            } else {
                mRefreshLayout2.finishLoadMore();
            }
        }
    }

    @Override
    public void getOperDoctorScheduResult(OperDoctorScheduResultBean operDoctorScheduResult) {
        mPresenter.sendSearchReserveDoctorDateRosterInfoImmediateRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
    }

    @Override
    public void getOperDoctorScheduError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==1001){
            pageIndex=1;
            mPresenter.sendSearchReservePatientDoctorInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    treatmentType,pageSize+"",pageIndex+"",
                    edPatientName.getText().toString(),endAge,startAge,
                    appointStartTime,appointEndTime,priceRegion,reserveStatus,dateSort,priceSort,
                    MyClinicDetialActivity.this);
        }
    }
}
