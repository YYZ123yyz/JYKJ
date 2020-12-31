package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.ReportDetContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter.ReportDetPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;

public class ReportDetActivity extends AbstractMvpBaseActivity<ReportDetContract.View, ReportDetPresenter> implements ReportDetContract.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.right_image_search)
    ImageButton imageButtonE;
    private JYKJApplication mApp;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_report_det;
    }

    @Override
    protected void initView() {
        super.initView();

        setToolBar();
    }


    @Override
    protected void initData() {
        super.initData();
        mApp= (JYKJApplication) getApplication();
    }

    @Override
    public void showLoading(int code) {

    }


    private void setToolBar() {
        toolbar.setMainTitle("统计报表");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleClickListener(v -> {
            MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(ReportDetActivity.this);
            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
            }
        });
    }

}
