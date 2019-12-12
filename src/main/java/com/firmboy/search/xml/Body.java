package com.firmboy.search.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author playboy
 * @create 2019-12-06 1:48 下午
 * @description
 * @serviceLogic
 **/
public class Body {
    private Logger log = Logger.getLogger(Body.class);

    @JacksonXmlProperty(localName = "ID")
    private String ID;

    @JacksonXmlProperty(localName = "BusiDate")
    private String BusiDate;

    @JacksonXmlProperty(localName = "InsTypeCode")
    private String InsTypeCode;

    @JacksonXmlProperty(localName = "DistCode")
    private String DistCode;

    @JacksonXmlProperty(localName = "Amount")
    private String Amount;

    @JacksonXmlProperty(localName = "BatchNo")
    private String BatchNo;

    @JacksonXmlProperty(localName = "Time")
    private String Time;

    @JacksonXmlElementWrapper(localName = "RecDetails")
    @JacksonXmlProperty(localName = "RecDetail")
    private List<RecDetail> RecDetails;

}
