package www.jykj.com.jykj_zxyl.appointment.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.appointment.dialog.AddSignalSourceDialog;

/**
 * Description:我的线上排班
 *
 * @author: qiuxinhai
 * @date: 2020-08-26 16:03
 */
public class MyOnlineScheduActivity extends BaseActivity {
    @BindView(R.id.txt_left_title)
    TextView txtLeftTitle;
    @BindView(R.id.left_image_id)
    ImageButton leftImageId;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.txt_right_title)
    TextView txtRightTitle;
    @BindView(R.id.right_image_search)
    ImageButton rightImageSearch;
    @BindView(R.id.right_image_id)
    ImageButton rightImageId;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    AddSignalSourceDialog addSignalSourceDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_online_scheduling;
    }

    @Override
    protected void initView() {
        super.initView();
        addSignalSourceDialog=new AddSignalSourceDialog(this);
        setToolBar();
        addListener();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的线上排班设置");
        toolbar.setRightTitleDrawable(R.mipmap.bg_schedu_set);
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvAddBtn.setOnClickListener(v -> addSignalSourceDialog.show());
    }
}
