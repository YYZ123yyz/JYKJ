package www.jykj.com.jykj_zxyl.app_base.http;

import android.app.Activity;


import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.ViewSysUserDoctorInfoAndHospital;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.SharedPreferences_DataSave;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-15 14:02
 */
public class ParameUtil {

    public static String loginDoctorPosition = "108.93425^34.23053";
    /**
     * 构建基础请求参数
     * @return HashMap
     */
    public static HashMap<String, Object> buildBaseParam() {
        return new HashMap<>();
    }



    /**
     * 请求医生端请求参数
     * @param activity 当前activity
     * @return HashMap
     */
    public static HashMap<String,Object> buildBaseDoctorParam(Activity activity){
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(activity,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        ViewSysUserDoctorInfoAndHospital mProvideViewSysUserPatientInfoAndRegion
                = GsonUtils.fromJson(userInfoSuLogin, ViewSysUserDoctorInfoAndHospital.class);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginDoctorPosition",loginDoctorPosition);
        paramMap.put("requestClientType","1");
        paramMap.put("operDoctorCode",mProvideViewSysUserPatientInfoAndRegion.getDoctorCode());
        paramMap.put("operDoctorName",mProvideViewSysUserPatientInfoAndRegion.getUserName());
        return paramMap;
    }

}
