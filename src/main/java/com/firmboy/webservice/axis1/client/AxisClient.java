package com.firmboy.webservice.axis1.client;


import org.apache.axis.Constants;
import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import javax.xml.namespace.QName;


/**
 * @author playboy
 * @create 2019-11-30 7:44 下午
 * @description
 * @serviceLogic
 **/
public class AxisClient {

    public static void main(String[] args) {
        //axis2();
        cxf();
    }

    public static void cxf(){
        try {
            //cxf
            String endpoint = "http://localhost:8080/ws/helloCxf";

            Service service = new Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress( new java.net.URL(endpoint) );
            call.setOperationName(new QName("http://server.cxf.webservice.firmboy.com/", "sayHello"));


            call.addParameter("arg0",
                    org.apache.axis.Constants.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter("arg1",
                    org.apache.axis.Constants.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.Constants.XSD_STRING);

            String ret = (String) call.invoke(new Object[]{"tom","5"});

            System.out.println("Sent 'Hello!', got '" + ret + "'");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.toString());
        }
    }

    /**
     * axis2
     */
    public static void  axis2(){
        try {
            //axis2
            String endpoint = "http://localhost:8080/services/helloAxis2/sayHello";

            Service service = new Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress( new java.net.URL(endpoint) );
            call.setOperationName(new QName("http://server.axis2.webservice.java.firmboy.com", "sayHello"));

            String ret = (String) call.invoke(new Object[]{"Hello!"});

            System.out.println("Sent 'Hello!', got '" + ret + "'");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
