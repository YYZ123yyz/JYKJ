package www.jykj.com.jykj_zxyl.activity.myself;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import entity.mySelf.DataCleanManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.LoginActivity;
import www.jykj.com.jykj_zxyl.activity.myself.setting.AboutActivity;
import www.jykj.com.jykj_zxyl.activity.myself.setting.OpeaPassWordActivity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import yyz_exploit.Utils.DestroyActivityUtil;
import yyz_exploit.activity.activity.FeedbackActivity;
import yyz_exploit.activity.activity.VersionActivity;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {

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
    private LinearLayout myselfBack;
    private String totalCacheSize;
    private MoreFeaturesPopupWindow mPopupWindow;
    private ImageButton ivAdd;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_myself_setting;
    }

    @Override
    protected void initView() {
        super.initView();
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

        ivAdd = findViewById(R.id.right_image_search);
        ivAdd.setOnClickListener(new ButtonClick());
        mOperPassWord = (LinearLayout) this.findViewById(R.id.li_activitySetting_operPassWord);
        mOperPassWord.setOnClickListener(new ButtonClick());


        //返回
        myselfBack = findViewById(R.id.ll_back);
        myselfBack.setOnClickListener(new ButtonClick());

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
            totalCacheSize = DataCleanManager.getTotalCacheSize(Objects.requireNonNull(SettingActivity.this));
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
                    mApp.cleanPersistence();
                    mApp.LoginOut(mActivity);
                    Intent intent_login = new Intent();
                    intent_login.setClass(SettingActivity.this,LoginActivity.class);
                    intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                    startActivity(intent_login);
                    finish();
                 break;
                case R.id.li_activitySetting_aboutLayout:
                    startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                    break;
                case R.id.li_activitySetting_operPassWord:
                    startActivity(new Intent(SettingActivity.this, OpeaPassWordActivity.class));
                    break;
                case R.id.ll_back:
                    finish();
                    break;
                    //清除缓存
                case R.id.myself_Vt:
                    showComplexDialog();
                    break;
                case  R.id.setting_opinion:
                    Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myself_Update:
                    Intent intent2 = new Intent(SettingActivity.this, VersionActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.right_image_search:
                    if (mPopupWindow ==null){
                        mPopupWindow = new MoreFeaturesPopupWindow(SettingActivity.this);
                    }
                    if (!mPopupWindow.isShowing()) {
                        mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                    }else {
                        mPopupWindow.dismiss();
                    }
                    break;
            }
        }
    }

    private void showComplexDialog() {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        //    设置Content来显示一个信息
        builder.setMessage("确定删除缓存吗？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DataCleanManager.clearAllCache(Objects.requireNonNull(SettingActivity.this));
                String   clearSize = null;
                try {
                    clearSize = DataCleanManager.getTotalCacheSize(Objects.requireNonNull(SettingActivity.this));
                    myself_cache.setText(clearSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(SettingActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
            }
        });

        //    显示出该对话框
        builder.show();

    }
    /**
     * 设置数据
     */
    private void setData() {

    }


}
