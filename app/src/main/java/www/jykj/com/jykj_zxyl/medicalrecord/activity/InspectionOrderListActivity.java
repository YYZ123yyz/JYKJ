package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemProjectBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionItemContract;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionItemPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.InspectionItemOrderAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.utils.ConvertUtils;

/**
 * Description:检查检验单列表
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 18:44
 */
public class InspectionOrderListActivity extends AbstractMvpBaseActivity<InspectionItemContract.View,
        InspectionItemPresenter> implements InspectionItemContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_add_more)
    ImageView ivAddMore;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private InspectionItemOrderAdapter mInspectionItemProjectAdapter;
    private List<InspectionItemDataBean> dataBeans;
    private SparseArray<InspectionItemProjectBean> projectSparseArray;
    private SparseArray<List<InspectionItemPositionBean>> positionSparseArray;
    private SparseArray<InspectionItemGradeBean> gradeSparseArray;
    private int currentPos;
    private String patientCode;
    private String patientName;
    private String orderId;
    private JYKJApplication mApp;
    private boolean isShowLoading;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            patientCode=extras.getString("patientCode");
            patientName=extras.getString("patientName");
            orderId=extras.getString("orderId");
        }
        dataBeans=new ArrayList<>();
        positionSparseArray=new SparseArray<>();
        projectSparseArray=new SparseArray<>();
        gradeSparseArray=new SparseArray<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inspection_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        //初始化ToolBar
        setToolBar();
        //初始化loading
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchInteractOrderInspectionListRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getHospitalInfoId()+""
                ,orderId,"",this);
    }


    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(rvList);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchInteractOrderInspectionListRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getHospitalInfoId()+""
                    ,orderId,"",this);
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mInspectionItemProjectAdapter=new InspectionItemOrderAdapter(this,dataBeans);
        rvList.setAdapter(mInspectionItemProjectAdapter);
    }
    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("检查检验");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener() {
        ivAddMore.setOnClickListener(v -> {
            InspectionItemDataBean itemDataBean = new InspectionItemDataBean();
            dataBeans.add(itemDataBean);
            mInspectionItemProjectAdapter.notifyDataSetChanged();
            mLoadingLayoutManager.showContent();
        });
        tvEnsureBtn.setOnClickListener(v -> {
            if (CollectionUtils.isEmpty(dataBeans)) {
                ToastUtils.showToast("请添加检查检验项目");
                return;
            }
            isShowLoading=true;
            List<InspectionItemUploadBean> uploadBeans = ConvertUtils.convertLocalToUploadBean(dataBeans,
                    orderId, patientCode, patientName, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    , mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            mPresenter.sendOperUpdInteractOrderInspectionRequest(uploadBeans,this);
        });
        mInspectionItemProjectAdapter.setOnItemClickListener(
                new InspectionItemOrderAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int pos) {


                    }

                    @Override
                    public void onClickInspectionProject(int pos) {
                        Bundle bundle=new Bundle();
                        bundle.putInt("pos",pos);
                        InspectionItemProjectBean inspectionItemProjectBean
                                = projectSparseArray.get(pos);
                        bundle.putSerializable("currentProject",inspectionItemProjectBean);
                        startActivity(InspectionProjectChoosedActivity.class,
                                bundle,100);
                    }

                    @Override
                    public void onClickInspectionPosition(int pos) {
                        String inspectionCode = dataBeans.get(pos).getInspectionCode();
                        if (!StringUtils.isNotEmpty(inspectionCode)) {
                            ToastUtils.showToast("请选择检查项目");
                            return;
                        }
                        InspectionItemDataBean itemDataBean = dataBeans.get(pos);
                        Bundle bundle=new Bundle();
                        bundle.putString("hospitalInfoCode",itemDataBean.getHospitalInfoCode());
                        bundle.putString("inspectionCode",itemDataBean.getInspectionCode());
                        bundle.putInt("pos",pos);
                        List<InspectionItemPositionBean> positionBeans = positionSparseArray.get(pos);
                        if (!CollectionUtils.isEmpty(positionBeans)) {
                            bundle.putSerializable("positionList", (Serializable) positionBeans);
                        }
                        startActivity(InspectionPositionChoosedActivity.class,bundle,101);
                    }

                    @Override
                    public void onClickInspectionGrade(int pos) {
                        String inspectionCode = dataBeans.get(pos).getInspectionCode();
                        if (!StringUtils.isNotEmpty(inspectionCode)) {
                            ToastUtils.showToast("请选择检查项目");
                            return;
                        }
                        if (gradeSparseArray.size() > 0) {
                            showInspectionGradeDialog(pos);
                        }

                    }

                    @Override
                    public void onClickInspectionTime(int pos) {
                        showChoosedTimeDialog(pos);
                    }

                    @Override
                    public void onClickDeleteItem(int pos) {
                        InspectionItemDataBean itemDataBean = dataBeans.get(pos);
                        String inspectionOrderCode = itemDataBean.getInspectionOrderCode();
                        if (StringUtils.isNotEmpty(inspectionOrderCode)) {
                            isShowLoading=true;
                            mPresenter.sendOperDelInteractOrderInspectionRequest(inspectionOrderCode
                                    ,pos,InspectionOrderListActivity.this);

                        }else{
                            dataBeans.remove(pos);
                            mInspectionItemProjectAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }




    /**
     * 预约选择时间弹框
     * @param pos 位置
     */
    private void showChoosedTimeDialog(int pos) {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            if (DateUtils.isLessThanCurrentDate(DateUtils.getDate(date))) {
                ToastUtils.showToast("预约日期不能小于当前日期");
                return;
            }
            dataBeans.get(pos).setInspectionTime(DateUtils.getDate(date));
            mInspectionItemProjectAdapter.notifyDataSetChanged();

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").build();
        timePickerView.show();
    }


    /**
     * 检查检验等级弹框
     *
     * @param pos 位置
     */
    private void showInspectionGradeDialog(int pos) {
        InspectionItemGradeBean inspectionItemGradeBean = gradeSparseArray.get(pos);
        List<String> inspectionGradeListNames = ConvertUtils.getInspectionGradeListName(inspectionItemGradeBean);
        List<String> inspectionGradeListCodes = ConvertUtils.getInspectionGradeListCode(inspectionItemGradeBean);
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    if (inspectionItemGradeBean != null && !CollectionUtils.isEmpty(inspectionGradeListNames)) {
                        dataBeans.get(pos).setInspectionGradeName(inspectionGradeListNames.get(options1));
                        dataBeans.get(pos).setInspectionGradeCode(inspectionGradeListCodes.get(options1));
                        mInspectionItemProjectAdapter.notifyDataSetChanged();
                    }
                })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(inspectionGradeListNames,
                null, null);
        optionPickUnit.show();
    }


    /**
     * 处理数据
     * @param itemDataBeans 检查检验单列表
     */
    private void handlerData( List<InspectionItemDataBean> itemDataBeans){
        for (int i = 0; i < itemDataBeans.size(); i++) {
            InspectionItemProjectBean inspectionItemProjectBean=new InspectionItemProjectBean();
            InspectionItemDataBean itemDataBean = itemDataBeans.get(i);
            inspectionItemProjectBean.setInspectionCode(itemDataBean.getInspectionCode());
            inspectionItemProjectBean.setInspectionName(itemDataBean.getInspectionName());
            inspectionItemProjectBean.setGradeCode(itemDataBean.getGradeCode());
            inspectionItemProjectBean.setHospitalInfoCode(itemDataBean.getHospitalInfoCode());
            String hospitalInfoCode = inspectionItemProjectBean.getHospitalInfoCode();
            mPresenter.sendSearchInspectionGradeListRequest(
                    StringUtils.isNotEmpty(hospitalInfoCode)?hospitalInfoCode:"0",
                    inspectionItemProjectBean.getGradeCode(),i,this);
            projectSparseArray.put(i,inspectionItemProjectBean);
            String positionCode = itemDataBean.getPositionCode();
            String positionName = itemDataBean.getPositionName();
            List<InspectionItemPositionBean> inspectionItemPositionBeans=new ArrayList<>();
            if (StringUtils.isNotEmpty(positionCode)) {
                String[] split = positionCode.split(",");
                for (String s : split) {
                    InspectionItemPositionBean inspectionItemPositionBean = new InspectionItemPositionBean();
                    inspectionItemPositionBean.setPositionCode(s);
                    inspectionItemPositionBeans.add(inspectionItemPositionBean);
                }

            }
            if (StringUtils.isNotEmpty(positionName)) {
                String[] split = positionName.split(",");
                if(inspectionItemPositionBeans.size()==split.length){
                    for (int i1 = 0; i1 < split.length; i1++) {
                        inspectionItemPositionBeans.get(i1).setPositionName(split[i1]);
                    }
                }

            }

            positionSparseArray.put(i,inspectionItemPositionBeans);
            InspectionItemGradeBean inspectionItemGradeBean=new InspectionItemGradeBean();
            inspectionItemGradeBean.setGradeContentCode(itemDataBean.getInspectionGradeCode());
            inspectionItemGradeBean.setGradeContentName(itemDataBean.getInspectionGradeName());
            gradeSparseArray.put(i,inspectionItemGradeBean);

        }

    }

    @Override
    public void getSearchInspectionItemListResult(List<InspectionItemBean> inspectionItemBeans) {

        List<InspectionItemDataBean> itemDataBeans = ConvertUtils.convertToItemDataBean(inspectionItemBeans,
                mApp.mViewSysUserDoctorInfoAndHospital.getHospitalInfoId() + "");

        dataBeans.addAll(itemDataBeans);
        handlerData(dataBeans);
        mInspectionItemProjectAdapter.notifyDataSetChanged();
        mLoadingLayoutManager.showContent();
    }

    @Override
    public void getSearchInspectionGradeListResult(InspectionItemGradeBean inspectionItemGradeBean,int pos) {
        gradeSparseArray.put(pos,inspectionItemGradeBean);
    }

    @Override
    public void getDelInteractOrderInspectionResult(boolean isSucess,int pos, String msg) {
        if(isSucess){
            dataBeans.remove(pos);
            mInspectionItemProjectAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getUpdateInteractOrderInspectionResult(boolean isSucess, String msg) {
        if (isSucess) {
            setResult(1000);
            this.finish();
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void showLoading(int code) {
        if(isShowLoading){
            showLoading("",null);
            isShowLoading=false;
        }

    }

    @Override
    public void showEmpty() {
       mLoadingLayoutManager.showEmpty();
    }

    @Override
    public void showRetry() {
        mLoadingLayoutManager.showError();
    }

    @Override
    public void hideLoading() {
       dismissLoading();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            Bundle extras = data.getExtras();
            switch (resultCode) {
                case 1000:
                    if (extras!=null) {
                        InspectionItemProjectBean inspectionProjectBean=(InspectionItemProjectBean)
                                extras.getSerializable("result");
                        int currentPos = extras.getInt("pos");
                        if (inspectionProjectBean!=null) {
                            projectSparseArray.put(currentPos,inspectionProjectBean);
                            String inspectionName = inspectionProjectBean.getInspectionName();
                            String inspectionCode = inspectionProjectBean.getInspectionCode();
                            String hospitalInfoCode = inspectionProjectBean.getHospitalInfoCode();
                            String gradeCode = inspectionProjectBean.getGradeCode();
                            int inspectionType = inspectionProjectBean.getInspectionType();
                            String inspectionTypeName = inspectionProjectBean.getInspectionTypeName();
                            dataBeans.get(currentPos).setInspectionName(inspectionName);
                            dataBeans.get(currentPos).setInspectionCode(inspectionCode);
                            dataBeans.get(currentPos).setHospitalInfoCode(hospitalInfoCode);
                            dataBeans.get(currentPos).setGradeCode(gradeCode);
                            dataBeans.get(currentPos).setInspectionType(inspectionType+"");
                            dataBeans.get(currentPos).setInspectionTypeName(inspectionTypeName);
                            mInspectionItemProjectAdapter.notifyDataSetChanged();
                            mPresenter.sendSearchInspectionGradeListRequest(
                                    inspectionProjectBean.getHospitalInfoCode(),
                                    gradeCode,currentPos,this);
                        }

                    }
                    break;
                case 1001:
                    if (extras!=null) {
                        currentPos = extras.getInt("pos");
                        List<InspectionItemPositionBean>
                                mChoosedPositionBeans = (List<InspectionItemPositionBean>)
                                extras.getSerializable("result");
                        if (!CollectionUtils.isEmpty(mChoosedPositionBeans)) {
                            positionSparseArray.put(currentPos,mChoosedPositionBeans);
                            dataBeans.get(currentPos).setPositionName(ConvertUtils
                                    .budInspectionPositionNames(mChoosedPositionBeans));
                            dataBeans.get(currentPos).setPositionCode(ConvertUtils
                                    .budInspectionPositionCodes(mChoosedPositionBeans));
                            mInspectionItemProjectAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                    default:
            }

        }

    }
}
