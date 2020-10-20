package www.jykj.com.jykj_zxyl.appointment.data;

import java.util.Arrays;
import java.util.List;

import jsc.kit.wheel.base.WheelItem;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 16:14
 */
public class DataUtil {
    public static List<String> getStartTimes(){
        List<String> stringList= Arrays.asList("0:00","00:30","1:00","1:30","2:00","2:30","3:00","3:30","4:00","4:30","5:00","5:30","6:00","6:30","7:00","7:30"
                ,"8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
        return stringList;
    }

    public static List<String> getEndTimes(){
        List<String> stringList= Arrays.asList("0:30","1:00","1:30","2:00","2:30","3:00","3:30","4:00","4:30","5:00","5:30","6:00","6:30","7:00","7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00",
                "15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30","24:00");
        return stringList;
    }



    public static WheelItem[] convertStrToWheelArry(List<String> strings){
        WheelItem[] items = new WheelItem[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            items[i]=new WheelItem(strings.get(i));
        }
        return items;
    }

    public static WheelItem[] convertObjToWheelArry(List<BaseReasonBean> baseReasonBeans){
        WheelItem[] items = new WheelItem[baseReasonBeans.size()];
        for (int i = 0; i < baseReasonBeans.size(); i++) {
            items[i]=new WheelItem(baseReasonBeans.get(i).getAttrName());
        }
        return items;
    }

    public static BaseReasonBean getBaseReasonBeanByAttrName(String attrName
            ,List<BaseReasonBean> baseReasonBeans){
        BaseReasonBean currentBaseReasonBean=null;
        for (BaseReasonBean baseReasonBean : baseReasonBeans) {
            String name = baseReasonBean.getAttrName();
            if (name.equals(attrName)) {
                currentBaseReasonBean= baseReasonBean;
                break;
            }
        }
        return currentBaseReasonBean;
    }
}
