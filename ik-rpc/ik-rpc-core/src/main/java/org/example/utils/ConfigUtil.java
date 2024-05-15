package org.example.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import io.netty.util.internal.StringUtil;

public class ConfigUtil {

    //加载配置文件
    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass,prefix,"");
    }

    //读取配置文件
    public static <T> T loadConfig(Class<T> tClass,String prefix , String environment){
        StringBuilder stringBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            stringBuilder.append("-" + environment );
        }
        stringBuilder.append(".properties");
        Props props = new Props(stringBuilder.toString());
        return props.toBean(tClass,prefix);
    }
}
