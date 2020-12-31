package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.io.Serializable;


public class AuthorityBean implements Serializable {


    /**
     * userGradeCode : 10
     * userGradeName : 院长
     */

    private String userGradeCode;
    private String userGradeName;

    public String getUserGradeCode() {
        return userGradeCode;
    }

    public void setUserGradeCode(String userGradeCode) {
        this.userGradeCode = userGradeCode;
    }

    public String getUserGradeName() {
        return userGradeName;
    }

    public void setUserGradeName(String userGradeName) {
        this.userGradeName = userGradeName;
    }
}
