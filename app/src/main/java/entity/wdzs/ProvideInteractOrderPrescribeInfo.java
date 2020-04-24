package entity.wdzs;


import java.io.Serializable;

/**
 * 【医患互动】
 * 患者就诊-处方记录[处方头文件(分组)后-每组的内容]
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderPrescribeInfo implements Serializable {
	private String drugCode;//药品编码.外键:drug_info(drugId)
	private String drugName;//药品名称
	private String productBatchNumber;//药品批次号
	private String specUnit;//单位
	private String specName;//格规
	private Float drugAmount;//数量
	private Float drugPrice;//单价
	private Float drugMoneys;//金额
	private Integer useNum;//每次服用数量(片、粒)
	private Integer useFrequency;//用于频率(多少次/每天)
	private Integer useCycle;//服用周期(服药天数)
	private String useDesc;//用药描述

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getProductBatchNumber() {
		return productBatchNumber;
	}

	public void setProductBatchNumber(String productBatchNumber) {
		this.productBatchNumber = productBatchNumber;
	}

	public String getSpecUnit() {
		return specUnit;
	}

	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Float getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(Float drugAmount) {
		this.drugAmount = drugAmount;
	}

	public Float getDrugPrice() {
		return drugPrice;
	}

	public void setDrugPrice(Float drugPrice) {
		this.drugPrice = drugPrice;
	}

	public Float getDrugMoneys() {
		return drugMoneys;
	}

	public void setDrugMoneys(Float drugMoneys) {
		this.drugMoneys = drugMoneys;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public Integer getUseFrequency() {
		return useFrequency;
	}

	public void setUseFrequency(Integer useFrequency) {
		this.useFrequency = useFrequency;
	}

	public Integer getUseCycle() {
		return useCycle;
	}

	public void setUseCycle(Integer useCycle) {
		this.useCycle = useCycle;
	}

	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}
}
