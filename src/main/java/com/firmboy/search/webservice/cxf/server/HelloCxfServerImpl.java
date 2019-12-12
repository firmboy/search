package com.firmboy.search.webservice.cxf.server;


import javax.jws.WebService;

/**
 * @author playboy
 * @create 2019-11-28 9:19 上午
 * @description Cxfserver端演示代码
 * @serviceLogic
 **/
@WebService
public class HelloCxfServerImpl{

    public String sayHello(String  name, String age) {
        return      name + " say : hello world! [axis] my age is " + age;
    }

}
