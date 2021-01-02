package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawTypeAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.MoneyDialog;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.PasswordEditText;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.WithdrawTypePop;

public class StatisticsDialog extends PopupWindow implements View.OnClickListener {


    private final Activity mContext;
    private View mPopView;


    private RecyclerView mRecycleView;
    private WithdrawTypeAdapter typeAdapter;
    private ArrayList<WithdrawTypelListBean> mDatas;


    public StatisticsDialog(Activity context) {
        super(context);
        mContext = context;
        init(context);
        setPopupWindow(context);
    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.myreport_layout_s, null);
        initView();

    }

    private void initData() {

    }

    private void initView() {

        mPopView.findViewById(R.id.disease_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDevChoose.onDiseaseChoose();
            }
        });
        mPopView.findViewById(R.id.department_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDevChoose.onDevChoose();
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
        this.setFocusable(true);// 设置弹出窗口可
//        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setBackgroundDrawable(null);
        this.setOutsideTouchable(true);
    }

    public void showPop(View view) {
        initData();
        this.showAsDropDown(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }
    public void setData(List<WithdrawTypelListBean> datas) {
        if (mDatas.size() >0){
            mDatas.clear();
        }
        this.mDatas.addAll(datas) ;
//        typeAdapter.addData(datas);
    }



    private onDevChoose myDevChoose;

    public void setOnDevChoose(onDevChoose listen) {
        this.myDevChoose = listen;
    }

    public interface onDevChoose {
        void onDevChoose();

        void onDiseaseChoose();
    }

}