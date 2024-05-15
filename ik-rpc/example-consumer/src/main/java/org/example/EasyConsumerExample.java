package org.example;

import org.example.domain.User;
import org.example.proxy.JdkProxyFactory;
import org.example.service.UserService;


public class EasyConsumerExample {
    public static void main(String[] args) {
        //动态代理获取的UserService
        UserService userService1 = JdkProxyFactory.getProxy(UserService.class);
        //静态代理获取的UserService
//        UserServiceProxy userServiceProxy = new UserServiceProxy();
//        UserService userService = null;
        User user = new User();
        user.setName("ikun1");
        User userName = userService1.getUserName(user);
        if(userName!=null){
            System.out.println(userName.getName());
        }else {
            System.out.println("userName==null");
        }


    }



}
