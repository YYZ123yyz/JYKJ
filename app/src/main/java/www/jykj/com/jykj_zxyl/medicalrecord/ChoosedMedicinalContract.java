package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalTypeBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 11:31
 */
public class ChoosedMedicinalContract  {

    public interface View extends BaseView {

        /**
         * 获取药品类型返回结果
         * @param medicinalTypeBeans 药品类型列表
         */
        void getMedicinalTypeListResult(List<MedicinalTypeBean> medicinalTypeBeans);

        /**
         * 获取药品信息返回结果
         * @param medicinalInfoBeans 药品信息列表
         */
        void getMedicinalInfoListResult(List<MedicinalInfoBean> medicinalInfoBeans);
    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送药品类型请求Tag
         * @param baseCode 基础code
         * @param activity activity
         */
        void sendMedicinalTypeListRequest(String baseCode, Activity activity);

        /**
         * 发送药品信息查询借口
         * @param drugUseType 药物分类编码
         * @param srarchDrugName 药物名称
         * @param rowNum 每页行数
         * @param pageNum 当前页码
         * @param activity activity
         */
        void sendMedicinalInfoListRequest(String drugUseType
                ,String srarchDrugName,String rowNum,String pageNum,Activity activity);


    }
}
