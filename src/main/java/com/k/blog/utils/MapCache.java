package com.k.blog.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kangkai on 2018/4/19.
 */
public class MapCache {

    /**
     * 默认缓存个数 1024
     */
    private static final int DEFAULT_CACHES = 1024;

    private static final MapCache INS = new MapCache();

    /**
     * 缓存容器
     */
    private Map<String,CacheObject> cachePool;

    public static MapCache single() {
        return INS;
    }

    public MapCache() {
        this(DEFAULT_CACHES);
    }

    public MapCache(int cacheCount) {
        cachePool = new ConcurrentHashMap<>(cacheCount);
    }

    /**
    * Description:获取一个缓存
    * @param key
    * @return
    */
    public <T> T get(String key) {
        CacheObject cacheObject = cachePool.get(key);
        if (null != cacheObject) {
            long cur = System.currentTimeMillis() / 1000;
            //cacheObject.getExpired() <= 0 说明设置缓存的时候 设置的永不过期
            //cacheObject.getExpired() > cur cur表示当前时间 -> 缓存还没到期
            if (cacheObject.getExpired() <= 0 || cacheObject.getExpired() > cur) {
                return (T)cacheObject.getValue();
            }
        }
        return null;
    }

    /**
     * Description:获取一个hash缓存
     * @param key
     * @param field
     * @return
     */
    public <T> T hget(String key, String field) {
        key = key + ":" + field;
        return this.get(key);
    }


    /**
     * 设置一个缓存
     * @param key 缓存 key
     * @param value 缓存 value
     */
    public void set(String key, Object value) {
        this.set(key,value,-1);
    }
    /**
    * Description:设置一个带时间的缓存
    * @param key 缓存 key
    * @param value 缓存 value
    * @param expired 缓存过期时间 单位为 秒
    */
    public void set(String key, Object value, long expired) {
        //判断是否expired > 0
        expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key,cacheObject);
    }
    /**
     * 设置一个hash缓存
     * @param key 缓存 key
     * @param key 缓存 field
     * @param value 缓存 value
     */
    public void hset(String key, String field, Object value) {
        this.hset(key,field,value,-1);
    }

    /**
     * Description:设置一个带时间的hash缓存
     * @param key 缓存 key
     * @param key 缓存 field
     * @param value 缓存 value
     * @param expired 缓存过期时间 单位为 秒
     */
    public void hset(String key, String field, Object value, long expired) {
        key = key + ":" + field;
        expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key,cacheObject);
     }

     /**
     * Description:根据 key 删除缓存
     * @param key
     * @return
     */
     public <T> T delete(String key) {
         CacheObject cacheObject = cachePool.remove(key);
         if (null != cacheObject) {
            return (T)cacheObject.getValue();
         }
         return null;
     }

    /**
     * Description:根据 key 删除缓存
     * @param key
     * @return
     */
    public <T> T hdelete(String key, String field) {
        key = key + ":" + field;
        return this.delete(key);
    }

    /**
     * 清空缓存
     */
    public void clean() {
        cachePool.clear();
    }

    static class CacheObject {

        private String key;
        private Object value;
        //过期时间
        private long expired;

        /**
         * 构造方法
         */
        public CacheObject(String key, Object value, long expired) {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        /**
        *  三个属性 get方法
        */
        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getExpired() {
            return expired;
        }
    }


}
