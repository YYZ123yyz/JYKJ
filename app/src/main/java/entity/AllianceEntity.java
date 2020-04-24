package entity;

public class AllianceEntity {


    private                 String                  allianceName;                  //联盟名称
    private                 String                  allianceRemark;                //联盟说明

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public String getAllianceRemark() {
        return allianceRemark;
    }

    public void setAllianceRemark(String allianceRemark) {
        this.allianceRemark = allianceRemark;
    }
}
