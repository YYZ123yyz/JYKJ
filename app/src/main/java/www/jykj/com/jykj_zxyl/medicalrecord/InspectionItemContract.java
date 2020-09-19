package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:检查检验单列表适配器
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 14:33
 */
public class InspectionItemContract {


    public interface View extends BaseView {


        /**
         * 获取检查检验项目列表
         * @param inspectionItemBeans 检查检验列表
         */
        void getSearchInspectionItemListResult(  List<InspectionItemBean> inspectionItemBeans);

        /**
         * 获取查询检查检验单返回结果
         * @param inspectionItemGradeBean 检查检验单返回结果
         * @param pos 位置
         */
        void getSearchInspectionGradeListResult(InspectionItemGradeBean inspectionItemGradeBean,int pos);

        /**
         * 获取删除或者创建检查检验单返回结果
         * @param isSucess true or false
         * @param pos
         * @param msg 返回信息
         */
        void getDelInteractOrderInspectionResult(boolean isSucess, int pos,String msg);

        /**
         * 获取修改或者创建检查检验单返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getUpdateInteractOrderInspectionResult(boolean isSucess,String msg);

    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 查询检查检验单据
         * @param hospitalInfoCode 医院code
         * @param orderCode 订单Id
         * @param inspectionOrderCode 检查检验单code
         */
        void sendSearchInteractOrderInspectionListRequest(String hospitalInfoCode,
                                                          String orderCode,
                                                          String inspectionOrderCode,Activity activity);

        /**
         * 查询检查等级列表
         * @param hospitalInfoCode 医院code
         * @param gradeCode 等级code
         * @param pos 位置
         * @param activity activity
         */
        void sendSearchInspectionGradeListRequest(String hospitalInfoCode
                ,String gradeCode, int pos,Activity activity);

        /**
         * 发送检查检验单删除请求
         * @param inspectionOrderCode 检查检验单code
         * @param pos 位置
         * @param activity activity
         */
        void sendOperDelInteractOrderInspectionRequest(String inspectionOrderCode,int pos,Activity activity);


        /**
         * 发送 检查检验单 保存/变更 请求
         * @param uploadBeans 检查检验单列表
         * @param activity activity
         */
        void sendOperUpdInteractOrderInspectionRequest(List<InspectionItemUploadBean> uploadBeans, Activity activity);
    }
}
