
package com.sup.supHome.common.cache;

import java.util.Collection;

import com.sup.supHome.common.util.Constants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @desc EhCache工具类
 */
public class EhCacheTemplate {

    private static CacheManager singletonManager = CacheManager.create();
    private static Cache        localCache       = null;
    public static String        cacheName        = Constants.DEFAULT_CACHE;
    static {
        localCache = singletonManager.getCache(cacheName);
    }

    public static CacheManager getSingletonManager() {
        return singletonManager;
    }

    /**
     * 创建一个cache
     * @param cacheName
     */
    public static void createCache(String cacheName) {
        singletonManager.addCache(cacheName);
    }

    /**
     * 获取一个cahce
     * @param cacheName
     * @return
     */
    public static Cache getCache(String cacheName) {
        Cache cache = singletonManager.getCache(cacheName);
        return cache;
    }

    public static void put(Object key, Object value) {
        Element element = new Element(key, value);
        localCache.put(element);
    }

    public static void put(Element element) {
        localCache.put(element);
    }

    public static void put(Collection<Element> elements) {
        localCache.putAll(elements);
    }

    public static Element get(Object key) {
        Element element = localCache.get(key);
        return element;
    }
}
