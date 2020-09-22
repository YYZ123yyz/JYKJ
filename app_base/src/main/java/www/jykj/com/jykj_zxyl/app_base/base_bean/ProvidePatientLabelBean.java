package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

public class ProvidePatientLabelBean implements Serializable {


    /**
     * createDate : 1594113626000
     * createMan : 平台智能AI
     * flagUseState : 1
     * userLabelSecondName : 待定
     */

    private long createDate;
    private String createMan;
    private int flagUseState;
    private String userLabelSecondName;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public int getFlagUseState() {
        return flagUseState;
    }

    public void setFlagUseState(int flagUseState) {
        this.flagUseState = flagUseState;
    }

    public String getUserLabelSecondName() {
        return userLabelSecondName;
    }

    public void setUserLabelSecondName(String userLabelSecondName) {
        this.userLabelSecondName = userLabelSecondName;
    }
}
