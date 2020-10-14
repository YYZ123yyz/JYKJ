package www.jykj.com.jykj_zxyl.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyLiveRoomActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyPatientActivity;
import www.jykj.com.jykj_zxyl.activity.home.QRCodeActivity;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.AddPatientActivity;
import www.jykj.com.jykj_zxyl.fragment.FragmentShouYe;
import www.jykj.com.jykj_zxyl.mypatient.activity.PatientActivity;
import yyz_exploit.activity.activity.HelpActivity;
import zxing.android.CaptureActivity;

public class MoreFeaturesPopupWindow extends PopupWindow implements View.OnClickListener {

    private FragmentShouYe  fragmentShouYe;
    private PatientActivity mMyPatientActivity;
    private Context mContext;
    private View mPopView;
    private TextView tvSys;
    private TextView tvYqth;
    private TextView tvTjhz;
    private TextView tvWdzs;
    private TextView tvWdxx;
    private TextView tvCjlm;
    private TextView tvFqhz;
    private TextView tvWdbb;
    private TextView tvBzfk;
    private TextView tv_wdhz;
    private TextView tv_wdzbj;
    private HelpActivity helpActivity;
    private QRCodeActivity qrCodeActivity;
    public MoreFeaturesPopupWindow(Activity context) {
        super(context);
        mContext = context;
        init(context);

        setPopupWindow(context);
        tvSys.setOnClickListener(this);
        tvYqth.setOnClickListener(this);
        tvTjhz.setOnClickListener(this);
        tvWdzs.setOnClickListener(this);
     //   tvWdxx.setOnClickListener(this);
      //  tvCjlm.setOnClickListener(this);
     //   tvFqhz.setOnClickListener(this);
      //  tvWdbb.setOnClickListener(this);
        tvBzfk.setOnClickListener(this);

        tv_wdhz.setOnClickListener(this);
        tv_wdzbj.setOnClickListener(this);
    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_window_more_feature, null);
        tvSys = mPopView.findViewById(R.id.tv_sys);
        tvYqth = mPopView.findViewById(R.id.tv_yqth);
        tvTjhz = mPopView.findViewById(R.id.tv_tjhz);
        tvWdzs = mPopView.findViewById(R.id.tv_wdzs);

        tvBzfk = mPopView.findViewById(R.id.tv_bzfk);
        //我的患者
        tv_wdhz = mPopView.findViewById(R.id.tv_wdhz);
        //我的直播间
        tv_wdzbj = mPopView.findViewById(R.id.tv_wdzbj);
    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow(Activity activity) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wdhz:
                mContext. startActivity(new Intent(mContext, MyPatientActivity.class));
                break;

            case R.id.tv_sys:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivityForResult(intent,fragmentShouYe.REQUEST_CODE_SCAN);
                if (mMyPatientActivity != null)
                    mMyPatientActivity.startActivityForResult(intent,fragmentShouYe.REQUEST_CODE_SCAN);
                break;
            case R.id.tv_yqth:
                mContext.startActivity(new Intent(mContext,QRCodeActivity.class));
                break;
            case R.id.tv_tjhz:
                mContext.startActivity(new Intent(mContext,AddPatientActivity.class));
                break;
            case R.id.tv_wdzs:
                mContext.startActivity(new Intent(mContext,MyClinicActivity.class));

                break;

//            case R.id.tv_cjlm:
//                mContext.startActivity(new Intent(mContext,DoctorsUnionActivity.class));
//                break;
            case R.id.tv_wdzbj:
                mContext.startActivity(new Intent(mContext, MyLiveRoomActivity.class));
                break;

                //帮助与反馈
            case R.id.tv_bzfk:
                mContext.startActivity(new Intent(mContext, HelpActivity.class));
                break;
        }
    }

    /**
     * 设置入口为首页
     * @param mFragment
     */
    public void setSouYeFragment(FragmentShouYe mFragment) {
        fragmentShouYe = mFragment;
    }

    /**
     * 设置入口为我的
     * @param mActivity
     */
    public void setMyPatientActivity(PatientActivity mActivity) {
        mMyPatientActivity = mActivity;
    }

    /**
     * 设置入口为帮助与反馈
     * @param mActivity
     */
    public void setHelpActivity(HelpActivity mActivity) {
        helpActivity = mActivity;
    }

    /**
     * 设置入口为我的识别码
     * @param mActivity
     */
    public void setQRCodeActivity(QRCodeActivity mActivity) {
        qrCodeActivity = mActivity;
    }
}
