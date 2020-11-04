package www.jykj.com.jykj_zxyl.appointment.activity;


import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.dialog.ColumnWheelDialog;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DcotorScheduTimesWeekBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.base_view.SwipeItemLayout;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.OnlineScheduTemplateContract;
import www.jykj.com.jykj_zxyl.appointment.OnlineScheduTemplatePresenter;
import www.jykj.com.jykj_zxyl.appointment.adapter.CalendarAdapter;
import www.jykj.com.jykj_zxyl.appointment.adapter.DoctorSeheduTimeWeekAdapter;
import www.jykj.com.jykj_zxyl.appointment.data.DataUtil;
import www.jykj.com.jykj_zxyl.appointment.dialog.AddSignalSourceDialog;
import www.jykj.com.jykj_zxyl.appointment.dialog.AppointTimeDialog;
import www.jykj.com.jykj_zxyl.app_base.base_dialog.CommonConfirmDialog;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:线上排班模版
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 10:48
 */
public class MyOnlineScheduTemplateActivity extends AbstractMvpBaseActivity<
        OnlineScheduTemplateContract.View, OnlineScheduTemplatePresenter> implements
        OnlineScheduTemplateContract.View {
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
    @BindView(R.id.rv_list_head)
    RecyclerView rvListHead;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_content_root)
    LinearLayout llContentRoot;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private JYKJApplication mApp;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private CalendarAdapter mCalendarAdapter;
    private DoctorSeheduTimeWeekAdapter doctorSeheduTimeAdapter;
    private AddSignalSourceDialog addSignalSourceDialog;
    private AppointTimeDialog appointTimeDialog;
    private CommonConfirmDialog checkStepDialog;
    private List<DcotorScheduTimesWeekBean> doctorScheduTimesBeans;
    private List<CalendarItemBean> calendarItemBeans;
    private String mStartTime;//开始时间
    private String mEndTime;//结束时间
    private BaseReasonBean currentBaseReasonBean;//号源类型
    private CalendarItemBean currentCalendarItemBean;//当前日期
    private String mSignalSourceNum;//号源数量
    private String reserveDateRosterCode;//医生排班明细编号
    private int currentPos;
    private String checkStep="0";//校验步骤
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        calendarItemBeans=new ArrayList<>();
        addSignalSourceDialog=new AddSignalSourceDialog(this);
        appointTimeDialog=new AppointTimeDialog(this);
        checkStepDialog=new CommonConfirmDialog(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_online_scheduling_template;
    }


    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        setToolBar();
        initLoadingAndRetryManager();
        initCalendarAdapter();
        initSeheduAdapter();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchReserveDoctorRosterInfoHeaderRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的线上排班设置");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime=null;
                mEndTime=null;
                currentBaseReasonBean=null;
                mSignalSourceNum=null;
                reserveDateRosterCode=null;
                checkStep="0";
                addSignalSourceDialog.setShowSignalType(false);
                addSignalSourceDialog.show();
                addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
                addSignalSourceDialog.setSignalType(currentBaseReasonBean);
                addSignalSourceDialog.setSignalNum(mSignalSourceNum);
            }
        });
        addSignalSourceDialog.setOnClickDialogListener(new AddSignalSourceDialog.OnClickDialogListener() {
            @Override
            public void onClickChooseTime() {
                //appointTimeDialog.show();
                showChoosedTimesDialog();
            }

            @Override
            public void onClickChooseType() {

            }

            @Override
            public void onSignalChange(String signalSourceNum) {
                mSignalSourceNum=signalSourceNum;
            }

            @Override
            public void onClickEnsure() {

                if (currentCalendarItemBean==null) {
                    ToastUtils.showToast("请选择星期数");
                    return;
                }
                if (!StringUtils.isNotEmpty(mStartTime)) {
                    ToastUtils.showToast("请选择放号时间");
                    return;
                }

                if (!StringUtils.isNotEmpty(mSignalSourceNum)) {
                    ToastUtils.showToast("号源数量不能为空");
                    return;
                }

                addSignalSourceDialog.dismiss();
                String weekStr = DateUtils.getWeekStr(currentCalendarItemBean.getWeek());
                mPresenter.sendOperUpdReserveDoctorRosterInfoRequest(
                        mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                        mApp.mViewSysUserDoctorInfoAndHospital.getUserName()
                        ,mApp.mViewSysUserDoctorInfoAndHospital.getUserNameAlias(),
                        currentCalendarItemBean.getWeek()+"",
                        weekStr,mStartTime,mEndTime,mSignalSourceNum,checkStep
                        ,reserveDateRosterCode,MyOnlineScheduTemplateActivity.this

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
        checkStepDialog.setOnClickListener(() -> {
            String weekStr = DateUtils.getWeekStr(currentCalendarItemBean.getWeek());
            mPresenter.sendOperUpdReserveDoctorRosterInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                    mApp.mViewSysUserDoctorInfoAndHospital.getUserName()
                    ,mApp.mViewSysUserDoctorInfoAndHospital.getUserNameAlias(),
                    currentCalendarItemBean.getWeek()+"",
                    weekStr,mStartTime,mEndTime,mSignalSourceNum,checkStep
                    ,reserveDateRosterCode,MyOnlineScheduTemplateActivity.this);
        });

    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(llContentRoot);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();

        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化周日历Adapter
     */
    private void initCalendarAdapter(){
        mCalendarAdapter=new CalendarAdapter(this,calendarItemBeans);
        mCalendarAdapter.setShowDate(false);
        mCalendarAdapter.setOnClickItemListener(new CalendarAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {
                currentPos=pos;
                currentCalendarItemBean=calendarItemBeans.get(pos);
                setChoosedCalendar(pos);
                mPresenter.sendSearchReserveDoctorRosterInfoRequest(
                        mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                        currentCalendarItemBean.getWeek()+"",
                        MyOnlineScheduTemplateActivity.this);
            }
        });
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvListHead.setLayoutManager(linearLayoutManager);
        rvListHead.setAdapter(mCalendarAdapter);
    }


    /**
     * 设置选中状态
     * @param pos 当前位置
     */
    private void setChoosedCalendar(int pos){
        for (int i = 0; i < calendarItemBeans.size(); i++) {
            if (pos==i) {
                calendarItemBeans.get(i).setChoosed(true);
            }else{
                calendarItemBeans.get(i).setChoosed(false);
            }
        }
        mCalendarAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化RecyclerView
     */
    private void initSeheduAdapter(){
        rvList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        doctorSeheduTimeAdapter=new DoctorSeheduTimeWeekAdapter(this,doctorScheduTimesBeans);
        doctorSeheduTimeAdapter.setOnClickItemListener(
                new DoctorSeheduTimeWeekAdapter.OnClickItemListener() {
                    @Override
                    public void onClickUpdate(int pos) {
                        DcotorScheduTimesWeekBean doctorScheduTimesBean = doctorScheduTimesBeans.get(pos);
                        mStartTime = doctorScheduTimesBean.getStartTimes();
                        mEndTime = doctorScheduTimesBean.getEndTimes();
                        mSignalSourceNum=doctorScheduTimesBean.getReserveCount()+"";
                        reserveDateRosterCode=doctorScheduTimesBean.getReserveDoctorRosterCode();
                        addSignalSourceDialog.show();
                        addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
                        addSignalSourceDialog.setSignalNum(mSignalSourceNum);
                        addSignalSourceDialog.setShowSignalType(false);
                    }

                    @Override
                    public void onClickDelete(int pos) {
                        DcotorScheduTimesWeekBean doctorScheduTimesBean = doctorScheduTimesBeans.get(pos);
                        mPresenter.sendOperDelReserveDoctorRosterInfoRequest(
                                doctorScheduTimesBean.getReserveDoctorRosterCode(),
                                MyOnlineScheduTemplateActivity.this);
                    }
                });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(doctorSeheduTimeAdapter);
    }

    @Override
    public void showLoading(int code) {
        if (code==102) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
       dismissLoading();
    }

    @Override
    public void getSearchReserveDoctorRosterInfoHeaderResult(List<CalendarItemBean> itemBeans) {
        this.calendarItemBeans=itemBeans;
        mCalendarAdapter.setData(calendarItemBeans);
        mCalendarAdapter.notifyDataSetChanged();
        if(currentCalendarItemBean==null){
            currentCalendarItemBean = getCurrentWeekCalendar(calendarItemBeans);
        }else{
            setChoosedCalendar(currentPos);
        }
        mPresenter.sendSearchReserveDoctorRosterInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                currentCalendarItemBean.getWeek()+"",this);

    }



    /**
     * 选择时间弹框
     */
    private void showChoosedTimesDialog(){
        ColumnWheelDialog<WheelItem, WheelItem, WheelItem, WheelItem, WheelItem>
                columnWheelDialog=new ColumnWheelDialog<>(this);
        columnWheelDialog.show();
        columnWheelDialog.setShowTitle(true);
        columnWheelDialog.setCancelButton("取消", null);
        columnWheelDialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            //tvResult.setText(result);
            String dateToYYYYMMDD = DateUtils.getDateToYYYYMMDD(currentCalendarItemBean.getTimes());
            String deviceTimeOfYMD = DateUtils.getDeviceTimeOfYMD();
            if (dateToYYYYMMDD.equals(deviceTimeOfYMD)) {
                String currentTime = www.jykj.com.jykj_zxyl.util.DateUtils.getCurrentTimeHH();
                int currentTimeHour = Integer.parseInt(currentTime);
                int startTimeHour=0;
                int endTimeHour=0;
                String startTime=item0.getShowText();
                String[] splitStartTime = startTime.split(":");
                if (splitStartTime.length>1) {
                    startTime=splitStartTime[0];
                    startTimeHour=Integer.parseInt(startTime);
                }
                String endTime = item1.getShowText();
                String[] splitEndTime = endTime.split(":");
                if (splitEndTime.length>1) {
                    endTime=splitEndTime[0];
                    endTimeHour=Integer.parseInt(endTime);
                }


                if(startTimeHour<currentTimeHour){
                    ToastUtils.showToast("开始时间不能小于当前时间");
                    return true;
                }
                if(endTimeHour<currentTimeHour){
                    ToastUtils.showToast("结束时间不能小于当前时间");
                    return true;
                }
            }
            mStartTime=item0.getShowText();
            mEndTime=item1.getShowText();
            com.hyphenate.easeui.jykj.utils.DateUtils.isLessThanEndDate(mStartTime, mEndTime);
            boolean lessThanEndDate = com.hyphenate.easeui.jykj.utils.DateUtils.isLessThanEndDate(mStartTime, mEndTime);
            if(!lessThanEndDate){
                ToastUtils.showToast("结束时间不能小于开始时间");
                return true;
            }
            if (addSignalSourceDialog.isShowing()) {
                addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
            }
            return false;
        });
        List<String> startTimes = DataUtil.getStartTimes();
        List<String> endTimes = DataUtil.getEndTimes();
        WheelItem[] wheelItems = DataUtil.convertStrToWheelArry(startTimes);
        WheelItem[] wheelItems1 = DataUtil.convertStrToWheelArry(endTimes);
        columnWheelDialog.setItems(wheelItems,wheelItems1,null,null,null);
        String currentTime = com.hyphenate.easeui.jykj.utils.DateUtils.getCurrentTime();
        columnWheelDialog.setSelected(getCurrentTimePos(startTimes,currentTime)
                ,getCurrentTimePos(startTimes,currentTime),0,0,0);
    }


    /**
     * 获取当前时间位置
     *
     * @param startTimes  时间列表
     * @param currentTime 当前时间
     * @return poisiton 位置
     */
    private int getCurrentTimePos(List<String> startTimes, String currentTime) {
        String[] split1 = currentTime.split(":");
        int currentPos = 0;
        for (int i = 0; i < startTimes.size(); i++) {
            String s = startTimes.get(i);

            String[] split2 = s.split(":");
            String time1 = "";
            String time2 = "";
            String endTime1 = "";
            String endTime2 = "";

            if (split1.length > 1) {
                time1 = split1[0];
                endTime1 = split1[1];
            }
            if (split2.length > 1) {
                time2 = split2[0];
                endTime2 = split2[1];
            }
            if (time1.equals(time2)) {
                int i1 = Integer.parseInt(endTime1);
                int i2 = Integer.parseInt(endTime2);
                if (i1 > i2) {
                    currentPos = i + 1;
                } else {
                    currentPos = i;
                }

                break;
            }
        }
        return currentPos;
    }



    /**
     * 获取当前日期
     * @param list 日历列表
     * @return 当前日期
     */
    private CalendarItemBean getCurrentWeekCalendar(List<CalendarItemBean> list){
        CalendarItemBean currentCalendarItemBean=null;
        int weekOfDate = DateUtils.getWeekOfDateNum(new Date());
        for (int i = 0; i < list.size(); i++) {
            CalendarItemBean calendarItemBean = list.get(i);
            int week = calendarItemBean.getWeek();
            if (weekOfDate== week) {
                calendarItemBean.setChoosed(true);
                currentCalendarItemBean=calendarItemBean;
                currentPos=i;
                break;
            }
        }
        return currentCalendarItemBean;
    }



    @Override
    public void getSearchReserveDoctorRosterInfoResult(List<DcotorScheduTimesWeekBean> doctorScheduTimesBeans) {
        mLoadingLayoutManager.showContent();
        this.doctorScheduTimesBeans = doctorScheduTimesBeans;
        doctorSeheduTimeAdapter.setData(this.doctorScheduTimesBeans);
        doctorSeheduTimeAdapter.notifyDataSetChanged();
        if(!CollectionUtils.isEmpty(doctorScheduTimesBeans)){
            tvNoData.setVisibility(View.GONE);
        }else{
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getOperDelReserveDoctorRosterInfoResult(boolean isSucess, String msg) {
        if(isSucess){
            mPresenter.sendSearchReserveDoctorRosterInfoHeaderRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getOperUpdReserveDoctorRosterInfoRequest(boolean isSucess, String msg) {
        if(isSucess){
            mPresenter.sendSearchReserveDoctorRosterInfoHeaderRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getOperUpdReservedDoctorRosterInfoCheckStepConfirm(String msg) {
        checkStepDialog.show();
        checkStepDialog.setContentText(msg);
        checkStep="1";
    }
}
