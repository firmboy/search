package com.firmboy.webservice.axis2.client;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

/**
 * @author playboy
 * @create 2019-11-30 3:37 下午
 * @description axis2的客户端代码
 * @serviceLogic
 **/
public class Axis2AXIOMClient {

    private static EndpointReference targetEPR =
            new EndpointReference("http://localhost:8080/services/helloAxis2");

    public static OMElement getPricePayload(String symbol) {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://server.axis2.webservice.java.firmboy.com", "tns");


        OMElement method = fac.createOMElement("sayHello", omNs);
        method.setText("tom");
        return method;
    }

    public static OMElement updatePayload(String symbol, double price) {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://axiom.service.quickstart.samples/xsd", "tns");

        OMElement method = fac.createOMElement("update", omNs);

        OMElement value1 = fac.createOMElement("symbol", omNs);
        value1.addChild(fac.createOMText(value1, symbol));
        method.addChild(value1);

        OMElement value2 = fac.createOMElement("price", omNs);
        value2.addChild(fac.createOMText(value2,
                Double.toString(price)));
        method.addChild(value2);
        return method;
    }

    public static void main(String[] args) {
        try {
            OMElement getPricePayload = getPricePayload("WSO");
            Options options = new Options();
            options.setTo(targetEPR);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);



            OMElement result = sender.sendReceive(getPricePayload);

            result.build();
            result.detach();

            OMElement firstElement = result.getFirstElement();
            String response = firstElement.getFirstElement().getText();
            System.err.println("Current price of WSO: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
