package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-07 15:21
 */
public class DrugDosageBean {


    /**
     * dosageCode : be87duf92m044326b2a6e856bdabd003
     * dosageName : 片剂
     * flagUseState : 1
     * sort : 1
     */

    private String dosageCode;
    private String dosageName;
    private int flagUseState;
    private int sort;

    public String getDosageCode() {
        return dosageCode;
    }

    public void setDosageCode(String dosageCode) {
        this.dosageCode = dosageCode;
    }

    public String getDosageName() {
        return dosageName;
    }

    public void setDosageName(String dosageName) {
        this.dosageName = dosageName;
    }

    public int getFlagUseState() {
        return flagUseState;
    }

    public void setFlagUseState(int flagUseState) {
        this.flagUseState = flagUseState;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
