package com.firmboy.search.webservice.axis1.server;


/**
 * @author playboy
 * @create 2019-11-28 11:48 上午
 * @description Axis1Server端演示代码
 * @serviceLogic
 **/
public class HelloAxis1Server {

    public String sayHello(String  name, int  age) {
        return      name + " say : hello world! [axis] my age is " + age;
    }

}
