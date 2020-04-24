package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.jykj.com.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.jykj.com.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.FragmentHZGL;

public class ViewPatientActivity extends AppCompatActivity {
    private Context mContext;
    private Handler mHandler;
    private MainActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mHZInfoRecycleView;              //患者列表
    private LinearLayoutManager layoutManager;
    private HZGLRecycleAdapter mHZGLRecycleAdapter;       //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private             List<HZIfno>                        mHZEntytiesClick = new ArrayList<>();            //点击之后的数据

    private LinearLayout mQB;            //全部
    private             LinearLayout                        mQBCut;         //全部下划线
    private             LinearLayout                        mYJ;            //预警
    private             LinearLayout                        mYJCut;         //预警下划线
    private             LinearLayout                        mTX;            //提醒
    private             LinearLayout                        mTXCut;         //提醒下划线
    private             LinearLayout                        mZC;            //正常
    private             LinearLayout                        mZCCut;         //正常下划线
    private             int                                 mState;         //状态

    private ImageView mSearch;        //搜索

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        mContext = this;
        initLayout();
        initHandler();
        setData();
    }

    /**
     * 初始化界面
     */
    private void initLayout() {
        mHZInfoRecycleView = (RecyclerView) findViewById(R.id.rv_fragmethzgl_hzinfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mHZInfoRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHZInfoRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGLRecycleAdapter = new HZGLRecycleAdapter(mHZEntyties,mContext);
        mHZInfoRecycleView.setAdapter(mHZGLRecycleAdapter);

        //患者资料点击事件
        mHZGLRecycleAdapter.setOnItemClickListener(new HZGLRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLHZZLActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //血压点击事件
        mHZGLRecycleAdapter.setOnXYItemClickListener(new HZGLRecycleAdapter.OnXYItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLXYActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //用药点击事件
        mHZGLRecycleAdapter.setOnYYItemClickListener(new HZGLRecycleAdapter.OnYYItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLYYXXActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });


        //其他打卡击事件
        mHZGLRecycleAdapter.setOnQTDKItemClickListener(new HZGLRecycleAdapter.OnQTDKItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLQTDKActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //提醒患者点击事件
        mHZGLRecycleAdapter.setOnTXHZItemClickListener(new HZGLRecycleAdapter.OnTXHZItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLTXHZActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mQB = (LinearLayout) findViewById(R.id.li_fragmentHZGL_qb);
        mQBCut = (LinearLayout) findViewById(R.id.li_fragmentHZGL_qbCut);
        mYJ = (LinearLayout) findViewById(R.id.li_fragmentHZGL_yj);
        mYJCut = (LinearLayout) findViewById(R.id.li_fragmentHZGL_yjCut);
        mTX = (LinearLayout) findViewById(R.id.li_fragmentHZGL_tx);
        mTXCut = (LinearLayout) findViewById(R.id.li_fragmentHZGL_txCut);
        mZC = (LinearLayout) findViewById(R.id.li_fragmentHZGL_zc);
        mZCCut = (LinearLayout) findViewById(R.id.li_fragmentHZGL_zcCut);

        mSearch = (ImageView) findViewById(R.id.tv_fragmentHZGL_ss);

        mQB.setOnClickListener(new ButtonClick());
        mYJ.setOnClickListener(new ButtonClick());
        mTX.setOnClickListener(new ButtonClick());
        mZC.setOnClickListener(new ButtonClick());
        mSearch.setOnClickListener(new ButtonClick());
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        mHZEntytiesClick.clear();
                        if (mState == 0)
                        {
                            for (int i = 0; i < mHZEntyties.size(); i++) {
                                mHZEntytiesClick.add(mHZEntyties.get(i));
                            }
                        }
                        else {
                            for (int i = 0; i < mHZEntyties.size(); i++) {
                                if (mHZEntyties.get(i).getState() == mState) {
                                    mHZEntytiesClick.add(mHZEntyties.get(i));
                                }
                            }
                        }
                        mHZGLRecycleAdapter.setDate(mHZEntytiesClick);
                        mHZGLRecycleAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_fragmentHZGL_qb:
                    cutDefault();
                    mQBCut.setVisibility(View.VISIBLE);
                    mState = 0;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_yj:
                    cutDefault();
                    mYJCut.setVisibility(View.VISIBLE);
                    mState = 1;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_tx:
                    cutDefault();
                    mTXCut.setVisibility(View.VISIBLE);
                    mState = 2;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_zc:
                    cutDefault();
                    mZCCut.setVisibility(View.VISIBLE);
                    mState = 3;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.tv_fragmentHZGL_ss:
                    Intent intent = new Intent();
                    intent.setClass(mContext,HZGLSearchActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }

    private void cutDefault(){
        mQBCut.setVisibility(View.GONE);
        mYJCut.setVisibility(View.GONE);
        mTXCut.setVisibility(View.GONE);
        mZCCut.setVisibility(View.GONE);
    }

    /**
     * 设置数据
     */
    private void setData() {
        for (int i = 0; i < 40; i++)
        {
            HZIfno hzIfno = new HZIfno();
            if (i%3 == 0)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(3);
            }
            mHZEntyties.add(hzIfno);
        }
        mHZGLRecycleAdapter.setDate(mHZEntyties);
        mHZGLRecycleAdapter.notifyDataSetChanged();
    }
}
