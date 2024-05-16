package org.example.config;

import lombok.Data;

@Data
public class RpcConfig {

    //服务器名称
    private String host = "localhost";
    //服务器端口号
    private Integer port=8081;
    //名称
    private String name = "ik";
    //版本号
    private String version = "1.0";

    //是否开启mock数据
    private boolean mock = false;
}
