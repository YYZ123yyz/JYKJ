package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 15:59
 */
public class PrescriptionNotesListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_notes_list;
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
