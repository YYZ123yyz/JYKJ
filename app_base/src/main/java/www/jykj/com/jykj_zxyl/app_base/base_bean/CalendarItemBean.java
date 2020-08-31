package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 13:21
 */
public class CalendarItemBean {

    /**
     * reserveTotal : 0
     * times : 1598630400000
     * week : 7
     */

    private int reserveTotal;
    private long times;
    private int week;
    private boolean isChoosed;
    public int getReserveTotal() {
        return reserveTotal;
    }

    public void setReserveTotal(int reserveTotal) {
        this.reserveTotal = reserveTotal;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
