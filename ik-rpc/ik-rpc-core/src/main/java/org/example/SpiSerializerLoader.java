package org.example;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.serializer.Serializer;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class SpiSerializerLoader {

    private static final Map<String,Map<String , Class<?>>> loaderMap = new ConcurrentHashMap<>();

    private static Map<String , Object> instanceCache = new ConcurrentHashMap<>();
    private static final String SYSTEM_SERIALIZER = "META/system/";

    private static final String CUSTOM_SERIALIZER = "META/custom/";
    private static final String[] SCAN_DIR = new String[]{SYSTEM_SERIALIZER,CUSTOM_SERIALIZER};

    public static void loadAll(){
        log.info("加载所有的序列化器");
        loadSerializer(Serializer.class);
    }

    public static <T> T getInstance(Class<?> tClass , String key){
        String name = tClass.getName();
        Map<String, Class<?>> classMap = loaderMap.get(name);
        if(classMap == null){
            throw new RuntimeException(String.format("spi还没有加载%s该类型", name));
        }
        if (!classMap.containsKey(key)){
            throw new RuntimeException(String.format("spi加载的 %s 不包括 key=%s", name , key));
        }
        Class<?> aClass = classMap.get(key);
        String name1 = aClass.getName();
        if(!instanceCache.containsKey(key)){
            try{
                instanceCache.put(name1,aClass.newInstance());
            }catch(InstantiationException | IllegalAccessException exception){
                String s = String.format("%s实例化失败", aClass);
                throw new RuntimeException(s,exception);
            }
        }
        return (T) instanceCache.get(name1);
    }
    public static Map<String , Class<?> > loadSerializer(Class<?> tClass ) {
        HashMap<String, Class<?>> map = new HashMap<>();
        for (String str : SCAN_DIR){
            URL resource = ResourceUtil.getResource(str + tClass.getName());
            try{
                InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while(bufferedReader.readLine() != null){
                    String s = bufferedReader.readLine();
                    String[] split = s.split("=");
                    if(split.length>1){
                        String key = split[0];
                        String ClassName = split[1];
                        map.put(key,Class.forName(ClassName));
                    }
                }
            }catch (Exception e){
                log.error("spi resource load error " ,e);
            }

        }
        loaderMap.put(tClass.getName(),map);
        return map;
    }

}
