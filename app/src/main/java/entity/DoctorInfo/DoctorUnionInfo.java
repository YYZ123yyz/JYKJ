package entity.DoctorInfo;


/**
 * 医生联盟基础信息
 * 
 * @author JiaQ
 */
public class DoctorUnionInfo implements java.io.Serializable {
	private Integer unionId;//联盟ID
	private String unionCode;//联盟编码
	private String unionLogoUrl;//头像地址
	private String unionName;//联盟名称
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String city;//用户所在城市
	private String hospitalName;//所在医院名称
	private String departmentName;//所在科室名称
	private String unionSynopsis;//联盟简介
	private long createDate;//联盟创建日期
	private String unionCreateDateStr;//联盟创建日期

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

	public String getUnionLogoUrl() {
		return unionLogoUrl;
	}

	public void setUnionLogoUrl(String unionLogoUrl) {
		this.unionLogoUrl = unionLogoUrl;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
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

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUnionSynopsis() {
		return unionSynopsis;
	}

	public void setUnionSynopsis(String unionSynopsis) {
		this.unionSynopsis = unionSynopsis;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getUnionCreateDateStr() {
		return unionCreateDateStr;
	}

	public void setUnionCreateDateStr(String unionCreateDateStr) {
		this.unionCreateDateStr = unionCreateDateStr;
	}
}
