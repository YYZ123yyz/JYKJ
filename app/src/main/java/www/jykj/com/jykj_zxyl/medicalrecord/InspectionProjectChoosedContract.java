package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemProjectBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemCategoryBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:检查检验项目选择契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 10:31
 */
public class InspectionProjectChoosedContract {


    public interface View extends BaseView {

        /**
         * 获取检查检检验类型返回结果
         * @param inspectionItemBeans 检查检验选项列表
         */
        void getSearchInspectionProjectDetialClassListResult(List<InspectionItemCategoryBean>
                                                                     inspectionItemBeans);

        /**
         * 获取检查检验项目返回结果
         * @param inspectionItemBeans 检查检验项目列表
         */
        void getSearchInspectionProjectDetailListResult(List<InspectionItemProjectBean> inspectionItemBeans);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送获取检查检验项目请求
         * @param hospitalInfoCode 医院Id
         * @param activity activity
         */
        void sendSearchInspectionProjectDetailClassListRequest(String hospitalInfoCode, Activity activity);


        /**
         * 检查检验项目类别列表请求
         * @param hospitalInfoCode 医院编码
         * @param parentInspectionCode 类别编码
         * @param inspectionName 检验项目名称【查询条件】
         * @param rowNum 页面显示数量
         * @param pageNum 页码
         */
        void sendSearchInspectionProjectDetailListRequest(String hospitalInfoCode
                , String parentInspectionCode
                , String inspectionName, String rowNum, String pageNum, Activity activity);
    }
}
