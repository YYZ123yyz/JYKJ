package www.jykj.com.jykj_zxyl.wxapi;

public class PayInfoBean {
    private boolean paySucess;


    public PayInfoBean(boolean is){
        this.paySucess = is;
    }
    public boolean isPaySucess() {
        return paySucess;
    }

    public void setPaySucess(boolean paySucess) {
        this.paySucess = paySucess;
    }
}
