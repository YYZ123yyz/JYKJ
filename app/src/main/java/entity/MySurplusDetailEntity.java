package entity;

public class MySurplusDetailEntity {

    private                 int                     suType;                  //类型
    private                 String                  suTypeString;           //类型
    private                 String                  date;                   //时间
    private                 String                  price;                  //价格
    private                 String                  ye;                     //余额

    public int getSuType() {
        return suType;
    }

    public void setSuType(int suType) {
        this.suType = suType;
    }

    public String getSuTypeString() {
        return suTypeString;
    }

    public void setSuTypeString(String suTypeString) {
        this.suTypeString = suTypeString;
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

    public String getYe() {
        return ye;
    }

    public void setYe(String ye) {
        this.ye = ye;
    }
}
