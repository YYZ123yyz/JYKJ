package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.barnettwong.dragfloatactionbuttonlibrary.view.DragFloatActionButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import entity.conditions.QueryRoomDetailCond;
import entity.liveroom.FocusBean;
import entity.liveroom.ProvideLiveBroadcastDetails;
import entity.liveroom.RoomDetailInfo;
import entity.liveroom.SubFocusResp;
import netService.HttpNetService;
import wechatShare.WechatShareManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.ShareDialog;
import www.jykj.com.jykj_zxyl.util.CircleImageView;
import www.jykj.com.jykj_zxyl.util.StrUtils;

import java.io.*;
import java.util.List;

public class LectureDetailActivity extends AppCompatActivity {
    JYKJApplication mApp;
    Activity mActivity;
    Context mContext;
    ImageView liveroom_det_head_pic;
    TextView doctor_head_tit;
    TextView live_doctor_name;
    TextView live_doctor_education;
    TextView live_doctor_dep;
    RelativeLayout liveroom_share_holder;
    TextView det_live_tit;
    TextView det_room_key;
    TextView det_room_type;
    TextView det_room_watchnum;
    TextView det_live_time;
    TextView go_liveroom_btn;
    DragFloatActionButton room_det_live;
    private String detailCode;
    public ProgressDialog mDialogProgress = null;
    RoomDetailInfo mRoomDetailInfo = null;
    ProvideLiveBroadcastDetails mSubliveDetail = null;
    ImageView iv_live_room_share;
    ShareDialog shareDialog = null;
    String shareurl = "http://jiuyihtn.com/AppAssembly/sharePosters.html";
    WechatShareManager mShareManager;
    boolean isfocus = false;
    ImageView liveroom_store_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailCode = StrUtils.defaulObjToStr(getIntent().getStringExtra("detailCode"));
        mSubliveDetail = (ProvideLiveBroadcastDetails)getIntent().getSerializableExtra("sub_room_info");
        mApp = (JYKJApplication)getApplication();
        mActivity = LectureDetailActivity.this;
        mContext = LectureDetailActivity.this;
        mShareManager = WechatShareManager.getInstance(mContext);
        setContentView(R.layout.activity_liveroom_lecture_detail);
        initview();
        loadData();
    }

    void initview(){
        liveroom_det_head_pic = findViewById(R.id.liveroom_det_head_pic);
        doctor_head_tit = findViewById(R.id.doctor_head_tit);
        live_doctor_name = findViewById(R.id.live_doctor_name);
        live_doctor_education = findViewById(R.id.live_doctor_education);
        live_doctor_dep = findViewById(R.id.live_doctor_dep);
        liveroom_share_holder = findViewById(R.id.liveroom_share_holder);
        det_live_tit = findViewById(R.id.det_live_tit);
        det_room_key = findViewById(R.id.det_room_key);
        det_room_type = findViewById(R.id.det_room_type);
        det_room_watchnum = findViewById(R.id.det_room_watchnum);
        det_live_time = findViewById(R.id.det_live_time);
        go_liveroom_btn = findViewById(R.id.go_liveroom_btn);
        room_det_live = findViewById(R.id.room_det_live);
        iv_live_room_share = findViewById(R.id.iv_live_room_share);
        liveroom_store_btn = findViewById(R.id.liveroom_store_btn);
        iv_live_room_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "北京鹫一科技发展有限公司,详情请查看"+shareurl);
                mContext.startActivity(Intent.createChooser(textIntent, "分享"));*/
                share_dialog();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        liveroom_share_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFocus();
            }
        });
        go_liveroom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vodint = new Intent(mActivity,VodPlayerActivity.class);
                vodint.putExtra("playurl",mSubliveDetail.getPlayUrl());
                vodint.putExtra("TITLE",mSubliveDetail.getBroadcastTitle());
                mActivity.startActivity(vodint);
            }
        });
    }

    void doFocus(){
        getProgressBar("提交数据","数据提交中，请稍后");
        FocusBean subbean = new FocusBean();
        subbean.setDetailsCode(mRoomDetailInfo.getDetailsCode());
        subbean.setLoginUserPosition(mApp.loginDoctorPosition);
        subbean.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        subbean.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        subbean.setRequestClientType("1");
        SubFocusTask subFocusTask = new SubFocusTask(subbean);
        subFocusTask.execute();
    }

    class SubFocusTask extends AsyncTask<Void,Void,Boolean> {
        FocusBean subean;
        String repmsg = "";
        SubFocusResp subresp = null;
        SubFocusTask(FocusBean subean){
            this.subean = subean;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                String suburl = "https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/extendBroadcastFollowNum";
                if(1==mRoomDetailInfo.getFlagLikes()){
                    suburl = "https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/Numberofprecastviewerscancelled";
                }
                String repjson = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subean),suburl);
                NetRetEntity retEntity = JSON.parseObject(repjson,NetRetEntity.class);
                repmsg = retEntity.getResMsg();
                if(1==retEntity.getResCode()){
                    subresp = JSON.parseObject(StrUtils.defaulObjToStr(retEntity.getResJsonData()),SubFocusResp.class);
                    return true;
                }
            }catch (Exception ex){
                repmsg = "系统异常";
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            cacerProgress();
            Toast.makeText(mContext,repmsg,Toast.LENGTH_SHORT).show();
            if(aBoolean){
                //loadData();
            }
        }
    }

    void share_dialog(){
        if(null==mRoomDetailInfo){
            Toast.makeText(mContext,"分享信息为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(null==shareDialog){
            shareDialog = new ShareDialog(mActivity);
        }
        shareDialog.show();
        Glide.with(mContext).load(mRoomDetailInfo.getBroadcastUserLogoUrl())
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
                Bitmap bitmap = screenShot(mActivity);
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
                Toast.makeText(mContext,"已复制链接至剪贴板",Toast.LENGTH_SHORT).show();
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
        if (isWeixinAvilible(mContext)) {
            Bitmap bitmap = screenShot(mActivity);
            WechatShareManager.ShareContentPicture mShareContent =
                    (WechatShareManager.ShareContentPicture) mShareManager.getShareContentPicture(R.mipmap.logo, bitmap);

            mShareManager.shareByWebchat(mShareContent, type);
        } else {
            Toast.makeText(mContext, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
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



    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(null!=mRoomDetailInfo && mRoomDetailInfo.getUserCode().equals(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode())){
                Intent parint = new Intent(mActivity, LivePublisherThreeActivity.class);
                parint.putExtra("pushUrl",mRoomDetailInfo.getPullUrl());
                parint.putExtra("chatRoomName",mRoomDetailInfo.getChatRoomCode());
                parint.putExtra("chatId",mRoomDetailInfo.getChatRoomCode());
                parint.putExtra("liveTitle",mRoomDetailInfo.getTitleMainShow());
                parint.putExtra("detailCode",mRoomDetailInfo.getDetailsCode());
                parint.putExtra("live_type", LivePublisherThreeActivity.LIVE_TYPE_HOTLIVE);
                LectureDetailActivity.this.startActivity(parint);
            }else{
                Intent theintent = new Intent(mActivity, LivePlayerTwoActivity.class);
                theintent.putExtra("chatId",mRoomDetailInfo.getChatRoomCode());
                theintent.putExtra("pullUrl",mRoomDetailInfo.getPullUrl());
                theintent.putExtra("detailCode",mRoomDetailInfo.getDetailsCode());
                theintent.putExtra("PLAY_TYPE", LivePlayerTwoActivity.ACTIVITY_TYPE_LIVE_PLAY);
                mActivity.startActivity(theintent);
            }
        }
    }

    void loadData(){
        getProgressBar("加载数据","加载数据中心，请稍后...");
        QueryRoomDetailCond queryCond = new QueryRoomDetailCond();
        queryCond.setDetailsCode(detailCode);
        queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
        queryCond.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        queryCond.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        queryCond.setRequestClientType("1");
        LoadDataTask loadDataTask = new LoadDataTask(queryCond);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void, RoomDetailInfo>{
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
            if(null!=roomDetailInfo){
                mRoomDetailInfo = roomDetailInfo;
                if(StrUtils.defaulObjToStr(roomDetailInfo.getBroadcastUserLogoUrl()).length()>0){
                    Glide.with(mContext).load(roomDetailInfo.getBroadcastUserLogoUrl()).into(liveroom_det_head_pic);
                }
                doctor_head_tit.setText(StrUtils.defaulObjToStr(roomDetailInfo.getBroadcastUserTitleName()));
                live_doctor_name.setText(StrUtils.defaulObjToStr(roomDetailInfo.getBroadcastUserName()));
                live_doctor_education.setText(StrUtils.defaulObjToStr(roomDetailInfo.getTitleMainShow()));
                live_doctor_dep.setText(StrUtils.defaulObjToStr(roomDetailInfo.getTitleDetailShow()));
                det_live_tit.setText(StrUtils.defaulObjToStr(roomDetailInfo.getBroadcastTitle()));
                det_room_key.setText(StrUtils.defaulObjToStr(roomDetailInfo.getAttrName()));
                det_room_type.setText(StrUtils.defaulObjToStr(roomDetailInfo.getClassName()));
                det_live_time.setText("");
                if(0==mRoomDetailInfo.getFlagLikes()) {
                    liveroom_store_btn.setImageResource(R.mipmap.store);
                }else{
                    liveroom_store_btn.setImageResource(R.mipmap.store_cancel);
                }
                if(null!=roomDetailInfo.getExtendBroadcastFollowNum()){
                    det_room_watchnum.setText(String.valueOf(roomDetailInfo.getExtendBroadcastFollowNum().intValue())+"人想看");
                }
            }
            cacerProgress();
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
