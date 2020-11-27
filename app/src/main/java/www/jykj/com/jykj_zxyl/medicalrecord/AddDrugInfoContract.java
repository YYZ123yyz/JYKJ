package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-18 10:12
 */
public class AddDrugInfoContract {


    public interface View extends BaseView {


        /**
         * 获取新增药品信息返回结果
         * @param isSucess true or false
         * @param msg 信息
         */
        void getOperUpdDrugInfoResult(boolean isSucess,String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送[基础数据]药品信息增加
         * @param drugUseType 药品分类
         * @param drugName 药品名称
         * @param specUnit 药品单位
         * @param specName 药品规格
         * @param activity activity
         */
        void sendOperUpdDrugInfoRequest(String drugUseType, String drugName, String specUnit
                , String specName, Activity activity);

        /**
         * 诊所详情】[基础数据]药品信息增加
         * @param medicineCode 药品分类【选取】
         * @param drugName 药品名称(通用)
         * @param drugNameAlias 商品名称
         * @param specUnit 药品单位
         * @param specName 药品规格
         * @param dosageCode 剂型编码【选取】
         * @param factoryName 厂家名称
         * @param drugUsageDay 药品用法
         * @param drugUsageNum 药品用法
         * @param activity activity
         */
        void sendOperUpdDrugInfo_201116(String medicineCode,String drugName,
                                        String drugNameAlias,String specUnit
                ,String specName,String dosageCode,String factoryName
                ,String drugUsageDay,String drugUsageNum,Activity activity);
    }
}
