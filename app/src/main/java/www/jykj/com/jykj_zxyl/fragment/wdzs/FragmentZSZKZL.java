package www.jykj.com.jykj_zxyl.fragment.wdzs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideInteractClinicStateOverview;
import www.jykj.com.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.jykj.com.jykj_zxyl.adapter.MyClinicZSZKZLAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 诊所状况总览
 * Created by admin on 2016/6/1.
 */
public class FragmentZSZKZL extends Fragment {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private Handler mHandler;
    private MyClinicActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;              //总览列表
    private LinearLayoutManager layoutManager;
    private MyClinicZSZKZLAdapter mMyClinicZSZKZLAdapter;       //适配器
    private List<ProvideInteractClinicStateOverview> mHZEntyties = new ArrayList<>();            //所有数据
    private String mNetRetStr;                 //返回字符串

    private TextView mJZLXText;                  //就诊类型
    private TextView mJZRCext;                   //就诊人次
    private TextView mWWCext;                    //未完成
    private TextView mTKext;                     //退款
    private TextView mCSBTText;                  //其他
    private LinearLayout mSearchLayout;              //搜索
    private EditText mPatientName;               //患者姓名
    private TextView mPatientLaber;              //患者标签
    private TextView mPatientJZZT;               //就诊状态
    private TextView mPatientDate;               //就诊日期
    private String[] mPatientType = new String[]{"查询全部", "图文就诊", "音频就诊", "视频就诊", "签约服务", "签约服务"};
    private TextView mPatientTypeText;

    private List<ProvideBasicsDomain> mList = new ArrayList<>();              //患者标签数据
    private List<ProvideBasicsDomain> mJZZTList = new ArrayList<>();              //就诊状态数据
    private ProvideInteractClinicStateOverview mProvideInteractClinicStateOverview = new ProvideInteractClinicStateOverview();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymyclinic_zszkzl, container, false);
        mContext = getContext();
        mActivity = (MyClinicActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        //先获取功能使用标识
        getUserState();

        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mSearchLayout = (LinearLayout) view.findViewById(R.id.li_searcLayout);
        mSearchLayout.setOnClickListener(new ButtonClick());
        mPatientLaber = (TextView) view.findViewById(R.id.tv_choicePatientLaber);
        mPatientLaber.setOnClickListener(new ButtonClick());
        mPatientJZZT = (TextView) view.findViewById(R.id.tv_choicePatientJZZT);
        mPatientJZZT.setOnClickListener(new ButtonClick());
        mPatientDate = (TextView) view.findViewById(R.id.tv_patientChoiceDate);
        mPatientDate.setOnClickListener(new ButtonClick());
        mPatientTypeText = (TextView) view.findViewById(R.id.tv_patientType);
        mPatientTypeText.setOnClickListener(new ButtonClick());

        mPatientName = (EditText) view.findViewById(R.id.et_patientName);
        mJZLXText = (TextView) view.findViewById(R.id.jzlx_title);
        mJZRCext = (TextView) view.findViewById(R.id.jzrc_jzrc);
        mWWCext = (TextView) view.findViewById(R.id.jzrc_wwc);
        mTKext = (TextView) view.findViewById(R.id.jzrc_tk);
        mCSBTText = (TextView) view.findViewById(R.id.jzrc_csbt);

        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmentZSZKZL_list);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMyClinicZSZKZLAdapter = new MyClinicZSZKZLAdapter(mHZEntyties, mContext);
        mRecycleView.setAdapter(mMyClinicZSZKZLAdapter);
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:

                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            cacerProgress();
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            if (mHZEntyties.size() > 0)
                                showLayoutDate();
                            mHZEntyties = JSON.parseArray(netRetEntity.getResJsonData(), ProvideInteractClinicStateOverview.class);
                            mMyClinicZSZKZLAdapter.setDate(mHZEntyties);
                            mMyClinicZSZKZLAdapter.notifyDataSetChanged();
                            getBasicDate();
                            getJZZT();
                        }
                        break;
                    case 1:

                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            cacerProgress();
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            if ("0".equals(netRetEntity.getResJsonData())) {
                                showDialog();
                            } else {
                                getData();

                            }
                        }
                        break;
                    case 2:
                        cacerProgress();
                        break;
                }
            }
        };
    }

    /**
     * 显示提示框
     */
    private void showDialog() {
        // 下面的参数上下文 对话框里面一般都用this
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提示");
        builder.setMessage("目前属于未认证，请前往认证");
        // 设置取消按钮
        builder.setNegativeButton("不去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.e("dialog", "点击了取消按钮");
                mActivity.finish();
            }
        });
        // 设置确定按钮
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.e("dialog", "点击了确定按钮！");
                mActivity.finish();
                startActivity(new Intent(mContext, UserAuthenticationActivity.class));
            }
        });
        builder.setCancelable(false);
        // 和Toast一样 最后一定要show 出来
        builder.show();
    }

    /**
     *
     */
    private void showLayoutDate() {
        mJZLXText.setText(mHZEntyties.get(0).getTitleName1());
        mJZRCext.setText(mHZEntyties.get(0).getTitleName2());
        mWWCext.setText(mHZEntyties.get(0).getTitleName3());
        mTKext.setText(mHZEntyties.get(0).getTitleName4());
        mCSBTText.setText(mHZEntyties.get(0).getTitleName5());
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_searcLayout:
                    //获取输入框内容
                    mProvideInteractClinicStateOverview.setPatientName(mPatientName.getText().toString());
                    //判断是否为空
                    if (mProvideInteractClinicStateOverview.getPatientName() == null || "".equals(mProvideInteractClinicStateOverview.getPatientName())) {
                        Toast.makeText(mContext, "请输入患者姓名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getData();
                    break;
                case R.id.tv_choicePatientLaber:
                    showHZBQDialog();
                    break;
                case R.id.tv_choicePatientJZZT:
                    showJZZTDialog();
                    break;
                case R.id.tv_patientChoiceDate:
                    showBirthDayChoiceDialog();
                    break;
                case R.id.tv_patientType:
                    showJZLXDialog();
                    break;

            }
        }
    }

    /**
     * 选择生日
     */
    private void showBirthDayChoiceDialog() {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
//            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            days = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString();
            mPatientDate.setText(days);
            mProvideInteractClinicStateOverview.setTreatmentDate(days);
            getData();
        }
    };

    /**
     * 就诊类型
     */
    private void showJZLXDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(mPatientType, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                mPatientTypeText.setText(mPatientType[arg1]);
                mProvideInteractClinicStateOverview.setTreatmentType(arg1 + "");
                getData();
            }
        });
        builder.create().show();

    }

    /**
     * 就诊状态
     */
    private void showJZZTDialog() {
        final String[] items = new String[mJZZTList.size()];
        for (int i = 0; i < mJZZTList.size(); i++) {
            items[i] = mJZZTList.get(i).getAttrName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                mPatientJZZT.setText(items[arg1]);
                mProvideInteractClinicStateOverview.setTreatmentState(mJZZTList.get(arg1).getAttrCode() + "");
                getData();
            }
        });
        builder.create().show();

    }

    /**
     * 患者标签选择框
     */
    private void showHZBQDialog() {
        final String[] items = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            items[i] = mList.get(i).getAttrName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                mPatientLaber.setText(items[arg1]);
                mProvideInteractClinicStateOverview.setPatientLabel(mList.get(arg1).getAttrCode() + "");
                getData();
            }
        });
        builder.create().show();

    }

    //获取患者标签
    public void getBasicDate() {

        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(30001);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(2);
                    return;
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    //获取就诊状态
    public void getJZZT() {

        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(10032);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mJZZTList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(2);
                    return;
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    /**
     * 设置数据
     */
    private void getData() {
        mProvideInteractClinicStateOverview.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mProvideInteractClinicStateOverview.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mProvideInteractClinicStateOverview.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(mProvideInteractClinicStateOverview);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/getMyClinicStateOverview");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 设置数据
     */
    private void getUserState() {
        getProgressBar("请稍候", "正在获取数据。。。");
        mProvideInteractClinicStateOverview.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mProvideInteractClinicStateOverview.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mProvideInteractClinicStateOverview.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(mProvideInteractClinicStateOverview);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicResDoctorState");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
