package www.jykj.com.jykj_zxyl.medicalrecord;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.medicalrecord.popup.TopMiddlePopup;

/**
 * Description:检查检验项目选择
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 15:19
 */
public class InspectionItemChoosedActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_title_type)
    TextView tvTitleType;
    @BindView(R.id.rl_choose_type)
    RelativeLayout rlChooseType;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.tv_search_btn)
    TextView tvSearchBtn;
    @BindView(R.id.rl_search_title)
    RelativeLayout rlSearchTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.viewl_top_line)
    View viewTopLine;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;

    TopMiddlePopup topMiddlePopup;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inspection_item_choosed;
    }

    @Override
    protected void initView() {

        super.initView();
        topMiddlePopup=new TopMiddlePopup(this,new ArrayList<>());
        setToolBar();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
    }



    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("检查检验项目选择");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMiddlePopup.show(viewTopLine);
            }
        });
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
