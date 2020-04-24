package entity.DoctorInfo;

import java.util.List;


/**
 * 【医患互动】
 * 医生联盟基础信息
 * 
 * @author JiaQ
 */
public class InteractDoctorUnionInfo implements java.io.Serializable {
	private Integer unionId;//联盟ID
	private String unionCode;//联盟编码
	private String unionLogoUrl;//头像地址
	private String unionName;//联盟名称
	private	boolean	click;		//是否点击

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	private List<InteractDoctorUnionPersonnel> doctorUnionPersonnelList;

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

	public List<InteractDoctorUnionPersonnel> getDoctorUnionPersonnelList() {
		return doctorUnionPersonnelList;
	}

	public void setDoctorUnionPersonnelList(List<InteractDoctorUnionPersonnel> doctorUnionPersonnelList) {
		this.doctorUnionPersonnelList = doctorUnionPersonnelList;
	}
}
