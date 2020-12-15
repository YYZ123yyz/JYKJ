package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_adapter.SecondaryListAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.ChoosedMedicinalContract;
import www.jykj.com.jykj_zxyl.medicalrecord.ChoosedMedicinalPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.MedicinalItemAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.popup.MedicinalCategoryPopup;
import www.jykj.com.jykj_zxyl.medicalrecord.popup.MedicinalCategoryPopup2;

/**
 * Description:选择药物
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 10:38
 */
public class ChoosedMedicinalListActivity extends AbstractMvpBaseActivity<
        ChoosedMedicinalContract.View, ChoosedMedicinalPresenter>
        implements ChoosedMedicinalContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_title_type)
    TextView tvTitleType;
    @BindView(R.id.rl_choose_type)
    RelativeLayout rlChooseType;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.tv_search_btn)
    TextView tvSearchBtn;
    @BindView(R.id.rl_search_title)
    RelativeLayout rlSearchTitle;
    @BindView(R.id.viewl_top_line)
    View viewlTopLine;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private MedicinalCategoryPopup medicinalCategoryPopup;//检查检验列表弹框
    private MedicinalCategoryPopup2 medicinalCategoryPopup2;
    private DrugClassificationBean.DrugTypeMedicineListBean currentMedicinalTypeBean;//当前药品类型
    private MedicinalItemAdapter mMedicinalItemAdapter;//药品选项适配器
    private List<MedicinalTypeBean> medicinalTypeBeans;//药品类型
    private List<MedicinalInfoBean> mMedicinalInfoBeans;//药品信息
    private MedicinalInfoBean currentMedicinalInfoBean;//当前药品信息
    private String srarchDrugName="";
    private int pos;
    /**
     * 评论二级列表
     */
    private List<SecondaryListAdapter.DataTree<DrugClassificationBean
            , DrugClassificationBean.DrugTypeMedicineListBean>> datas;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            pos = extras.getInt("pos");
            currentMedicinalInfoBean = (MedicinalInfoBean)
                    extras.getSerializable("currentMedicinalInfo");
        }
        medicinalCategoryPopup = new MedicinalCategoryPopup(this);
        medicinalCategoryPopup2= new MedicinalCategoryPopup2(this);
        medicinalTypeBeans = new ArrayList<>();
        mMedicinalInfoBeans = new ArrayList<>();
        datas=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_medicinal_choosed;
    }



    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        initLoadingAndRetryManager();
        initRecyclerView();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
       // mPresenter.sendMedicinalTypeListRequest("10036",this);
        mPresenter.sendGetDrugTypeMedicineRequest("0",this);
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("选择药物");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleDrawable(R.mipmap.bg_right_add);
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle=new Bundle();
//                bundle.putString("drugUseType",currentMedicinalTypeBean.getMedicineCode()+"");
//                startActivity(AddDrugInfoActivity.class,bundle,100);


                Bundle bundle=new Bundle();
                bundle.putString("medicineCode",currentMedicinalTypeBean.getMedicineCode()+"");
                startActivity(AddDrugInfoActivity2.class,bundle,100);
            }
        });
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();

        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlChooseType.setOnClickListener(v -> {
            if(!CollectionUtils.isEmpty(datas)){
                medicinalCategoryPopup2.show(viewlTopLine);
                medicinalCategoryPopup2.setData(datas);
            }
        });
        medicinalCategoryPopup2.setOnClickListener(new MedicinalCategoryPopup2.OnClickListener() {
            @Override
            public void onClickChanged(DrugClassificationBean.DrugTypeMedicineListBean drugTypeMedicineListBean) {
                currentMedicinalTypeBean=drugTypeMedicineListBean;
                tvTitleType.setText(currentMedicinalTypeBean.getMedicineName());
                mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116(
                        currentMedicinalTypeBean.getMedicineCode()+""
                        ,srarchDrugName,pageSize,pageIndex
                        ,ChoosedMedicinalListActivity.this);
            }

            @Override
            public void onClickAll() {
                tvTitleType.setText("全部");
                mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116("-1"
                        ,srarchDrugName,pageSize,pageIndex
                        ,ChoosedMedicinalListActivity.this);
            }
        });
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMedicinalInfoBean = mMedicinalItemAdapter.getCurrentMedicinalInfoBean();
                if(currentMedicinalInfoBean ==null){
                    ToastUtils.showToast("请选择检查检验项目");
                    return;
                }

                Bundle bundle=new Bundle();
                bundle.putSerializable("result",currentMedicinalInfoBean);
                bundle.putInt("pos",pos);
                Intent intent=new Intent();
                intent.putExtras(bundle);
                setResult(1000,intent);
                ChoosedMedicinalListActivity.this.finish();
            }
        });
        tvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(ChoosedMedicinalListActivity.this);
                srarchDrugName = edInputContent.getText().toString();
                if (!StringUtils.isNotEmpty(srarchDrugName)) {
                    ToastUtils.showToast("请输入搜索内容");
                    return;
                }
                pageIndex=1;
                mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116(
                        currentMedicinalTypeBean.getMedicineCode()+""
                        ,srarchDrugName,pageSize,pageIndex,
                        ChoosedMedicinalListActivity.this);
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
                if (TextUtils.isEmpty(s)) {
                    pageIndex=1;
                    srarchDrugName="";
                    mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116(
                            currentMedicinalTypeBean.getMedicineCode()+""
                            ,srarchDrugName,pageSize,pageIndex,
                            ChoosedMedicinalListActivity.this);
                }
            }
        });

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        mMedicinalItemAdapter=new MedicinalItemAdapter(this,mMedicinalInfoBeans);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mMedicinalItemAdapter);
    }


    @Override
    public void showLoading(int code) {

    }


    @Override
    public void getMedicinalTypeListResult(List<MedicinalTypeBean> list) {
//        medicinalTypeBeans = list;
//        MedicinalTypeBean medicinalTypeBeanTop = medicinalTypeBeans.get(0);
//        int attrCode = medicinalTypeBeanTop.getAttrCode();
//        if (attrCode != -1) {
//            MedicinalTypeBean medicinalTypeBean = new MedicinalTypeBean();
//            medicinalTypeBean.setAttrCode(-1);
//            medicinalTypeBean.setAttrName("全部");
//            medicinalTypeBeans.add(0, medicinalTypeBean);
//        }
//        currentMedicinalTypeBean = medicinalTypeBeans.get(0);
//        mPresenter.sendMedicinalInfoListRequest(
//                currentMedicinalTypeBean.getAttrCode() + ""
//                , srarchDrugName, pageSize + "", pageIndex + "", this);
    }

    @Override
    public void getMedicinalInfoListResult(List<MedicinalInfoBean> medicinalInfoBeans) {
        if (pageIndex == 1) {
            mMedicinalInfoBeans.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(medicinalInfoBeans)) {

            mMedicinalInfoBeans.addAll(medicinalInfoBeans);
            handlerData(currentMedicinalInfoBean);
            mMedicinalItemAdapter.setData(mMedicinalInfoBeans);
            mMedicinalItemAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayoutManager.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayoutManager.showContent();
    }

    @Override
    public void getDrugClassificationBeanResult(List<DrugClassificationBean> drugClassificationBeans) {
        for (int i = 0; i < drugClassificationBeans.size(); i++) {
            List<DrugClassificationBean.DrugTypeMedicineListBean> drugTypeMedicineList
                    = drugClassificationBeans.get(i).getDrugTypeMedicineList();
            if (!CollectionUtils.isEmpty(drugTypeMedicineList)) {
                datas.add(new SecondaryListAdapter.DataTree<>(drugClassificationBeans.get(i),
                        drugTypeMedicineList));
            }else{
                datas.add(new SecondaryListAdapter.DataTree<>(drugClassificationBeans.get(i),
                        new ArrayList<>()));
            }
        }
//        MedicinalTypeBean medicinalTypeBeanTop = medicinalTypeBeans.get(0);
//        int attrCode = medicinalTypeBeanTop.getAttrCode();
//        if (attrCode != -1) {
//            MedicinalTypeBean medicinalTypeBean = new MedicinalTypeBean();
//            medicinalTypeBean.setAttrCode(-1);
//            medicinalTypeBean.setAttrName("全部");
//            medicinalTypeBeans.add(0, medicinalTypeBean);
//        }
        DrugClassificationBean groupItem = datas.get(0).getGroupItem();
        if (groupItem!=null) {
            List<DrugClassificationBean.DrugTypeMedicineListBean>
                    drugTypeMedicineList = groupItem.getDrugTypeMedicineList();
            if (!CollectionUtils.isEmpty(drugTypeMedicineList)) {
                currentMedicinalTypeBean=drugTypeMedicineList.get(0);
            }
        }
//        mPresenter.sendMedicinalInfoListRequest(
//                currentMedicinalTypeBean.getMedicineCode() + ""
//                , srarchDrugName, pageSize + "", pageIndex + "", this);
        mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116(   currentMedicinalTypeBean.getMedicineCode() + ""
                , srarchDrugName, pageSize , pageIndex , this);
    }


    /**
     * 处理数据
     *
     * @param currentMedicinalInfoBean 当前项目
     */
    private void handlerData(MedicinalInfoBean currentMedicinalInfoBean) {
        if (currentMedicinalInfoBean != null) {
            for (MedicinalInfoBean medicinalInfoBean : mMedicinalInfoBeans) {
                if (medicinalInfoBean.getDrugCode()
                        .equals(currentMedicinalInfoBean.getDrugCode())) {
                    medicinalInfoBean.setChoosed(true);
                } else {
                    medicinalInfoBean.setChoosed(false);
                }
            }
        }

    }


    @Override
    public void showEmpty() {
        if(pageIndex == 1){
            mLoadingLayoutManager.showEmpty();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    @Override
    public void showRetry() {
        if (pageIndex==1) {
            mLoadingLayoutManager.showError();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1000) {
            mPresenter.sendSearchMyClinicDetailResPrescribeDrugInfo_201116(
                    currentMedicinalTypeBean.getMedicineCode()+""
                    ,srarchDrugName,pageSize,pageIndex,this);
        }
    }
}
