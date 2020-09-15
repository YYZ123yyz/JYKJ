package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 17:59
 */
public class InspectionItemPositionBean implements Serializable {


    /**
     * configPositionCode : 300000001
     * inspectionCode : 1000000004
     * inspectionName : 胸部CT
     * positionCode : 500000001
     * positionName : 心脏
     * positionType : 10
     * positionTypeName : 部位
     */

    private String configPositionCode;
    private String inspectionCode;
    private String inspectionName;
    private String positionCode;
    private String positionName;
    private int positionType;
    private String positionTypeName;
    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getConfigPositionCode() {
        return configPositionCode;
    }

    public void setConfigPositionCode(String configPositionCode) {
        this.configPositionCode = configPositionCode;
    }

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getPositionType() {
        return positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }

    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
    }
}
