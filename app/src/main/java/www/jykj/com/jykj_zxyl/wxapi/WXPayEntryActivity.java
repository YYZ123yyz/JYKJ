package www.jykj.com.jykj_zxyl.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.mipush.sdk.Constants;

import www.jykj.com.jykj_zxyl.R;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {



//private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";



    private IWXAPI api;



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

// setContentView(R.layout.pay_result);


        api = WXAPIFactory.createWXAPI(this, "wxaf6f64f6a5878261");

        api.handleIntent(getIntent(), this);

    }



    @Override

    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        setIntent(intent);

        api.handleIntent(intent, this);

    }



    @Override

    public void onReq(BaseReq req) {



        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();



    }

//支付结果

    @Override

    public void onResp(BaseResp resp) {

        if (TextUtils.isEmpty(resp.openId)) {

            Log.i("xml", "啥也没有");

        }

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

// AlertDialog.Builder builder = new AlertDialog.Builder(this);

//builder.setTitle("微信支付提示");

            switch (resp.errCode) {

                case 0://支付成功h

                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();//builder.setMessage("支付成功.");


                    break;



                case -1://支付失败，一般是后端签名失败等问题

                    Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();

// builder.setMessage("支付失败，请清理微信缓存或重新安装.");

                    break;



                case -2://用户取消了支付



                    Toast.makeText(getApplicationContext(), "支付已取消.", Toast.LENGTH_SHORT).show();

//builder.setMessage("支付已取消.");

// Intent intent = new Intent(WXPayEntryActivity.this, TicketpurchaserecordActivity.class);

// startActivity(intent);

// finish();

                    break;

            }

// builder.show();

        }

        finish();

    }

}
