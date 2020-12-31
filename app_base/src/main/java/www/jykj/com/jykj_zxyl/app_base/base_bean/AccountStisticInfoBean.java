package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 21:00
 */
public class AccountStisticInfoBean {

    /**
     * accountDoctorIncomeOutList : [{"incomeOutType":"21","incomeOutTypeName":"医生充值","incomeOutTypeRGB":"0xCCE198","incomeOutTypeValue":0.1}]
     * totalIncome : 0.1
     * totalOut : 0.0
     */

    private double totalIncome;
    private double totalOut;
    private List<AccountDoctorIncomeOutListBean> accountDoctorIncomeOutList;

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(double totalOut) {
        this.totalOut = totalOut;
    }

    public List<AccountDoctorIncomeOutListBean> getAccountDoctorIncomeOutList() {
        return accountDoctorIncomeOutList;
    }

    public void setAccountDoctorIncomeOutList(List<AccountDoctorIncomeOutListBean> accountDoctorIncomeOutList) {
        this.accountDoctorIncomeOutList = accountDoctorIncomeOutList;
    }

    public static class AccountDoctorIncomeOutListBean {
        /**
         * incomeOutType : 21
         * incomeOutTypeName : 医生充值
         * incomeOutTypeRGB : 0xCCE198
         * incomeOutTypeValue : 0.1
         */

        private String incomeOutType;
        private String incomeOutTypeName;
        private String incomeOutTypeRGB;
        private float incomeOutTypeValue;

        public String getIncomeOutType() {
            return incomeOutType;
        }

        public void setIncomeOutType(String incomeOutType) {
            this.incomeOutType = incomeOutType;
        }

        public String getIncomeOutTypeName() {
            return incomeOutTypeName;
        }

        public void setIncomeOutTypeName(String incomeOutTypeName) {
            this.incomeOutTypeName = incomeOutTypeName;
        }

        public String getIncomeOutTypeRGB() {
            return incomeOutTypeRGB;
        }

        public void setIncomeOutTypeRGB(String incomeOutTypeRGB) {
            this.incomeOutTypeRGB = incomeOutTypeRGB;
        }

        public float getIncomeOutTypeValue() {
            return incomeOutTypeValue;
        }

        public void setIncomeOutTypeValue(float incomeOutTypeValue) {
            this.incomeOutTypeValue = incomeOutTypeValue;
        }
    }
}
