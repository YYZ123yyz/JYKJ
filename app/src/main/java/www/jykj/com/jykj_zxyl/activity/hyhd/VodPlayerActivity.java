package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.*;

import com.allen.library.interceptor.Transformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
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
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromAdapter;
import www.jykj.com.jykj_zxyl.fragment.liveroom.adapter.LiveProgromPicAdapter;
import www.jykj.com.jykj_zxyl.util.CircleImageView;
import www.jykj.com.jykj_zxyl.util.StringUtils;

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
    static JYKJApplication mApp;
    private TXVodPlayer mLivePlayer = null;
    private TXCloudVideoView mPlayerView;
    private ImageView mLoadingView;
    private boolean          mHWDecode   = false;
    private LinearLayout mRootView;
    private Button mBtnLog;
    private Button mBtnPlay;
    private Button           mBtnRenderRotation;
    private Button           mBtnRenderMode;
    private Button           mBtnHWDecode;
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
                goMinimalscreen();
            }
        });

        mVideoPlay = startPlayRtmp();
        sendGetLiveProgromRequest(detailsCode);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

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

    void goFullscreen(){
        FrameLayout.LayoutParams fullparams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mPlayerView.setLayoutParams(fullparams);
    }
    void goMinimalscreen(){
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
