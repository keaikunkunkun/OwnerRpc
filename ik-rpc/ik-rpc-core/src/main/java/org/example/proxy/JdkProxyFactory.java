package org.example.proxy;

import org.example.RpcApplication;

import java.lang.reflect.Proxy;
//服务代理对象生成工厂，根据配置文件来选择是否生成mock对象
public class JdkProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass){
        if (RpcApplication.getRpcConfig().isMock()){
            return getMockProxy(serviceClass);
        }
        return  (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy()
        );
    }

    public static <T> T getMockProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }


}
