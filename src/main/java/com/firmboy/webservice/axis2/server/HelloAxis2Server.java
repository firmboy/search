package com.firmboy.webservice.axis2.server;


import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;

/**
 * @author playboy
 * @create 2019-11-28 2:49 下午
 * @description axis2Server演示代码
 * @serviceLogic
 **/
@Service
public class HelloAxis2Server {

    public String sayHello(String name)
            throws XMLStreamException {
        System.out.println("hehe");
        return "hi "+name;
    }

}
