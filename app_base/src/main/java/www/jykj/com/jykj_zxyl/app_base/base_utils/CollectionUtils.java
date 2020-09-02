package www.jykj.com.jykj_zxyl.app_base.base_utils;

import java.util.Collection;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 18:21
 */
public class CollectionUtils {
    private CollectionUtils() {
        throw new AssertionError();
    }

    public static <V> boolean isEmpty(Collection<V> c) {
        return c == null || c.size() == 0;
    }
}
