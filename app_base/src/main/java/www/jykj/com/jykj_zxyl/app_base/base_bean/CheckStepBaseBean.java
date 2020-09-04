package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-04 09:47
 */
public class CheckStepBaseBean implements Serializable {
    private String confim;
    private String message;
    private String status;

    public String getConfim() {
        return confim;
    }

    public void setConfim(String confim) {
        this.confim = confim;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
