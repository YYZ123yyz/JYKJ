package entity.basicDate;


/**
 * 【医院】
 * 医院基础信息
 * 
 * @author JiaQ
 */
public class ProvideHospitalInfo implements java.io.Serializable {
	private Integer hospitalInfoId;//编号
	private String hospitalInfoCode;//关联编码
	private Integer basicsRegionId;//地区编码.外键:basics_region中字段basicsRegionId
	private String hospitalName;//医院名称
	private String hospitalNameSpell;//名称助记码
	private String hospitalNameChinese;//中文简称
	private String hospitalNameEnglish;//英文简称
	private Integer hospitalType;//医院类型
	private Integer hospitalNature;//医院性质
	private String hospitalFax;//医院传真
	private String hospitalEmail;//医院邮箱
	private String hospitalPhone;//医院电话
	private String hospitalAddress;//医院地址
	private String country;//所在国家.默认为：0(中国)
	private String province;//所在省份.外键:basics_region(region_id)
	private String city;//所在城市.外键:basics_region(region_id)
	private String area;//所在区(县).外键:basics_region(region_id)
	private String longitude;//坐标经度
	private String latitude;//坐标纬度
	private String logoImg;//公司Logo图片

	public Integer getHospitalInfoId() {
		return hospitalInfoId;
	}

	public void setHospitalInfoId(Integer hospitalInfoId) {
		this.hospitalInfoId = hospitalInfoId;
	}

	public String getHospitalInfoCode() {
		return hospitalInfoCode;
	}

	public void setHospitalInfoCode(String hospitalInfoCode) {
		this.hospitalInfoCode = hospitalInfoCode;
	}

	public Integer getBasicsRegionId() {
		return basicsRegionId;
	}

	public void setBasicsRegionId(Integer basicsRegionId) {
		this.basicsRegionId = basicsRegionId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalNameSpell() {
		return hospitalNameSpell;
	}

	public void setHospitalNameSpell(String hospitalNameSpell) {
		this.hospitalNameSpell = hospitalNameSpell;
	}

	public String getHospitalNameChinese() {
		return hospitalNameChinese;
	}

	public void setHospitalNameChinese(String hospitalNameChinese) {
		this.hospitalNameChinese = hospitalNameChinese;
	}

	public String getHospitalNameEnglish() {
		return hospitalNameEnglish;
	}

	public void setHospitalNameEnglish(String hospitalNameEnglish) {
		this.hospitalNameEnglish = hospitalNameEnglish;
	}

	public Integer getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(Integer hospitalType) {
		this.hospitalType = hospitalType;
	}

	public Integer getHospitalNature() {
		return hospitalNature;
	}

	public void setHospitalNature(Integer hospitalNature) {
		this.hospitalNature = hospitalNature;
	}

	public String getHospitalFax() {
		return hospitalFax;
	}

	public void setHospitalFax(String hospitalFax) {
		this.hospitalFax = hospitalFax;
	}

	public String getHospitalEmail() {
		return hospitalEmail;
	}

	public void setHospitalEmail(String hospitalEmail) {
		this.hospitalEmail = hospitalEmail;
	}

	public String getHospitalPhone() {
		return hospitalPhone;
	}

	public void setHospitalPhone(String hospitalPhone) {
		this.hospitalPhone = hospitalPhone;
	}

	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
}
