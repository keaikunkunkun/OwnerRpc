package org.example;

import org.example.config.RpcConfig;
import org.example.utils.ConfigUtil;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig loadConfig = ConfigUtil.loadConfig(RpcConfig.class, "rpc");
        System.out.println(loadConfig);

    }
}
