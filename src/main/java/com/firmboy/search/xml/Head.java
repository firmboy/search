package com.firmboy.search.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-06 1:47 下午
 * @description
 * @serviceLogic
 **/
public class Head {
    private Logger log = Logger.getLogger(Head.class);

    @JacksonXmlProperty(localName = "MsgType")
    private String MsgType;

    @JacksonXmlProperty(localName = "MsgId")
    private String MsgId;

    @JacksonXmlProperty(localName = "Src")
    private String Src;

    @JacksonXmlProperty(localName = "SrcName")
    private String SrcName;

    @JacksonXmlProperty(localName = "SrcZone")
    private String SrcZone;

    @JacksonXmlProperty(localName = "SrcUserName")
    private String SrcUserName;

    @JacksonXmlProperty(localName = "Dst")
    private String Dst;

    @JacksonXmlProperty(localName = "DstName")
    private String DstName;

    @JacksonXmlProperty(localName = "DstZone")
    private String DstZone;

    @JacksonXmlProperty(localName = "MsgTime")
    private String MsgTime;

    @JacksonXmlProperty(localName = "Remark")
        private String Remark;

        public String getMsgType() {
            return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getSrcName() {
        return SrcName;
    }

    public void setSrcName(String srcName) {
        SrcName = srcName;
    }

    public String getSrcZone() {
        return SrcZone;
    }

    public void setSrcZone(String srcZone) {
        SrcZone = srcZone;
    }

    public String getSrcUserName() {
        return SrcUserName;
    }

    public void setSrcUserName(String srcUserName) {
        SrcUserName = srcUserName;
    }

    public String getDst() {
        return Dst;
    }

    public void setDst(String dst) {
        Dst = dst;
    }

    public String getDstName() {
        return DstName;
    }

    public void setDstName(String dstName) {
        DstName = dstName;
    }

    public String getDstZone() {
        return DstZone;
    }

    public void setDstZone(String dstZone) {
        DstZone = dstZone;
    }

    public String getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(String msgTime) {
        MsgTime = msgTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
