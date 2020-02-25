package com.firmboy.util.hutool;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.date.*;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.StaticLog;
import cn.hutool.script.ScriptUtil;
import cn.hutool.setting.Setting;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author playboy
 * @create 2019-12-12 9:55 上午
 * @description hutool工具类集
 * @serviceLogic
 **/
public class HutoolTest {
    //private Logger log = Logger.getLogger(HutoolTest.class);

    private static final Log log = LogFactory.get();

    public static void main(String[] args) throws Exception{
        //setting();
        //classPath();
        date();
        //io();
    }

    public static void annotation() {
        boolean inherited = AnnotationUtil.isInherited(RestController.class);
    }

    public static void template() {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("templates/velocity_test.vtl");
        String result = template.render(Dict.create().set("name", "Hutool"));
    }

    public static void setting() {
        StaticLog.get();

        Object put = Dict.create().put("user", "root");

        Setting setting = new Setting("test.setting");
        String user = setting.get("user");
        //String user = setting.getByGroup("user","demo");
        Console.log(user);
    }
    
    public static void io() {
//        BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
//        BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
//        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
//        IoUtil.getReader(in, Charset.forName("UTF-8"));

        ClassPathResource resource = new ClassPathResource("log4j.properties");
        FastByteArrayOutputStream read = IoUtil.read(resource.getStream());
        String s = read.toString();
        Console.log(s);

        IoUtil.close(read);

        //IoUtil.copy(in,out);
    }

    public static void date() {
        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        //结果：2017-01-05 12:34:23
        String dateStr = dateTime.toString();

        int i = DateUtil.ageOfNow("1991-05-28");
        Console.log(i);
        int year = DateUtil.year(DateUtil.date());
        Console.log(year);

        DateTime parse = DateUtil.parse("2019/10/21");
        Console.log(parse);
        long l = DateUtil.current(false);
        Console.log(l);
        long l1 = DateUtil.current(true);
        Console.log(l1);

        

        String format = DatePattern.PURE_TIME_FORMAT.format(DateUtil.date());
        Console.log(format);
    }

    public static void time() {
        TimeInterval timer = DateUtil.timer();
        core();
        System.out.println(timer.interval());
        core();
        System.out.println(timer.intervalRestart());
    }

    public static void core() {
        for (int i =0;i<100000;i++){
            System.out.println("hehe");
        }
    }

    public static void classPath() throws IOException {
        ClassPathResource resource = new ClassPathResource("log4j.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());


        Console.log(properties.getProperty("log4j.rootLogger"));

        Console.log("Properties: {}", properties);

        
    }
    
}
