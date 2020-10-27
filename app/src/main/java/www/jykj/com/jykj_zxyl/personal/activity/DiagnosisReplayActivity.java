package www.jykj.com.jykj_zxyl.personal.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.jykj.bean.OrderMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import entity.wdzs.ProvideBasicsImg;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DiagnosisReplayBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.personal.DiagnosisReplayContract;
import www.jykj.com.jykj_zxyl.personal.DiagnosisReplayPresenter;
import www.jykj.com.jykj_zxyl.personal.adapter.DiagnosisReplayAdapter;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:诊后留言activity
 *
 * @author: qiuxinhai
 * @date: 2020-10-15 16:24
 */
public class DiagnosisReplayActivity extends
        AbstractMvpBaseActivity<DiagnosisReplayContract.View, DiagnosisReplayPresenter>
        implements DiagnosisReplayContract.View {
    @BindView(R.id.iv_back_left)
    ImageView ivBackLeft;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_patientName)
    TextView tvPatientName;
    @BindView(R.id.tv_msgType)
    TextView tvMsgType;
    @BindView(R.id.tv_msgDate)
    TextView tvMsgDate;
    @BindView(R.id.tv_linkPhone)
    TextView tvLinkPhone;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.no_commit)
    TextView noCommit;
    @BindView(R.id.rv_imageView)
    RecyclerView rvImageView;
    @BindView(R.id.tv_messageReply)
    EditText tvMessageReply;
    @BindView(R.id.zhli_status)
    TextView zhliStatus;
    @BindView(R.id.lin_status)
    LinearLayout linStatus;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rv_history_repaly_list)
    RecyclerView rvHistoryReplayList;
    private String orderCode;//订单code
    private String treatmentType;//就诊类型
    private String messageId;//留言消息id
    private String replyType;//回复类型.1:正常;2:一般;3:紧急;4:重大紧急;
    private String patientCode;//患者code
    private String patientName;//患者name
    private String patientPhone;//患者phone
    private WZZXImageViewRecycleAdapter mAdapter;
    private FullyGridLayoutManager mGridLayoutManager;
    private List<ProvideBasicsImg> mProvideBasicsImg;
    private DiagnosisReplayAdapter diagnosisReplayAdapter;
    private JYKJApplication mApp;
    List<DiagnosisReplayBean.InteractPatientMessageActiveListBean> historyReplayList;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mApp = (JYKJApplication) getApplication();
        mProvideBasicsImg = new ArrayList<>();
        historyReplayList = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderCode = extras.getString("orderCode");
            treatmentType = extras.getString("treatmentType");
            patientCode = extras.getString("patientCode");
            patientName = extras.getString("patientName");
            patientPhone = extras.getString("patientPhone");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_zhly_doctor_reply;
    }


    @Override
    protected void initView() {
        super.initView();
        //添加监听
        addListener();
        //初始化RecyclerView
        initRecyclerView();
        //初始化历史留言回复
        initHistoryReplayRecyclerView();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchMyClinicDetailResPatientMessageContent_20201012Request(orderCode, this);
    }


    /**
     * 添加监听
     */
    private void addListener() {
        llBack.setOnClickListener(v -> DiagnosisReplayActivity.this.finish());
        tvCommit.setOnClickListener(v -> {
            String replayContent = tvMessageReply.getText().toString();
            if (TextUtils.isEmpty(replayContent)) {
                ToastUtils.showToast("回复内容不能为空");
                return;
            }
            if (TextUtils.isEmpty(replyType)) {
                ToastUtils.showToast("请选择消息类型");
                return;
            }
            if (TextUtils.isEmpty(messageId)) {
                messageId = "0";
            }

            mPresenter.sendOperUpdMyClinicDetailByOrderPatientMessageRequest(
                    messageId, orderCode, treatmentType, replayContent,
                    replyType, patientCode, patientName, patientPhone, DiagnosisReplayActivity.this);

        });
        linStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }


    private void initRecyclerView() {
        //创建默认的线性LayoutManager

        mGridLayoutManager = new FullyGridLayoutManager(this, 3);

        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvImageView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvImageView.setHasFixedSize(true);


        //创建并设置Adapter
        mAdapter = new WZZXImageViewRecycleAdapter(mProvideBasicsImg, this);
        rvImageView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WZZXImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
//                PhotoDialog photoDialog = new PhotoDialog(,R.style.PhotoDialog);
//                photoDialog.setDate(mContext,mApp,mProvideBasicsImg,position);
//                photoDialog.show();
            }


            @Override
            public void onLongClick(int position) {

            }
        });
        rvImageView.setNestedScrollingEnabled(false);
    }

    /**
     * 初始化历史留言回复
     */
    private void initHistoryReplayRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        diagnosisReplayAdapter = new DiagnosisReplayAdapter(this, historyReplayList);
        rvHistoryReplayList.setLayoutManager(layoutManager);
        rvHistoryReplayList.setAdapter(diagnosisReplayAdapter);
        rvHistoryReplayList.setNestedScrollingEnabled(false);
    }


    @SuppressLint("ResourceAsColor")
    private void dialog() {
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_nocommit, null);
        dialog.setContentView(view);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        tv1.setOnClickListener(v -> {
            zhliStatus.setText("重大紧急");
            zhliStatus.setTextColor(DiagnosisReplayActivity.this.getResources().getColor(R.color.tv1));
            replyType = "4";
            dialog.dismiss();
        });
        tv2.setOnClickListener(v -> {
            zhliStatus.setText("紧急");
            zhliStatus.setTextColor(DiagnosisReplayActivity.this.getResources().getColor(R.color.tv2));
            replyType = "3";
            dialog.dismiss();
        });
        tv3.setOnClickListener(v -> {
            zhliStatus.setText("一般");
            zhliStatus.setTextColor(DiagnosisReplayActivity.this.getResources().getColor(R.color.tv3));
            replyType = "2";
            dialog.dismiss();
        });
        tv4.setOnClickListener(v -> {
            zhliStatus.setText("正常");
            zhliStatus.setTextColor(DiagnosisReplayActivity.this.getResources().getColor(R.color.tv4));
            replyType = "1";
            dialog.dismiss();
        });
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = DiagnosisReplayActivity.this.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }


    @Override
    public void showLoading(int code) {
        if (code == 100) {
            showLoading("", null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getOperUpdMyClinicDetailByOrderPatientMessageResult(boolean isSucess, String msg) {
        if (isSucess) {
            startJumpChatActivity();
            this.finish();
        } else {
            ToastUtils.showToast(msg);
        }

    }

    @Override
    public void getSearchMyClinicDetailResPatientMessageContentResult(
            DiagnosisReplayBean diagnosisReplayBean) {
        setData(diagnosisReplayBean);


    }

    /**
     * 设数据
     *
     * @param diagnosisReplayBean 设置数据
     */
    private void setData(DiagnosisReplayBean diagnosisReplayBean) {
        treatmentType = diagnosisReplayBean.getTreatmentType();
        String messageDate = diagnosisReplayBean.getMessageDate();
        String patientLinkPhone = diagnosisReplayBean.getPatientLinkPhone();
        String messageContent = diagnosisReplayBean.getMessageContent();
        tvMsgDate.setText(StringUtils.isNotEmpty(messageDate) ? messageDate : "未提交");
        tvLinkPhone.setText(StringUtils.isNotEmpty(patientLinkPhone) ? patientLinkPhone : "未提交");
        content.setText(StringUtils.isNotEmpty(messageContent) ? messageContent : "未提交");
        String messageImgArray = diagnosisReplayBean.getMessageImgArray();
        List<ProvideBasicsImg> provideBasicsImgs = convertStrToArray(messageImgArray);
        if (!CollectionUtils.isEmpty(provideBasicsImgs)) {
            noCommit.setVisibility(View.GONE);
            mAdapter.setDate(provideBasicsImgs);
            mAdapter.notifyDataSetChanged();
        } else {
            noCommit.setVisibility(View.VISIBLE);
        }
        diagnosisReplayAdapter.setData(diagnosisReplayBean.getInteractPatientMessageActiveList());
    }


    /**
     * 跳转IM
     */
    private void startJumpChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        //患者
        intent.putExtra("userCode", patientCode);
        intent.putExtra("userName", patientName);
        //医生
        intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //URL
        intent.putExtra("doctorUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
        intent.putExtra("patientCode", patientCode);
        OrderMessage orderMessage = new OrderMessage(
                mApp.mViewSysUserDoctorInfoAndHospital.getUserName()
                , mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl()
                , orderCode, replyType
                , "MessageAfterDiagnosis");

        Bundle bundle = new Bundle();
        bundle.putSerializable("orderMsg", orderMessage);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }

    /**
     * 转换数据
     *
     * @param messageImgArray
     * @return list
     */
    private List<ProvideBasicsImg> convertStrToArray(String messageImgArray) {
        List<ProvideBasicsImg> provideBasicsImgs = new ArrayList<>();
        if (StringUtils.isNotEmpty(messageImgArray)) {
            String[] split = messageImgArray.split(",");
            for (String s : split) {
                ProvideBasicsImg provideBasicsImg = new ProvideBasicsImg();
                provideBasicsImg.setImgUrl(s);
                provideBasicsImgs.add(provideBasicsImg);
            }
        }
        return provideBasicsImgs;
    }

}
