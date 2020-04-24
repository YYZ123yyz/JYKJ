package entity;

/**
 * 排班信息
 */
public class PBInfo {
    private             String                  date;           //时间
    private             int                     morning;        //早上  0=无  1=有
    private             int                     afternoon;      //中午  0=无  1=有
    private             int                     ninght;         //晚上  0=无  1=有


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMorning() {
        return morning;
    }

    public void setMorning(int morning) {
        this.morning = morning;
    }

    public int getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(int afternoon) {
        this.afternoon = afternoon;
    }

    public int getNinght() {
        return ninght;
    }

    public void setNinght(int ninght) {
        this.ninght = ninght;
    }
}
