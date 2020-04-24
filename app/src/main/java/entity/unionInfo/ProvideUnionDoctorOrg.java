package entity.unionInfo;


/**
 * 【医生联盟】
 * 联盟层级(组织架构)
 * 
 * @author JiaQ
 */

public class ProvideUnionDoctorOrg implements java.io.Serializable {
	private Integer unionOrgId;//联盟组织架构编号
	private String unionCode;//联盟编码
	private String orgName;//层级名称
	private String orgNameSpell;//拼音助记码
	private Integer upOrgId;//上级组织编号.根级节点为:-1
	private Integer sort;//排序编号

    private String     operType;       // 操作类型。1：新增；2：修改；3：删除
    private String  operDoctorCode; //操作医生编码
	private String loginDoctorPosition; //

	private	boolean	choice;				//是否选中

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperDoctorCode() {
        return operDoctorCode;
    }

    public void setOperDoctorCode(String operDoctorCode) {
        this.operDoctorCode = operDoctorCode;
    }

    public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public Integer getUnionOrgId() {
		return unionOrgId;
	}

	public void setUnionOrgId(Integer unionOrgId) {
		this.unionOrgId = unionOrgId;
	}

	public String getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(String unionCode) {
		this.unionCode = unionCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNameSpell() {
		return orgNameSpell;
	}

	public void setOrgNameSpell(String orgNameSpell) {
		this.orgNameSpell = orgNameSpell;
	}

	public Integer getUpOrgId() {
		return upOrgId;
	}

	public void setUpOrgId(Integer upOrgId) {
		this.upOrgId = upOrgId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
