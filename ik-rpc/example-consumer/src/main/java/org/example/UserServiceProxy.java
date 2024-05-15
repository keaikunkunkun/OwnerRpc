package org.example;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.example.domain.User;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.serializer.JdkSerializer;
import org.example.serializer.Serializer;
import org.example.service.UserService;

import java.io.IOException;

public class UserServiceProxy implements UserService {
    //静态代理
    public User getUserName(User user) {
        Serializer serializer = new JdkSerializer();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .args(new Object[]{user})
                .methodName("getUserName")
                .parameterTypes(new Class[]{User.class})
                .build();

        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            HttpResponse response = HttpRequest.post("localhost:8080")
                    .body(bytes)
                    .execute();
            if (response.isOk()) {
                byte[] bodyBytes = response.bodyBytes();
                RpcResponse rpcResponse = serializer.deserialize(bodyBytes, RpcResponse.class);
                return (User) rpcResponse.getResult();
            } else {
                throw new RuntimeException("Failed to call remote service, status: " + response.getStatus());
            }
        } catch (IOException e) {
            throw new RuntimeException("Serialization or communication error", e);
        }
    }
}
