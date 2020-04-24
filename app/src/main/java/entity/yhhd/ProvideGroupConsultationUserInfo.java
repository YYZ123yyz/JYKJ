package entity.yhhd;

import java.util.Date;


/**
 * 会诊-消息列表所需的用户信息
 * 
 * @author JiaQ
 */
public class ProvideGroupConsultationUserInfo implements java.io.Serializable {
	private Integer userUseType;//用户的标识(5:医生;6:患者;)
	private Date serviceStopDate;//服务截止日期
	private	String	loginUserPosition;
	private	String	userCode;

	public Integer getUserUseType() {
		return userUseType;
	}

	public void setUserUseType(Integer userUseType) {
		this.userUseType = userUseType;
	}

	public Date getServiceStopDate() {
		return serviceStopDate;
	}

	public void setServiceStopDate(Date serviceStopDate) {
		this.serviceStopDate = serviceStopDate;
	}

	public String getLoginUserPosition() {
		return loginUserPosition;
	}

	public void setLoginUserPosition(String loginUserPosition) {
		this.loginUserPosition = loginUserPosition;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
