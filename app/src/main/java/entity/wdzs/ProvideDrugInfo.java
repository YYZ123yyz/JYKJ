package entity.wdzs;

import java.util.Date;


/**
 * 【医院】
 * 药品-信息
 * 前台医嘱使用
 * 
 * @author JiaQ
 */
public class ProvideDrugInfo implements java.io.Serializable {
	private String drugCode;//药品信息编码
	private String drugName;//药品名称
	private String drugUnit;//药品单位
	private String drugSpec;//药品规格

	private	String rowNum;
	private	String pageNum;
	private	String loginDoctorPosition;
	private	String operDoctorCode;
	private	String operDoctorName;
	private	String srarchDrugName;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugUnit() {
		return drugUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	public String getDrugSpec() {
		return drugSpec;
	}

	public void setDrugSpec(String drugSpec) {
		this.drugSpec = drugSpec;
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

	public String getSrarchDrugName() {
		return srarchDrugName;
	}

	public void setSrarchDrugName(String srarchDrugName) {
		this.srarchDrugName = srarchDrugName;
	}
}
