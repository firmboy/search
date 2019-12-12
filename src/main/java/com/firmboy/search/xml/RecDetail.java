package com.firmboy.search.xml;

import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-07 11:02 上午
 * @description
 * @serviceLogic
 **/
public class RecDetail {
    private Logger log = Logger.getLogger(RecDetail.class);

    public String ID;

    public String SequNo;

    public String DistCode;

    public String IoItemCode;

    public String Amount;

    public String Remark;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSequNo() {
        return SequNo;
    }

    public void setSequNo(String sequNo) {
        SequNo = sequNo;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getIoItemCode() {
        return IoItemCode;
    }

    public void setIoItemCode(String ioItemCode) {
        IoItemCode = ioItemCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
