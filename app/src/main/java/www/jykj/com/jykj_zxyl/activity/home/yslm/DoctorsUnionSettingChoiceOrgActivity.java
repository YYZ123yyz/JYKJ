package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.unionInfo.ProvideUnionDoctorOrg;
import entity.unionInfo.ProvideViewUnionDoctorMemberDetailInfo;
import entity.unionInfo.UnionMemberSettingParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.SettingMemberUnionOrgDateAdapter;
import www.jykj.com.jykj_zxyl.adapter.UnionOrgDateAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 医生联盟 == > 设置 == 选择联盟层级
 */
public class DoctorsUnionSettingChoiceOrgActivity extends AppCompatActivity {

    private         Context         mContext;



    public          ProgressDialog mDialogProgress =null;

    private         DoctorUnionSettingOrgDateActivity mActivity;
    private         JYKJApplication mApp;
    private         String                      mNetRetStr;                 //返回字符串
    private         Handler mHandler;
    private         RecyclerView mOrgDateRecycleView;
    private         LinearLayoutManager layoutManager;
    private         SettingMemberUnionOrgDateAdapter mUnionOrgDateAdapter;       //适配器
    private         List<ProvideUnionDoctorOrg> mProvideUnionDoctorOrgs = new ArrayList<>();    // 获取到的所有层级数据
    private         List<ProvideUnionDoctorOrg> mShowProvideUnionDoctorOrgs = new ArrayList<>();    // 要显示的层级数据
    private         String                      mUnionCode;                 //联盟编码
    private         String                      mUnionName;                 //联盟名称
    private         TextView                    mNewOrgDate;                //新增联盟
    private         String                      mOrgNeme;                   //层级名称
    private         String                      mOrgPy;                     //拼音助记码
    private         ProvideUnionDoctorOrg       mUpOrg;                     //上级层级结构信息
    private         ProvideUnionDoctorOrg       mTopOrg;                     //顶级层级结构信息
    private         int                         mOperaType;                 //当前操作类型
    private         int                         mOperaIndex;                //当前操作下标
    private         TextView                    mUpOrgName;                 //上级层级结构名称
    private         UnionMemberSettingParment   mUnionMemberSettingParment; //
    private         LinearLayout                mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settingorgdate);
        mApp = (JYKJApplication) getApplication();
        mContext = this;
        mUnionCode = getIntent().getStringExtra("unionCode");
        mUnionName = getIntent().getStringExtra("unionName");
        mUnionMemberSettingParment = (UnionMemberSettingParment) getIntent().getSerializableExtra("choiceOrg");
        initLayout();
        initHandler();
        getOrgDate();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 1)
                        {
                            mProvideUnionDoctorOrgs = JSON.parseArray(retEntity.getResJsonData(),ProvideUnionDoctorOrg.class);
                            mShowProvideUnionDoctorOrgs.clear();
                            //找出顶级层级
                            for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                            {
                                if ((mProvideUnionDoctorOrgs.get(i).getUnionOrgId()+"").equals(mUnionMemberSettingParment.getUnionOrgId()))
                                    mProvideUnionDoctorOrgs.get(i).setChoice(true);
                                if (mProvideUnionDoctorOrgs.get(i).getUpOrgId() == -1)
                                {
                                    mTopOrg = mProvideUnionDoctorOrgs.get(i);
                                    mUpOrg = mTopOrg;
                                }
                            }
                            //找出第二级数据并显示
                            for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                            {
                                if (mProvideUnionDoctorOrgs.get(i).getUpOrgId() == mTopOrg.getUnionOrgId())
                                {
                                    mShowProvideUnionDoctorOrgs.add(mProvideUnionDoctorOrgs.get(i));
                                }
                            }
                            mUpOrgName.setText(mTopOrg.getOrgName());
                            mUnionOrgDateAdapter.setDate(mShowProvideUnionDoctorOrgs);
                            mUnionOrgDateAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 2:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 1)
                        {
                            switch (mOperaType)
                            {
                                case 1:
                                    getOrgDate();
                                    break;
                                case 2:
                                    //修改
                                    mShowProvideUnionDoctorOrgs.get(mOperaIndex).setOrgName(mOrgNeme);
                                    break;
                                case 3:
                                    mShowProvideUnionDoctorOrgs.remove(mShowProvideUnionDoctorOrgs.get(mOperaIndex));
                                    break;
                            }
                            mUnionOrgDateAdapter.setDate(mShowProvideUnionDoctorOrgs);
                            mUnionOrgDateAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.ll_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mOrgDateRecycleView = (RecyclerView) this.findViewById(R.id.unionOrgDate_list);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mOrgDateRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mOrgDateRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mUnionOrgDateAdapter = new SettingMemberUnionOrgDateAdapter(mContext,mShowProvideUnionDoctorOrgs);
        mOrgDateRecycleView.setAdapter(mUnionOrgDateAdapter);
        mUpOrgName = (TextView)this.findViewById(R.id.tv_upOrgName);
        mUpOrgName.setText(mUnionName);
        mUnionOrgDateAdapter.setOnNextItemClickListener(new SettingMemberUnionOrgDateAdapter.OnNextItemClickListener() {
            @Override
            public void onClick(int position) {
                //设置上一层为点击的层级
                mUpOrg = mShowProvideUnionDoctorOrgs.get(position);
                mShowProvideUnionDoctorOrgs.clear();
                //找出下一层数据并显示
                for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                {
                    if (mProvideUnionDoctorOrgs.get(i).getUpOrgId() == mUpOrg.getUnionOrgId())
                    {
                        mShowProvideUnionDoctorOrgs.add(mProvideUnionDoctorOrgs.get(i));
                    }
                }
                mUpOrgName.setText(mUpOrg.getOrgName());
                mUnionOrgDateAdapter.setDate(mShowProvideUnionDoctorOrgs);
                mUnionOrgDateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });


        mUnionOrgDateAdapter.setOnItemClickListener(new SettingMemberUnionOrgDateAdapter.OnClickLayoutItemClickListener() {
            @Override
            public void onClick(int position) {

                for (int i = 0; i < mShowProvideUnionDoctorOrgs.size(); i++)
                {
                    if (i == position)
                        mShowProvideUnionDoctorOrgs.get(i).setChoice(true);
                    else
                        mShowProvideUnionDoctorOrgs.get(i).setChoice(false);
                }
                for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                {
                    if (mProvideUnionDoctorOrgs.get(i).getUnionOrgId() == mShowProvideUnionDoctorOrgs.get(position).getUnionOrgId())
                        mProvideUnionDoctorOrgs.get(i).setChoice(true);
                    else
                        mProvideUnionDoctorOrgs.get(i).setChoice(false);
                }
                mUnionOrgDateAdapter.setDate(mShowProvideUnionDoctorOrgs);
                mUnionOrgDateAdapter.notifyDataSetChanged();



            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mNewOrgDate = (TextView)this.findViewById(R.id.tv_activityUnionOrgdate_newOrgDate);
        mNewOrgDate.setOnClickListener(new ButtonClick());
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityUnionOrgdate_newOrgDate:
                    for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                    {
                        if (mProvideUnionDoctorOrgs.get(i).isChoice())
                            mApp.unionSettionChoiceOrg = mProvideUnionDoctorOrgs.get(i);
                    }
                    finish();
                    break;
            }
        }
    }






    /**
     * 获取层级结构
     * @param
     */
    private void getOrgDate() {

        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideUnionDoctorOrg provideUnionDoctorOrg = new ProvideUnionDoctorOrg();
                    provideUnionDoctorOrg.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideUnionDoctorOrg.setUnionCode(mUnionCode);

                    //实体转JSON字符串
                    String str = new Gson().toJson(provideUnionDoctorOrg);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/unionDoctorController/getDoctorUnionOrgData");
                    Log.i("deb",mNetRetStr);
                    System.out.println(mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //判断上层节点是否为顶级节点
            if (mUpOrg == mTopOrg)
                finish();
            else
            {
                mShowProvideUnionDoctorOrgs.clear();
                //找出上级节点的上级节点
                for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                {
                    //设置返回后的上级节点
                    if (mProvideUnionDoctorOrgs.get(i).getUnionOrgId() == mUpOrg.getUpOrgId())
                    {
                        mUpOrg = mProvideUnionDoctorOrgs.get(i);
                        break;
                    }
                }
                //获取上级节点的子节点并显示
                for (int i = 0; i < mProvideUnionDoctorOrgs.size(); i++)
                {
                    if (mProvideUnionDoctorOrgs.get(i).getUpOrgId() == mUpOrg.getUnionOrgId())
                    {
                        mShowProvideUnionDoctorOrgs.add(mProvideUnionDoctorOrgs.get(i));
                    }
                }
                mUpOrgName.setText(mUpOrg.getOrgName());
                mUnionOrgDateAdapter.setDate(mShowProvideUnionDoctorOrgs);
                mUnionOrgDateAdapter.notifyDataSetChanged();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
