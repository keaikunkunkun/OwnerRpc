package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.config.RpcConfig;
import org.example.constant.RpcConstant;
import org.example.serializer.Serializer;
import org.example.utils.ConfigUtil;

@Slf4j
public class RpcApplication {

    //全局配置对象
    private static  volatile RpcConfig rpcConfig;

    //框架初始化，支持传入自定义配置对象
    public static void init(RpcConfig newrpcConfig){
        rpcConfig = newrpcConfig;
        log.info("rpc init , config:{}",rpcConfig.toString());
        System.out.println("log.info(\"rpc init , config:{}\",rpcConfig.toString());");
    }
    public static void init(){
        RpcConfig newRpcConfig;
        Class<Serializer> serializerClass = Serializer.class;
        String name = serializerClass.getName();
        try{
            newRpcConfig = ConfigUtil.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if(rpcConfig == null){
            synchronized (RpcConfig.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
