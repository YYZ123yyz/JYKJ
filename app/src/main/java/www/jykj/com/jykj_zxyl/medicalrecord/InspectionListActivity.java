package www.jykj.com.jykj_zxyl.medicalrecord;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;

/**
 * Description:检查检验单列表
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 18:44
 */
public class InspectionListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_add_more)
    ImageView ivAddMore;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inspection_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("检查检验");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }



}
