package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * 就诊总览==》个人总览==>个人状况
 */
public class GRXX_GRZKActivity extends AppCompatActivity {


    private Context mContext;
    private TWJZ_CFQActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private LinearLayout mJBXX;                                  //基本信息
    private LinearLayout mHZBQ;                                  //患者标签
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk);
        mContext = this;
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mJBXX = (LinearLayout) this.findViewById(R.id.li_activityGRZK_JBXX);
        mHZBQ = (LinearLayout) this.findViewById(R.id.li_activityGRZK_HZBQ);
        mJBXX.setOnClickListener(new ButtonClick());
        mHZBQ.setOnClickListener(new ButtonClick());
//返回
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityGRZK_JBXX:
                    startActivity(new Intent(mContext, GRXX_GRZK_JBXXActivity.class));
                    break;
                case R.id.li_activityGRZK_HZBQ:
                    startActivity(new Intent(mContext, GRXX_GRZK_HZBQActivity.class));
                    break;
            }
        }
    }
}
