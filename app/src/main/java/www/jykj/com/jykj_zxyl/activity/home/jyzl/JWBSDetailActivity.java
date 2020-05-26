package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 就诊总览==》个人总览==>个人状况==>既往病史==详情
 */
public class JWBSDetailActivity extends AppCompatActivity {


    private Context mContext;
    private TWJZ_CFQActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private LinearLayout mJBXX;                                  //基本信息
    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //获取返回字符串
    private String mPatientCode;
    private int mRowNum = 10;                            //每页行数
    private int mPageNum = 1;                           //页数
    private int recordTypeId = 0;                           //搜索的记录类型
    private TextView mMore;

    private LinearLayout mQB;                                //选择全部
    private LinearLayout mJWBS;                                //选择既往病史
    private LinearLayout mBCJL;                                //选择病程记录
    private RelativeLayout mBack;
    private ProvidePatientConditionDiseaseRecord mProvidePatientConditionDiseaseRecords = new ProvidePatientConditionDiseaseRecord();

    private PatientJWBSAdapter mPatientJWBSAdapter;

    private PopupWindow mPopuWindow;

    private TextView mBCBM;                      //病程别名
    private TextView mJZRQ;                      //就诊日期
    private TextView mBCFL;                      //病程分类
    private TextView mBQZS;                      //病情自述

    private ImageView mImageView01;
    private ImageView mImageView02;
    private ImageView mImageView03;
    private ImageView mImageView04;
    private ImageView mImageView05;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_jwbsdetail);
        ActivityUtil.setStatusBarMain(JWBSDetailActivity.this);

        mProvidePatientConditionDiseaseRecords = (ProvidePatientConditionDiseaseRecord) getIntent().getSerializableExtra("patientInfo");
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();

    }

    private void initLayout() {
        mBack = (RelativeLayout) this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());
        mBCBM = (TextView) this.findViewById(R.id.tv_activityHZZL_userSG);
        mJZRQ = (TextView) this.findViewById(R.id.tv_activityHZZL_userYW);
        mBCFL = (TextView) this.findViewById(R.id.tv_activityHZZL_userTZ);
        mBQZS = (TextView) this.findViewById(R.id.tv_activityHZZL_region);
        mBCBM.setText(mProvidePatientConditionDiseaseRecords.getRecordName());
        mJZRQ.setText(Util.dateToStr(mProvidePatientConditionDiseaseRecords.getTreatmentDate()));
        mBCFL.setText(mProvidePatientConditionDiseaseRecords.getRecordTypeName());
        mBQZS.setText(mProvidePatientConditionDiseaseRecords.getRecordContent());

        mImageView01 = (ImageView) this.findViewById(R.id.image01);
        mImageView02 = (ImageView) this.findViewById(R.id.image02);
        mImageView03 = (ImageView) this.findViewById(R.id.image03);
        mImageView04 = (ImageView) this.findViewById(R.id.image04);
        mImageView05 = (ImageView) this.findViewById(R.id.image05);
        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl1() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1())) {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1());
                Glide.with(mContext).load(avatarResId).into(mImageView01);

            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView01);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl2() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2())) {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2());
                Glide.with(mContext).load(avatarResId).into(mImageView02);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView02);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl3() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3())) {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3());
                Glide.with(mContext).load(avatarResId).into(mImageView03);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView03);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl4() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4())) {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4());
                Glide.with(mContext).load(avatarResId).into(mImageView04);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView04);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl5() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5())) {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5());
                Glide.with(mContext).load(avatarResId).into(mImageView05);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView05);
            }
        }

    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
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
