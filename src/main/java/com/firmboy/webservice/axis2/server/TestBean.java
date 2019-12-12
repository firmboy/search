package com.firmboy.webservice.axis2.server;


import org.springframework.stereotype.Service;

/**
 * @author playboy
 * @create 2019-11-30 2:40 下午
 * @description 测试用的springbean
 * @serviceLogic
 **/
@Service
public class TestBean {

    public String sayHello(String name){
        return "hi "+name;
    }

}
