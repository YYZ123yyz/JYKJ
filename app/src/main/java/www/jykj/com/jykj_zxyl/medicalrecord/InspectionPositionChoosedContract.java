package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:检查检验项目选择契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 10:31
 */
public class InspectionPositionChoosedContract {


    public interface View extends BaseView {

        void getSearchInspectionPositionResult(List<InspectionItemPositionBean>
                                                       inspectionItemPositionBeans);

    }

    public interface Presenter extends BasePresenter<View> {

        void sendSearchInspectionPositionRequest(
                String hospitalInfoCode, String inspectionCode
                , String positionName, String rowNum, String pageNum,Activity activity
        );
    }
}
