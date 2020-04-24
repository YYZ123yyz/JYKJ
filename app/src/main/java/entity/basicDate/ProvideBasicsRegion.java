package entity.basicDate;


/**
 * 【基础数据】
 * 地区表
 * 
 * @author JiaQ
 */
public class ProvideBasicsRegion implements java.io.Serializable {
	private Integer basicsRegionId;//编号
	private String region_id;//地区主键编号
	private String region_name;//地区名称
	private String region_parent_id;//地区父id.[省、自治区、直辖市为:-1]
	private Integer region_level;//地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县

	public Integer getBasicsRegionId() {
		return basicsRegionId;
	}

	public void setBasicsRegionId(Integer basicsRegionId) {
		this.basicsRegionId = basicsRegionId;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_parent_id() {
		return region_parent_id;
	}

	public void setRegion_parent_id(String region_parent_id) {
		this.region_parent_id = region_parent_id;
	}

	public Integer getRegion_level() {
		return region_level;
	}

	public void setRegion_level(Integer region_level) {
		this.region_level = region_level;
	}
}
