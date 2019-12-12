package com.firmboy.freemaker.data;

import org.apache.log4j.Logger;

/**
 * @author playboy
 * @create 2019-12-02 6:34 下午
 * @description
 * @serviceLogic
 **/
public class Product {
    private Logger log = Logger.getLogger(Product.class);

    private String url;

    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
