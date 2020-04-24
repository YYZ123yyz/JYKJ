package entity.mySelf.doctorScheduling;

public class DoctorSetScheduling {

    private int date;                           //时间（星期一到日 1~~7）
    private int morning;                        //[早]是否坐诊.0:否;1:是;
    private int morningSourceNum;               //[预留][周一][早]剩余号源数量
    private int noon;                           //[中]是否坐诊.0:否;1:是;
    private int noonSourceNum;                  //[预留][周一][中]剩余号源数量
    private int night;                          //[晚]是否坐诊.0:否;1:是;
    private int nightSourceNum;                 //[预留][周一][晚]剩余号源数量

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMorning() {
        return morning;
    }

    public void setMorning(int morning) {
        this.morning = morning;
    }

    public int getMorningSourceNum() {
        return morningSourceNum;
    }

    public void setMorningSourceNum(int morningSourceNum) {
        this.morningSourceNum = morningSourceNum;
    }

    public int getNoon() {
        return noon;
    }

    public void setNoon(int noon) {
        this.noon = noon;
    }

    public int getNoonSourceNum() {
        return noonSourceNum;
    }

    public void setNoonSourceNum(int noonSourceNum) {
        this.noonSourceNum = noonSourceNum;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public int getNightSourceNum() {
        return nightSourceNum;
    }

    public void setNightSourceNum(int nightSourceNum) {
        this.nightSourceNum = nightSourceNum;
    }
}
