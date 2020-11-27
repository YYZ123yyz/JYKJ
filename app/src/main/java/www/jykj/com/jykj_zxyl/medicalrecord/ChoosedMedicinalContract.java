package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalTypeBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:选择药物契约类
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


        /**
         * 发送获取药品信息请求
         * @param medicineCode 药物分类编码
         * @param srarchDrugName 选填,可为空]查询的药品名称
         * @param rowNum 分页查询属性.每页行数
         * @param pageNum 分页查询属性.当前页码
         * @param activity Activity
         */
        void sendSearchMyClinicDetailResPrescribeDrugInfo_201116(String medicineCode
                ,String srarchDrugName,int rowNum,int pageNum,Activity activity);

        /**
         * 发送药品分类请求
         * @param medicineCode 药物分类编码(0:显示全部; -1:一级目录; 显示子目录:如001 )
         * @param activity Activity
         */
        void sendGetDrugTypeMedicineRequest(String medicineCode,Activity activity);
    }
}
