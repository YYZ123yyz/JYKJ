package www.jykj.com.jykj_zxyl.live;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UpLoadLiveProgromBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:直播大纲列表
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 10:57
 */
public class LiveProgromListActivity extends AbstractMvpBaseActivity<AddLiveProgromContract.View
        , AddLiveProgromPresenter> implements AddLiveProgromContract.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.ll_add_progrom_btn)
    LinearLayout llAddProgromBtn;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.ll_root_view)
    LinearLayout llRootView;
    private List<UpLoadLiveProgromBean> upLoadLiveProgromBeans;
    private JYKJApplication mApp;
    private String detailsCode;
    private String syllabusCode;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            detailsCode = extras.getString("detailCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_live_progrom_list;
    }


    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        upLoadLiveProgromBeans = new ArrayList<>();
        initKeyBoardListener(scrollView);
        setToolBar();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.sendGetBroadcastDetailInfoRequest(detailsCode, this);
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("直播大纲");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());

    }

    /**
     * 添加监听
     */
    private void addListener() {
        llAddProgromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpLoadLiveProgromBean upLoadLiveProgromBean = new UpLoadLiveProgromBean();
                upLoadLiveProgromBean.setUuId(DateUtils.getCurrentMillis());
                upLoadLiveProgromBeans.add(upLoadLiveProgromBean);
                llRootView.addView(getView(upLoadLiveProgromBean));
                initKeyBoardListener(scrollView);
            }
        });

        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CollectionUtils.isEmpty(upLoadLiveProgromBeans)) {
                    ToastUtils.showToast("大纲内容不能为空");
                    return;
                }
                boolean pass = checkNullInputData(upLoadLiveProgromBeans);
                String contentJson = GsonUtils.toJson(upLoadLiveProgromBeans);
                if (pass) {
                    if (StringUtils.isNotEmpty(syllabusCode)) {
                        mPresenter.sendUpdateBroadcastSyllabusRequest(
                                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                                , syllabusCode, contentJson);
                    }else{
                        mPresenter.sendAddBroadcastSyllabusRequest(
                                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                                , detailsCode, contentJson);
                    }

                }

            }
        });
    }



    @SuppressLint("DefaultLocale")
    private View getView(UpLoadLiveProgromBean upLoadLiveProgromBean) {
        View view = View.inflate(context, R.layout.item_live_progrom, null);
        ImageView ivDeleteBtn = view.findViewById(R.id.iv_delete_btn);
        TextView tvLiveTitle = view.findViewById(R.id.tv_live_title);
        EditText edInputContent = view.findViewById(R.id.ed_input_content);
        String content = upLoadLiveProgromBean.getContent();
        if (StringUtils.isNotEmpty(content)) {
            edInputContent.setText(content);
        }
        tvLiveTitle.setText(String.format("第%d章", upLoadLiveProgromBeans.size()));
        ivDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getItemByUuId(upLoadLiveProgromBean.getUuId()
                        , upLoadLiveProgromBeans);
                upLoadLiveProgromBeans.remove(pos);
                llRootView.removeView(view);
            }
        });
        edInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = getItemByUuId(upLoadLiveProgromBean.getUuId(), upLoadLiveProgromBeans);
                upLoadLiveProgromBeans.get(pos).setContent(s.toString());
            }
        });

        return view;
    }




    /**
     * 检查输入内容不为空
     *
     * @param dataBeans 输入内容
     * @return true or false
     */
    private boolean checkNullInputData(List<UpLoadLiveProgromBean> dataBeans) {
        boolean isPass = true;
        for (int i = 0; i < dataBeans.size(); i++) {
            String content = dataBeans.get(i).getContent();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.showToast("大纲内容不能空");
                isPass = false;
                break;
            }
        }
        return isPass;
    }

    /**
     * 根据uuid获取当前位置
     *
     * @param uuId uuId
     * @param list 处方列表
     * @return position
     */
    private int getItemByUuId(String uuId, List<UpLoadLiveProgromBean> list) {
        int currentPos = -1;
        for (int i = 0; i < list.size(); i++) {

            String uuID = list.get(i).getUuId();
            if (uuID.equals(uuId)) {
                currentPos = i;
                break;
            }
        }
        return currentPos;
    }

    @Override
    public void showLoading(int code) {
        showLoading("", null);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        dismissLoading();

    }

    @Override
    public void getAddBroadcastSyllabusResult(boolean isSucess, String msg) {
        if (isSucess) {
            this.finish();
        } else {
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getUpdateBroadcastSyllabusResult(boolean isSucess, String msg) {
        if (isSucess) {
            this.finish();
        } else {
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getBroadcastDetailInfoResult(LiveProtromDetialBean liveProtromDetialBean) {
        LiveProtromDetialBean.SyllabusBean syllabus = liveProtromDetialBean.getSyllabus();
        if (syllabus!=null) {
            String syllabusContent = syllabus.getSyllabusContent();
            syllabusCode = syllabus.getSyllabusCode();
            if (StringUtils.isNotEmpty(syllabusContent)) {
                List<UpLoadLiveProgromBean> list
                        = GsonUtils.jsonToList(syllabusContent, UpLoadLiveProgromBean.class);
                if (!CollectionUtils.isEmpty(list)) {
                    upLoadLiveProgromBeans.addAll(list);
                    setLiveProgromData(upLoadLiveProgromBeans);
                }
            }
        }


    }


    /**
     * 设置直播大纲数据
     * @param upLoadLiveProgromBeans 直播大纲列表
     */
    private void setLiveProgromData(List<UpLoadLiveProgromBean> upLoadLiveProgromBeans){
        for (UpLoadLiveProgromBean upLoadLiveProgromBean : upLoadLiveProgromBeans) {
            llRootView.addView(getView(upLoadLiveProgromBean));
        }
    }

}
