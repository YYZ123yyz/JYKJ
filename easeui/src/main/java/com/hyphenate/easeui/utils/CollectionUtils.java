package com.hyphenate.easeui.utils;

import java.util.Collection;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-13 14:53
 */
public class CollectionUtils {
    private CollectionUtils() {
        throw new AssertionError();
    }

    public static <V> boolean isEmpty(Collection<V> c) {
        return c == null || c.size() == 0;
    }
}
