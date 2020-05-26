package www.jykj.com.jykj_zxyl.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 邀请同行
 */
public class InvitepeersActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvInvite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_peers);
        ActivityUtil.setStatusBarMain(InvitepeersActivity.this);

        initView();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvInvite = (TextView) findViewById(R.id.btn_invite);
        ivBack.setOnClickListener(this);
        tvInvite.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_invite:
                showPopupWindow();
                break;
        }

    }

    private void showPopupWindow(){
            View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_invite, null);
            PopupWindow mPopWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.setFocusable(true);
            mPopWindow.update();
            // 设置背景颜色变暗
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.7f;
            getWindow().setAttributes(lp);
            mPopWindow.showAtLocation(findViewById(R.id.btn_invite),
                    Gravity.BOTTOM, 0, 0);
            mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);
                }
            });
    }
}
