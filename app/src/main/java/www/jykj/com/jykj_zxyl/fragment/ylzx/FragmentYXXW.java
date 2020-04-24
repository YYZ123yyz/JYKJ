package www.jykj.com.jykj_zxyl.fragment.ylzx;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.adapter.RMJXRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 医学新闻fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYXXW extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private             MainActivity                        mActivity;
    private             JYKJApplication                     mApp;

    private             RecyclerView                        mYXWXRecycleView;              //医学文献列表
    private             LinearLayoutManager                 layoutManager;
    private             RMJXRecycleAdapter                  mYXWXRecycleAdapter;       //适配器
    private             List<HZIfno>                        mHZEntyties = new ArrayList<>();            //所有数据
    private             List<HZIfno>                        mHZEntytiesClick = new ArrayList<>();            //点击之后的数据


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmentylzx_yxxw, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {

        mYXWXRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmentYLZX_yxxwRecycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mYXWXRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mYXWXRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mYXWXRecycleAdapter = new RMJXRecycleAdapter(mHZEntyties,mContext,mActivity);
        mYXWXRecycleView.setAdapter(mYXWXRecycleAdapter);

    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                      break;
                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {



            }
        }
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
        mYXWXRecycleAdapter.setDate(mHZEntyties);
        mYXWXRecycleAdapter.notifyDataSetChanged();
    }
}
