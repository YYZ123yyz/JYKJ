package www.jykj.com.jykj_zxyl.fragment.liveroom;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UpLoadLiveProgromBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromAdapter;
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromPicAdapter;
import www.jykj.com.jykj_zxyl.util.CircleImageView;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 15:15
 */
public class LiveProgromFragment extends
        AbstractMvpBaseFragment<LiveProgromContract.View, LiveProgromPresenter>
        implements LiveProgromContract.View {
    @BindView(R.id.iv_live_user_head)
    CircleImageView ivLiveUserHead;
    @BindView(R.id.tv_academic_title)
    TextView tvAcademicTitle;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_consult_btn)
    TextView tvConsultBtn;
    @BindView(R.id.tv_areas_expertise)
    TextView tvAreasExpertise;
    @BindView(R.id.tv_live_theme)
    TextView tvLiveTheme;
    @BindView(R.id.tv_live_category)
    TextView tvLiveCategory;
    @BindView(R.id.tv_risk_factor)
    TextView tvRiskFactor;
    @BindView(R.id.tv_live_keywords)
    TextView tvLiveKeywords;
    @BindView(R.id.rv_live_protrom_list)
    RecyclerView rvLiveProtromList;
    @BindView(R.id.rv_live_picture_list)
    RecyclerView rvLivePictureList;

    private String detailsCode;
    public static LiveProgromFragment newInstance(String detailsCode) {
        LiveProgromFragment fragment = new LiveProgromFragment();
        Bundle args = new Bundle();
        args.putString("detailsCode",detailsCode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_live_progrom;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Bundle arguments = this.getArguments();
        if (arguments!=null) {
            detailsCode= arguments.getString("detailsCode");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendGetBroadcastDetailInfoRequest(detailsCode,this.getActivity());
    }

    @Override
    public void getBroadcastDetailInfoResult(LiveProtromDetialBean
                                                     liveProtromDetialBean) {
        setLiveDetialData(liveProtromDetialBean);
    }

    /**
     * 设置直播详情数据
     *
     * @param liveProtromDetialBean 直播详情
     */
    private void setLiveDetialData(LiveProtromDetialBean liveProtromDetialBean) {
        LiveProtromDetialBean.DoctorInfoBean doctorInfo = liveProtromDetialBean.getDoctorInfo();
        Glide.with(this).load(doctorInfo.getUserLogoUrl())
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivLiveUserHead);
        tvAcademicTitle.setText(doctorInfo.getDoctorTitleName());
        tvHospital.setText(doctorInfo.getHospitalInfoName());
        String goodAtRealm = doctorInfo.getGoodAtRealm();
        tvAreasExpertise.setText(StringUtils.isNotEmpty(goodAtRealm)?goodAtRealm:"无");
        tvLiveTheme.setText(String.format("01.直播主题：%s", liveProtromDetialBean.getBroadcastTitle()));
        tvLiveCategory.setText(String.format("02.直播类目：%s", liveProtromDetialBean.getClassName()));
        tvRiskFactor.setText(String.format("03.危险因素：%s", liveProtromDetialBean.getRiskName()));
        tvLiveKeywords.setText(String.format("04.直播关键字：%s", liveProtromDetialBean.getAttrName()));
        LiveProtromDetialBean.SyllabusBean syllabus = liveProtromDetialBean.getSyllabus();
        if (syllabus != null) {
            String syllabusContent = syllabus.getSyllabusContent();
            if (StringUtils.isNotEmpty(syllabusContent)) {
                List<UpLoadLiveProgromBean> list
                        = GsonUtils.jsonToList(syllabusContent, UpLoadLiveProgromBean.class);
                setLiveProgromAdapter(list);
            }
        }
        List<LiveProtromDetialBean.ImageListBean> imageList = liveProtromDetialBean.getImageList();
        if (!CollectionUtils.isEmpty(imageList)) {
            setLivePhotoAdapter(imageList);
        }

    }

    /**
     * 设置直播大纲数据
     * @param list 直播大纲列表
     */
    private void setLiveProgromAdapter(List<UpLoadLiveProgromBean> list){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvLiveProtromList.setLayoutManager(layoutManager);
        LiveProgromAdapter liveProgromAdapter=new LiveProgromAdapter(list,this.getContext());
        liveProgromAdapter.setData(list);
        rvLiveProtromList.setAdapter(liveProgromAdapter);
    }

    /**
     * 设置图片数九
     * @param listBeans 图片列表
     */
    private void setLivePhotoAdapter(List<LiveProtromDetialBean.ImageListBean> listBeans){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvLivePictureList.setLayoutManager(layoutManager);
        LiveProgromPicAdapter liveProgromPicAdapter=new LiveProgromPicAdapter(listBeans,this.getContext());
        liveProgromPicAdapter.setData(listBeans);
        rvLivePictureList.setAdapter(liveProgromPicAdapter);
    }

}
