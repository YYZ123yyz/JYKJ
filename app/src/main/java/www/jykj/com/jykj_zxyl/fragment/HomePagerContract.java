package www.jykj.com.jykj_zxyl.fragment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BannerAndHospitalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-09 18:01
 */
public class HomePagerContract {


    public interface View extends BaseView {

        /**
         * 获取Banner和医院返回结果
         * @param bannerAndHospitalInfoBean
         */
        void getBannerAndHospitalInfoResult(BannerAndHospitalInfoBean bannerAndHospitalInfoBean);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送获取Banner医院请求
         *
         * @param appTypeCode      App类型（1鹫一科技医生端，2鹫一科技患者端）
         * @param positionTypeList 位置信息（获取多个位置的banner信息）
         * @param hospitalCode     医院编码
         */
        void sendGetBannerAndHospitalInfoRequest(String appTypeCode,
                                                 List<String> positionTypeList,
                                                 String hospitalCode);
    }
}
