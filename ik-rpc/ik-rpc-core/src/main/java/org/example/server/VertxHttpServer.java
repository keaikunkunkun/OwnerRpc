package org.example.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    //创建基于Vertx的服务器
    @Override
    public void doStart(int port) {
        //首先先创建vertx的实例
        Vertx vertx = Vertx.vertx();
        //创建服务器
        io.vertx.core.http.HttpServer vertxHttpServer = vertx.createHttpServer();
        //监听端口并处理请求,
        /*vertxHttpServer.requestHandler( request -> {
            //处理HTTP请求
            System.out.println("接收到来自" + request.method() + " " + request.uri());
            //发送HTTP相应
            request.response()
                    .putHeader("content-type","text/plain")
                    .end("Welcome to my vertx funqy");
        });*/
        //使用自定的请求处理器
        vertxHttpServer.requestHandler(new HttpServerHandler());

        //启动服务器并监听指定端口
        vertxHttpServer.listen( port , result -> {
            if (result.succeeded()){
                System.out.println("服务启动成功正在监听端口：" + port);
            }else if (result.failed()){
                System.out.println("服务启动失败" + result.cause());
            }
        });
    }
}
