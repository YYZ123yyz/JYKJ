package entity;

/**
 * 我的积分
 */
public class MyIntegralEntity {

    private                 int                     integralType;                  //积分类型类型（0=支出  1=收入）
    private                 String                  integralTypeString;           //类型
    private                 String                  date;                   //时间
    private                 String                  price;                  //价格

    public int getIntegralType() {
        return integralType;
    }

    public void setIntegralType(int integralType) {
        this.integralType = integralType;
    }

    public String getIntegralTypeString() {
        return integralTypeString;
    }

    public void setIntegralTypeString(String integralTypeString) {
        this.integralTypeString = integralTypeString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
