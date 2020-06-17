package www.jykj.com.jykj_zxyl.activity.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.home.newsMessage.ProvideMsgPushReminderCount;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 消息
 */
public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private NewsActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串


    private RelativeLayout mLMXXLayout;                        //联盟消息
    private RelativeLayout mHZJZLayout;                        //患者就诊消息
    private RelativeLayout mZHLYLayout;                        //诊后留言消息
    private RelativeLayout mTJHZLayout;                        //添加患者消息
    private RelativeLayout mYHQLayout;                        //医患圈消息
    private RelativeLayout mJJTXLayout;                        //紧急提醒消息
    private RelativeLayout mQYHZLayout;                        //签约患者消息
    private RelativeLayout mXTXXLayout;                        //系统消息


    private TextView mUnionMessageNum;                   //联盟消息数量
    private TextView mHZJZMessageNum;                   //患者就诊数量
    private TextView mZHLYMessageNum;                   //诊后留言数量
    private TextView mTJHZMessageNum;                   //添加患者数量
    private TextView mYHQMessageNum;                   //医患圈数量
    private TextView mJJTXMessageNum;                   //紧急提醒息数量
    private TextView mQYHZMessageNum;                   //患者签约数量
    private TextView mXTXXMessageNum;                   //系统消息数量

    private ProvideMsgPushReminderCount mProvideMsgPushReminderCount;       //新消息数量


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ActivityUtil.setStatusBarMain(NewsActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideMsgPushReminderCount = (ProvideMsgPushReminderCount) getIntent().getSerializableExtra("newMessage");
        initView();
        initHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新一下未读消息数
        ProvideMsgPushReminderCount provideMsgPushReminderCount = new ProvideMsgPushReminderCount();
        provideMsgPushReminderCount.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideMsgPushReminderCount.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideMsgPushReminderCount);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount");
                    Log.e("tag", "消息 "+mNetRetStr );
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

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        mProvideMsgPushReminderCount = JSON.parseObject(netRetEntity.getResJsonData(), ProvideMsgPushReminderCount.class);
                        if (mProvideMsgPushReminderCount != null) {
                            if (mProvideMsgPushReminderCount.getMsgTypeCount01() > 0) {
                                mHZJZMessageNum.setVisibility(View.VISIBLE);
                                mHZJZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount01() + "");
                            } else {
                                mHZJZMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount02() > 0) {
                                mZHLYMessageNum.setVisibility(View.VISIBLE);
                                mZHLYMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount02() + "");
                            } else {
                                mZHLYMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount03() > 0) {
                                mTJHZMessageNum.setVisibility(View.VISIBLE);
                                mTJHZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount03() + "");
                            } else {
                                mTJHZMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount04() > 0) {
                                mUnionMessageNum.setVisibility(View.VISIBLE);
                                mUnionMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount04() + "");
                            } else {
                                mUnionMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount05() > 0) {
                                mYHQMessageNum.setVisibility(View.VISIBLE);
                                mYHQMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount05() + "");
                            } else {
                                mYHQMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount06() > 0) {
                                mJJTXMessageNum.setVisibility(View.VISIBLE);
                                mJJTXMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount06() + "");
                            } else {
                                mJJTXMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount07() > 0) {
                                mQYHZMessageNum.setVisibility(View.VISIBLE);
                                mQYHZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount07() + "");
                            } else {
                                mQYHZMessageNum.setVisibility(View.GONE);
                            }

                            if (mProvideMsgPushReminderCount.getMsgTypeCount08() > 0) {
                                mXTXXMessageNum.setVisibility(View.VISIBLE);
                                mXTXXMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount08() + "");
                            } else {
                                mXTXXMessageNum.setVisibility(View.GONE);
                            }
                        }
                        break;
                }
            }
        };
    }


    private void initView() {
        mLMXXLayout = (RelativeLayout) this.findViewById(R.id.rl_lmxx);
        mUnionMessageNum = (TextView) this.findViewById(R.id.tv_lmxx);

        mHZJZLayout = (RelativeLayout) this.findViewById(R.id.rl_hzjz);
        mHZJZMessageNum = (TextView) this.findViewById(R.id.tv_hzjz);

        mZHLYLayout = (RelativeLayout) this.findViewById(R.id.rl_zhly);
        mZHLYMessageNum = (TextView) this.findViewById(R.id.tv_zhly);

        mTJHZLayout = (RelativeLayout) this.findViewById(R.id.rl_add_patient);
        mTJHZMessageNum = (TextView) this.findViewById(R.id.tv_tjhz);

        mYHQLayout = (RelativeLayout) this.findViewById(R.id.ri_yjq);
        mYHQMessageNum = (TextView) this.findViewById(R.id.tv_yhq);

        mJJTXLayout = (RelativeLayout) this.findViewById(R.id.ri_jjtx);
        mJJTXMessageNum = (TextView) this.findViewById(R.id.tv_jjtx);

        mQYHZLayout = (RelativeLayout) this.findViewById(R.id.ri_qyhz);
        mQYHZMessageNum = (TextView) this.findViewById(R.id.tv_qyhz);

        mXTXXLayout = (RelativeLayout) this.findViewById(R.id.ri_xtxx);
        mXTXXMessageNum = (TextView) this.findViewById(R.id.tv_xtxx);
        //联盟消息
        if (mProvideMsgPushReminderCount != null) {
//            if (mProvideMsgPushReminderCount.getMsgTypeCount04() > 0) {
//                mUnionMessageNum.setVisibility(View.VISIBLE);
//                mUnionMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount04() + "");
//            } else {
//                mUnionMessageNum.setVisibility(View.GONE);
//            }
            //患者就诊
            if (mProvideMsgPushReminderCount.getMsgTypeCount01() > 0) {
                mHZJZMessageNum.setVisibility(View.VISIBLE);
                mHZJZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount01() + "");
            } else {
                mHZJZMessageNum.setVisibility(View.GONE);
            }
            //诊后留言
            if (mProvideMsgPushReminderCount.getMsgTypeCount02() > 0) {
                mZHLYMessageNum.setVisibility(View.VISIBLE);
                mZHLYMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount02() + "");
            } else {
                mZHLYMessageNum.setVisibility(View.GONE);
            }
            //添加患者
            if (mProvideMsgPushReminderCount.getMsgTypeCount03() > 0) {
                mTJHZMessageNum.setVisibility(View.VISIBLE);
                mTJHZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount03() + "");
            } else {
                mTJHZMessageNum.setVisibility(View.GONE);
            }
            //医患圈
            if (mProvideMsgPushReminderCount.getMsgTypeCount05() > 0) {
                mYHQMessageNum.setVisibility(View.VISIBLE);
                mYHQMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount05() + "");
            } else {
                mYHQMessageNum.setVisibility(View.GONE);
            }
            //紧急提醒
            if (mProvideMsgPushReminderCount.getMsgTypeCount06() > 0) {
                mJJTXMessageNum.setVisibility(View.VISIBLE);
                mJJTXMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount06() + "");
            } else {
                mJJTXMessageNum.setVisibility(View.GONE);
            }
            //签约患者
            if (mProvideMsgPushReminderCount.getMsgTypeCount07() > 0) {
                mQYHZMessageNum.setVisibility(View.VISIBLE);
                mQYHZMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount07() + "");
            } else {
                mQYHZMessageNum.setVisibility(View.GONE);
            }
            //签约患者
            if (mProvideMsgPushReminderCount.getMsgTypeCount08() > 0) {
                mXTXXMessageNum.setVisibility(View.VISIBLE);
                mXTXXMessageNum.setText(mProvideMsgPushReminderCount.getMsgTypeCount08() + "");
            } else {
                mXTXXMessageNum.setVisibility(View.GONE);
            }
        }
        initListener();
    }

    private void initListener() {
        findViewById(R.id.ll_back).setOnClickListener(this);
        findViewById(R.id.rl_hzjz).setOnClickListener(this);
        findViewById(R.id.rl_zhly).setOnClickListener(this);
        findViewById(R.id.rl_add_patient).setOnClickListener(this);
      //  findViewById(R.id.rl_lmxx).setOnClickListener(this);
        findViewById(R.id.ri_yjq).setOnClickListener(this);
        findViewById(R.id.ri_jjtx).setOnClickListener(this);
        findViewById(R.id.ri_qyhz).setOnClickListener(this);
        findViewById(R.id.ri_xtxx).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_hzjz://跳转到患者就诊
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000101"));
                break;
            case R.id.rl_zhly:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000102"));
                break;
            case R.id.rl_add_patient:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000103"));
                break;
//            case R.id.rl_lmxx:
//                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000104"));
//                break;
            case R.id.ri_yjq:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000105"));
                break;
            case R.id.ri_jjtx:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000106"));
                break;
            case R.id.ri_qyhz:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000107"));
                break;
            case R.id.ri_xtxx:
                startActivity(new Intent(this, UnionNewsActivity.class).putExtra("messageType", "4000108"));
                break;
        }
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
