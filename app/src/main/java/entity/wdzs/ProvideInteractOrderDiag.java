package entity.wdzs;


/**
 * 【医患互动】
 * 患者就诊-诊断信息
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderDiag implements java.io.Serializable {
	private Integer diagId;//诊断信息编号
	private String orderCode;//订单关联编码
	private String imgCode;//图片编码
	private String patientCode;//用户(患者)基本信息编码
	private String patientName;//[冗余]问诊患者姓名
	private String doctorCode;//就诊医生编码
	private String doctorName;//[冗余]就诊医生姓名
	private String diagDiseaseCode1;//诊断编码.外键:
	private String diagDiseaseName1;//[冗余]诊断名称
	private String diagDiseaseNameAlias1;//诊断名称别名
	private String diagDiseaseCode2;//诊断编码.外键:
	private String diagDiseaseName2;//[冗余]诊断名称
	private String diagDiseaseNameAlias2;//诊断名称别名
	private String diagDiseaseCode3;//诊断编码.外键:
	private String diagDiseaseName3;//[冗余]诊断名称
	private String diagDiseaseNameAlias3;//诊断名称别名
	private String diagDiseaseDesc;//[预留]诊断描述

	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;


	public Integer getDiagId() {
		return diagId;
	}

	public void setDiagId(Integer diagId) {
		this.diagId = diagId;
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

	public String getDiagDiseaseCode1() {
		return diagDiseaseCode1;
	}

	public void setDiagDiseaseCode1(String diagDiseaseCode1) {
		this.diagDiseaseCode1 = diagDiseaseCode1;
	}

	public String getDiagDiseaseName1() {
		return diagDiseaseName1;
	}

	public void setDiagDiseaseName1(String diagDiseaseName1) {
		this.diagDiseaseName1 = diagDiseaseName1;
	}

	public String getDiagDiseaseNameAlias1() {
		return diagDiseaseNameAlias1;
	}

	public void setDiagDiseaseNameAlias1(String diagDiseaseNameAlias1) {
		this.diagDiseaseNameAlias1 = diagDiseaseNameAlias1;
	}

	public String getDiagDiseaseCode2() {
		return diagDiseaseCode2;
	}

	public void setDiagDiseaseCode2(String diagDiseaseCode2) {
		this.diagDiseaseCode2 = diagDiseaseCode2;
	}

	public String getDiagDiseaseName2() {
		return diagDiseaseName2;
	}

	public void setDiagDiseaseName2(String diagDiseaseName2) {
		this.diagDiseaseName2 = diagDiseaseName2;
	}

	public String getDiagDiseaseNameAlias2() {
		return diagDiseaseNameAlias2;
	}

	public void setDiagDiseaseNameAlias2(String diagDiseaseNameAlias2) {
		this.diagDiseaseNameAlias2 = diagDiseaseNameAlias2;
	}

	public String getDiagDiseaseCode3() {
		return diagDiseaseCode3;
	}

	public void setDiagDiseaseCode3(String diagDiseaseCode3) {
		this.diagDiseaseCode3 = diagDiseaseCode3;
	}

	public String getDiagDiseaseName3() {
		return diagDiseaseName3;
	}

	public void setDiagDiseaseName3(String diagDiseaseName3) {
		this.diagDiseaseName3 = diagDiseaseName3;
	}

	public String getDiagDiseaseNameAlias3() {
		return diagDiseaseNameAlias3;
	}

	public void setDiagDiseaseNameAlias3(String diagDiseaseNameAlias3) {
		this.diagDiseaseNameAlias3 = diagDiseaseNameAlias3;
	}

	public String getDiagDiseaseDesc() {
		return diagDiseaseDesc;
	}

	public void setDiagDiseaseDesc(String diagDiseaseDesc) {
		this.diagDiseaseDesc = diagDiseaseDesc;
	}

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public String getOperDoctorCode() {
		return operDoctorCode;
	}

	public void setOperDoctorCode(String operDoctorCode) {
		this.operDoctorCode = operDoctorCode;
	}

	public String getOperDoctorName() {
		return operDoctorName;
	}

	public void setOperDoctorName(String operDoctorName) {
		this.operDoctorName = operDoctorName;
	}
}
