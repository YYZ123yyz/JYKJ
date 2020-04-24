package entity.basicDate;

import java.util.Date;



/**
 * 【医生联盟】
 * 基础信息
 * 
 * @author JiaQ
 */

public class ProvideUnionDoctor implements java.io.Serializable {
	private Integer unionId;//编号
	private String unionCode;//关联编码
	private String unionName;//联盟名称
	private String unionNameAlias;//联盟名称(别名)
	private String unionNameSpell;//联盟名称拼音助记码
	private Integer unionGrade;//联盟等级
	private String unionQrCode;//联盟二维码
	private String unionLogoUrl;//联盟图标
	private String unionSynopsis;//联盟简介
	private String country;//所在国家.默认为：中国
	private String province;//所在省份
	private String city;//所在城市
	private String area;//所在区(县)
	private String address;//地址
	private String hospitalId;//医院编号.外键:hospital_info(hospitalInfoCode)
	private String departmentId;//科室编号(大类).外键:hospital_department(departmentId)
	private String departmentSecondId;//二级科室编号(小类).外键:hospital_department(departmentId)
	private String applyDoctorCode;//申请医生-关联编码.外键:sys_user_doctor_info(doctorCode)
	private Integer flagApplyType;//申请类型.0:未知;1:平台申请;
	private Date applyDate;//申请日期
	private String applyReason;//申请描述
	private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述

	private String applyDoctorTitle;//医生职称

	private String unionLogoImgUrl;	//联盟图片URL

	private String base64ImgData;	//联盟图片base64
	private String provideString;	//省份字符串
	private String cityString;	//城市字符串
	private String areaString;	//地区字符串
	private String hospitalString;	//医院字符串
	private String departmentString;	//科室字符串
	private String departmentSecondString;	//二级科室字符串

    private String loginDoctorPosition;
    private String operDoctorCode;
    private String operDoctorName;


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

    public String getProvideString() {
		return provideString;
	}

	public void setProvideString(String provideString) {
		this.provideString = provideString;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getAreaString() {
		return areaString;
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

	public String getHospitalString() {
		return hospitalString;
	}

	public void setHospitalString(String hospitalString) {
		this.hospitalString = hospitalString;
	}

	public String getDepartmentString() {
		return departmentString;
	}

	public void setDepartmentString(String departmentString) {
		this.departmentString = departmentString;
	}

	public String getDepartmentSecondString() {
		return departmentSecondString;
	}

	public void setDepartmentSecondString(String departmentSecondString) {
		this.departmentSecondString = departmentSecondString;
	}

	public String getBase64ImgData() {
		return base64ImgData;
	}

	public void setBase64ImgData(String base64ImgData) {
		this.base64ImgData = base64ImgData;
	}

	public String getUnionLogoImgUrl() {
		return unionLogoImgUrl;
	}

	public void setUnionLogoImgUrl(String unionLogoImgUrl) {
		this.unionLogoImgUrl = unionLogoImgUrl;
	}

	public String getApplyDoctorTitle() {
		return applyDoctorTitle;
	}

	public void setApplyDoctorTitle(String applyDoctorTitle) {
		this.applyDoctorTitle = applyDoctorTitle;
	}

	public Integer getUnionId() {
		return unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}

	public String getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(String unionCode) {
		this.unionCode = unionCode;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public String getUnionNameAlias() {
		return unionNameAlias;
	}

	public void setUnionNameAlias(String unionNameAlias) {
		this.unionNameAlias = unionNameAlias;
	}

	public String getUnionNameSpell() {
		return unionNameSpell;
	}

	public void setUnionNameSpell(String unionNameSpell) {
		this.unionNameSpell = unionNameSpell;
	}

	public Integer getUnionGrade() {
		return unionGrade;
	}

	public void setUnionGrade(Integer unionGrade) {
		this.unionGrade = unionGrade;
	}

	public String getUnionQrCode() {
		return unionQrCode;
	}

	public void setUnionQrCode(String unionQrCode) {
		this.unionQrCode = unionQrCode;
	}

	public String getUnionLogoUrl() {
		return unionLogoUrl;
	}

	public void setUnionLogoUrl(String unionLogoUrl) {
		this.unionLogoUrl = unionLogoUrl;
	}

	public String getUnionSynopsis() {
		return unionSynopsis;
	}

	public void setUnionSynopsis(String unionSynopsis) {
		this.unionSynopsis = unionSynopsis;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentSecondId() {
		return departmentSecondId;
	}

	public void setDepartmentSecondId(String departmentSecondId) {
		this.departmentSecondId = departmentSecondId;
	}

	public String getApplyDoctorCode() {
		return applyDoctorCode;
	}

	public void setApplyDoctorCode(String applyDoctorCode) {
		this.applyDoctorCode = applyDoctorCode;
	}

	public Integer getFlagApplyType() {
		return flagApplyType;
	}

	public void setFlagApplyType(Integer flagApplyType) {
		this.flagApplyType = flagApplyType;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public Integer getFlagApplyState() {
		return flagApplyState;
	}

	public void setFlagApplyState(Integer flagApplyState) {
		this.flagApplyState = flagApplyState;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
}
