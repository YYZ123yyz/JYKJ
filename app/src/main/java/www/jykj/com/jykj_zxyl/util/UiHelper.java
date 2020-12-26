package www.jykj.com.jykj_zxyl.util;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.NewsActivity;
import www.jykj.com.jykj_zxyl.activity.liveroom.LiveroomDetailActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.PatientRecordActivity;
import www.jykj.com.jykj_zxyl.mypatient.activity.PatientActivity;

/**
 * Description:跳转帮助类
 *
 * @author: qiuxinhai
 * @date: 2020-12-26 11:18
 */
public class UiHelper {


    /**
     * 跳转系统消息
     *
     * @param context 上下文
     */
    public static void goToJumpSysMessage(Context context) {
        startActivity(context, NewsActivity.class, null);
    }


    /**
     * 跳转直播详情
     *
     * @param context    上下文
     * @param detailCode 直播间code
     */
    public static void goToJumpLiveDetial(Context context, String detailCode) {
        Bundle bundle = new Bundle();
        bundle.putString("detailCode", detailCode);
        startActivity(context, LiveroomDetailActivity.class, bundle);
    }

    /**
     * 跳转我的患者
     *
     * @param context 上下文
     */
    public static void goToJumpMyPatient(Context context) {
        startActivity(context, PatientActivity.class, null);
    }

    /**
     * 跳转我的患者
     *
     * @param context 我的病历
     */
    public static void goToJumpMedicalRecord(Context context, String orderCode
            , String patientCode, String patientName) {
        Bundle bundle = new Bundle();
        bundle.putString("orderCode", orderCode);
        bundle.putString("patientCode", patientCode);
        bundle.putString("patientName", patientName);
        startActivity(context, PatientRecordActivity.class, bundle);
    }

    /**
     * 跳转我的诊所
     *
     * @param context 上下文
     */
    public static void goToJumpMyClinic(Context context) {
        startActivity(context, MyClinicActivity.class, null);
    }



    /**
     * 跳转Activity
     * @param context 上下文
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    private static void startActivity(Context context,Class paramClass
            , Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(context, paramClass);
        context.startActivity(localIntent);
    }


}
