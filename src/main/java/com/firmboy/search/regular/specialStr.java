package com.firmboy.search.regular;

import cn.hutool.core.lang.Console;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author playboy
 * @create 2020-02-18 7:43 下午
 * @description 特定字符串的正则表达式
 * @serviceLogic
 **/
public class specialStr {
    private Logger log = Logger.getLogger(specialStr.class);

    public static String str  ="select function (select FUNCTION from SSSFM_PAGE_ELEMENT where COMPO_ID = 'BFSR_CONFIRM' and ELEMENT_ID = 'queryBox') from SSSFM_PAGE_ELEMENT";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^[select].[from]$");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            String group = matcher.group();
            Console.log(group);
        }
    }
}
