package org.example.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class MockServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据方法返回的返回值类型生成对应的对象
        Class<?> returnType = method.getReturnType();
        log.info("mock数据的类型{}" ,method.getName());
        return getDefaultData(returnType);
    }

    public Object getDefaultData(Class<?> tClass){
        if(tClass.isPrimitive()){
            if(tClass == int.class){
                return 0;
            }
            if(tClass == short.class){
                System.out.println(tClass == short.class);
                return (short)0;
            }
            if(tClass == String.class){
                return "ss";
            }
        }
        return null;
    }
}
