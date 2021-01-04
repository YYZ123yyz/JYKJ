package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawTypeAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class MyReportDialog extends PopupWindow implements View.OnClickListener {


    private final Activity mContext;
    private View mPopView;


    private RecyclerView mRecycleView;
    private WithdrawTypeAdapter typeAdapter;
    private ArrayList<WithdrawTypelListBean> mDatas;


    public MyReportDialog(Activity context) {
        super(context);
        mContext = context;
        init(context);
        setPopupWindow(context);
    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.myreport_layout, null);
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
        mPopView.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDevChoose.onCommit();
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

        void onCommit();
    }

}