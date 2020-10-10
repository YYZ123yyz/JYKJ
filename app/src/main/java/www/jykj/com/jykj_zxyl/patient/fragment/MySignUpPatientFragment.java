package www.jykj.com.jykj_zxyl.patient.fragment;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.patient.MySignUpPatientContract;
import www.jykj.com.jykj_zxyl.patient.MySignUpPatientPresenter;

/**
 * Description：我的患者fragment
 *
 * @author: qiuxinhai
 * @date: 2020-09-30 10:24
 */
public class MySignUpPatientFragment extends AbstractMvpBaseFragment<MySignUpPatientContract.View,
        MySignUpPatientPresenter> implements MySignUpPatientContract.View {
    @BindView(R.id.tv_All)
    TextView tvAll;
    @BindView(R.id.li_fragmentHZGL_qbCut)
    LinearLayout liFragmentHZGLQbCut;
    @BindView(R.id.li_fragmentHZGL_qb)
    LinearLayout liFragmentHZGLQb;
    @BindView(R.id.tv_Warning)
    TextView tvWarning;
    @BindView(R.id.li_fragmentHZGL_yjCut)
    LinearLayout liFragmentHZGLYjCut;
    @BindView(R.id.li_fragmentHZGL_yj)
    LinearLayout liFragmentHZGLYj;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.li_fragmentHZGL_txCut)
    LinearLayout liFragmentHZGLTxCut;
    @BindView(R.id.li_fragmentHZGL_tx)
    LinearLayout liFragmentHZGLTx;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.li_fragmentHZGL_zcCut)
    LinearLayout liFragmentHZGLZcCut;
    @BindView(R.id.li_fragmentHZGL_zc)
    LinearLayout liFragmentHZGLZc;
    @BindView(R.id.rv_fragmethzgl_hzinfo)
    RecyclerView rvFragmethzglHzinfo;
    private JYKJApplication mApp;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my_patient;
    }




    @Override
    protected void initView(View view) {
        super.initView(view);
        mApp = (JYKJApplication) Objects.requireNonNull(getActivity()).getApplication();
        mPresenter.sendSearchDoctorManagePatientDataByParamRequest(pageSize,pageIndex,
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                ,"1",this.getActivity());
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @OnClick({R.id.li_fragmentHZGL_qb, R.id.li_fragmentHZGL_yj,
            R.id.li_fragmentHZGL_tx, R.id.li_fragmentHZGL_zc

    })
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.li_fragmentHZGL_qb:
              cutDefault();
              liFragmentHZGLQbCut.setVisibility(View.VISIBLE);
              break;
          case R.id.li_fragmentHZGL_yj:
              cutDefault();
              liFragmentHZGLYjCut.setVisibility(View.VISIBLE);
              break;
          case R.id.li_fragmentHZGL_tx:
              cutDefault();
              liFragmentHZGLTxCut.setVisibility(View.VISIBLE);
              break;
          case R.id.li_fragmentHZGL_zc:
              cutDefault();
              liFragmentHZGLZcCut.setVisibility(View.VISIBLE);
              break;
              default:
      }
    }

    private void cutDefault() {
        liFragmentHZGLQbCut.setVisibility(View.GONE);
        liFragmentHZGLYjCut.setVisibility(View.GONE);
        liFragmentHZGLTxCut.setVisibility(View.GONE);
        liFragmentHZGLZcCut.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}
