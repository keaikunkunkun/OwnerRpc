package org.example.proxy;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy()
        );
    }
}
