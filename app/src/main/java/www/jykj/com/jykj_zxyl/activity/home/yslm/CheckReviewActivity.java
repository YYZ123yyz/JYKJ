package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.List;

import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.unionInfo.ApprovalDoctorUnionApplyParment;
import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.Util;

public class CheckReviewActivity extends AppCompatActivity {

    private Context mContext;

    private             ProvideViewUnionDoctorMemberApplyInfo mProvideViewUnionDoctorMemberApplyInfo;
    private             TextView                    mApplyDateText;                     //申请时间
    private             TextView                    mApplyStateText;                     //申请状态
    private             TextView                    mApplyUserNameText;                     //申请者姓名
    private             TextView                    mApplyUserAddressText;                     //申请者医院
    private             TextView                    mApplyUserDepartmentText;                     //申请者科室
    private             TextView                    mApplyTitleText;                     //申请提示

    private             TextView                    mAgreeBt;                           //通过按钮
    private             TextView                    mReasonTitle;                     //拒绝理由标题
    private             EditText                    mReasonEdit;                     //拒绝理由
    private             TextView                      mRefundBt;                          //拒绝按钮

    private RelativeLayout    mBack;

    public ProgressDialog mDialogProgress =null;

    private CheckReviewActivity mActivity;
    private JYKJApplication mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_review);
            mApp = (JYKJApplication) getApplication();
        mContext = this;
        mProvideViewUnionDoctorMemberApplyInfo = (ProvideViewUnionDoctorMemberApplyInfo) getIntent().getSerializableExtra("applyInfo");
//        mProvideViewUnionDoctorMemberApplyInfo = (ProvideViewUnionDoctorMemberApplyInfo) JSON.parse(string);
        initLayout();
        initHandler();
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
        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mApplyDateText = (TextView)this.findViewById(R.id.tv_activityCheckReview_dateText);
        mApplyStateText = (TextView)this.findViewById(R.id.tv_activityCheckReview_stateText);
        mApplyUserNameText = (TextView)this.findViewById(R.id.tv_name);
        mApplyUserAddressText = (TextView)this.findViewById(R.id.tv_userAddress);
        mApplyUserDepartmentText = (TextView)this.findViewById(R.id.userDepartment);
        mApplyTitleText = (TextView)this.findViewById(R.id.tv_activityCheckReview_applyReason);
        mAgreeBt = (TextView)this.findViewById(R.id.tv_activityCheckReview_agree);
        mReasonTitle = (TextView)this.findViewById(R.id.tv_activityCheckReview_jjReasonTitle);
        mReasonEdit = (EditText) this.findViewById(R.id.et_activityReview_jjReasonEdit);
        mRefundBt = (TextView) this.findViewById(R.id.et_activityReview_jjReasonBt);
        mAgreeBt.setOnClickListener(new ButtonClick());
        mRefundBt.setOnClickListener(new ButtonClick());
        if (mProvideViewUnionDoctorMemberApplyInfo.getFlagApplyState() == 0)
        {
            mAgreeBt.setVisibility(View.VISIBLE);
            mReasonTitle.setVisibility(View.VISIBLE);
            mReasonEdit.setVisibility(View.VISIBLE);
            mRefundBt.setVisibility(View.VISIBLE);
            if (mProvideViewUnionDoctorMemberApplyInfo.getFlagApplyState() == 0)
            {
                mApplyStateText.setText("待处理");
                mApplyStateText.setTextColor(mContext.getResources().getColor(R.color.groabColor));
            }

        }
        else
        {
            if (mProvideViewUnionDoctorMemberApplyInfo.getFlagApplyState() == 1)
            {
                mApplyStateText.setText("未通过");
                mApplyStateText.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
            }

            if (mProvideViewUnionDoctorMemberApplyInfo.getFlagApplyState() == 2)
            {
                mApplyStateText.setText("已过期");
                mApplyStateText.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
            }

            if (mProvideViewUnionDoctorMemberApplyInfo.getFlagApplyState() == 3)
            {
                mApplyStateText.setText("已通过");
                mApplyStateText.setTextColor(mContext.getResources().getColor(R.color.groabColor));
            }

            mAgreeBt.setVisibility(View.GONE);
            mReasonTitle.setVisibility(View.GONE);
            mReasonEdit.setVisibility(View.GONE);
            mRefundBt.setVisibility(View.GONE);
        }
        mApplyDateText.setText(Util.dateToStr(mProvideViewUnionDoctorMemberApplyInfo.getApplyDate()));
        mApplyUserNameText.setText(mProvideViewUnionDoctorMemberApplyInfo.getApplyDoctorName());
        mApplyUserAddressText.setText(mProvideViewUnionDoctorMemberApplyInfo.getHospitalInfoName());
        mApplyUserDepartmentText.setText(mProvideViewUnionDoctorMemberApplyInfo.getDepartmentName()+"|"+mProvideViewUnionDoctorMemberApplyInfo.getDepartmentSecondName());
        mApplyTitleText.setText("申请描述："+mProvideViewUnionDoctorMemberApplyInfo.getApplyReason());
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityCheckReview_agree:
                    reviewApply(3);
                    break;
                case R.id.et_activityReview_jjReasonBt:

                    reviewApply(1);
                    break;
            }
        }
    }

    /**
     * 审核
     * @param
     */
    private void reviewApply(int state) {

        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ApprovalDoctorUnionApplyParment approvalDoctorUnionApplyParment = new ApprovalDoctorUnionApplyParment();
                    approvalDoctorUnionApplyParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    approvalDoctorUnionApplyParment.setMemberApplyId(mProvideViewUnionDoctorMemberApplyInfo.getMemberApplyId()+"");
                    approvalDoctorUnionApplyParment.setApplyCode(mProvideViewUnionDoctorMemberApplyInfo.getApplyCode());
                    approvalDoctorUnionApplyParment.setUnionCode(mProvideViewUnionDoctorMemberApplyInfo.getUnionCode()+"");
                    approvalDoctorUnionApplyParment.setApprovalDoctorCode(mProvideViewUnionDoctorMemberApplyInfo.getApprovalDoctorCode()+"");
                    approvalDoctorUnionApplyParment.setApprovalDoctorName(mProvideViewUnionDoctorMemberApplyInfo.getApprovalDoctorName()+"");
                    approvalDoctorUnionApplyParment.setFlagApplyState(state+"");
                    approvalDoctorUnionApplyParment.setRefuseReason(mReasonEdit.getText().toString());
                    //实体转JSON字符串
                    String str = new Gson().toJson(approvalDoctorUnionApplyParment);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/unionDoctorController/operApprovalDoctorUnionApply");
                    Log.i("deb",mNetRetStr);
                    System.out.println(mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
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
                mApp.isUpdateReviewUnion = true;
                mProvideViewUnionDoctorMemberApplyInfo.setFlagApplyState(state);
                mHandler.sendEmptyMessage(1);
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
