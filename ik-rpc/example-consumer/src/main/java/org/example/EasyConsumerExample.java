package org.example;

import org.example.domain.User;
import org.example.proxy.JdkProxyFactory;
import org.example.service.UserService;


public class EasyConsumerExample {
    public static void main(String[] args) {
        //动态代理获取的UserService
        UserService userService = JdkProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("ikun1");
        User userName = userService.getUserName(user);
        if(userName != null){
            System.out.println(userName.getName());
        }else {
            System.out.println("userName==null");
        }
        short number = userService.getNumber();
        System.out.println(number + "number");
    }



}
