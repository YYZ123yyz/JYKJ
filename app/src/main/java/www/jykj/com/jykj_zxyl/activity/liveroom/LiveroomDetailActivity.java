package www.jykj.com.jykj_zxyl.activity.liveroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.barnettwong.dragfloatactionbuttonlibrary.view.DragFloatActionButton;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import entity.conditions.QueryRoomDetailCond;
import entity.liveroom.RoomDetailInfo;
import netService.HttpNetService;
import org.w3c.dom.Text;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.LivePublisherActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.NewLivePlayerActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.StrUtils;

public class LiveroomDetailActivity extends AppCompatActivity {
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
    Button go_liveroom_btn;
    DragFloatActionButton room_det_live;
    private String detailCode;
    public ProgressDialog mDialogProgress = null;
    RoomDetailInfo mRoomDetailInfo = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailCode = StrUtils.defaulObjToStr(getIntent().getStringExtra("detailCode"));
        mApp = (JYKJApplication)getApplication();
        mActivity = LiveroomDetailActivity.this;
        mContext = LiveroomDetailActivity.this;
        setContentView(R.layout.activity_liveroom_detail);
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
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(null!=mRoomDetailInfo && mRoomDetailInfo.getUserCode().equals(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode())){
                Intent parint = new Intent(mActivity, LivePublisherActivity.class);
                parint.putExtra("pushUrl",mRoomDetailInfo.getPullUrl());
                parint.putExtra("chatRoomName",mRoomDetailInfo.getChatRoomCode());
                parint.putExtra("chatId",mRoomDetailInfo.getChatRoomCode());
                parint.putExtra("liveTitle",mRoomDetailInfo.getTitleMainShow());
                parint.putExtra("detailCode",mRoomDetailInfo.getDetailsCode());
                parint.putExtra("live_type", LivePublisherActivity.LIVE_TYPE_HOTLIVE);
                LiveroomDetailActivity.this.startActivity(parint);
            }else{
                Intent theintent = new Intent(mActivity, NewLivePlayerActivity.class);
                theintent.putExtra("chatId",mRoomDetailInfo.getChatRoomCode());
                theintent.putExtra("pullUrl",mRoomDetailInfo.getPullUrl());
                theintent.putExtra("detailCode",mRoomDetailInfo.getDetailsCode());
                theintent.putExtra("PLAY_TYPE", NewLivePlayerActivity.ACTIVITY_TYPE_LIVE_PLAY);
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
