package www.jykj.com.jykj_zxyl.medicalrecord.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemUploadBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 18:45
 */
public class ConvertUtils {


    public static String buildInspectionIds(List<InspectionItemPositionBean> list){
        StringBuilder inspectionIds=new StringBuilder();
        if(null!=list&&!list.isEmpty())
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    inspectionIds.append(list.get(i).getPositionCode());
                } else {
                    inspectionIds.append(list.get(i).getPositionCode()).append(",");
                }
            }
        return inspectionIds.toString();
    }



    public static String budInspectionPositionNames(List<InspectionItemPositionBean> list){
        StringBuilder inspectionNames=new StringBuilder();
        if(null!=list&&!list.isEmpty())
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    inspectionNames.append(list.get(i).getPositionName());
                } else {
                    inspectionNames.append(list.get(i).getPositionName()).append(",");
                }
            }
        return inspectionNames.toString();
    }


    public static String budInspectionPositionCodes(List<InspectionItemPositionBean> list){
        StringBuilder inspectionNames=new StringBuilder();
        if(null!=list&&!list.isEmpty())
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    inspectionNames.append(list.get(i).getPositionCode());
                } else {
                    inspectionNames.append(list.get(i).getPositionCode()).append(",");
                }
            }
        return inspectionNames.toString();
    }

    public static List<String> getInspectionGradeListName(InspectionItemGradeBean gradeBean){
        List<String> stringList=new ArrayList<>();
        if (gradeBean!=null) {
            String gradeContentName = gradeBean.getGradeContentName();
            if (StringUtils.isNotEmpty(gradeContentName)) {
                String[] split = gradeContentName.split(",");
                stringList.addAll(Arrays.asList(split));
            }
        }
        return stringList;
    }

    public static List<String> getInspectionGradeListCode(InspectionItemGradeBean gradeBean){
        List<String> stringList=new ArrayList<>();
        if (gradeBean!=null) {
            String gradeContentCode = gradeBean.getGradeContentCode();
            if (StringUtils.isNotEmpty(gradeContentCode)) {
                String[] split = gradeContentCode.split(",");
                stringList.addAll(Arrays.asList(split));
            }
        }
        return stringList;
    }

    public static List<InspectionItemUploadBean> convertLocalToUploadBean(
            List<InspectionItemDataBean> itemDataBeans, String orderCode
            , String patientCode, String patientName,
            String doctorCode, String doctorName) {
        List<InspectionItemUploadBean> uploadBeans = new ArrayList<>();
        for (InspectionItemDataBean itemDataBean : itemDataBeans) {
            InspectionItemUploadBean inspectionItemUploadBean = new InspectionItemUploadBean();
            inspectionItemUploadBean.setOrderCode(orderCode);
            inspectionItemUploadBean.setPatientCode(patientCode);
            inspectionItemUploadBean.setPatientName(patientName);
            inspectionItemUploadBean.setDoctorCode(doctorCode);
            inspectionItemUploadBean.setDoctorName(doctorName);
            inspectionItemUploadBean.setInspectionOrderCode(itemDataBean.getInspectionOrderCode());

            inspectionItemUploadBean.setInspectionCode(itemDataBean.getInspectionCode());
            inspectionItemUploadBean.setInspectionName(itemDataBean.getInspectionName());
            inspectionItemUploadBean.setSampleOrLocationCode(itemDataBean.getPositionCode());
            inspectionItemUploadBean.setSampleOrLocationName(itemDataBean.getPositionName());

            inspectionItemUploadBean.setGradeCode(itemDataBean.getGradeCode());
            inspectionItemUploadBean.setGradeContentCode(itemDataBean.getInspectionGradeCode());
            inspectionItemUploadBean.setGradeContentName(itemDataBean.getInspectionGradeName());
            inspectionItemUploadBean.setInspectionTimes(itemDataBean.getInspectionTime());
            inspectionItemUploadBean.setInspectionTarget(itemDataBean.getInspectionPurpose());
            inspectionItemUploadBean.setPrecautions("test");
            uploadBeans.add(inspectionItemUploadBean);
        }

        return uploadBeans;
    }


    public static List<InspectionItemDataBean> convertToItemDataBean(List<InspectionItemBean>
                                                                               itemBeans,String hospitalInfoCode){
        List<InspectionItemDataBean> dataBeans=new ArrayList<>();
        for (InspectionItemBean itemBean : itemBeans) {
            InspectionItemDataBean dataBean=new InspectionItemDataBean();
            dataBean.setHospitalInfoCode(hospitalInfoCode);
            dataBean.setInspectionOrderCode(itemBean.getInspectionOrderCode());
            dataBean.setInspectionCode(itemBean.getInspectionCode());
            dataBean.setInspectionName(itemBean.getInspectionName());
            dataBean.setPositionCode(itemBean.getSampleOrLocationCode());
            dataBean.setPositionName(itemBean.getSampleOrLocationName());
            dataBean.setGradeCode(itemBean.getGradeCode());
            dataBean.setInspectionGradeCode(itemBean.getGradeContentCode());
            dataBean.setInspectionGradeName(itemBean.getGradeContentName());
            dataBean.setInspectionTime(DateUtils.getDateToYYYYMMDD(itemBean.getInspectionTimes()));
            dataBean.setInspectionPurpose(itemBean.getInspectionTarget());
            dataBeans.add(dataBean);
        }
        return dataBeans;

    }
}
