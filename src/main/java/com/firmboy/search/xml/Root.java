package com.firmboy.search.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-06 1:47 下午
 * @description
 * @serviceLogic
 **/
public class Root {
    private Logger log = Logger.getLogger(Root.class);

    @JacksonXmlProperty(localName = "Head")
    private Head head;

    @JacksonXmlProperty(localName = "Body")
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
