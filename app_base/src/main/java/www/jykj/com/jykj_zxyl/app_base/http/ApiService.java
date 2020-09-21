package www.jykj.com.jykj_zxyl.app_base.http;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Description:存放所有的Api
 *
 * @author: qiuxinhai
 * @date: 2018/5/31 16:10
 */
public interface ApiService {


    //排班-医生线上排班展示-头信息(天)
    @POST("/doctorReserveControlle/searchReserveDoctorDateRosterInfoHeader")
    Observable<String> searchReserveDoctorDateRosterInfoHeader(@Query(value = "jsonDataInfo", encoded = true) String queryJson);


    //排班-医生线上排班展示(天)
    @POST("/doctorReserveControlle/searchReserveDoctorDateRosterInfo")
    Observable<String> searchReserveDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //获取号源类型
    @POST("/basicDataController/getBasicsDomain")
    Observable<String> getBasicsDomain(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //排班-医生线上排班设置(天)
    @POST("/doctorReserveControlle/operUpdDoctorDateRosterInfo")
    Observable<String> operUpdDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //排班-线上排班删除(天)
    @POST("/doctorReserveControlle/operDelDoctorDateRosterInfo")
    Observable<String> operDelDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生查看排班信息-头信息(周)
    @POST("/doctorReserveControlle/searchReserveDoctorRosterInfoHeader")
    Observable<String> searchReserveDoctorRosterInfoHeader(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生查看排班信息(周)
    @POST("/doctorReserveControlle/searchReserveDoctorRosterInfo")
    Observable<String> searchReserveDoctorRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生设置排班信息(周)
    @POST("/doctorReserveControlle/operDelReserveDoctorRosterInfo")
    Observable<String> operDelReserveDoctorRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生设置排班信息(周)
    @POST("/doctorReserveControlle/operUpdReserveDoctorRosterInfo")
    Observable<String> operUpdReserveDoctorRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的诊所-预约单查询(签约患者、一次性就诊)
    @POST("/doctorReserveControlle/searchReservePatientDoctorInfo")
    Observable<String> searchReservePatientDoctorInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的诊所-医生操作[确认接诊]
    @POST("/doctorReserveControlle/operConfirmReservePatientDoctorInfo")
    Observable<String> operConfirmReservePatientDoctorInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的诊所-医生操作[取消预约]
    @POST("/doctorReserveControlle/operCancelReservePatientDoctorInfo")
    Observable<String> operCancelReservePatientDoctorInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的诊所-预约单查询(签约患者、一次性就诊)
    @POST("/doctorReserveControlle/searchReserveDoctorDateRosterInfoImmediate")
    Observable<String> searchReserveDoctorDateRosterInfoImmediate(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的诊所-查看医生当前日期的预约患者、接诊患者、取消预约患者列表
    @POST("/doctorReserveControlle/searchReservePatientDoctorInfoByStatus")
    Observable<String> searchReservePatientDoctorInfoByStatus(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //订单详情信息查看
    @POST("/doctorReserveControlle/searchReserveInfo")
    Observable<String> searchReserveInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //解约订单详情
    @POST("/doctorSignControlle/searchSignPatientDoctorOrder")
    Observable<String> searchSignPatientDoctorOrder(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //医生配置-读取签约服务项目信息到价格表
    @POST("/doctorSignControlle/searchDoctorSignConfigDetail")
    Observable<String> searchDoctorSignConfigDetail(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //医生配置-签约服务价格变更【批量修改】
    @POST("/doctorSignControlle/operDoctorSignConfigPriceBatch")
    Observable<String> operDoctorSignConfigPriceBatch(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取用户(医生、患者)的[简要]信息
    @POST("/doctorPatientCommonDataController/getUserInfoListAndService")
    Observable<String> getUserInfoListAndService(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的预约-预约单-查询预约单详情(取消原因)
    @POST("/doctorReserveControlle/searchReservePatientDoctorInfoXx")
    Observable<String> getSearchReservePatientDoctorInfoXx(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取患者基本信息
    @POST("/patientDataControlle/searchDoctorManagePatientStateResBasic")
    Observable<String> searchDoctorManagePatientStateResBasic(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //项目-医生查询检查检验项目类别信息列表
    @POST("/DoctorInspectionControlle/searchInspectionProjectDetailClassList")
    Observable<String> searchInspectionProjectDetailClassList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //检查检验项目类别列表
    @POST("/DoctorInspectionControlle/searchInspectionProjectDetailList")
    Observable<String> searchInspectionProjectDetailList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //检查检验部位列表
    @POST("/DoctorInspectionControlle/searchInspectionProjectDetailConfigList")
    Observable<String> searchInspectionProjectDetailConfigList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //查询检查等级列表
    @POST("/DoctorInspectionControlle/searchInspectionProjectDetailGradeList")
    Observable<String> searchInspectionProjectDetailGradeList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //检查检验单 删除
    @POST("/DoctorInspectionControlle/operDelInteractOrderInspection")
    Observable<String> operDelInteractOrderInspection(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //检查检验单 保存/变更
    @POST("/DoctorInspectionControlle/operUpdInteractOrderInspection")
    Observable<String> operUpdInteractOrderInspection(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //查询检查检验单据
    @POST("/DoctorInspectionControlle/searchInteractOrderInspectionList")
    Observable<String> searchInteractOrderInspectionList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取签约服务项目基础配置信息
    @POST("/doctorSignControlle/searchSignConfigDetail")
    Observable<String> searchSignConfigDetail(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //[基础数据]药品信息
    @POST("/doctorInteractDataControlle/searchMyClinicDetailResPrescribeDrugInfo_200915")
    Observable<String> searchMyClinicDetailResPrescribeDrugInfo_200915(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //[基础数据]药品信息增加
    @POST("/doctorInteractDataControlle/operUpdDrugInfo")
    Observable<String> operUpdDrugInfo(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //处方 保存/变更
    @POST("/doctorInteractDataControlle/operUpdMyClinicDetailByPrescribe_200915")
    Observable<String> operUpdMyClinicDetailByPrescribe_200915(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //处方笺列表
    @POST("doctorInteractDataControlle/searchMyClinicDetailResPrescribe_200915")
    Observable<String> searchMyClinicDetailResPrescribe_200915(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //加载病历信息
    @POST("/medicalRecordControlle/getMyClinicDetailResPatientMedicalRecord")
    Observable<String> getMyClinicDetailResPatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //处方删除[按处方凭证]
    @POST("/doctorInteractDataControlle/operDelInteractOrderPrescribeByprescribeVoucher_200915")
    Observable<String> operDelInteractOrderPrescribeByprescribeVoucher_200915(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取病历详情
    @POST("medicalRecordControlle/getMyClinicDetailResPatientMedicalRecord")
    Observable<String> getPatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //发送病历
    @POST("/medicalRecordControlle/operMyClinicDetailPatientMedicalRecordSend")
    Observable<String> sendPatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //保存病历
    @POST("/medicalRecordControlle/operMyClinicDetailPatientMedicalRecordByAll")
    Observable<String> savePatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取处方详细信息[按处方凭证]
    @POST("/doctorInteractDataControlle/searchInteractOrderPrescribeList")
    Observable<String> searchInteractOrderPrescribeList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //IM检测接口
    @POST("/imDataControlle/iMTesting")
    Observable<String> iMTesting(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
}

