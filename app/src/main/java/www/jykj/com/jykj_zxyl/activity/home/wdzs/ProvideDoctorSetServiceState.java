package www.jykj.com.jykj_zxyl.activity.home.wdzs;


/**
 * 医生设置类-就诊类型开通状态
 * 
 * @author JiaQ
 */

public class ProvideDoctorSetServiceState implements java.io.Serializable {

	private String doctorCode;//医生编码
	private String doctorName;//医生姓名
	
	private Integer flagDoctorStatus;//医生认证状态(0:未认证;1:已认证;)
	
	private Integer flagImgText;//图文就诊.默认图文就诊为开通状态.0:未开通;1:已开通;
	private Integer flagAudio;//音频就诊.0:未开通;1:已开通;
	private Integer flagVideo;//视频就诊.0:未开通;1:已开通;
	private Integer flagSigning;//签约就诊.0:未开通;1:已开通;
	private Integer flagPhone;//电话就诊.0:未开通;1:已开通;


	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;

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

	public Integer getFlagDoctorStatus() {
		return flagDoctorStatus;
	}

	public void setFlagDoctorStatus(Integer flagDoctorStatus) {
		this.flagDoctorStatus = flagDoctorStatus;
	}

	public Integer getFlagImgText() {
		return flagImgText;
	}

	public void setFlagImgText(Integer flagImgText) {
		this.flagImgText = flagImgText;
	}

	public Integer getFlagAudio() {
		return flagAudio;
	}

	public void setFlagAudio(Integer flagAudio) {
		this.flagAudio = flagAudio;
	}

	public Integer getFlagVideo() {
		return flagVideo;
	}

	public void setFlagVideo(Integer flagVideo) {
		this.flagVideo = flagVideo;
	}

	public Integer getFlagSigning() {
		return flagSigning;
	}

	public void setFlagSigning(Integer flagSigning) {
		this.flagSigning = flagSigning;
	}

	public Integer getFlagPhone() {
		return flagPhone;
	}

	public void setFlagPhone(Integer flagPhone) {
		this.flagPhone = flagPhone;
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
