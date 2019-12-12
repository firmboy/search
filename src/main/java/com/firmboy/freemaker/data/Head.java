package com.firmboy.freemaker.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-10 1:44 下午
 * @description
 * @serviceLogic
 **/
@Data
public class Head {
    private Logger log = Logger.getLogger(Head.class);
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;
    @JacksonXmlProperty(localName = "MsgId")
    private String msgId;


}
