package www.jykj.com.jykj_zxyl.wxapi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;
    private JYKJApplication mApp;
    private BaseToolBar mToolBar;//顶部toolBar
    private String type;
    private String paramAppid;
    private String paramProductid;
    public static final String PAY_STATE = "PAY_STATE";
    private int code;
    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;
    public static final int CANCEL = -2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wxpay_result_activity);
        LogUtils.e("走我了 ");
        mApp = (JYKJApplication) getApplication();

       mToolBar =(BaseToolBar)findViewById(R.id.toolbar);
        setToolBar();
        Intent intent = getIntent();
        paramAppid = intent.getStringExtra("pay_appid");
        paramAppid = (null == paramAppid || paramAppid.length() == 0) ? "wxaf6f64f6a5878261" : paramAppid;
        paramProductid = intent.getStringExtra("pay_productid");
        if (intent.hasExtra(PAY_STATE)) {
            code = intent.getIntExtra(PAY_STATE, 0);
        }


        if (code == 0) {

            iwxapi = WXAPIFactory.createWXAPI(this, paramAppid);
            iwxapi.registerApp(paramAppid);
            Intent parintent = getIntent();
            iwxapi.handleIntent(parintent, this);
        } else {
            if (code == FAILURE) {
                showMessageError();
            } else if (code == SUCCESS) {
                showMessage("成功");
            } else {
                showMessage("未知错误");
            }
        }
    }
    public void showMessage(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).
                setTitle("支付结果").
                setMessage(message).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        alertDialog.show();
    }

    public void showMessageError() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).
                setTitle("支付结果").
                setMessage("当前支付失败").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        alertDialog.show();
    }

    private void setToolBar() {
        mToolBar.setMainTitle("支付结果");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
        //add
       /* mToolBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });*/
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if ( code == 0) {
            iwxapi.handleIntent(intent, this);
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.e("支付开始 "+baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.e("支付结果   "+baseResp.errCode);
    }
}
