package org.example;

import org.example.registry.LocalRegistry;
import org.example.server.HttpServer;
import org.example.server.VertxHttpServer;
import org.example.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {

        RpcApplication.init();

        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        //提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getPort());
    }
}
