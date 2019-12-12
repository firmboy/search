package com.firmboy.gzip;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author playboy
 * @create 2019-12-11 9:40 上午
 * @description
 * @serviceLogic
 **/
public class GzipTest {
    private Logger log = Logger.getLogger(GzipTest.class);

    public static void main(String[] args) throws IOException {
        String param = "hello world";
        String en = compress(param);
        System.out.println("加密后：" + en);
        String de = unCompress(en);
        System.out.println("解密后：" + de);


    }


    /**
     * gzip压缩
     *
     * @param param
     * @return
     * @throws IOException
     */
    public static String compress(String param) throws IOException {
        String str = null;
        byte[] bytes = null;
        try (   // 开启数据输出流
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // 开启压缩输出流
                GZIPOutputStream gzipOut = new GZIPOutputStream(out)) {
            // 将字串转换成字节，然后按照UTF-8的形式压缩
            gzipOut.write(param.getBytes("UTF-8"));
            // 压缩完毕
            gzipOut.finish();
            // 将压缩好的流转换到byte数组中去
            bytes = out.toByteArray();
            out.flush();
        } catch (Exception e) {
            System.out.println("数据压缩失败");
            e.printStackTrace();
        }

        try {
            byte[] encode = Base64.encode(bytes);
            str = new String(encode, "UTF-8");
        } catch (Exception e) {
            System.out.println("数组Base64转码失败");
            e.printStackTrace();
        }

        return str;
    }


    /**
     * 解压缩
     *
     * @param param
     * @return
     * @throws IOException
     */
    public static String unCompress(String param) throws IOException {
        String str = null;
        byte[] encode = null;
        byte[] decode = null;
        byte[] bytes = null;

        try {
            encode = param.getBytes("UTF-8");
            decode = Base64.decode(encode);
        } catch (Exception e) {
            System.out.println("数组Base64解码失败");
            e.printStackTrace();
        }

        try (   // 创建输出流
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // 创建压缩输入流
                ByteArrayInputStream in = new ByteArrayInputStream(decode);
                // 创建输入流,并把传入的字串参数转码成UTF-8
                GZIPInputStream gzipIn = new GZIPInputStream(in)) {

            // 创建byte数组用于接收解压后的流转化成byte数组
            byte[] buffer = new byte[2048];
            int n = -1;
            while ((n = gzipIn.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            // 转换数据
            bytes = out.toByteArray();
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("数据解压缩失败");
            e.printStackTrace();
        }
        str = new String(bytes, "UTF-8");
        return str;
    }
}
