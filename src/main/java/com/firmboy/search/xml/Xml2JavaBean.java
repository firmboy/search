package com.firmboy.search.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-06 1:33 下午
 * @description xml转换成javaBean
 * @serviceLogic
 **/
public class Xml2JavaBean {
    private Logger log = Logger.getLogger(Xml2JavaBean.class);

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        Root value = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><Head><MsgType>SR01</MsgType><MsgId>130</MsgId><Src>00111101</Src><SrcName>A001010011112017111600000173</SrcName><SrcZone>1</SrcZone><SrcUserName>1</SrcUserName><Dst>1</Dst><DstName>1</DstName><DstZone>20171116</DstZone><MsgTime></MsgTime><Remark></Remark></Head><Body><ID>B00101</ID><BusiDate>130</BusiDate><InsTypeCode>00111101</InsTypeCode><DistCode>A001010011112017111600000173</DistCode><Amount>1</Amount><BatchNo>1</BatchNo><Time>1</Time><RecDetails><RecDetail><ID>B00101</ID><SequNo>130</SequNo><DistCode>00111101</DistCode><IoItemCode>A001010011112017111600000173</IoItemCode><Amount>1</Amount><Remark>1</Remark></RecDetail><RecDetail><ID>B00101</ID><SequNo>130</SequNo><DistCode>00111101</DistCode><IoItemCode>A001010011112017111600000173</IoItemCode><Amount>1</Amount><Remark>1</Remark></RecDetail></RecDetails></Body></Root>", Root.class);

        System.out.println(value);
    }
}
