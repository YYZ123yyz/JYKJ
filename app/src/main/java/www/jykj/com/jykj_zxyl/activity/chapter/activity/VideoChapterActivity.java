package www.jykj.com.jykj_zxyl.activity.chapter.activity;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.activity.chapter.contract.VideoChapterContract;
import www.jykj.com.jykj_zxyl.activity.chapter.presenter.VideoChapterPresenter;
import www.jykj.com.jykj_zxyl.adapter.VideoChapterAdapter;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.ChapterPop;


public class VideoChapterActivity extends AbstractMvpBaseActivity<VideoChapterContract.View, VideoChapterPresenter>
        implements VideoChapterContract.View {
    @BindView(R.id.li_back)
    LinearLayout linBack;
    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;
    @BindView(R.id.iv_tittle)
    ImageView ivTittle;
    @BindView(R.id.part_0)
    LinearLayout tittlePart;
    @BindView(R.id.click_num)
    TextView clickNum;//浏览
    @BindView(R.id.tittle_tv)
    TextView tittleTv;//标题
    @BindView(R.id.program_tv)
    TextView programTv;//类目名
    @BindView(R.id.iv_userhead)
    ImageView ivHead;//头像
    @BindView(R.id.name_tv)
    TextView nameTv;//姓名
    @BindView(R.id.price_tv)
    TextView priceTv;//价格
    @BindView(R.id.all_num)
    TextView allNum;//全部章节
    private JYKJApplication mApp;
    private ChapterPop chapterPop;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_video_chapter;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        chapterPop = new ChapterPop(this);
        chapterPop.setGo2PayListen(type -> {
            switch (type) {
                case 0: //余额
                    ToastUtils.showShort("余额");
                    break;
                case 1://微信
                    ToastUtils.showShort("微信");
                    break;
                case 2://支付宝
                    ToastUtils.showShort("支付宝");
                    break;
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication)getApplication();
        mPresenter.getVideoChapterList(getParams());
    }

    private String getParams(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("courseWareCode","1e18a17de66441c781bfe8a98d6dc1fc");
        stringStringHashMap.put("operUserCode",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @OnClick({R.id.li_back})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.li_back:
                finish();
                break;
        }
    }

    @Override
    public void getListSucess(ChapterListBean data) {
        Glide.with(this).load(data.getIconUrl()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                tittlePart.setBackground( ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.drawable2Bitmap(resource),1,25)));
                ivTittle.setImageDrawable(resource);
            }
        });
        allNum.setText(String.format("(共%d章节)", data.getSecondList().size()));
        clickNum.setText(String.format("浏览量: %d", data.getClickCount()));
        tittleTv.setText(data.getTitle());
        nameTv.setText(data.getCreateBy());
        int freeType = data.getFreeType();//0免费,1收费
        priceTv.setText(String.format("¥ %s元", data.getPrice()));
        List<ChapterListBean.SecondListBean> secondList = data.getSecondList();
        VideoChapterAdapter videoChapterAdapter = new VideoChapterAdapter(R.layout.item_videochapter, secondList);
        mRecycleview.setAdapter(videoChapterAdapter);
        videoChapterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("点击");
                switch (view.getId()) {
                    case R.id.tv_price:
                        int freeTypePay = secondList.get(position).getFreeType();
                        chapterPop.setPayMoney("200");
                        chapterPop.showPop(tittlePart);
                        break;
                }
            }
        });
        videoChapterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转

            }
        });
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtils.showShort(msg);
        Glide.with(this).load("http://114.215.137.171:8040/liveImage/cover/915b29f3d1b7451fa1d4995a8f91b156/cover_20201120095105.jpg").into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                tittlePart.setBackground( ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.drawable2Bitmap(resource),1,25)));
                ivTittle.setImageDrawable(resource);
            }
        });
    }
}
