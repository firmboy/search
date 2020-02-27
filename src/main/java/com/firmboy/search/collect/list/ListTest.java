package com.firmboy.search.collect.list;

import cn.hutool.core.lang.Console;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author playboy
 * @create 2020-02-27 1:46 下午
 * @description
 * @serviceLogic
 **/
public class ListTest {
    private Logger log = Logger.getLogger(ListTest.class);


    public static void main(String[] args) {
        String str = "12,21,21,23";
        String[] split = str.split(",");
        List<String> strings = Arrays.asList(split);

        ArrayList<String> strings1 = new ArrayList<>(strings);


        boolean add = strings1.add("56");
        Console.log(strings1);
    }
}
