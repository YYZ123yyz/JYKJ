package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.liyi.grid.AutoGridView;
import com.liyi.grid.adapter.BaseAutoGridAdapter;
import com.liyi.grid.adapter.BaseAutoGridHolder;
import com.liyi.grid.adapter.SimpleAutoGridAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter.Myself_DetailAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.Myself_DetaiBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * 个人填写详情
 */
public class Myself_DetailActivity extends AbstractMvpBaseActivity<Myself_DetailContract.View,
        Myself_DetailPresenter> implements Myself_DetailContract.View {


    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.autoGridView)
    RecyclerView autoGridView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String recordId;
    private JYKJApplication mApp;
    private String patientCode;
    private String patientName;
    private SimpleAutoGridAdapter mAdapter;
    private Myself_DetailAdapter myself_detailAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_myself__detail;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        recordId = intent.getStringExtra("recordId");
        SharedPreferences mSharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        patientCode = mSharedPreferences.getString("patientCode", "");
        patientName = mSharedPreferences.getString("patientName", "");
        ActivityUtil.setStatusBarMain(this);
        mApp = (JYKJApplication) getApplication();
        addClick();
        //创建默认的线性LayoutManager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(tihs);
//        layoutManager.setOrientation(LinearLayout.VERTICAL);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        autoGridView.setLayoutManager(gridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        autoGridView.setHasFixedSize(true);
    }

    private void addClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchMyself_DetaiRequest(mApp.loginDoctorPosition,
                "1", patientCode, patientName, recordId,this);
    }

    @Override
    public void getSearchMyself_DetailResult(Myself_DetaiBean myself_detaiBean) {
        if (myself_detaiBean != null) {
            tvTitle.setText(myself_detaiBean.getRecordName());
            //日期
            long treatmentDate = myself_detaiBean.getTreatmentDate();
            String dateToString = DateUtils.getDateToYYYYMMDD(treatmentDate);
            tvTime.setText(dateToString);
            //描述
            tvContent.setText(myself_detaiBean.getRecordContent());
            //图片
            List<String> list=new ArrayList<>();
            String recordImgArray = myself_detaiBean.getRecordImgArray();
            String[] str=recordImgArray.split(",");
            for (String s1 : str) {
                list.add(s1);
            }

            myself_detailAdapter = new Myself_DetailAdapter(list, Myself_DetailActivity.this);
            autoGridView.setAdapter(myself_detailAdapter);
        }
    }

    @Override
    public void getSearchMyself_DetailResultError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading(int code) {

    }

}