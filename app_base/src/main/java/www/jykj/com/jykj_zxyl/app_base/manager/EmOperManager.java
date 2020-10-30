package www.jykj.com.jykj_zxyl.app_base.manager;


/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-29 14:47
 */
public class EmOperManager {

    private static class InstanceHolder {
        private static final EmOperManager INSTANCE = new EmOperManager();
    }

    public static EmOperManager getInstance() {
        return InstanceHolder.INSTANCE;
    }



}
