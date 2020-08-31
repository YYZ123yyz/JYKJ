package www.jykj.com.jykj_zxyl.appointment.activity;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.utils.CollectionUtils;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorScheduTimesBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.base_view.SwipeItemLayout;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.OnlineScheduContract;
import www.jykj.com.jykj_zxyl.appointment.OnlineScheduPresenter;
import www.jykj.com.jykj_zxyl.appointment.adapter.DoctorSeheduTimeAdapter;
import www.jykj.com.jykj_zxyl.appointment.dialog.AddSignalSourceDialog;
import www.jykj.com.jykj_zxyl.appointment.dialog.AppointTimeDialog;
import www.jykj.com.jykj_zxyl.appointment.dialog.AppointTypeDialog;
import www.jykj.com.jykj_zxyl.appointment.view.CalendarView;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:我的在线排班
 *
 * @author: qiuxinhai
 * @date: 2020-08-28 19:56
 */
public class MyOnlineScheduActivity extends AbstractMvpBaseActivity<OnlineScheduContract.View,
        OnlineScheduPresenter> implements OnlineScheduContract.View {
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
    @BindView(R.id.iv_more_left_arrow)
    ImageView ivMoreLeftArrow;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.iv_more_right_arrow)
    ImageView ivMoreRightArrow;
    @BindView(R.id.rl_calendar_root)
    RelativeLayout rlCalendarRoot;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.ll_content_root)
    LinearLayout llContentRoot;
    private JYKJApplication mApp;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private AddSignalSourceDialog addSignalSourceDialog;
    private AppointTimeDialog appointTimeDialog;
    private AppointTypeDialog appointTypeDialog;
    private DoctorSeheduTimeAdapter doctorSeheduTimeAdapter;
    private List<DoctorScheduTimesBean> doctorScheduTimesBeans;
    private List<CalendarItemBean> calendarItemBeans;
    private List<BaseReasonBean> baseReasonBeans;
    private String mStartTime;//开始时间
    private String mEndTime;//结束时间
    private BaseReasonBean currentBaseReasonBean;//号源类型
    private CalendarItemBean currentCalendarItemBean;//当前日期
    private String mSignalSourceNum;//号源数量
    private String reserveDateRosterCode;//医生排班明细编号

    @Override
    protected int setLayoutId() {
        return R.layout.activity_online_scheduling_2;
    }

    @Override
    protected void initView() {
        super.initView();
        doctorScheduTimesBeans=new ArrayList<>();
        addSignalSourceDialog=new AddSignalSourceDialog(this);
        appointTimeDialog=new AppointTimeDialog(this);
        appointTypeDialog=new AppointTypeDialog(this);
        mApp = (JYKJApplication) getApplication();
        setToolBar();
        initLoadingAndRetryManager();
        initRecyclerView();
        addListener();
    }




    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的线上排班");
        toolbar.setRightTitleDrawable(R.mipmap.bg_schedu_set);
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyOnlineScheduTemplateActivity.class,null);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchSchedulingMsgCalandarRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
        mPresenter.sendGetSignalSourceTypeRequest("900060");
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(llContentRoot);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchSchedulingMsgCalandarRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),this);
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        rvList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        doctorSeheduTimeAdapter=new DoctorSeheduTimeAdapter(this,doctorScheduTimesBeans);
        doctorSeheduTimeAdapter.setOnClickItemListener(
                new DoctorSeheduTimeAdapter.OnClickItemListener() {
            @Override
            public void onClickUpdate(int pos) {
                DoctorScheduTimesBean doctorScheduTimesBean = doctorScheduTimesBeans.get(pos);
                long startTimes = doctorScheduTimesBean.getStartTimes();
                mStartTime = DateUtils.getDateToStringDD(startTimes);
                long endTimes = doctorScheduTimesBean.getEndTimes();
                mEndTime=DateUtils.getDateToStringDD(endTimes);
                int reserveType = doctorScheduTimesBean.getReserveType();
                currentBaseReasonBean = getCurrentReserveBean(reserveType);
                mSignalSourceNum=doctorScheduTimesBean.getReserveCount()+"";
                reserveDateRosterCode=doctorScheduTimesBean.getReserveDateRosterCode();
                addSignalSourceDialog.show();
                addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
                addSignalSourceDialog.setSignalType(currentBaseReasonBean);
                addSignalSourceDialog.setSignalNum(mSignalSourceNum);
            }

            @Override
            public void onClickDelete(int pos) {
                DoctorScheduTimesBean doctorScheduTimesBean = doctorScheduTimesBeans.get(pos);
                mPresenter.sendOperDelDoctorDateRosterInfoRequest(
                        doctorScheduTimesBean.getReserveDateRosterCode(),
                        MyOnlineScheduActivity.this);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(doctorSeheduTimeAdapter);
    }

    /**
     * 根据Id获取号源类型
     * @param reserveType 号源类型
     * @return BaseReasonBean
     */
    private BaseReasonBean getCurrentReserveBean(int reserveType){
        BaseReasonBean currentBaseReasonBean=null;
        if (!CollectionUtils.isEmpty(baseReasonBeans)) {
            for (BaseReasonBean baseReasonBean : baseReasonBeans) {
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
        tvAddBtn.setOnClickListener(v -> {
            mStartTime=null;
            mEndTime=null;
            currentBaseReasonBean=null;
            mSignalSourceNum=null;
            reserveDateRosterCode=null;
            addSignalSourceDialog.show();
            addSignalSourceDialog.setAppointTime(mStartTime,mEndTime);
            addSignalSourceDialog.setSignalType(currentBaseReasonBean);
            addSignalSourceDialog.setSignalNum(mSignalSourceNum);
        });
        addSignalSourceDialog.setOnClickDialogListener(new AddSignalSourceDialog.OnClickDialogListener() {
            @Override
            public void onClickChooseTime() {
                appointTimeDialog.show();
            }

            @Override
            public void onClickChooseType() {
                if (!CollectionUtils.isEmpty(baseReasonBeans)) {
                    appointTypeDialog.show();
                    appointTypeDialog.setData(baseReasonBeans);
                }

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
                        currentCalendarItemBean.getWeek()+"",
                        currentBaseReasonBean.getAttrCode()+""
                        ,currentBaseReasonBean.getAttrName(),
                        DateUtils.getDateToYYYYMMDD(currentCalendarItemBean.getTimes()),
                        mStartTime,mEndTime,mSignalSourceNum,"1",
                        reserveDateRosterCode,MyOnlineScheduActivity.this
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

    @Override
    public void showLoading(int code) {
        if(code==103||code==104){
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
       dismissLoading();
    }

    @Override
    public void getSearchSchedulingMsgCalandarResult(List<CalendarItemBean> calendarItemBeans) {
        this.calendarItemBeans =calendarItemBeans;
        currentCalendarItemBean = getCurrentCalendarItemBeans();
        PagerAdapter mPagerAdapter=new MyPagerAdapter(this,calendarItemBeans);
        viewPager.setAdapter(mPagerAdapter);
        mLoadingLayoutManager.showContent();
        long times = currentCalendarItemBean.getTimes();
        mPresenter.searchReserveDoctorDateRosterInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), DateUtils.getDateToYYYYMMDD(times),this);
    }

    private CalendarItemBean getCurrentCalendarItemBeans(){
        CalendarItemBean currentCalendarItemBean=null;
        String deviceTimeOfYMD = DateUtils.getDeviceTimeOfYMD();
        if (!CollectionUtils.isEmpty(calendarItemBeans)) {
            for (CalendarItemBean calendarItemBean : calendarItemBeans) {
                long times = calendarItemBean.getTimes();
                String dateToYYYYMMDD = DateUtils.getDateToYYYYMMDD(times);
                if (deviceTimeOfYMD.equals(dateToYYYYMMDD)) {
                    calendarItemBean.setChoosed(true);
                    currentCalendarItemBean=calendarItemBean;
                    break;
                }
            }
        }
        return currentCalendarItemBean;

    }

    @Override
    public void getSignalSourceTypeResult(List<BaseReasonBean> baseReasonBeans) {
        this.baseReasonBeans = baseReasonBeans;

    }

    @Override
    public void getOperDoctorScheduResult(OperDoctorScheduResultBean operDoctorScheduResultBean) {
        long times = currentCalendarItemBean.getTimes();
        mPresenter.searchReserveDoctorDateRosterInfoRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                ,DateUtils.getDateToYYYYMMDD(times),this);
        ToastUtils.showToast("操作成功");
    }

    @Override
    public void getOperDoctorScheduError() {
        ToastUtils.showToast("操作失败");
    }

    @Override
    public void getSearchReserveDoctorDateRosterInfoResult(List<DoctorScheduTimesBean>
                                                                   doctorScheduTimesBeans) {
        this.doctorScheduTimesBeans = doctorScheduTimesBeans;
        doctorSeheduTimeAdapter.setData(this.doctorScheduTimesBeans);
        doctorSeheduTimeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getOperDelDoctorDateRosterInfoResult(boolean isSucess, String msg) {
        if (isSucess) {
            long times = currentCalendarItemBean.getTimes();
            mPresenter.searchReserveDoctorDateRosterInfoRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    ,DateUtils.getDateToYYYYMMDD(times),this);
        }else{
            ToastUtils.showToast(msg);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private ArrayList<View> views;
        private List<CalendarItemBean> calendarItemBeans;
        private
        MyPagerAdapter(Context context,List<CalendarItemBean> list) {
           this.calendarItemBeans=list;
            views=new ArrayList<>();
            if (!CollectionUtils.isEmpty(calendarItemBeans)
                    &&calendarItemBeans.size()>28) {
                List<CalendarItemBean> calendarItemBeans =
                        this.calendarItemBeans.subList(0, 7);
                CalendarView calendarView=new CalendarView(context);
                calendarView.setOnClickCalendarListener(calendarItemBean -> {
                    currentCalendarItemBean=calendarItemBean;
                    long times = currentCalendarItemBean.getTimes();
                    mPresenter.searchReserveDoctorDateRosterInfoRequest(
                            mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                            DateUtils.getDateToYYYYMMDD(times),MyOnlineScheduActivity.this);
                });
                calendarView.setData(calendarItemBeans);
                views.add(calendarView);
                List<CalendarItemBean> calendarItemBeans1 =
                        this.calendarItemBeans.subList(7, 14);
                CalendarView calendarView1=new CalendarView(context);
                calendarView1.setOnClickCalendarListener(calendarItemBean -> {
                    currentCalendarItemBean=calendarItemBean;
                    long times = currentCalendarItemBean.getTimes();
                    mPresenter.searchReserveDoctorDateRosterInfoRequest(
                            mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                            DateUtils.getDateToYYYYMMDD(times),MyOnlineScheduActivity.this);
                });
                calendarView1.setData(calendarItemBeans1);
                views.add(calendarView1);
                List<CalendarItemBean> calendarItemBeans2 =
                        this.calendarItemBeans.subList(14, 21);
                CalendarView calendarView2=new CalendarView(context);
                calendarView2.setOnClickCalendarListener(calendarItemBean -> {
                    currentCalendarItemBean=calendarItemBean;
                    long times = currentCalendarItemBean.getTimes();
                    mPresenter.searchReserveDoctorDateRosterInfoRequest(
                            mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                            DateUtils.getDateToYYYYMMDD(times),MyOnlineScheduActivity.this);
                });
                calendarView2.setData(calendarItemBeans2);
                views.add(calendarView2);
                List<CalendarItemBean> calendarItemBeans3 =
                        this.calendarItemBeans.subList(21, 28);
                CalendarView calendarView3=new CalendarView(context);
                calendarView3.setOnClickCalendarListener(calendarItemBean -> {
                    currentCalendarItemBean=calendarItemBean;
                    long times = currentCalendarItemBean.getTimes();
                    mPresenter.searchReserveDoctorDateRosterInfoRequest(
                            mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                            DateUtils.getDateToYYYYMMDD(times),MyOnlineScheduActivity.this);
                });
                calendarView3.setData(calendarItemBeans3);
                views.add(calendarView3);
                List<CalendarItemBean> calendarItemBeans4 =
                        this.calendarItemBeans.subList(28, this.calendarItemBeans.size());
                CalendarView calendarView4=new CalendarView(context);
                calendarView4.setOnClickCalendarListener(calendarItemBean -> {
                    currentCalendarItemBean=calendarItemBean;
                    long times = currentCalendarItemBean.getTimes();
                    mPresenter.searchReserveDoctorDateRosterInfoRequest(
                            mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                            DateUtils.getDateToYYYYMMDD(times),MyOnlineScheduActivity.this);
                });
                calendarView4.setData(calendarItemBeans4);
                views.add(calendarView4);


            }


        }

        @Override
        public int getCount() {
            return views.size();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ((ViewGroup) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

}


