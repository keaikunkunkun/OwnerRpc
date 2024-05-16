package org.example.proxy;



import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.serializer.JdkSerializer;
import org.example.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //初始化序列器
        Serializer serializer = new JdkSerializer();
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        //发送请求
        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            try(HttpResponse response = HttpRequest.post("localhost:8081")
                    .body(bytes)
                    .execute()){
                    byte[] bodyBytes = response.bodyBytes();
                    RpcResponse rpcResponse = serializer.deserialize(bodyBytes, RpcResponse.class);
                    return rpcResponse.getResult();

            }
        } catch (IOException e) {
        e.printStackTrace();
    }
        return null;
    }
}

