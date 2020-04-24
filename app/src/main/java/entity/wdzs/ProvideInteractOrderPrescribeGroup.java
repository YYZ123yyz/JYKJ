package entity.wdzs;

import java.util.List;


/**
 * 【医患互动】
 * 患者就诊-处方记录[处方头文件(分组)]
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderPrescribeGroup implements java.io.Serializable {
	private Integer prescribeId;//处方记录信息编号
	private String orderCode;//订单关联编码
	private String imgCode;//图片编码.外键:basics_img(tableImgId)
	private String prescribeVoucher;//处方凭证编码
	private Integer prescribeType;//处方类型.0:未知;1:平台处方;2:医院处方;3:患者自购;4:其他;
	private String patientCode;//用户(患者)基本信息编码.外键:sys_patient_info
	private String patientName;//[冗余]问诊患者姓名
	private String doctorCode;//就诊医生编码.外键:sys_user_doctor_info
	private String doctorName;//[冗余]就诊医生姓名
	private Integer flagUseState;//使用状态.0:未使用;1:使用中
	  

	/******************* 非对称属性 ********************/
	private String prescribeTypeName;//处方类型.0:未知;1:平台处方;2:医院处方;3:患者自购;4:其他;
	
	//处方分组后-每组处方下所包含的药品信息
	List<ProvideInteractOrderPrescribeInfo> interactOrderPrescribeInfoList;

	public Integer getPrescribeId() {
		return prescribeId;
	}

	public void setPrescribeId(Integer prescribeId) {
		this.prescribeId = prescribeId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getPrescribeVoucher() {
		return prescribeVoucher;
	}

	public void setPrescribeVoucher(String prescribeVoucher) {
		this.prescribeVoucher = prescribeVoucher;
	}

	public Integer getPrescribeType() {
		return prescribeType;
	}

	public void setPrescribeType(Integer prescribeType) {
		this.prescribeType = prescribeType;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public String getPrescribeTypeName() {
		return prescribeTypeName;
	}

	public void setPrescribeTypeName(String prescribeTypeName) {
		this.prescribeTypeName = prescribeTypeName;
	}

	public List<ProvideInteractOrderPrescribeInfo> getInteractOrderPrescribeInfoList() {
		return interactOrderPrescribeInfoList;
	}

	public void setInteractOrderPrescribeInfoList(List<ProvideInteractOrderPrescribeInfo> interactOrderPrescribeInfoList) {
		this.interactOrderPrescribeInfoList = interactOrderPrescribeInfoList;
	}
}
