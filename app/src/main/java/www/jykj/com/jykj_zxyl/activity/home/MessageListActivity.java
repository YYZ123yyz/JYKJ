package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.contract.UnReadMsgContract;
import www.jykj.com.jykj_zxyl.activity.home.contract.UnReadMsgPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UnReadMsgBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-31 15:46
 */
public class MessageListActivity extends AbstractMvpBaseActivity<UnReadMsgContract.View,
        UnReadMsgPresenter> implements UnReadMsgContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_yhhd)
    ImageView ivYhhd;
    @BindView(R.id.iv_arrow_right)
    ImageView ivArrowRight;
    @BindView(R.id.tv_unread_patient_visit)
    TextView tvUnreadPatientVisit;
    @BindView(R.id.rl_patient_visit)
    RelativeLayout rlPatientVisit;
    @BindView(R.id.iv_leave_msg)
    ImageView ivLeaveMsg;
    @BindView(R.id.iv_leave_msg_arrow_right)
    ImageView ivLeaveMsgArrowRight;
    @BindView(R.id.tv_unread_lieave_msg)
    TextView tvUnreadLieaveMsg;
    @BindView(R.id.rl_leave_msg)
    RelativeLayout rlLeaveMsg;
    @BindView(R.id.iv_patient_sign_msg)
    ImageView ivPatientSignMsg;
    @BindView(R.id.iv_patient_sign_arrow_right)
    ImageView ivPatientSignArrowRight;
    @BindView(R.id.tv_unread_patient_sign_msg)
    TextView tvUnreadPatientSignMsg;
    @BindView(R.id.rl_patient_sign)
    RelativeLayout rlPatientSign;
    @BindView(R.id.iv_health_education_msg)
    ImageView ivHealthEducationMsg;
    @BindView(R.id.iv_health_education_arrow_right)
    ImageView ivHealthEducationArrowRight;
    @BindView(R.id.tv_unread_health_education_msg)
    TextView tvUnreadHealthEducationMsg;
    @BindView(R.id.rl_health_education)
    RelativeLayout rlHealthEducation;
    @BindView(R.id.iv_urgent_remind_msg)
    ImageView ivUrgentRemindMsg;
    @BindView(R.id.iv_urgent_remind_arrow_right)
    ImageView ivUrgentRemindArrowRight;
    @BindView(R.id.tv_unread_urgent_remind_msg)
    TextView tvUnreadUrgentRemindMsg;
    @BindView(R.id.rl_urgent_remind)
    RelativeLayout rlUrgentRemind;
    @BindView(R.id.iv_system_msg)
    ImageView ivSystemMsg;
    @BindView(R.id.iv_system_msg_arrow_right)
    ImageView ivSystemMsgArrowRight;
    @BindView(R.id.tv_unread_system_msg)
    TextView tvUnreadSystemMsg;
    @BindView(R.id.rl_system_msg)
    RelativeLayout rlSystemMsg;
    @BindView(R.id.ll_content_root)
    LinearLayout llContentRoot;
    private LoadingLayoutManager mLoadingLayout;
    private UnReadMsgBean unReadMsgBean;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_msg_list;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        initLoadingAndRetryManager();
        addListener();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("消息");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleSearchBtnVisible(false);
    }


    /**
     * 初始化空页面加载布局
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(llContentRoot);
        mLoadingLayout.showLoading();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.sendSearchMsgPushReminderAllCount(this);
    }

    /**
     * 添加监听
     */
    private void addListener() {
        rlPatientVisit.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000101");
                startActivity(MessageListChildActivity.class, bundle);
            }

        });
        rlLeaveMsg.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000102");
                startActivity(MessageListChildActivity.class, bundle);
            }
        });
        rlPatientSign.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000107");
                startActivity(MessageListChildActivity.class, bundle);
            }
        });
        rlHealthEducation.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000109");
                startActivity(MessageListChildActivity.class, bundle);
            }
        });
        rlUrgentRemind.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000106");
                startActivity(MessageListChildActivity.class, bundle);
            }
        });
        rlSystemMsg.setOnClickListener(v -> {
            if (unReadMsgBean != null) {
                Bundle bundle = new Bundle();
                bundle.putString("messageType", "4000108");
                startActivity(MessageListChildActivity.class, bundle);
            }
        });
    }


    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getSearchMsgPUshReminderAllResult(UnReadMsgBean unReadMsgBean) {
        mLoadingLayout.showContent();
        this.unReadMsgBean = unReadMsgBean;
        int msgTypeCount01 = unReadMsgBean.getMsgTypeCount01();
        int msgTypeCount02 = unReadMsgBean.getMsgTypeCount02();
        int msgTypeCount06 = unReadMsgBean.getMsgTypeCount06();
        int msgTypeCount07 = unReadMsgBean.getMsgTypeCount07();
        int msgTypeCount08 = unReadMsgBean.getMsgTypeCount08();
        int msgTypeCount09 = unReadMsgBean.getMsgTypeCount09();
        if (msgTypeCount01 > 0) {
            tvUnreadPatientVisit.setVisibility(View.VISIBLE);
            tvUnreadPatientVisit.setText(String.valueOf(msgTypeCount01));
        } else {
            tvUnreadPatientVisit.setVisibility(View.GONE);
        }
        if (msgTypeCount02 > 0) {
            tvUnreadLieaveMsg.setVisibility(View.VISIBLE);
            tvUnreadLieaveMsg.setText(String.valueOf(msgTypeCount02));
        } else {
            tvUnreadLieaveMsg.setVisibility(View.GONE);
        }
        if (msgTypeCount06 > 0) {
            tvUnreadUrgentRemindMsg.setVisibility(View.VISIBLE);
            tvUnreadUrgentRemindMsg.setText(String.valueOf(msgTypeCount06));
        } else {
            tvUnreadUrgentRemindMsg.setVisibility(View.GONE);
        }

        if (msgTypeCount07 > 0) {
            tvUnreadPatientSignMsg.setVisibility(View.VISIBLE);
            tvUnreadPatientSignMsg.setText(String.valueOf(msgTypeCount07));
        } else {
            tvUnreadPatientSignMsg.setVisibility(View.GONE);
        }

        if (msgTypeCount09 > 0) {
            tvUnreadHealthEducationMsg.setVisibility(View.VISIBLE);
            tvUnreadHealthEducationMsg.setText(String.valueOf(msgTypeCount09));
        } else {
            tvUnreadHealthEducationMsg.setVisibility(View.GONE);
        }

        if (msgTypeCount08 > 0) {
            tvUnreadSystemMsg.setVisibility(View.VISIBLE);
            tvUnreadSystemMsg.setText(String.valueOf(msgTypeCount08));
        } else {
            tvUnreadSystemMsg.setVisibility(View.GONE);
        }


    }
}
