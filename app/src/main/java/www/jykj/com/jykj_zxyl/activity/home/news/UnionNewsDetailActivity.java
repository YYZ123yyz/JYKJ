package www.jykj.com.jykj_zxyl.activity.home.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import entity.home.newsMessage.ProvideMsgPushReminder;
import entity.home.newsMessage.OperMessageParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.TextViewHtml;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

/**
 * 医生联盟消息详情
 */
public class UnionNewsDetailActivity extends AppCompatActivity {

    private Context mContext;
    private             ProvideMsgPushReminder      mProvideMsgPushReminder;            //消息

    private             TextView                    mApplyUserNameText;                     //申请者姓名
    private             TextView                    mApplyTitleText;                     //申请提示

    private             TextView                    mAgreeBt;                           //通过按钮
    private             TextView                    mReasonTitle;                     //拒绝理由标题
    private             EditText                    mReasonEdit;                     //拒绝理由
    private             TextView                      mRefundBt;                          //拒绝按钮

    public ProgressDialog mDialogProgress =null;

    private UnionNewsDetailActivity mActivity;
    private JYKJApplication mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         String[]                    mBtnString;                 //按钮列表
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageunion_detail);
            mApp = (JYKJApplication) getApplication();
        mContext = this;
        mProvideMsgPushReminder = (ProvideMsgPushReminder) getIntent().getSerializableExtra("newMessage");
        mBtnString = mProvideMsgPushReminder.getOperBtnContent().split("\\^");
        initLayout();
        initHandler();
        setMsgReadState();
    }



    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        initLayout();
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mApplyUserNameText = (TextView)this.findViewById(R.id.tv_activityCheckReview_nameText);
        mApplyTitleText = (TextView)this.findViewById(R.id.tv_activityCheckReview_applyReason);
        mAgreeBt = (TextView)this.findViewById(R.id.tv_activityCheckReview_agree);
        mReasonTitle = (TextView)this.findViewById(R.id.tv_activityCheckReview_jjReasonTitle);
        mReasonEdit = (EditText) this.findViewById(R.id.et_activityReview_jjReasonEdit);
        mRefundBt = (TextView) this.findViewById(R.id.et_activityReview_jjReasonBt);
        mAgreeBt.setOnClickListener(new ButtonClick());
        mRefundBt.setOnClickListener(new ButtonClick());
        if (mProvideMsgPushReminder.getFlagOperBtn() == 1)
        {
            if (mBtnString.length == 0)
            {
                mAgreeBt.setVisibility(View.GONE);
                mReasonTitle.setVisibility(View.GONE);
                mReasonEdit.setVisibility(View.GONE);
                mRefundBt.setVisibility(View.GONE);
            }
            else if (mBtnString.length == 1)
            {
                mAgreeBt.setVisibility(View.VISIBLE);
                mReasonTitle.setVisibility(View.GONE);
                mReasonEdit.setVisibility(View.GONE);
                mRefundBt.setVisibility(View.GONE);
                mAgreeBt.setText(mBtnString[0]);
            }
            else if (mBtnString.length == 2)
            {
                mAgreeBt.setVisibility(View.VISIBLE);
                mReasonTitle.setVisibility(View.VISIBLE);
                mReasonEdit.setVisibility(View.VISIBLE);
                mRefundBt.setVisibility(View.VISIBLE);
                mAgreeBt.setText(mBtnString[0]);
                mRefundBt.setText(mBtnString[1]);
            }
        }
        else
        {

            mAgreeBt.setVisibility(View.GONE);
            mReasonTitle.setVisibility(View.GONE);
            mReasonEdit.setVisibility(View.GONE);
            mRefundBt.setVisibility(View.GONE);
        }
        mApplyUserNameText.setText("发送人："+mProvideMsgPushReminder.getSenderUserName());
        mApplyTitleText.setText(Html.fromHtml(mProvideMsgPushReminder.getMsgContent()));
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityCheckReview_agree:
                    operMsg(3);
                    break;
                case R.id.et_activityReview_jjReasonBt:

                    operMsg(1);
                    break;
            }
        }
    }


    /**
     * 审核
     * @param
     */
    private void operMsg(int state) {

        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    OperMessageParment operMessageParment = new OperMessageParment();
                    operMessageParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    operMessageParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    operMessageParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    operMessageParment.setReminderId(mProvideMsgPushReminder.getReminderId()+"");
                    operMessageParment.setOperCode(mProvideMsgPushReminder.getOperCode());
                    operMessageParment.setMsgOper(mProvideMsgPushReminder.getMsgOper()+"");
                    operMessageParment.setFlagApplyState(state+"");
                    operMessageParment.setRefuseReason(mReasonEdit.getText().toString());
                    //实体转JSON字符串
                    String str = new Gson().toJson(operMessageParment);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/msgDataControlle/operMsgPushReminderStateByUnionDoctor");
                    Log.i("deb",mNetRetStr);
                    System.out.println(mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("操作失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 设置消息已读
     */
    private void setMsgReadState() {
        new Thread(){
            public void run(){
                try {
                    OperMessageParment operMessageParment = new OperMessageParment();
                    operMessageParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    operMessageParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    operMessageParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    operMessageParment.setReminderId(mProvideMsgPushReminder.getReminderId()+"");
                    //实体转JSON字符串
                    String str = new Gson().toJson(operMessageParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/msgDataControlle/operUpdMsgPushReminderState");
                    Log.i("deb",mNetRetStr);
                    System.out.println(mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("操作失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
//                    mHandler.sendEmptyMessage(0);
                    return;
                }
//                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }



    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}
