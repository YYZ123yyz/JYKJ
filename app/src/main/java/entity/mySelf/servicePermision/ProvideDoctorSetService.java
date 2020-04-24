package entity.mySelf.servicePermision;


/**
 * 医生设置类-就诊类型参数相关设置
 * 
 * @author JiaQ
 */
public class ProvideDoctorSetService implements java.io.Serializable {
	private Integer serviceSetId;//就诊类型(服务权限)设置编号
	private String doctorCode;//医生关联编码
	private Integer serviceType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;
	private Integer flagOpening;//是否开通此类型服务.0:否;1:是;
	private Float priceBasics;//基础价格
	private Float pricePremium;//溢价价格
	private Integer sourceNumBasics;//基础号源数量
	private Integer sourceNumPremium;//溢价号源数量
	private Integer sourceNumFree;//免费号源数量
	private Integer serviceDate;//服务时间.Eg.图文:48小时;音视频:1周内;签约周期:1个月;
	private Integer limitNum;//限制数量.图文(数量):20次;音视频(分钟数):10分钟;签约周期(月):1个月;
	private Integer flagUseState;//使用状态.0:未使用;1:使用中;
	
	
	
	/* 【非签约】展示类属性 */
	private String serviceDateStr;//服务时间.展示使用
	private String limitNumStr;//限制数量.展示使用
	
	/* 【签约】展示类属性 */
	private String signingImgTextStr;//签约设置,图文消息数量.展示使用
	private String signingAudioStr;//签约设置,音频通话时长.展示使用
	private String signingVideoStr;//签约设置,视频通话时长.展示使用

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

	public Integer getServiceSetId() {
		return serviceSetId;
	}

	public void setServiceSetId(Integer serviceSetId) {
		this.serviceSetId = serviceSetId;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getFlagOpening() {
		return flagOpening;
	}

	public void setFlagOpening(Integer flagOpening) {
		this.flagOpening = flagOpening;
	}

	public Float getPriceBasics() {
		return priceBasics;
	}

	public void setPriceBasics(Float priceBasics) {
		this.priceBasics = priceBasics;
	}

	public Float getPricePremium() {
		return pricePremium;
	}

	public void setPricePremium(Float pricePremium) {
		this.pricePremium = pricePremium;
	}

	public Integer getSourceNumBasics() {
		return sourceNumBasics;
	}

	public void setSourceNumBasics(Integer sourceNumBasics) {
		this.sourceNumBasics = sourceNumBasics;
	}

	public Integer getSourceNumPremium() {
		return sourceNumPremium;
	}

	public void setSourceNumPremium(Integer sourceNumPremium) {
		this.sourceNumPremium = sourceNumPremium;
	}

	public Integer getSourceNumFree() {
		return sourceNumFree;
	}

	public void setSourceNumFree(Integer sourceNumFree) {
		this.sourceNumFree = sourceNumFree;
	}

	public Integer getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Integer serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public String getServiceDateStr() {
		return serviceDateStr;
	}

	public void setServiceDateStr(String serviceDateStr) {
		this.serviceDateStr = serviceDateStr;
	}

	public String getLimitNumStr() {
		return limitNumStr;
	}

	public void setLimitNumStr(String limitNumStr) {
		this.limitNumStr = limitNumStr;
	}

	public String getSigningImgTextStr() {
		return signingImgTextStr;
	}

	public void setSigningImgTextStr(String signingImgTextStr) {
		this.signingImgTextStr = signingImgTextStr;
	}

	public String getSigningAudioStr() {
		return signingAudioStr;
	}

	public void setSigningAudioStr(String signingAudioStr) {
		this.signingAudioStr = signingAudioStr;
	}

	public String getSigningVideoStr() {
		return signingVideoStr;
	}

	public void setSigningVideoStr(String signingVideoStr) {
		this.signingVideoStr = signingVideoStr;
	}
}
