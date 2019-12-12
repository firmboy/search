package com.firmboy.search.freemaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.firmboy.search.freemaker.config.FreeMarkerConfiguration;
import com.firmboy.search.freemaker.data.Root;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author playboy
 * @create 2019-12-10 1:57 下午
 * @description
 * @serviceLogic
 **/
public class JavaBean2Xml {
    private Logger log = Logger.getLogger(JavaBean2Xml.class);

    public static void main(String[] args) {
        try {
            toXml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toXml() throws IOException, TemplateException {
        ObjectMapper xmlMapper = new XmlMapper();
        Root root = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><Head><MsgType>SR01</MsgType><MsgId>A001010011112017111600000173</MsgId></Head><Body><ID>A001010011112017111600000173</ID><BatchNo>SR01_001</BatchNo></Body></Root>", Root.class);

        Configuration cfg = FreeMarkerConfiguration.getConfiguration();

        Template temp = cfg.getTemplate("test1.ftlh");

        StringWriter stringWriter = new StringWriter();

        temp.process(root, stringWriter);

        String s = stringWriter.toString();

        stringWriter.close();

        String s1 = s.replaceAll("\n", "").replaceAll(" ","");

        System.out.println(s1);
    }

}
