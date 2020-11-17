package www.jykj.com.jykj_zxyl.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;

/**
 * Description:添加直播大纲
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 10:31
 */
public class AddLiveProgromActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_live_progrom_pic)
    ImageView ivLiveProgromPic;
    @BindView(R.id.tv_live_progrom)
    TextView tvLiveProgrom;
    @BindView(R.id.rl_live_progrom)
    RelativeLayout rlLiveProgrom;
    @BindView(R.id.iv_live_add_pic)
    ImageView ivLiveAddPic;
    @BindView(R.id.rl_add_pic)
    RelativeLayout rlAddPic;
    private String detailCode;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            detailCode=extras.getString("detailCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_live_program;
    }



    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        addListener();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("添加直播大纲");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());

    }

    /**
     * 添加监听
     */
    private void addListener() {
        rlLiveProgrom.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("detailCode",detailCode);
            startActivity(LiveProgromListActivity.class, bundle);

        });

        rlAddPic.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("detailCode",detailCode);
            startActivity(LiveAddPicActivity.class, bundle);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
