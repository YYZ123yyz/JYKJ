package www.jykj.com.jykj_zxyl.medicalrecord.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import entity.wdzs.ProvideBasicsImg;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionMedicinalItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
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
            dataBean.setInspectionType(itemBean.getInspectionType()+"");
            dataBean.setInspectionTypeName(itemBean.getInspectionTypeName());
            dataBeans.add(dataBean);
        }
        return dataBeans;

    }

    public static List<String> convertTakeMedicinalRateToStringArry(List<TakeMedicinalRateBean>
                                                                            takeMedicinalRateBeans){
        List<String> stringList=new ArrayList<>();
        for (TakeMedicinalRateBean takeMedicinalRateBean : takeMedicinalRateBeans) {
            String attrName = takeMedicinalRateBean.getAttrName();
            stringList.add(attrName);
        }
        return stringList;
    }

    public static List<String> convertPrescriptionTypeToStringArry(List<PrescriptionTypeBean>
                                                                           prescriptionTypeBeans){
        List<String> stringList=new ArrayList<>();
        for (PrescriptionTypeBean prescriptionTypeBean : prescriptionTypeBeans) {
            String attrName = prescriptionTypeBean.getAttrName();
            stringList.add(attrName);
        }
        return stringList;
    }

    public static List<PrescriptionItemUploadBean> convertPrescriptionLocalToUploadBean(
            List<PrescriptionMedicinalItemDataBean> list, String orderCode
            , String patientCode, String patientName,
            String doctorCode, String doctorName){
            List<PrescriptionItemUploadBean> uploadBeans=new ArrayList<>();
        for (PrescriptionMedicinalItemDataBean itemDataBean : list) {
            PrescriptionItemUploadBean uploadBean=new PrescriptionItemUploadBean();
            uploadBean.setOrderCode(orderCode);
            uploadBean.setPrescribeType(itemDataBean.getPrescriptionTypeCode());
            uploadBean.setDrugOrderCode(itemDataBean.getDrugOrderCode());
            uploadBean.setPatientCode(patientCode);
            uploadBean.setPatientName(patientName);
            uploadBean.setDoctorCode(doctorCode);
            uploadBean.setDoctorName(doctorName);
            uploadBean.setDrugCode(itemDataBean.getDrugCode());
            uploadBean.setDrugName(itemDataBean.getDrugName());
            uploadBean.setSpecUnit(itemDataBean.getSpecUnit());
            uploadBean.setSpecName(itemDataBean.getSpecName());
            uploadBean.setDrugAmount(itemDataBean.getBuyMedicinalNum());
            uploadBean.setUseNum(itemDataBean.getTakeMedicinalNum());
            uploadBean.setUseFrequencyCode(itemDataBean.getTakeMedicinalRateCode());
            uploadBean.setUseCycle(itemDataBean.getUseCycle());
            uploadBean.setUseDesc(itemDataBean.getTakeMedicinalRemark());
            uploadBean.setUseFrequency(itemDataBean.getUseFrequency());
            uploadBean.setUseFrequencyDay(itemDataBean.getUseFrequencyDay());
            uploadBean.setUseUsageDay(itemDataBean.getUseUsageDay());
            uploadBeans.add(uploadBean);

        }
        return uploadBeans;

    }

    public static List<PrescriptionMedicinalItemDataBean> convertPrescriptionNotesToItem(
            PrescriptionNotesBean prescriptionNotesBean){
        List<PrescriptionMedicinalItemDataBean> prescriptionItemBeans=new ArrayList<>();
        if (prescriptionNotesBean !=null) {
            List<PrescriptionNotesBean.PrescribeInfoBean>
                    prescribeInfos = prescriptionNotesBean.getPrescribeInfo();
            if (!CollectionUtils.isEmpty(prescribeInfos)) {

                for (PrescriptionNotesBean.PrescribeInfoBean prescribeInfo : prescribeInfos) {

                    PrescriptionMedicinalItemDataBean itemDataBean=
                            new PrescriptionMedicinalItemDataBean();
                    itemDataBean.setPrescriptionTypeName(prescribeInfo.getPrescribeTypeName());
                    itemDataBean.setPrescriptionTypeCode(prescribeInfo.getPrescribeType()+"");
                    itemDataBean.setDrugName(prescribeInfo.getDrugName());
                    itemDataBean.setDrugCode(prescribeInfo.getDrugCode());
                    itemDataBean.setBuyMedicinalNum(prescribeInfo.getDrugAmount()+"");
                    itemDataBean.setUnitName(prescribeInfo.getDrugPack());
                    itemDataBean.setTakeMedicinalNum(prescribeInfo.getUseNum()+"");
                    itemDataBean.setSpecUnit(prescribeInfo.getSpecUnit());
                    itemDataBean.setSpecName(prescribeInfo.getSpecName());
                    itemDataBean.setTakeMedicinalRateName(prescribeInfo.getUseFrequencyName());
                    itemDataBean.setTakeMedicinalRateCode(prescribeInfo.getUseFrequencyCode());
                    itemDataBean.setTakeMedicinalRemark(prescribeInfo.getUseDesc());
                    itemDataBean.setDrugOrderCode(prescribeInfo.getDrugOrderCode());
                    itemDataBean.setOrderCode(prescribeInfo.getOrderCode());
                    itemDataBean.setUseCycle(prescribeInfo.getUseCycle()+"");
                    itemDataBean.setUuId(DateUtils.getCurrentMillis());
                    itemDataBean.setUseFrequency(prescribeInfo.getUseFrequency()+"");
                    itemDataBean.setUseFrequencyDay(prescribeInfo.getUseFrequencyDay());
                    itemDataBean.setUseUsageDay(prescribeInfo.getUseUsageDay());
                    prescriptionItemBeans.add(itemDataBean);
                }

            }

        }
        return prescriptionItemBeans;

    }


    public static List<String> convertStrToList(String content){
        List<String> list=new ArrayList<>();
        if (StringUtils.isNotEmpty(content)) {
            String[] split = content.split(",");
            list.addAll(Arrays.asList(split));

        }
        return list;
    }

    public static List<ProvideBasicsImg> convertListToBasicsImg(List<String> list){
        List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
        for (String s : list) {
            ProvideBasicsImg provideBasicsImg=new ProvideBasicsImg();
            provideBasicsImg.setImgUrl(s);
            mProvideBasicsImg.add(provideBasicsImg);
        }
        return mProvideBasicsImg;
    }

}
