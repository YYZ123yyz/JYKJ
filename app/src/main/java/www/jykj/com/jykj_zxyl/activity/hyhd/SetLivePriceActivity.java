package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.liveroom.PreLiveInfo;
import entity.liveroom.SetPriceBean;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.StrUtils;

public class SetLivePriceActivity extends AppCompatActivity {
    private JYKJApplication mApp;
    private Context mContext;
    private SetLivePriceActivity mActivity;
    private PreLiveInfo setLiveInfo;
    EditText ed_price_value;
    TextView tv_commit;
    public ProgressDialog mDialogProgress = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (JYKJApplication)getApplication();
        mContext = SetLivePriceActivity.this;
        mActivity = SetLivePriceActivity.this;
        setLiveInfo = (PreLiveInfo)getIntent().getSerializableExtra("set_live_info");
        setContentView(R.layout.activity_set_live_price);
        ed_price_value = findViewById(R.id.ed_price_value);
        tv_commit = findViewById(R.id.tv_commit);
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        tv_commit.setOnClickListener(new ButtonClick());
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    mActivity.finish();
                    break;
                case R.id.tv_commit:
                    setPrice();
                    break;
            }
        }
    }

    void setPrice(){
        String edprice = StrUtils.defaulObjToStr(ed_price_value.getText());
        if(edprice.length()==0){
            Toast.makeText(mContext,"请输入价格",Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("设置价格","数据提交中，请稍后...");
        SetPriceBean setbean = new SetPriceBean();
        setbean.setDetailsCode(setLiveInfo.getDetailsCode());
        setbean.setExtendBroadcastPrice(Float.parseFloat(edprice));
        setbean.setLoginUserPosition(mApp.loginDoctorPosition);
        setbean.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        setbean.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        setbean.setRequestClientType("1");
        SetPriceTask setPriceTask = new SetPriceTask(setbean);
        setPriceTask.execute();
    }

    class SetPriceTask extends AsyncTask<Void,Void,Boolean> {
        SetPriceBean setbean;
        String repmsg = "";
        SetPriceTask(SetPriceBean setbean){
            this.setbean = setbean;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                String reqjson = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(setbean),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/extendBroadcastPrice");
                NetRetEntity retEntity = JSON.parseObject(reqjson,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    return true;
                }
                repmsg = retEntity.getResMsg();
            }catch (Exception ex){
                ex.printStackTrace();
                repmsg = "系统异常";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            cacerProgress();
            if(aBoolean){
               mActivity.finish();
            }else {
                Toast.makeText(mContext,repmsg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
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
