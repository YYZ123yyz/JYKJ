package entity.basicDate;


/**
 * 【医院】
 * 科室信息
 * 
 * @author JiaQ
 */
public class ProvideHospitalDepartment implements java.io.Serializable {
	private	String	hospitalInfoCode;				//医院编码
	private Integer hospitalDepartmentId;//编号
	private String departmentName;//科室名称
	private String departmentNameSpell;//科室名称拼音助记码
	private String departmentFax;//科室传真
	private String departmentPhone;//科室电话
	private String departmentAddress;//科室地址
	private Integer sort;//排序编号
	private Integer parentId;//上级科室编号【根节点ID为:0;】


	public String getHospitalInfoCode() {
		return hospitalInfoCode;
	}

	public void setHospitalInfoCode(String hospitalInfoCode) {
		this.hospitalInfoCode = hospitalInfoCode;
	}

	public Integer getHospitalDepartmentId() {
		return hospitalDepartmentId;
	}

	public void setHospitalDepartmentId(Integer hospitalDepartmentId) {
		this.hospitalDepartmentId = hospitalDepartmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentNameSpell() {
		return departmentNameSpell;
	}

	public void setDepartmentNameSpell(String departmentNameSpell) {
		this.departmentNameSpell = departmentNameSpell;
	}

	public String getDepartmentFax() {
		return departmentFax;
	}

	public void setDepartmentFax(String departmentFax) {
		this.departmentFax = departmentFax;
	}

	public String getDepartmentPhone() {
		return departmentPhone;
	}

	public void setDepartmentPhone(String departmentPhone) {
		this.departmentPhone = departmentPhone;
	}

	public String getDepartmentAddress() {
		return departmentAddress;
	}

	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
