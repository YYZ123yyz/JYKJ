package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.util.AppManager;
import www.jykj.com.jykj_zxyl.util.Constants;
import www.jykj.com.jykj_zxyl.util.DensityUtils;
import www.jykj.com.jykj_zxyl.util.FontSizeView;
import www.jykj.com.jykj_zxyl.util.IntentUtils;
import www.jykj.com.jykj_zxyl.util.SPUtils;


public class TextSizeActivity extends AppCompatActivity {

    private FontSizeView fsv_font_size;
    private ImageView back;
    private float fontSizeScale;
    private boolean isChange;//用于监听字体大小是否有改动
    private int defaultPos;
    private TextView tv_font_size,tv_font_size1,tv_font_size2,tv_font_size3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size);

        initView();
    }

    private void initView() {
        tv_font_size = findViewById(R.id.tv_font_size);
        tv_font_size1 = findViewById(R.id.tv_font_size1);
        tv_font_size2 = findViewById(R.id.tv_font_size2);
        tv_font_size3 = findViewById(R.id.tv_font_size3);
        fsv_font_size = findViewById(R.id.fsv_font_size);
        back = findViewById(R.id.back);

        //滑动返回监听
        fsv_font_size.setChangeCallbackListener(new FontSizeView.OnChangeCallbackListener() {
            @Override
            public void onChangeListener(int position) {
                int dimension = getResources().getDimensionPixelSize(R.dimen.sp_stander);
                //根据position 获取字体倍数
                fontSizeScale = (float) (0.875 + 0.125 * position);
                //放大后的sp单位
                double v = fontSizeScale * (int) DensityUtils.px2dip(TextSizeActivity.this, dimension);
                //改变当前页面大小
                changeTextSize((int) v);
                isChange = !(position==defaultPos);
            }
        });
        float  scale = (float) SPUtils.get(this, Constants.SP_FontScale, 0.0f);
        if (scale > 0.5) {
            defaultPos = (int) ((scale - 0.875) / 0.125);
        } else {
            defaultPos=1;
        }
        //注意： 写在改变监听下面 —— 否则初始字体不会改变
        fsv_font_size.setDefaultPosition(defaultPos);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChange){
                    SPUtils.put(TextSizeActivity.this,Constants.SP_FontScale,fontSizeScale);
                    //重启应用
               //     AppManager.getAppManager().finishAllActivity();
                    AppUtils.relaunchApp();
                    IntentUtils.toActivity(TextSizeActivity.this, MainActivity.class,true);
                }else{
                    finish();
                }
                finish();
            }
        });

    }
    /**
     * 改变textsize 大小
     */
    private void changeTextSize(int dimension) {
        tv_font_size.setTextSize(dimension);
        tv_font_size1.setTextSize(dimension);
        tv_font_size2.setTextSize(dimension);
        tv_font_size3.setTextSize(dimension);

    }


    /**
     * 重新配置缩放系数
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale= 1;//1 设置正常字体大小的倍数
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}