package com.firmboy.sftp;

import org.apache.log4j.Logger;
import com.jcraft.jsch.*;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author playboy
 * @create 2019-12-06 3:34 下午
 * @description
 * @serviceLogic
 **/
public class SftpUtil {
    private static Logger log = Logger.getLogger(SftpUtil.class);

    private String host;

    private String username;

    private int port;

    public SftpUtil(String host, String username, int port){
        this.host = host;
        this.username = username;
        this.port = port;
    }

    /**
     * 登陆sftp
     *
     * @param authTypeMode 认证方式
     * @return 登陆信息
     */
    public Map<String,Object> loginIn(AuthTypeMode authTypeMode){
        //解决JSch日志打印问题
        JSch.setLogger(new SettleJschLogPrint());
        try {
            ChannelSftp sftp = null;
            Session session = null;

            JSch jsch = new JSch();
            log.info("获取SFTP服务器连接username:"+username+",host:"+host+",port:"+port);
            session = jsch.getSession(username,host,port);
            log.info("连接成功建立");
            if (AuthTypeEnum.RSA.getCode().equals(authTypeMode.getAuthType())){
                jsch.addIdentity(authTypeMode.getAuthValue(),"");
            }else {
                session.setPassword(authTypeMode.getAuthValue());
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking","no");
            sshConfig.put("PreferredAuthentications","publickey,gssapi-with-mic,keyboard-interactive,password");
            session.setConfig(sshConfig);
            session.connect();
            log.info("用户" + username + "成功登陆");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            HashMap<String,Object> loginInfo = new HashMap<>();
            loginInfo.put("sftp",sftp);
            loginInfo.put("session",session);
            return loginInfo;
        } catch (JSchException e) {
            throw new RuntimeException("user login SFTP server occur exception:" + e);
        }
    }

    /**
     * 退出登陆
     *
     * @param sftp sftp对象
     * @param session session对象
     */
    public void loginOut(ChannelSftp sftp, Session session){
        try {
            if(null != sftp && sftp.isConnected()){
                sftp.disconnect();
            }
            if (null != session && session.isConnected()){
                session.disconnect();
            }
        } catch (Exception e) {
            log.warn("用户退出SFTP服务器出现异常:" + e);
        }
    }

    /**
     * 下载文件
     *
     * @param downloadFilePath 要下载的文件所在绝对路径
     * @param downloadFileName 要下载的文件名(sftp服务器上的文件名)
     * @param saveFile 文件存放位置（文件所在绝对路径）
     * @param authTypeMode 用户认证方式
     */
    public void download(String downloadFilePath, String downloadFileName, File saveFile, AuthTypeMode authTypeMode) throws Exception{
        Assert.notNull(downloadFilePath,"download file absolute path is not null");
        Assert.notNull(downloadFileName,"download file is not null");
        Assert.notNull(saveFile,"save file location is not null");
        Assert.notNull(authTypeMode,"auth type way is not null");

        OutputStream outputStream = null;
        ChannelSftp sftp = null;
        Session session = null;
        try {
            Map<String,Object> loginInfo = loginIn(authTypeMode);
            sftp = (ChannelSftp) loginInfo.get("sftp");
            session = (Session) loginInfo.get("session");

            log.info("待下载文件地址为:" + downloadFilePath + ",文件名为:" + downloadFileName + ",认证方式:" + authTypeMode.getAuthValue());
            sftp.cd(downloadFilePath);
            sftp.ls(downloadFileName);
            outputStream = new FileOutputStream(saveFile);
            sftp.get(downloadFileName,outputStream);
            log.info("文件下载完成！");

        } finally {
            if (null != outputStream){
                outputStream.close();
            }
            loginOut(sftp,session);
        }
    }

    /**
     * 上传文件
     *
     * @param uploadPath 上传SFTP完整路径
     * @param uploadFile 上传文件（完整路径）
     * @param authTypeMode 认证方式
     */
    public void upload(String uploadPath, String uploadFile, AuthTypeMode authTypeMode) throws Exception{
        Assert.notNull(uploadPath,"upload path is not null");
        Assert.notNull(uploadFile,"upload file is not null");
        Assert.notNull(authTypeMode,"auth type way is not null");

        InputStream inputStream = null;
        ChannelSftp sftp = null;
        Session session = null;
        try {
            Map<String,Object> loginInfo = loginIn(authTypeMode);
            sftp = (ChannelSftp) loginInfo.get("sftp");
            session = (Session) loginInfo.get("session");

            log.info("待上传文件为:" + uploadFile + ",上传SFTP服务器路径:" + uploadPath + ",认证方式:" + authTypeMode.getAuthValue());
            File file = new File(uploadFile);
            inputStream = new FileInputStream(file);
            try {
                sftp.cd(uploadPath);
            } catch (SftpException e) {
                log.error("SFTP器服务存放文件路径不存在");
                throw new RuntimeException("upload path is not exist");
            }
            sftp.put(inputStream,file.getName());
            log.info("上传文件成功！");
        } finally {
            if (null != inputStream){
                inputStream.close();
            }
            loginOut(sftp,session);
        }

    }

    /**
     * authType = PASSWORD，authValue = password
     * authType = rsa，authValue = rsa文件路径
     */
    class AuthTypeMode{
        private String authType;

        private String authValue;

        AuthTypeMode(String authType, String authValue){
            this.authType = authType;
            this.authValue = authValue;
        }

        String getAuthType(){
            return authType;
        }

        String getAuthValue(){
            return authValue;
        }
    }

    /**
     * 在slf4j日志框架里打印JSch日志
     */
    class SettleJschLogPrint implements com.jcraft.jsch.Logger{

        @Override
        public boolean isEnabled(int i) {
            return true;
        }

        @Override
        public void log(int i, String s) {
            log.info(s);
        }
    }

    enum AuthTypeEnum {

        PASSWORD("PASSWORD","密码认证"),

        RSA("RSA","rsa密钥认证");

        private String code;

        private String desc;

        AuthTypeEnum(String code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getCode(){
            return this.code;
        }
    }

    public static void main(String[] args) throws Exception{
        //密码认证方式
        //SftpUtil SftpUtil = new SftpUtil("192.168.79.151","albert",22);
        //下载文件
        //File saveFile = new File("/data/sftp/verifyfile_01.txt");
        //SftpUtil.download("/verifyfile","verifyfile_01.csv",saveFile,SftpUtil.new AuthTypeMode(AuthTypeEnum.PASSWORD.getCode(),"alan123456"));
        //上传文件
        //SftpUtil.upload("/verifyfile","/data/sftp/verifyfile_01.txt",SftpUtil.new AuthTypeMode(AuthTypeEnum.PASSWORD.getCode(),"alan123456"));

        //密钥认证方式下载文件
        root();
        //sftp1();
    }


    public static void root() {
        try{
            SftpUtil sftpToolTwo = new SftpUtil("192.168.56.7","root",22);
            File saveFileTwo = new File("/Users/playboy/dev/test.txt");
            sftpToolTwo.download("/home/sftp1","test.txt",saveFileTwo,sftpToolTwo.new AuthTypeMode(AuthTypeEnum.RSA.getCode(),"/Users/playboy/dev/temp/id_rsa"));
            //密钥认证方式上传文件( 上传会失败Permission denied，因为qingniu这个用户没有写的权限 )
            //sftpToolTwo.upload("/verifyfile","/data/sftp/verifyfile_01.txt",sftpToolTwo.new AuthTypeMode(AuthTypeEnum.RSA.getCode(),"/data/rsa/id_rsa_qingniu"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sftp1() {
        try{
            SftpUtil sftpToolTwo = new SftpUtil("192.168.56.7","sftp1",22);
            File saveFileTwo = new File("/Users/playboy/dev/test.txt");
            sftpToolTwo.download("/root","test.txt",saveFileTwo,sftpToolTwo.new AuthTypeMode(AuthTypeEnum.RSA.getCode(),"/Users/playboy/dev/id_rsa"));
            //密钥认证方式上传文件( 上传会失败Permission denied，因为qingniu这个用户没有写的权限 )
            //sftpToolTwo.upload("/verifyfile","/data/sftp/verifyfile_01.txt",sftpToolTwo.new AuthTypeMode(AuthTypeEnum.RSA.getCode(),"/data/rsa/id_rsa_qingniu"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void test(String[] args) {
        try{
            ChannelSftp sftp = null;
            Session session = null;
            JSch jsch = new JSch();
            session = jsch.getSession("root","192.168.56.7");
            log.info("连接成功建立");
            jsch.addIdentity("/Users/playboy/dev/id_rsa","");


            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking","no");
            sshConfig.put("PreferredAuthentications","publickey,gssapi-with-mic,keyboard-interactive,password");
            session.setConfig(sshConfig);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            HashMap<String,Object> loginInfo = new HashMap<>();
            loginInfo.put("sftp",sftp);
            loginInfo.put("session",session);
            try {
                if(null != sftp && sftp.isConnected()){
                    sftp.disconnect();
                }
                if (null != session && session.isConnected()){
                    session.disconnect();
                }
            } catch (Exception e) {
                log.warn("用户退出SFTP服务器出现异常:" + e);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
