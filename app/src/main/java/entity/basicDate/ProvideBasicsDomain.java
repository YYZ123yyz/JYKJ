package entity.basicDate;



/**
 * 【基础数据】
 * 数据字典
 * 
 * @author JiaQ
 */
public class ProvideBasicsDomain implements java.io.Serializable {
	private Integer basicsDomainId;//编号
	private Integer baseCode;//基础编码
	private String baseName;//基础名称
	private Integer attrCode;//属性编码
	private String attrName;//属性名称


	public Integer getBasicsDomainId() {
		return basicsDomainId;
	}

	public void setBasicsDomainId(Integer basicsDomainId) {
		this.basicsDomainId = basicsDomainId;
	}

	public Integer getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(Integer baseCode) {
		this.baseCode = baseCode;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public Integer getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(Integer attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
}
