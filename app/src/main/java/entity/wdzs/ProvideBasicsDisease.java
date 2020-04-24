package entity.wdzs;

import java.util.Date;


/**
 * 【基础数据】
 * 基础类-疾病编码
 * 
 * @author JiaQ
 */

public class ProvideBasicsDisease implements java.io.Serializable {
	private Integer diseaseId;//疾病编号
	private String diseaseCode;//疾病编码
	private Integer diseaseType;//疾病类型.0:未分类;
	private String diseaseName;//疾病名称
	private String diseaseNameSpell;//疾病名称助记码

	private	String rowNum;
	private	String pageNum;
	private	String loginDoctorPosition;
	private	String operDoctorCode;
	private	String operDoctorName;
	private	String srarchDiseaseName;

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public Integer getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(Integer diseaseType) {
		this.diseaseType = diseaseType;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getDiseaseNameSpell() {
		return diseaseNameSpell;
	}

	public void setDiseaseNameSpell(String diseaseNameSpell) {
		this.diseaseNameSpell = diseaseNameSpell;
	}

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

	public String getSrarchDiseaseName() {
		return srarchDiseaseName;
	}

	public void setSrarchDiseaseName(String srarchDiseaseName) {
		this.srarchDiseaseName = srarchDiseaseName;
	}
}
