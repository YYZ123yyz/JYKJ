package www.jykj.com.jykj_zxyl.consultation.utils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.DoctorInfo.InteractPatient;
import entity.basicDate.ProvideDoctorPatientUserInfo;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 14:53
 */
public class ConvertUtils {

    public static String getConsultationUserList() {
        Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
        //遍历map中的键,获取用户Code列表
        List<String> userCodeList = new ArrayList<>(conversationMap.keySet());
        String parmentString = "";
        for (int i = 0; i < userCodeList.size(); i++) {
            if (i == userCodeList.size() - 1)
                parmentString += userCodeList.get(i);
            else
                parmentString += userCodeList.get(i) + ",";
        }
        return parmentString;
    }

    public static List<InteractPatient> convertDataToInteractList(List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo){
        List<InteractPatient> mInteractPatients=new ArrayList<>();
        for (int i = 0; i < mProvideDoctorPatientUserInfo.size(); i++) {
            InteractPatient interactPatient = new InteractPatient();
            interactPatient.setPatientCode(mProvideDoctorPatientUserInfo.get(i).getUserCode());
            interactPatient.setPatientTitleDesc(mProvideDoctorPatientUserInfo.get(i).getDoctorTitleName());
            interactPatient.setPatientDiagnosisType(mProvideDoctorPatientUserInfo.get(i).getUserUseType());
            interactPatient.setPatientNewLoginDate(mProvideDoctorPatientUserInfo.get(i).getNewLoginDate());
            interactPatient.setPatientUserLabelName(mProvideDoctorPatientUserInfo.get(i).getHospitalInfoName());
            interactPatient.setPatientUserName(mProvideDoctorPatientUserInfo.get(i).getUserName());
            interactPatient.setType("message");
            interactPatient.setLastMessage(mProvideDoctorPatientUserInfo.get(i).getLastMessage());
            interactPatient.setNoRead(mProvideDoctorPatientUserInfo.get(i).isNoRead());
            interactPatient.setPatientUserLogoUrl(mProvideDoctorPatientUserInfo.get(i).getUserLogoUrl());
            mInteractPatients.add(interactPatient);
        }
        return mInteractPatients;
    }
}
