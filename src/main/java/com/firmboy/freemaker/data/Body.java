package com.firmboy.freemaker.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-10 1:45 下午
 * @description
 * @serviceLogic
 **/
@Data
public class Body {
    private Logger log = Logger.getLogger(Body.class);

    @JacksonXmlProperty(localName = "ID")
    private String id;
    @JacksonXmlProperty(localName = "BatchNo")
    private String batchNo;

}
