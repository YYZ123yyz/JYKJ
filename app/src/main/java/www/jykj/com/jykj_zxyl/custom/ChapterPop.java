package www.jykj.com.jykj_zxyl.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import www.jykj.com.jykj_zxyl.R;


public class ChapterPop extends PopupWindow implements View.OnClickListener {


    private final Activity mContext;
    private View mPopView;
    private String mMoney;
    private TextView priceTv;
    private RelativeLayout balanceLayout;
    private RelativeLayout weichatLayout;
    private RelativeLayout aliLayout;
    private ImageView balanceIv;
    private ImageView weichatIv;
    private ImageView aliIv;
    private ArrayList<ImageView> imageViews;
    private ImageView ivAccept;

    public ChapterPop(Activity context) {
        super(context);
        mContext = context;
        init(context);
        setPopupWindow(context);


    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_window_chat_pay, null);
        initView();

    }

    private void initData() {
        priceTv.setText(mMoney);
    }

    private void initView() {
        priceTv = mPopView.findViewById(R.id.price_tv);

        balanceLayout = mPopView.findViewById(R.id.balace_layout);
        weichatLayout = mPopView.findViewById(R.id.weichat_layout);
        aliLayout = mPopView.findViewById(R.id.ali_layout);
        balanceLayout.setOnClickListener(this);
        weichatLayout.setOnClickListener(this);
        aliLayout.setOnClickListener(this);

        balanceIv = mPopView.findViewById(R.id.iv_balance);
        weichatIv = mPopView.findViewById(R.id.iv_weichat_choose);
        aliIv = mPopView.findViewById(R.id.iv_ali_choose);

        imageViews = new ArrayList<>();
        imageViews.add(balanceIv);
        imageViews.add(weichatIv);
        imageViews.add(aliIv);

        ivAccept = mPopView.findViewById(R.id.iv_accept);
        ivAccept.setOnClickListener(this);
        mPopView.setOnClickListener(this);
        mPopView.findViewById(R.id.go2pay_tv).setOnClickListener(this);
        mPopView.findViewById(R.id.service_tv).setOnClickListener(this);
        mPopView.findViewById(R.id.iv_close).setOnClickListener(this);
    }


    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow(Activity activity) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });

    }

    public void showPop(View view) {
        initData();
        showHideIv(0);
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mContext.getWindow().setAttributes(lp);
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balace_layout:
                showHideIv(0);
                break;
            case R.id.weichat_layout:
                showHideIv(1);
                break;
            case R.id.ali_layout:
                showHideIv(2);
                break;
            case R.id.iv_accept:

                ivAccept.setSelected(!ivAccept.isSelected());
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.go2pay_tv:
                for (int i = 0; i < imageViews.size(); i++) {
                    if (imageViews.get(i).getVisibility()==View.VISIBLE){
                        mListen.go2Pay(i);
                    }
                }
                break;
            case R.id.service_tv:

                break;
        }

    }

    private void showHideIv(int posit) {
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setVisibility(posit == i ? View.VISIBLE : View.GONE);
        }

    }

    public void setPayMoney(String money) {
        mMoney = money;
    }
    private go2PayListen mListen ;
    public void setGo2PayListen(go2PayListen listen){
        this.mListen = listen;
    }
    public interface go2PayListen{
        void go2Pay(int type);
    }

}
