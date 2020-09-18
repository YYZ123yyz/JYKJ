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
         * @param activity AActivity
         */
        void sendOperUpdDrugInfoRequest(String drugUseType, String drugName, String specUnit
                , String specName, Activity activity);

    }
}
