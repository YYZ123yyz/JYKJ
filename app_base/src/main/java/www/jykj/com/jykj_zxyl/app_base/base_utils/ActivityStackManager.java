package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;


/**
 * Activity管理类
 */
public class ActivityStackManager {
    /**
     * instance
     */
    private static ActivityStackManager instance;
    /**
     * 管理activity
     */
    private Stack<Activity> listActivity;

    private ActivityStackManager() {
        listActivity = new Stack<>();
    }

    /**
     * 获取Instance
     */
    public static ActivityStackManager getInstance() {
        if (instance == null) {
            synchronized (ActivityStackManager.class) {
                if (instance == null) {
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }

    /**
     * 将Activity添加到队列中，onCreate()中执行
     *
     * @param activity activity_history
     */
    public void add(Activity activity) {
        if (activity == null) {
            return;
        }
        listActivity.push(activity);
    }

    /**
     * 将Activity添加到队列中，onCreate()中执行
     *
     * @param activity activity_history
     */
    public void remove(Activity activity) {
        if (activity == null) {
            return;
        }
        listActivity.remove(activity);
    }

    /**
     * 清除栈中的Activity
     */
    public void finishActivityList() {
        while (!listActivity.empty()) {
            try {
                //查看堆栈顶部的对象，但不从堆栈中移除它
                listActivity.peek().finish();
                //移除堆栈顶部的对象，并作为此函数的值返回该对象
                listActivity.pop();
            } catch (Exception e) {
                ExceptionUtil.handleException(e);
            }
        }
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getTop() {
        if (!listActivity.empty()) {
            return listActivity.peek();
        }
        return null;
    }
    /**
     * 新建一个activity打开
     * @param cls
     */
    public void gotoActivity(Activity activity,Class<?> cls) {
        Intent intent;
        intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }
    /**
     * finish指定Activity
     *
     * @param activityClass activityClass
     */
    public void finish(Class<? extends Activity> activityClass) {
        for (Activity activity : listActivity) {
            if (activity.getClass().getName().equals(activityClass.getName())) {
                activity.finish();
            }
        }
    }

    /**
     * 判断队列中是否存在指定Activity
     *
     * @param activityClass activityClass
     */
    public boolean exists(Class<? extends Activity> activityClass) {
        for (Activity activity : listActivity) {
            if (activity.getClass().getName().equals(activityClass.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断指定Activity是否在栈顶
     *
     * @param activityClass activityClass
     */
    public boolean existsTop(Class<? extends Activity> activityClass) {
        if (!listActivity.empty()) {
            return listActivity.peek().getClass().getName().equals(activityClass.getName());
        }
        return false;
    }
}
