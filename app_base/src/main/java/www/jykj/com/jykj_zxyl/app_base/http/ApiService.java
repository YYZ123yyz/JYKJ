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
}

