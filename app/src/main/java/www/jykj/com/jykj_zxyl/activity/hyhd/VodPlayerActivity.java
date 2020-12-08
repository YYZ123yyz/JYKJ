package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
import entity.conditions.QueryRoomDetailCond;
import entity.liveroom.RoomDetailInfo;
import netService.HttpNetService;
import util.NetWorkUtils;
import util.QRCodeUtil;
import wechatShare.WechatShareManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UpLoadLiveProgromBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.ShareDialog;
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromAdapter;
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromPicAdapter;
import www.jykj.com.jykj_zxyl.util.CircleImageView;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StrUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VodPlayerActivity extends BaseActivity implements ITXLivePlayListener {
    private static final String TAG = VodPlayerActivity.class.getSimpleName();
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
    @BindView(R.id.iv_live_room_share)
    ImageView ivLiveRoomShare;
    @BindView(R.id.rl_horizontal_root)
    RelativeLayout rlHorizontalRoot;
    @BindView(R.id.landscape_back_ll)
    LinearLayout landscapeBackLl;
    static JYKJApplication mApp;
    private TXVodPlayer mLivePlayer = null;
    private TXCloudVideoView mPlayerView;
    private ImageView mLoadingView;
    private boolean      mHWDecode   = false;
    private LinearLayout mRootView;
    private Button mBtnLog;
    private Button mBtnPlay;
    private Button           mBtnRenderRotation;
    private Button           mBtnRenderMode;
    private Button           mBtnHWDecode;
    private Button btnShare;
    private Button btnShut;
    private ScrollView mScrollView;
    private SeekBar mSeekBar;
    private TextView mTextDuration;
    private TextView         mTextStart;
    private int              mCurrentRenderMode;
    private int              mCurrentRenderRotation;
    private long             mTrackingTouchTS = 0;
    private boolean          mStartSeek = false;
    private boolean          mVideoPause = false;
    private TXVodPlayConfig mPlayConfig;
    private long             mStartPlayTS = 0;
    private boolean mEnableCache;
    private boolean mVideoPlay;
    private String mPlayUrl;
    private ImageView iv_zoom_btn;
    private ImageView iv_miniaml_zoom_btn;
    private LinearLayout play_progress;
    private LinearLayout introduction_layout;
    RelativeLayout rl_back;
    int videowidth = 0;
    int videoheight = 0;
    private String detailsCode;
    private RoomDetailInfo mRoomDetailInfo;
    private ShareDialog shareDialog ;
    WechatShareManager mShareManager;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_vod;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication)getApplication();
        mPlayUrl = getIntent().getStringExtra("playurl");
        mPlayUrl = mPlayUrl.replaceAll("https:","http:");
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        detailsCode= getIntent().getStringExtra("detailsCode");
        mPlayConfig = new TXVodPlayConfig();
        mShareManager = WechatShareManager.getInstance(this);
        setContentView();
        LinearLayout backLL = (LinearLayout)findViewById(R.id.back_ll);
        backLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayRtmp();
                finish();
            }
        });
        rl_back = findViewById(R.id.rl_back);
        TextView titleTV = (TextView) findViewById(R.id.title_tv);
        titleTV.setText(getIntent().getStringExtra("TITLE"));

        checkPublishPermission();

        registerForContextMenu(findViewById(R.id.btnPlay));
        iv_zoom_btn = findViewById(R.id.iv_zoom_btn);
        iv_miniaml_zoom_btn = findViewById(R.id.iv_miniaml_zoom_btn);
        play_progress = findViewById(R.id.play_progress);
        introduction_layout = findViewById(R.id.introduction_layout);
        btnShare=findViewById(R.id.btnShare);
        btnShut=findViewById(R.id.btnShut);
        iv_zoom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
                //mLivePlayer.resume();
                iv_miniaml_zoom_btn.setVisibility(View.VISIBLE);
                iv_zoom_btn.setVisibility(View.GONE);
                play_progress.setVisibility(View.GONE);
                introduction_layout.setVisibility(View.GONE);
                rl_back.setVisibility(View.GONE);
                rlHorizontalRoot.setVisibility(View.VISIBLE);
                goFullscreen();

            }
        });

        iv_miniaml_zoom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
                //mLivePlayer.resume();
                iv_miniaml_zoom_btn.setVisibility(View.GONE);
                iv_zoom_btn.setVisibility(View.VISIBLE);
                play_progress.setVisibility(View.VISIBLE);
                introduction_layout.setVisibility(View.VISIBLE);
                rl_back.setVisibility(View.VISIBLE);
                rlHorizontalRoot.setVisibility(View.GONE);
                goMinimalscreen();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_dialog();
            }
        });
        btnShut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goMinimalscreen();
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
                //mLivePlayer.resume();
                iv_miniaml_zoom_btn.setVisibility(View.GONE);
                iv_zoom_btn.setVisibility(View.VISIBLE);
                play_progress.setVisibility(View.VISIBLE);
                introduction_layout.setVisibility(View.VISIBLE);
                rl_back.setVisibility(View.VISIBLE);
                rlHorizontalRoot.setVisibility(View.GONE);
                goMinimalscreen();
            }
        });

        mVideoPlay = startPlayRtmp();
        sendGetLiveProgromRequest(detailsCode);
        loadData();
        addListener();
    }

    /**
     * 添加监听
     */
    private void addListener() {
        ivLiveRoomShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_dialog();
            }
        });

        mPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHide();
            }
        });
        landscapeBackLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
                //mLivePlayer.resume();
                iv_miniaml_zoom_btn.setVisibility(View.GONE);
                iv_zoom_btn.setVisibility(View.VISIBLE);
                play_progress.setVisibility(View.VISIBLE);
                introduction_layout.setVisibility(View.VISIBLE);
                rl_back.setVisibility(View.VISIBLE);
                rlHorizontalRoot.setVisibility(View.GONE);
                goMinimalscreen();
            }
        });
    }

    /**
     * 显示隐藏动画
     */
    protected void showOrHide() {
        Configuration mConfiguration = this.getResources().getConfiguration();
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            if (rlHorizontalRoot.getVisibility() == View.VISIBLE) {
                hideAnim();
            } else {
                showAnim();
            }
        }else{

            if(rl_back.getVisibility()== View.VISIBLE){
                hideAnim();
            }else{
                showAnim();
            }
        }

    }

    /**
     * 隐藏动画
     */
    private void hideAnim(){

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            Animation animation1 = AnimationUtils.loadAnimation(this
                    , R.anim.anim_exit_bottom);
            animation1.setAnimationListener(new AnimationImp() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    //隐藏锚点

                    rlHorizontalRoot.setVisibility(View.GONE);

                }
            });
            rlHorizontalRoot.startAnimation(animation1);
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            Animation animation1 = AnimationUtils.loadAnimation(this
                    , R.anim.anim_exit_bottom);
            animation1.setAnimationListener(new AnimationImp() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    //隐藏锚点

                    rl_back.setVisibility(View.GONE);

                }
            });
            rl_back.startAnimation(animation1);

        }

    }

    /**
     * 展示动画
     */
    private void showAnim(){
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            if (rlHorizontalRoot.getVisibility() == View.GONE) {
                rlHorizontalRoot.setVisibility(View.VISIBLE);
                rlHorizontalRoot.clearAnimation();
                Animation animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_enter_top);
                rlHorizontalRoot.startAnimation(animation);
            }
        }else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            if (rl_back.getVisibility() == View.GONE) {
                rl_back.setVisibility(View.VISIBLE);
                rl_back.clearAnimation();
                Animation animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_enter_top);
                rl_back.startAnimation(animation);
            }

        }

    }


    /**
     * 动画实现类
     */
    private class AnimationImp implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

    /**
     * 发送直播大纲请求
     *
     * @param detailsCode 直播大纲code
     */
    private void sendGetLiveProgromRequest(String detailsCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("detailsCode", detailsCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().getBroadcastDetailInfo(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        int resCode = baseBean.getResCode();
                        if (resCode==1) {
                            String resJsonData = baseBean.getResJsonData();
                            LiveProtromDetialBean liveProtromDetialBean
                                    = GsonUtils.fromJson(resJsonData, LiveProtromDetialBean.class);
                            setLiveDetialData(liveProtromDetialBean);
                        }
                    }

                    @Override
                    protected void onError(String s) {
                        super.onError(s);
                    }
                });
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
                        = www.jykj.com.jykj_zxyl.util.GsonUtils.jsonToList(syllabusContent, UpLoadLiveProgromBean.class);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvLiveProtromList.setLayoutManager(layoutManager);
        LiveProgromAdapter liveProgromAdapter=new LiveProgromAdapter(list,this);
        liveProgromAdapter.setData(list);
        rvLiveProtromList.setAdapter(liveProgromAdapter);
    }

    /**
     * 设置图片数九
     * @param listBeans 图片列表
     */
    private void setLivePhotoAdapter(List<LiveProtromDetialBean.ImageListBean> listBeans){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvLivePictureList.setLayoutManager(layoutManager);
        LiveProgromPicAdapter liveProgromPicAdapter=new LiveProgromPicAdapter(listBeans,this);
        liveProgromPicAdapter.setData(listBeans);
        rvLivePictureList.setAdapter(liveProgromPicAdapter);
    }


    /**
     * 记载数据
     */
    void loadData(){
        QueryRoomDetailCond queryCond = new QueryRoomDetailCond();
        queryCond.setDetailsCode(detailsCode);
        queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
        queryCond.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        queryCond.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        queryCond.setRequestClientType("1");
        LoadDataTask loadDataTask = new LoadDataTask(queryCond);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void, RoomDetailInfo> {
        QueryRoomDetailCond queryCond;
        LoadDataTask(QueryRoomDetailCond queryCond){
            this.queryCond = queryCond;
        }

        @Override
        protected RoomDetailInfo doInBackground(Void... voids) {
            RoomDetailInfo retinfo = null;
            try{
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/getLiveRoomDetailsByDetailsCode");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaulObjToStr(retEntity.getResJsonData()).length()>3){
                    retinfo = JSON.parseObject(retEntity.getResJsonData(),RoomDetailInfo.class);
                }
            }catch (Exception ex){

            }
            return retinfo;
        }

        @Override
        protected void onPostExecute(RoomDetailInfo roomDetailInfo) {
            mRoomDetailInfo=roomDetailInfo;
        }
    }



    void share_dialog(){
        if(null==mRoomDetailInfo){
            Toast.makeText(this,"分享信息为空",Toast.LENGTH_SHORT).show();
            return;
        }
        shareDialog = new ShareDialog(this);
        shareDialog.show();
        Glide.with(this).load(mRoomDetailInfo.getBroadcastUserLogoUrl())
                .apply(RequestOptions.placeholderOf(R.mipmap.def_head)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into((CircleImageView)shareDialog.findViewById(R.id.iv_userhead));
        ((TextView)shareDialog.findViewById(R.id.tv_live_room_speaker)).setText(mRoomDetailInfo.getBroadcastUserName());
        ((ImageView)shareDialog.findViewById(R.id.iv_gb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        ((ImageView)shareDialog.findViewById(R.id.save_pic_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "jiuyikeji.jpg");
                Bitmap bitmap = screenShot(VodPlayerActivity.this);
                try {
                    if (!file.exists())
                        file.createNewFile();
                    boolean ret = save(bitmap, file, Bitmap.CompressFormat.JPEG, true);
                    if (ret) {
                        Toast.makeText(getApplicationContext(), "截图已保持至 " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        ((ImageView)shareDialog.findViewById(R.id.link_pic_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy_link();
                Toast.makeText(VodPlayerActivity.this,"已复制链接至剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)shareDialog.findViewById(R.id.friend_pic_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToShare(SendMessageToWX.Req.WXSceneSession);
            }
        });
        ((ImageView)shareDialog.findViewById(R.id.webchat_pic_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToShare(SendMessageToWX.Req.WXSceneTimeline);
            }
        });
        TextView tvLiveRoomTitle = shareDialog.findViewById(R.id.tv_live_room_title);
        tvLiveRoomTitle.setText(mRoomDetailInfo.getBroadcastTitle());
        ImageView ivAuthorQrcode = shareDialog.findViewById(R.id.iv_author_qrcode);
        produceQrBitmap(mRoomDetailInfo.getShare(),ivAuthorQrcode);
        TextView tvLiveRoomTime = shareDialog.findViewById(R.id.tv_live_room_time);
        String date = DateUtils.getDateToStringYYYMMDDHHMM(mRoomDetailInfo.getBroadcastDate());
        tvLiveRoomTime.setText(date);
    }

    /**
     * 生成二维码
     */
    void produceQrBitmap(String qrUrl,ImageView ivAuthorQuCode){
        Bitmap bitmap = NetWorkUtils.getHttpBitmap(qrUrl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = QRCodeUtil.getImageBitmap(bitmap, qrUrl, 360);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivAuthorQuCode.setImageBitmap(bm);
                    }
                });
            }
        }).start();
    }

    void copy_link(){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        //ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
        ClipData mClipData = ClipData.newRawUri(mRoomDetailInfo.getBroadcastTitle(), Uri.parse(mRoomDetailInfo.getPullUrl()));
        cm.setPrimaryClip(mClipData);
    }

    /**
     * 保存图片到文件File。
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true 成功 false 失败
     */
    public static boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (isEmptyBitmap(src))
            return false;

        OutputStream os;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled())
                src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏（Status Bar）。
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = (int) getDeviceDisplaySize(activity)[0];
        int height = (int) getDeviceDisplaySize(activity)[1];

        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();

        return ret;
    }

    public static float[] getDeviceDisplaySize(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        float[] size = new float[2];
        size[0] = width;
        size[1] = height;

        return size;
    }

    /**
     * 分享到微信
     *
     * @param type            SendMessageToWX.Req.WXSceneSession  //会话    SendMessageToWX.Req.WXSceneTimeline //朋友圈
     */
    public void ToShare(int type) {
        //i   0是会话  1是朋友圈
        if (isWeixinAvilible(this)) {
            Bitmap bitmap = screenShot(this);
            WechatShareManager.ShareContentPicture mShareContent =
                    (WechatShareManager.ShareContentPicture) mShareManager.getShareContentPicture(R.mipmap.logo, bitmap);

            mShareManager.shareByWebchat(mShareContent, type);
        } else {
            Toast.makeText(this, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }

        return height;
    }
    /**
     * Bitmap对象是否为空。
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }



    void goFullscreen(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        FrameLayout.LayoutParams fullparams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mPlayerView.setLayoutParams(fullparams);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);

    }
    void goMinimalscreen(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams fullparams = new FrameLayout.LayoutParams(videowidth,videoheight);
        mPlayerView.setLayoutParams(fullparams);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);


        //live_rl.bringToFront();
    }

    @Override
    public void onBackPressed() {
        stopPlayRtmp();
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.player_menu, menu);
    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    public static void scroll2Bottom(final ScrollView scroll, final View inner) {
        if (scroll == null || inner == null) {
            return;
        }
        int offset = inner.getMeasuredHeight() - scroll.getMeasuredHeight();
        if (offset < 0) {
            offset = 0;
        }
        scroll.scrollTo(0, offset);
    }


    public void setContentView() {
        mRootView = (LinearLayout) findViewById(R.id.root);
        if (mLivePlayer == null){
            mLivePlayer = new TXVodPlayer(this);
        }
        mPhoneListener = new TXPhoneStateListener(this, mLivePlayer);
        mPhoneListener.startListen();
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        mPlayerView.disableLog(true);
        mLoadingView = (ImageView) findViewById(R.id.loadingImageView);
        mVideoPlay = false;
        mBtnPlay = (Button) findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click playbtn isplay:" + mVideoPlay+" ispause:"+mVideoPause);
                if (mVideoPlay) {
                    if (!mLivePlayer.isPlaying()) {
                        mLivePlayer.resume();
                        mBtnPlay.setBackgroundResource(R.drawable.play_pause);
                        //mRootView.setBackgroundColor(0xff000000);
                    } else {
                        mLivePlayer.pause();
                        mBtnPlay.setBackgroundResource(R.drawable.play_start);
                    }
                    mVideoPause = !mVideoPause;
                } else {
                    mVideoPlay = startPlayRtmp();
                }
            }
        });
        //停止按钮
        /*mBtnStop = (Button) findViewById(R.id.btnStop);
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayRtmp();
                mVideoPlay = false;
                mVideoPause = false;
                if (mTextStart != null) {
                    mTextStart.setText("00:00");
                }
                if (mSeekBar != null) {
                    mSeekBar.setProgress(0);
                    mSeekBar.setSecondaryProgress(0);
                }
            }
        });*/
        //横屏|竖屏
        mBtnRenderRotation = (Button) findViewById(R.id.btnOrientation);
        mBtnRenderRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLivePlayer == null) {
                    return;
                }

                if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_PORTRAIT) {
                    mBtnRenderRotation.setBackgroundResource(R.drawable.portrait);
                    mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
                } else if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {
                    mBtnRenderRotation.setBackgroundResource(R.drawable.landscape);
                    mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
                }

                mLivePlayer.setRenderRotation(mCurrentRenderRotation);
            }
        });
        //平铺模式
        mBtnRenderMode = (Button) findViewById(R.id.btnRenderMode);
        mBtnRenderMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLivePlayer == null) {
                    return;
                }

                if (mCurrentRenderMode == TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN) {
                    mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
                    mBtnRenderMode.setBackgroundResource(R.drawable.fill_mode);
                    mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
                } else if (mCurrentRenderMode == TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION) {
                    mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                    mBtnRenderMode.setBackgroundResource(R.drawable.adjust_mode);
                    mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
                }
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                mTextStart.setText(String.format("%02d:%02d",progress/1000/60, progress/1000%60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ( mLivePlayer != null) {
                    mLivePlayer.seek(seekBar.getProgress()/1000.f);
                }
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });
        mTextDuration = (TextView) findViewById(R.id.duration);
        mTextStart = (TextView)findViewById(R.id.play_start);
        mTextDuration.setTextColor(Color.rgb(255, 255, 255));
        mTextStart.setTextColor(Color.rgb(255, 255, 255));
    }
    /**
     * 获取内置SD卡路径
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }
        mPlayConfig = null;
        Log.d(TAG,"vrender onDestroy");
        mPhoneListener.stopListen();
    }
    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop(){
        super.onStop();

        if (mLivePlayer != null) {
            mLivePlayer.pause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if (mVideoPlay && !mVideoPause) {
            if (mLivePlayer != null) {
                mLivePlayer.resume();
            }
        }
    }

    private boolean startPlayRtmp() {
        if (TextUtils.isEmpty(mPlayUrl)) {
            return false;
        }
        mBtnPlay.setBackgroundResource(R.drawable.play_pause);
        //mRootView.setBackgroundColor(0xff000000);
        //        测试自定义Surface，自定义Surface需要用户按一次开始播放才能真正开始
//        SurfaceView sfv = (SurfaceView)findViewById(R.id.testSurfaceView);
//        Surface sf = sfv.getHolder().getSurface();
//        mLivePlayer.setSurface(sf);
//        sfv.setVisibility(View.VISIBLE);

        mLivePlayer.setPlayerView(mPlayerView);

        mLivePlayer.setPlayListener(this);
        //        mLivePlayer.setRate(1.5f);
        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        mLivePlayer.enableHardwareDecode(mHWDecode);
        mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        mLivePlayer.setRenderMode(mCurrentRenderMode);
        mEnableCache = true;
        if (mEnableCache) {
            mPlayConfig.setCacheFolderPath(getInnerSDCardPath()+"/txcache");
            mPlayConfig.setMaxCacheItems(3);
        } else {
            mPlayConfig.setCacheFolderPath(null);
        }
        //        mPlayConfig.setPlayerType(TXVodPlayer.PLAYER_TYPE_EXO);
        Map<String, String> header = new HashMap<>();
//        header.put("Referer", "http://demo.vod.qcloud.com/encryption/index.html");
//        header.put("Cookie", "UM_distinctid=15beZDk7GR4RQ");
        mPlayConfig.setHeaders(header);
        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.setAutoPlay(true);
        int result = mLivePlayer.startPlay(mPlayUrl); // result返回值：0 success;  -1 empty url;
        if (result != 0) {
            mBtnPlay.setBackgroundResource(R.drawable.play_start);
            //mRootView.setBackgroundResource(R.drawable.main_bkg);
            return false;
        }
        startLoadingAnimation();
        mStartPlayTS = System.currentTimeMillis();

        findViewById(R.id.playerHeaderView).setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams mlayoutparams = (FrameLayout.LayoutParams)mPlayerView.getLayoutParams();
        videowidth = mlayoutparams.width;
        videoheight = mlayoutparams.height;
        return true;
    }

    private  void stopPlayRtmp() {
        mBtnPlay.setBackgroundResource(R.drawable.play_start);
        //mRootView.setBackgroundResource(R.drawable.main_bkg);
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mVideoPause = false;
        mVideoPlay = false;
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
            Log.d("AutoMonitor", "PlayFirstRender,cost=" +(System.currentTimeMillis()-mStartPlayTS));
            if (mPhoneListener.isInBackground()) {
                mLivePlayer.pause();
            }
        }else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS ) {
            if (mStartSeek) {
                return;
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
            int playable = param.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);
            long curTS = System.currentTimeMillis();
            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                return;
            }
            mTrackingTouchTS = curTS;
            if (mSeekBar != null) {
                mSeekBar.setProgress(progress);
                mSeekBar.setSecondaryProgress(playable);
                Log.d(TAG, "progress "+progress+"secondary progress "+playable);
            }
            if (mTextStart != null) {
                mTextStart.setText(String.format("%02d:%02d",progress/1000/60,progress/1000%60));
            }
            if (mTextDuration != null) {
                mTextDuration.setText(String.format("%02d:%02d",duration/1000/60,duration/1000%60));
            }
            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END || event == TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND) {
            stopPlayRtmp();
            mVideoPlay = false;
            mVideoPause = false;
            if (mTextStart != null) {
                mTextStart.setText("00:00");
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING){
            startLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            stopLoadingAnimation();
            findViewById(R.id.playerHeaderView).setVisibility(View.GONE);
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
        }

        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        if (event < 0) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }

        else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    //公用打印辅助函数
    protected String getNetStatusString(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-14s %-14s %-12s %-12s\n%-14s",
                "CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps",
                "JIT:"+status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps",
                //"QUE:"+status.getInt(TXLiveConstants.NET_STATUS_CODEC_CACHE)+"|"+status.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE),
                //"DRP:"+status.getInt(TXLiveConstants.NET_STATUS_CODEC_DROP_CNT)+"|"+status.getInt(TXLiveConstants.NET_STATUS_DROP_SIZE),
                "VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps",
                "SVR:"+status.getString(TXLiveConstants.NET_STATUS_SERVER_IP));
                //"AVRA:"+status.getInt(TXLiveConstants.NET_STATUS_SET_VIDEO_BITRATE),
                //"PLA:"+status.getInt(TXLiveConstants.NET_STATUS_PLAYABLE_DURATION));
        return str;
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        Log.d(TAG, "Current status, CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)+
                ", RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)+
                ", SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps"+
                ", FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS)+
                ", ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps"+
                ", VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps");
    }

    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable)mLoadingView.getDrawable()).start();
        }
    }

    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable)mLoadingView.getDrawable()).stop();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 100 || data ==null || data.getExtras() == null || TextUtils.isEmpty(data.getExtras().getString("result"))) {
            return;
        }
        String result = data.getExtras().getString("result");
    }

    class TXPhoneStateListener extends PhoneStateListener implements Application.ActivityLifecycleCallbacks {
        WeakReference<TXVodPlayer> mPlayer;
        Context mContext;
        int activityCount;

        public TXPhoneStateListener(Context context, TXVodPlayer player) {
            mPlayer = new WeakReference<>(player);
            mContext = context.getApplicationContext();
        }

        public void startListen() {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(this, PhoneStateListener.LISTEN_CALL_STATE);

            mApp.getApplication().registerActivityLifecycleCallbacks(this);
        }

        public void stopListen() {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(this, PhoneStateListener.LISTEN_NONE);

            mApp.getApplication().unregisterActivityLifecycleCallbacks(this);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXVodPlayer player = mPlayer.get();
            switch(state){
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (player != null) player.pause();
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (player != null) player.pause();
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (player != null && activityCount >= 0) player.resume();
                    break;
            }
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            activityCount++;
            Log.d(TAG, "onActivityResumed"+activityCount);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCount--;
            Log.d(TAG, "onActivityStopped"+activityCount);
        }

        boolean isInBackground() {
            return (activityCount < 0);
        }
    }
    private TXPhoneStateListener mPhoneListener = null;
}
