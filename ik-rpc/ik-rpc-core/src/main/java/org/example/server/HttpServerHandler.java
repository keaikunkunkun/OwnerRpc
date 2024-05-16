package org.example.server;


import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.registry.LocalRegistry;
import org.example.serializer.JdkSerializer;
import org.example.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
public class HttpServerHandler implements Handler<HttpServerRequest> {

    //自定义服务处理器
    @Override
    public void handle(HttpServerRequest httpServerRequest){
        //首先先指定序列化器
        final Serializer serializer = new JdkSerializer();
        //输出相应日志
        System.out.println("Received request from : " + httpServerRequest.method() + httpServerRequest.uri() + "wangu");
        System.out.println("nhaoyaassss");
        log.info("nhao");
        //异步处理HTTP请求
        httpServerRequest.bodyHandler( body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes,RpcRequest.class);
                System.out.println(rpcRequest+"nhaoyas");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //想在将请求反序列化为对象，之后构造响应结果
            RpcResponse rpcResponse = new RpcResponse();
            if(rpcRequest == null){
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }
            //请求不为空，调用服务得到响应结果构造响应

            try {
                Class<?> aClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = aClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(aClass.newInstance(), rpcRequest.getArgs());
                //构造响应结果
                rpcResponse.setMessage("okkk");
                rpcResponse.setResult(result);
                rpcResponse.setType(method.getReturnType());
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            doResponse(httpServerRequest,rpcResponse,serializer);

        });
    }
    public void doResponse(HttpServerRequest httpServerRequest,RpcResponse rpcResponse,Serializer serializer){
        HttpServerResponse response = httpServerRequest.response()
                .putHeader("content-type","application/json");
        try {
            byte[] bytes = serializer.serialize(rpcResponse);
            response.end(Buffer.buffer(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            response.end(Buffer.buffer());
        }
    }

}
