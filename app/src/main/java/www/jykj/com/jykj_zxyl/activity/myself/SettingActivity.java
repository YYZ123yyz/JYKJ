package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;
import java.util.Set;

import entity.mySelf.DataCleanManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.LoginActivity;
import www.jykj.com.jykj_zxyl.activity.myself.setting.AboutActivity;
import www.jykj.com.jykj_zxyl.activity.myself.setting.OpeaPassWordActivity;
import www.jykj.com.jykj_zxyl.activity.myself.setting.ServiceHotlineActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import yyz_exploit.activity.activity.FeedbackActivity;
import yyz_exploit.activity.activity.HelpActivity;
import yyz_exploit.activity.activity.OpinionActivity;
import yyz_exploit.activity.activity.VersionActivity;

/**
 * 设置
 */
public class SettingActivity extends AppCompatActivity {

    private Context mContext;
    private SettingActivity mActivity;
    private Button mExitButton;                    //退出当前账号
    private JYKJApplication mApp;
    private LinearLayout mAboutLayout;                   //关于我们
    private LinearLayout mServiceHolting;                //客服热线
    private LinearLayout mOperPassWord;                  //修改密码
    private ImageView myself_back;
    private LinearLayout myself_vt;
    private TextView myself_cache;
    private LinearLayout setting_opinion,myself_Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_setting);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }


    /**
     * 初始化布局
     */
    private void initLayout() {
        mExitButton = (Button) this.findViewById(R.id.bt_activityMySelfSetting_exitButton);
        mExitButton.setOnClickListener(new ButtonClick());

        mAboutLayout = (LinearLayout) this.findViewById(R.id.li_activitySetting_aboutLayout);
        mAboutLayout.setOnClickListener(new ButtonClick());

      //  mServiceHolting = (LinearLayout) this.findViewById(R.id.li_activitySetting_serviceHolting);
      //  mServiceHolting.setOnClickListener(new ButtonClick());

        mOperPassWord = (LinearLayout) this.findViewById(R.id.li_activitySetting_operPassWord);
        mOperPassWord.setOnClickListener(new ButtonClick());


        //返回
        myself_back = findViewById(R.id.myself_back);
        myself_back.setOnClickListener(new ButtonClick());

        //清除缓存
        myself_vt = findViewById(R.id.myself_Vt);
        myself_vt.setOnClickListener(new ButtonClick());

        //緩存
        myself_cache = findViewById(R.id.myself_Cache);
        myself_cache.setOnClickListener(new ButtonClick());
        clearData();

        //意见反馈
        setting_opinion = findViewById(R.id.setting_opinion);
        setting_opinion.setOnClickListener(new ButtonClick());

        //版本更新说明
        myself_Update=findViewById(R.id.myself_Update);
        myself_Update.setOnClickListener(new ButtonClick());
    }
    //获取系统缓存
    private void clearData() {

        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(Objects.requireNonNull(SettingActivity.this));
            myself_cache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_activityMySelfSetting_exitButton:
                    //清除缓存
                    mApp.cleanPersistence();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
//                    for (int i = 0; i < mApp.gActivityList.size(); i++) {
//                        mApp.gActivityList.get(i).finish();
//                    }
                    break;
                case R.id.li_activitySetting_aboutLayout:
                    startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                    break;
//                case R.id.li_activitySetting_serviceHolting:
//                    startActivity(new Intent(SettingActivity.this, ServiceHotlineActivity.class));
//                    break;
                case R.id.li_activitySetting_operPassWord:
                    startActivity(new Intent(SettingActivity.this, OpeaPassWordActivity.class));
                    break;
                case R.id.myself_back:
                    finish();
                    break;
                    //清除缓存
                case R.id.myself_Vt:
                    DataCleanManager.clearAllCache(Objects.requireNonNull(SettingActivity.this));
                    String   clearSize = null;
                    try {
                        clearSize = DataCleanManager.getTotalCacheSize(Objects.requireNonNull(SettingActivity.this));
                        myself_cache.setText(clearSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case  R.id.setting_opinion:
                    Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myself_Update:
                    Intent intent2 = new Intent(SettingActivity.this, VersionActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {

    }


}
