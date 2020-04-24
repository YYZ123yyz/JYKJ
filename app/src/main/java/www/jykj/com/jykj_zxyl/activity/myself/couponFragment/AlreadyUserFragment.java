package www.jykj.com.jykj_zxyl.activity.myself.couponFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.MyCouponDetailEntity;
import entity.RedeemRecordEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.MyCouponDetailActivity;
import www.jykj.com.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.jykj.com.jykj_zxyl.adapter.CouponDetailRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.RedeemRecordRecycleAdapter;

public class AlreadyUserFragment extends Fragment {
    private                 MyCouponDetailActivity      mActivity;
    private                 Context                     mContext;
    private                 RecyclerView                mCouponDetailRecycleView;         //优惠券明细记录列表
    private                 LinearLayoutManager         layoutManager;
    private                 CouponDetailRecycleAdapter  mCouponDetailRecycleAdapter;       //适配器
    private                 List<MyCouponDetailEntity>    mMyCouponDetailEntities = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_alreadyuser,container,false);
        mActivity = (MyCouponDetailActivity) getActivity();
        mCouponDetailRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmentAlreadyUser_couponInfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mCouponDetailRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mCouponDetailRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mCouponDetailRecycleAdapter = new CouponDetailRecycleAdapter(mMyCouponDetailEntities,mContext,mActivity);
        mCouponDetailRecycleView.setAdapter(mCouponDetailRecycleAdapter);
        setData();
        return view;
    }


    private void setData() {
        for (int i = 0; i < 20; i++)
        {
            MyCouponDetailEntity myCouponDetailEntity = new MyCouponDetailEntity();
            myCouponDetailEntity.setState(0);
            mMyCouponDetailEntities.add(myCouponDetailEntity);
        }
        mCouponDetailRecycleAdapter.setDate(mMyCouponDetailEntities);
        mCouponDetailRecycleAdapter.notifyDataSetChanged();

    }

}
