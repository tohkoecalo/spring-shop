package com.testproject.shop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlRequest {
    public enum Operation {
        CREATE_ORDER("CreateOrder"), CHECK_3DS_ENROLLED("Check3DSEnrolled"), GET_PAREQ_FORM("GetPAReqForm"),
        PROCESS_PARES("ProcessPARes"), PURCHASE("Purchase");

        private String value;
        Operation(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
    }

    private Document body;

    private XmlRequest(){}

    public Document getBody(){
        return body;
    }

    public static Builder newBuilder() throws Exception {
        return new XmlRequest().new Builder();
    }

    public class Builder{
        private Builder() throws ParserConfigurationException {

                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                XmlRequest.this.body = documentBuilder.newDocument();
                XmlRequest.this.body.setXmlVersion("1.0");

                Element root = XmlRequest.this.body.createElement("TKKPG");
                XmlRequest.this.body.appendChild(root);

                Element request = XmlRequest.this.body.createElement("Request");
                root.appendChild(request);

                Element order = XmlRequest.this.body.createElement("Order");
                request.appendChild(order);

        }

        public Builder setOperation(String operation){
            return setRequestElement("Operation", operation);
        }

        public Builder setLanguage(String language){
            return setRequestElement("Language", language);
        }

        public Builder setMerchant(String merchant){
            return setOrderElement("Merchant", merchant);
        }

        public Builder setAmount(String amount){
            return setOrderElement("Amount", amount);
        }

        public Builder setCurrency(String currency){
            return setOrderElement("Currency", currency);
        }

        public Builder setDescription(String description){
            return setOrderElement("Description", description);
        }

        public Builder setOrderId(String orderId){
            return setOrderElement("OrderID", orderId);
        }

        public Builder setSessionId(String sessionId){
            return setRequestElement("SessionID", sessionId);
        }

        public Builder setPAN(String pan){
            return setRequestElement("PAN", pan);
        }

        public Builder setExpDate(String expDate){
            return setRequestElement("ExpDate", expDate);
        }

        public Builder setEncodedPAReq(String encodedPAReq){
            return setRequestElement("EncodedPAReq", encodedPAReq);
        }

        public Builder setPARes(String paRes){
            return setRequestElement("PARes", paRes);
        }

        public Builder setCVV2(String cvv2){
            return setRequestElement("CVV2", cvv2);
        }

        public Builder setResponseFormat(String format){
            return setRequestElement("ResponseFormat", format);
        }

        private Builder setRequestElement(String tag, String value){
            Element element = XmlRequest.this.body.createElement(tag);
            element.appendChild(XmlRequest.this.body.createTextNode(value));
            NodeList tmp = XmlRequest.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(element);
            return this;
        }

        private Builder setOrderElement(String tag, String value){
            Element element = XmlRequest.this.body.createElement(tag);
            element.appendChild(XmlRequest.this.body.createTextNode(value));
            NodeList tmp = XmlRequest.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(element);
            return this;
        }

        public XmlRequest build(){
            return XmlRequest.this;
        }
    }
}
