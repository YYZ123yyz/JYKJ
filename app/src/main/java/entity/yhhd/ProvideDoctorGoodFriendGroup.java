package entity.yhhd;

import java.util.List;


/**
 * ******************************** 非对称实体类
 * 【医生好友】
 * 医生好友-分组标题
 * 
 * @author JiaQ
 */

public class ProvideDoctorGoodFriendGroup implements java.io.Serializable {
	private Integer groupId;//分组ID
	private String groupCode;//分组编码
	private String groupLogoUrl;//组头像地址
	private String groupName;//分组标题名称
	private boolean click;

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	private List<ProvideDoctorGoodFriendInfo> doctorGoodFriendInfoList;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupLogoUrl() {
		return groupLogoUrl;
	}

	public void setGroupLogoUrl(String groupLogoUrl) {
		this.groupLogoUrl = groupLogoUrl;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ProvideDoctorGoodFriendInfo> getDoctorGoodFriendInfoList() {
		return doctorGoodFriendInfoList;
	}

	public void setDoctorGoodFriendInfoList(List<ProvideDoctorGoodFriendInfo> doctorGoodFriendInfoList) {
		this.doctorGoodFriendInfoList = doctorGoodFriendInfoList;
	}
}
