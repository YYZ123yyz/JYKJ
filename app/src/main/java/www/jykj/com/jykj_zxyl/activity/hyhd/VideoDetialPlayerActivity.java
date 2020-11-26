package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.meg7.widget.CircleImageView;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.contract.VideoDetialContract;
import www.jykj.com.jykj_zxyl.activity.hyhd.presenter.VideoDetialPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.VideoDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * Description:视频详情
 *
 * @author: qiuxinhai
 * @date: 2020-11-24 17:34
 */
public class VideoDetialPlayerActivity extends AbstractMvpBaseActivity<VideoDetialContract.View
        , VideoDetialPresenter>implements ITXLivePlayListener,VideoDetialContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.video_view)
    TXCloudVideoView mPlayerView;
    @BindView(R.id.btnPlay)
    Button mBtnPlay;
    @BindView(R.id.play_start)
    TextView mTextStart;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;
    @BindView(R.id.duration)
    TextView mTextDuration;
    @BindView(R.id.play_progress)
    LinearLayout playProgress;
    @BindView(R.id.liveroom_det_head_pic)
    CircleImageView liveroomDetHeadPic;
    @BindView(R.id.doctor_head_tit)
    TextView doctorHeadTit;
    @BindView(R.id.live_doctor_name)
    TextView liveDoctorName;
    @BindView(R.id.live_doctor_education)
    TextView liveDoctorEducation;
    @BindView(R.id.live_doctor_dep)
    TextView liveDoctorDep;
    @BindView(R.id.tv_specialze_content)
    TextView tvSpecialzeContent;
    @BindView(R.id.tv_video_theme_content)
    TextView tvVideoThemeContent;
    @BindView(R.id.loadingImageView)
    ImageView mLoadingView;
    @BindView(R.id.iv_miniaml_zoom_btn)
    ImageView iv_miniaml_zoom_btn;
    @BindView(R.id.iv_zoom_btn)
    ImageView iv_zoom_btn;
    @BindView(R.id.introduction_layout)
    LinearLayout introduction_layout;
    @BindView(R.id.tv_video_content)
    TextView mTvVideoContent;
    private int videowidth = 0;
    private int videoheight = 0;
    private TXVodPlayer mLivePlayer = null;
    private TXPhoneStateListener mPhoneListener = null;
    private TXVodPlayConfig mPlayConfig;
    private String courseWareCode;
    private boolean mHWDecode;
    private boolean mEnableCache;
    private int mCurrentRenderMode;
    private String mPlayUrl;
    private int mCurrentRenderRotation;
    private long mStartPlayTS = 0;
    private boolean mVideoPause;
    private boolean mStartSeek;
    private boolean mVideoPlay;
    private long mTrackingTouchTS = 0;
    JYKJApplication mApp;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_video_detial;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication)getApplication();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            courseWareCode=extras.getString("courseWareCode");
            mPlayUrl = extras.getString("playUrl");
        }
        mPlayConfig = new TXVodPlayConfig();
        if (mLivePlayer == null){
            mLivePlayer = new TXVodPlayer(this);
        }
        mPhoneListener = new TXPhoneStateListener(this, mLivePlayer);
        mPhoneListener.startListen();
        setToolBar();
        checkPublishPermission();
        mVideoPlay = startPlayRtmp();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendGetCourseWareDetailRequest(courseWareCode,this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("视频详情");
        //返回键
        toolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayRtmp();
                finish();
            }
        });
    }

    /**
     * 添加监听
     */
    private void addListener(){
        iv_zoom_btn.setOnClickListener(v -> {
            mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
            //mLivePlayer.resume();
            iv_miniaml_zoom_btn.setVisibility(View.VISIBLE);
            iv_zoom_btn.setVisibility(View.GONE);
            playProgress.setVisibility(View.GONE);
            introduction_layout.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            goFullscreen();
        });
        iv_miniaml_zoom_btn.setOnClickListener(v -> {
            mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            //mLivePlayer.resume();
            iv_miniaml_zoom_btn.setVisibility(View.GONE);
            iv_zoom_btn.setVisibility(View.VISIBLE);
            playProgress.setVisibility(View.VISIBLE);
            introduction_layout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            goMinimalscreen();
        });

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
        mBtnPlay.setOnClickListener(v -> {
            //Log.d(TAG, "click playbtn isplay:" + mVideoPlay+" ispause:"+mVideoPause);
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
        });

        mPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHide();
            }
        });
    }

    /**
     * 显示隐藏动画
     */
    protected void showOrHide() {
        if (playProgress.getVisibility() == View.VISIBLE) {
            hideAnim();
        } else {
            showAnim();
        }
    }

    /**
     * 隐藏动画
     */
    private void hideAnim(){
        Animation animation1 = AnimationUtils.loadAnimation(this
                , R.anim.option_leave_from_bottom);
        animation1.setAnimationListener(new AnimationImp() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                //隐藏锚点

                playProgress.setVisibility(View.GONE);

            }
        });
        playProgress.startAnimation(animation1);
    }

    /**
     * 展示动画
     */
    private void showAnim(){
        if (playProgress.getVisibility() == View.GONE) {
            playProgress.setVisibility(View.VISIBLE);
            playProgress.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.boxing_option_entry_from_top);
            playProgress.startAnimation(animation);
        }
    }

    @Override
    protected void onPause() {
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

    /**
     * 获取内置SD卡路径
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
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

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getCourseWareDetailResult(VideoDetialBean videoDetialBean) {
//        Glide.with(this)
//                .load(parinfo.getBroadcastCoverImgUrl()).into(viewHolder.pre_live_btn);
        liveDoctorName.setText(videoDetialBean.getDoctorName());
        liveDoctorEducation.setText(videoDetialBean.getDoctorTitleName());
        liveDoctorDep.setText(videoDetialBean.getDoctorSynopsis());
        tvSpecialzeContent.setText(String.format("擅长：%s",
                videoDetialBean.getDoctorGoodAtRealm()));
        tvVideoThemeContent.setText(videoDetialBean.getTitle());
        mTvVideoContent.setText(videoDetialBean.getRemark());

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
        }

        boolean isInBackground() {
            return (activityCount < 0);
        }
    }

    @Override
    protected void onDestroy() {
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
        mPhoneListener.stopListen();
    }

    @Override
    public void onBackPressed() {
        stopPlayRtmp();
        super.onBackPressed();
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


}
