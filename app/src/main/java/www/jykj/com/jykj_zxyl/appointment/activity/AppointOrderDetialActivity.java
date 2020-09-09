package www.jykj.com.jykj_zxyl.appointment.activity;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OrderInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.appointment.AppointOrderDetialContract;
import www.jykj.com.jykj_zxyl.appointment.AppointOrderDetialPresenter;

/**
 * Description:预约订单详情
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 09:59
 */
public class AppointOrderDetialActivity
        extends AbstractMvpBaseActivity<AppointOrderDetialContract.View,
        AppointOrderDetialPresenter> implements AppointOrderDetialContract.View{
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
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_payment_status)
    TextView tvPaymentStatus;
    @BindView(R.id.tv_consult_doctor)
    TextView tvConsultDoctor;
    @BindView(R.id.tv_consultant)
    TextView tvConsultant;
    @BindView(R.id.tv_consultant_phone)
    TextView tvConsultantPhone;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_appoint_service_time)
    TextView tvAppointServiceTime;
    @BindView(R.id.tv_service_total_price)
    TextView tvServiceTotalPrice;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_service_validity)
    TextView tvServiceValidity;
    @BindView(R.id.tv_service_project)
    TextView tvServiceProject;
    @BindView(R.id.tv_actual_payment)
    TextView tvActualPayment;
    @BindView(R.id.tv_payment_mode)
    TextView tvPaymentMode;
    @BindView(R.id.iv_payment_icon)
    ImageView ivPaymentIcon;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    @Override
    protected int setLayoutId() {
        return R.layout.activity_appoint_order_detial;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        initLoadingAndRetryManager();
    }


    @Override
    protected void initData() {
        super.initData();


    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("订单详情");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(scrollView);
        mLoadingLayoutManager.setRetryListener(v -> {

        });
        mLoadingLayoutManager.showLoading();

    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getSearchReserveInfoResult(OrderInfoBean orderInfoBeans) {

        mLoadingLayoutManager.showContent();
    }

    @Override
    public void getSearchReserveInfoError() {
        mLoadingLayoutManager.showError();
    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }
}
