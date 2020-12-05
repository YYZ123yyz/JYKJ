package www.jykj.com.jykj_zxyl.app_base.http;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    //基本健康信息
    @POST("/patientDataControlle/searchDoctorManagePatientConditionHealthyResBasics")
    Observable<String> searchBasichealthinformation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //症状信息
    @POST("/patientDataControlle/searchDoctorManagePatientConditionHealthyResSymptom")
    Observable<String> searchsymptomformation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //标签记录
    @POST("/patientDataControlle/searchDoctorManagePatientConditionLabel")
    Observable<String> searchLabelformation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //既往病史
    @POST("/patientDataControlle/searchDoctorManagePatientConditionDiseaseRecordList")
    Observable<String> searchHistoryformation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //处方笺列表
    @POST("doctorInteractDataControlle/searchMyClinicDetailResPrescribe_200915")
    Observable<String> searchMyClinicDetailResPrescribe_200915(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //处方删除[按处方编码
    @POST("/doctorInteractDataControlle/operDelMyClinicDetailByPrescribe_200915")
    Observable<String> operDelMyClinicDetailByPrescribe_200915(@Query(value = "jsonDataInfo"
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
    //[获取]问诊详情数据
    @POST("/medicalRecordControlle/searchMyClinicDetailResPatientInterrogation")
    Observable<String> searchMyClinicDetailResPatientInterrogation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //发送病历
    @POST("/medicalRecordControlle/operMyClinicDetailPatientMedicalRecordSend")
    Observable<String> sendPatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //保存病历
    @POST("/medicalRecordControlle/operMyClinicDetailPatientMedicalRecordByAll")
    Observable<String> savePatientMedicalRecord(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //[获取]既往病史[医生填写详情]
    @POST("/patientDataControlle/searchDoctorManagePatientConditionDiseaseRecordResDoctorData")
    Observable<String> getFillindetails(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取处方详细信息[按处方凭证]
    @POST("/doctorInteractDataControlle/searchInteractOrderPrescribeList")
    Observable<String> searchInteractOrderPrescribeList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //IM检测接口
    @POST("/imDataControlle/IMTesting")
    Observable<String> iMTesting(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //我的患者】获取医生管理的患者信息(签约患者)
    @POST("/bindingDoctorPatientControlle/searchDoctorManagePatientDataByParam")
    Observable<String> searchDoctorManagePatientDataByParam(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //我的服务历史
    @POST("/doctorReserveControlle/searchReserveInfoMyHistory")
    Observable<String> searchReserveInfoMyHistory(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //首页】首页banner+医院信息
    @POST("/basicDataController/getBannerAndHospitalInfo")
    Observable<String> getBannerAndHospitalInfo(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取消息列表数据
    @POST("/doctorPatientCommonDataController/getUserInfoList")
    Observable<String> getUserInfoList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取全部资料患者列表
    @POST("/doctorManagePatient/interactPatientAllList")
    Observable<String> interactPatientAllList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取签约患者列表
    @POST("/doctorManagePatient/interactPatientSigningList")
    Observable<String> interactPatientSigningList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取医生好友列表
    @POST("/doctorManagePatient/interactDoctorUnionAllList")
    Observable<String> interactDoctorUnionAllList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //获取健康咨询列表
    @POST("/doctorDataControlle/searchIndexHealthEducation")
    Observable<String> searchIndexHealthEducation(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //课件
    @POST("/liveRoomController/listGetIndexHealthEducation")
    Observable<String> getHomeChapterDataList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //课件详情
    @POST("/liveRoomController/listGetCourseWare")
    Observable<String> getHomeChapterList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
  //患者数量统计
  @POST("/bindingDoctorPatientControlle/searchDoctorManagePatientDataByTotal")
  Observable<String> getNumber(@Query(value = "jsonDataInfo"
          , encoded = true) String queryJson);
  //我的签约患者数据展示
  @POST("/bindingDoctorPatientControlle/searchDoctorManagePatientDataByParam")
  Observable<String> searchList(@Query(value = "jsonDataInfo"
          , encoded = true) String queryJson);
  //撤销解约
  @POST("/doctorSignControlle/operTerminationRevoke")
  Observable<String> searchRevoke(@Query(value = "jsonDataInfo"
          , encoded = true) String queryJson);

    //我的未签约患者数据展示
    @POST("/bindingDoctorPatientControlle/searchDoctorManagePatientDataOtherByParam")
    Observable<String> searchNotList(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //诊后留言详情
    @POST("/doctorInteractDataControlle/searchMyClinicDetailResPatientMessageContent_20201012")
    Observable<String> searchMyClinicDetailResPatientMessageContent_20201012(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);

    //提交(新增、修改)】诊后留言(医生留言回复)(医生主动发起留言)
    @POST("/doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage_20201012")
    Observable<String> operUpdMyClinicDetailByOrderPatientMessage_20201012(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //健康档案-既往病史-个人填写详情
    @POST("/patientDataControlle/searchDoctorManagePatientConditionDiseaseRecordResData")
    Observable<String> searchmyself_detail(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //健康教育-图文资讯详情
    @POST("/doctorDataControlle/searchPatientIndexHealthEducationByImageTextDetail")
    Observable<String> searchPatientIndexHealthEducationByImageTextDetail(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //新增直播大纲
    @POST("/broadcastSyllabusController/addBroadcastSyllabus")
    Observable<String> addBroadcastSyllabus(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //查询直播大纲
    @POST("/broadcastLiveDataControlle/getBroadcastDetailInfo")
    Observable<String> getBroadcastDetailInfo(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //修改直播大纲
    @POST("/broadcastSyllabusController/updateBroadcastSyllabus")
    Observable<String> updateBroadcastSyllabus(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //新增直播间图片
    @POST("/broadcastImageController/addBroadcastImage")
    Observable<String> addBroadcastImage(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //新增直播间图片new
    @Multipart
    @POST
    Observable<String> addBroadcastImage2(@Url String uploadUrl, @Query(value = "jsonDataInfo"
                                                  , encoded = true) String queryJson,
                                          @Part List<MultipartBody.Part> imagefiles);
    //修改直播间图片
    @POST("/broadcastImageController/updateBroadcastImage")
    Observable<String> updateBroadcastImage(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //修改直播间图片
    @Multipart
    @POST
    Observable<String> updateBroadcastImage2(@Url String uploadUrl, @Query(value = "jsonDataInfo"
            , encoded = true) String queryJson,
                                             @Part List<MultipartBody.Part> imagefiles);

    //创建直播间
    @POST("/broadcastLiveDataControlle/operLiveRoomDetails")
    Observable<String> operLiveRoomDetails(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);
    //关闭直播
    @POST("/broadcastLiveDataControlle/operLiveRoomDetailsNoticeResCloseBroadcasting")
    Observable<String> operLiveRoomDetailsNoticeResCloseBroadcasting(
            @Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //Im查询追加次数
    @POST("/imDataControlle/searchReserveIMInfo")
    Observable<String> searchReserveIMInfo( @Query(value = "jsonDataInfo", encoded = true)
                                                    String queryJson);
    //变更Im追加次数
    @POST("/imDataControlle/operReserveIMInfo")
    Observable<String> operReserveIMInfo(@Query(value = "jsonDataInfo", encoded = true)
                                                 String queryJson);
    //查询首页健康教育整合数据列表
    @POST("/liveRoomController/listGetIndexHealthEducation")
    Observable<String> listGetIndexHealthEducation(@Query(value = "jsonDataInfo", encoded = true)
                                                           String queryJson);
    //开启直播
    @POST("/broadcastLiveDataControlle/operLiveRoomDetailsNoticeResStartBroadcasting")
    Observable<String> operLiveRoomDetailsNoticeResStartBroadcasting(@Query(value = "jsonDataInfo"
            , encoded = true) String queryJson);


    //课件价格详情
    @POST("/liveRoomController/getCourseWarePrice")
    Observable<String> getChapterPrice(@Query(value = "jsonDataInfo", encoded = true)
                                                 String queryJson);
    //课件预支付
    @POST("/liveRoomController/doUnifiedOrder")
    Observable<String> go2payChapter(@Query(value = "jsonDataInfo", encoded = true)
                                                 String queryJson);

    //课件查询直播资源
    @POST("/liveRoomController/getCourseWareLinkUrl")
    Observable<String> getChapterSource(@Query(value = "jsonDataInfo", encoded = true)
                                                 String queryJson);
    //课件详情信息
    @POST("/liveRoomController/getCourseWareDetail")
    Observable<String> getCourseWareDetail(@Query(value = "jsonDataInfo", encoded = true)
                                                   String queryJson);
    //药品信息
    @POST("/doctorInteractDataControlle/searchMyClinicDetailResPrescribeDrugInfo_201116")
    Observable<String> searchMyClinicDetailResPrescribeDrugInfo_201116(@Query(value = "jsonDataInfo", encoded = true)
                                                                               String queryJson);
    //药品信息增加
    @POST("/doctorInteractDataControlle/operUpdDrugInfo_201116")
    Observable<String> operUpdDrugInfo_201116(@Query(value = "jsonDataInfo", encoded = true)
                                                      String queryJson);
    //获取药品分类数据
    @POST("/doctorInteractDataControlle/getDrugTypeMedicine")
    Observable<String> getDrugTypeMedicine(@Query(value = "jsonDataInfo", encoded = true)
                                                   String queryJson);
    //点赞取
    @POST("/broadcastLiveDataControlle/extendBroadcastFollowNum")
    Observable<String> extendBroadcastFollowNum(@Query(value = "jsonDataInfo", encoded = true)
                                                        String queryJson);
    //取消点赞
    @POST("/broadcastLiveDataControlle/Numberofprecastviewerscancelled")
    Observable<String> Numberofprecastviewerscancelled(@Query(value = "jsonDataInfo", encoded = true)
                                                               String queryJson);

}

