package www.jykj.com.jykj_zxyl.capitalpool.weiget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawTypeAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class MoneyPop extends PopupWindow implements View.OnClickListener {


    private final Activity mContext;
    private View mPopView;
    private WithdrawCostBean mData;

    private RecyclerView mRecycleView;
    private WithdrawTypeAdapter typeAdapter;
    private ArrayList<WithdrawTypelListBean> mDatas;
    private TextView platformService;
    private TextView platformServiceCost;
    private TextView platformPersonTax;
    private TextView platformPersonTaxCost;
    private PasswordEditText passwordEditText;
    private TextView moneyTv;


    public MoneyPop(Activity context) {
        super(context);
        mContext = context;
        init(context);
        setPopupWindow(context);
    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_money, null);
        initView();

    }

    private void initData() {
        if (mData !=null){
            platformService.setText(mData.getPlatformService());
            platformServiceCost.setText(mData.getPlatformServeCost());
            platformPersonTax.setText(mData.getPlatformPersonTax());
            platformPersonTaxCost.setText(mData.getPlatformPersonTaxCost());
            moneyTv.setText(mData.getMoney());
        }
    }

    private void initView() {

        mPopView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                lp.alpha = 1f;
                mContext.getWindow().setAttributes(lp);
                dismiss();
            }
        });
        moneyTv = mPopView.findViewById(R.id.money_tv);
        platformService = mPopView.findViewById(R.id.platformService);
        platformServiceCost = mPopView.findViewById(R.id.platformServiceCost);
        platformPersonTax = mPopView.findViewById(R.id.platformPersonTax);
        platformPersonTaxCost = mPopView.findViewById(R.id.platformPersonTaxCost);
        passwordEditText = mPopView.findViewById(R.id.password_et);
        passwordEditText.setOnCompleteListener(new PasswordEditText.OnPasswordCompleteListener() {
            @Override
            public void onComplete(String password) {
                myDevChoose.onDevChoose(password);
            }
        });
    }


    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow(Activity activity) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(false);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(false);
    }

    public void showPop(View view) {
        initData();

        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mContext.getWindow().setAttributes(lp);
        this.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }
    public void setData(WithdrawCostBean data) {
        mData = data;
//        typeAdapter.addData(datas);
    }


    private onDevChoose myDevChoose;

    public void setOnDevChoose(onDevChoose listen) {
        this.myDevChoose = listen;
    }

    public interface onDevChoose {
        void onDevChoose(String password);
    }

}


