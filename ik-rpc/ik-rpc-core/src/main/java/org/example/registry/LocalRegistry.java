package org.example.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    //使用线程安全的concurrent-hashmap来存储服务，其中key是服务名称，value是服务的实现类
    private static final Map<String , Class<?>> map = new  ConcurrentHashMap<>();



    //注册服务
    public static void register(String serviceName, Class<?> implClass){
        map.put(serviceName,implClass);
    }

    //获取服务
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    //删除服务
    public static void del(String serviceName){
        map.remove(serviceName);
    }
}
