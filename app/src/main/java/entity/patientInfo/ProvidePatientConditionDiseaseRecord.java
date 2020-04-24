package entity.patientInfo;

import java.util.Date;


/**
 * 患者个人状况-既往病史(病程记录)
 * 
 * @author JiaQ
 */
public class ProvidePatientConditionDiseaseRecord implements java.io.Serializable {

	private Integer recordId;//记录(既往病史、病程)信息编号
	private String imgCode;//图片编码
	private String patientCode;//患者编码
	private String recordName;//名称.记录(既往病史、病程)
	private String recordNameAlias;//名称别名.记录(既往病史、病程)
	private Date treatmentDate;//就诊日期
	private Integer recordTypeId;//分类编码.记录(既往病史、病程).0:未分类;1:既往病史;2:病程记录;
	private String recordTypeName;//[冗余]分类名称.记录(既往病史、病程)
	private String recordContent;//病情描述/记录(既往病史、病程)内容
	private String recordImgUrl1;//上传图片记录1.记录(既往病史、病程)
	private String recordImgUrl2;//上传图片记录2.记录(既往病史、病程)
	private String recordImgUrl3;//上传图片记录3.记录(既往病史、病程)
	private String recordImgUrl4;//上传图片记录4.记录(既往病史、病程)
	private String recordImgUrl5;//上传图片记录5.记录(既往病史、病程)
	private Integer flagUseState;//使用状态.0:未使用;1:使用中;

	private	String loginDoctorPosition;
	private	String operDoctorCode;
	private	String operDoctorName;
	private	String searchPatientCode;
	private String rowNum;
	private String pageNum;

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordNameAlias() {
		return recordNameAlias;
	}

	public void setRecordNameAlias(String recordNameAlias) {
		this.recordNameAlias = recordNameAlias;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public Integer getRecordTypeId() {
		return recordTypeId;
	}

	public void setRecordTypeId(Integer recordTypeId) {
		this.recordTypeId = recordTypeId;
	}

	public String getRecordTypeName() {
		return recordTypeName;
	}

	public void setRecordTypeName(String recordTypeName) {
		this.recordTypeName = recordTypeName;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}

	public String getRecordImgUrl1() {
		return recordImgUrl1;
	}

	public void setRecordImgUrl1(String recordImgUrl1) {
		this.recordImgUrl1 = recordImgUrl1;
	}

	public String getRecordImgUrl2() {
		return recordImgUrl2;
	}

	public void setRecordImgUrl2(String recordImgUrl2) {
		this.recordImgUrl2 = recordImgUrl2;
	}

	public String getRecordImgUrl3() {
		return recordImgUrl3;
	}

	public void setRecordImgUrl3(String recordImgUrl3) {
		this.recordImgUrl3 = recordImgUrl3;
	}

	public String getRecordImgUrl4() {
		return recordImgUrl4;
	}

	public void setRecordImgUrl4(String recordImgUrl4) {
		this.recordImgUrl4 = recordImgUrl4;
	}

	public String getRecordImgUrl5() {
		return recordImgUrl5;
	}

	public void setRecordImgUrl5(String recordImgUrl5) {
		this.recordImgUrl5 = recordImgUrl5;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
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

	public String getSearchPatientCode() {
		return searchPatientCode;
	}

	public void setSearchPatientCode(String searchPatientCode) {
		this.searchPatientCode = searchPatientCode;
	}
}

