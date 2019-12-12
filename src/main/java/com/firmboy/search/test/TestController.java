package com.firmboy.search.test;

import com.firmboy.search.freemaker.data.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author playboy
 * @create 2019-12-02 2:38 下午
 * @description 测试Controller
 * @serviceLogic
 **/
@RestController
@RequestMapping(value = "/test",produces = "text/html; charset=UTF-8")
public class TestController {
    private Logger log = Logger.getLogger(TestController.class);

    @Autowired
    Configuration cfg;

    @RequestMapping("/freemark")
    public String freeMark(HttpServletResponse response) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("人才");
        root.put("latestProduct", latest);

        Template temp = cfg.getTemplate("test.ftlh");


        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream("hello world".getBytes()));



        StringWriter stringWriter = new StringWriter();


        ByteArrayOutputStream byteO = new ByteArrayOutputStream();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int n;
        while ((n = gzip.read(buffer)) >= 0) {
            byteO.write(buffer,0,n);
        }
        String s2 = byteO.toString();




        temp.process(root, stringWriter);

        String s = stringWriter.toString();

        stringWriter.close();

        String s1 = s.replaceAll("\n", "").replaceAll(" ","");

        System.out.println(s1);

        return s1;
    }


    public static void main(String[] args) {
        String xml = "<?xml sion=\"1.0\" encoding=\"utf-8\"?><Root><Head><Version>v1.0</Version><MsgType>A00201</MsgType><Src>130</Src><Dst>00111101</Dst><MsgId>A002010011110011112017111600000173</MsgId><PageMaxSize>1</PageMaxSize><PageCount>1</PageCount><PageNo>1</PageNo><RecordCount>1</RecordCount><WorkDate>20171116</WorkDate><Reserved></Reserved></Head><Body><Record><BankAcctNo>6298786</BankAcctNo><BankAcctName>离休干部医药费</BankAcctName><BankNodeName>上海浦东发展银行股份有限公司支行</BankNodeName><PbcInterBankNo>01</PbcInterBankNo><SubBankAcctNo>6298786-01</SubBankAcctNo><SubBankAcctName>二级账户名</SubBankAcctName><SubBankAcctBlnc>10000</SubBankAcctBlnc><BankAcctStatus>01</BankAcctStatus><BankAcctType>03</BankAcctType><InsTypeCode>1299</InsTypeCode><BatchId>123123</BatchId><Currency>RMB</Currency><Reserve3></Reserve3><Reserve4></Reserve4>";
        for (int i = 0; i < 4000; i++) {
            xml = xml + "<RecDetail><TrnsDateTime>20170831153114</TrnsDateTime><IncAndExType>1</IncAndExType><SerialId>20170831110646200022</SerialId><IncAndExItem>99</IncAndExItem><ArrvDateTime>20170831153114</ArrvDateTime><RcprBankAcctNo>110002003</RcprBankAcctNo><RcprBankAcctName>财政厅社保户</RcprBankAcctName><RcptEstbBankName>国家金库</RcptEstbBankName><Summary>摘要</Summary><TrnsAmount>347274.08</TrnsAmount><BlncAmount>403781840.46</BlncAmount><Reserve5></Reserve5><Reserve6></Reserve6></RecDetail>";
        }
        xml = xml + "</Record></Body></Root>";
        
        System.out.println(xml);

        
    }
}
