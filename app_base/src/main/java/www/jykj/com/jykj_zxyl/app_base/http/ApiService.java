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
    //排班-医生查看排班信息(周)
    @POST("/doctorReserveControlle/searchReserveDoctorRosterInfo")
    Observable<String> searchReserveDoctorRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生线上排班展示(天)
    @POST("/doctorReserveControlle/searchReserveDoctorDateRosterInfo")
    Observable<String> searchReserveDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取号源类型
    @POST("/basicDataController/getBasicsDomain")
    Observable<String> getBasicsDomain(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //排班-医生线上排班设置(天)
    @POST("/doctorReserveControlle/operUpdDoctorDateRosterInfo")
    Observable<String> operUpdDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJso);
    //排班-线上排班删除(天)
    @POST("/doctorReserveControlle/operDelDoctorDateRosterInfo")
    Observable<String> operDelDoctorDateRosterInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJso);
}

