package com.testproject.shop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Request {
    public static enum Operation {
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

    private Request(){}

    public Document getBody(){
        return body;
    }

    public static Builder newBuilder() {
        return new Request().new Builder();
    }

    public class Builder{
        private Builder(){
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Request.this.body = documentBuilder.newDocument();

                Element root = Request.this.body.createElement("TKKPG");
                Request.this.body.appendChild(root);

                Element request = Request.this.body.createElement("Request");
                root.appendChild(request);

                Element order = Request.this.body.createElement("Order");
                request.appendChild(order);
            } catch (ParserConfigurationException e){
                e.printStackTrace();
            }
        }

        public Builder setOperation(String operation){
            Element eOperation = Request.this.body.createElement("Operation");
            eOperation.appendChild(Request.this.body.createTextNode(operation));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eOperation);
            return this;
        }

        public Builder setLanguage(String language){
            Element eLanguage = Request.this.body.createElement("Language");
            eLanguage.appendChild(Request.this.body.createTextNode(language));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eLanguage);
            return this;
        }

        public Builder setMerchant(String merchant){
            Element eMerchant = Request.this.body.createElement("Merchant");
            eMerchant.appendChild(Request.this.body.createTextNode(merchant));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(eMerchant);
            return this;
        }

        public Builder setAmount(String amount){
            Element eAmount = Request.this.body.createElement("Amount");
            eAmount.appendChild(Request.this.body.createTextNode(amount));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(eAmount);
            return this;
        }

        public Builder setCurrency(String currency){
            Element eCurrency = Request.this.body.createElement("Currency");
            eCurrency.appendChild(Request.this.body.createTextNode(currency));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(eCurrency);
            return this;
        }

        public Builder setDescription(String description){
            Element eDescription = Request.this.body.createElement("Description");
            eDescription.appendChild(Request.this.body.createTextNode(description));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(eDescription);
            return this;
        }

        public Builder setOrderId(String orderId){
            Element eOrderId = Request.this.body.createElement("OrderID");
            eOrderId.appendChild(Request.this.body.createTextNode(orderId));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(eOrderId);
            return this;
        }

        public Builder setSessionId(String sessionId){
            Element eSessionID = Request.this.body.createElement("SessionID");
            eSessionID.appendChild(Request.this.body.createTextNode(sessionId));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eSessionID);
            return this;
        }

        public Builder setPAN(String pan){
            Element ePAN = Request.this.body.createElement("PAN");
            ePAN.appendChild(Request.this.body.createTextNode(pan));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(ePAN);
            return this;
        }

        public Builder setExpDate(String expDate){
            Element eExpDate = Request.this.body.createElement("ExpDate");
            eExpDate.appendChild(Request.this.body.createTextNode(expDate));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eExpDate);
            return this;
        }

        public Builder setEncodedPAReq(String encodedPAReq){
            Element eEncodedPAReq = Request.this.body.createElement("EncodedPAReq");
            eEncodedPAReq.appendChild(Request.this.body.createTextNode(encodedPAReq));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eEncodedPAReq);
            return this;
        }

        public Builder setPARes(String paRes){
            Element ePARes = Request.this.body.createElement("PARes");
            ePARes.appendChild(Request.this.body.createTextNode(paRes));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(ePARes);
            return this;
        }

        public Builder setCVV2(String cvv2){
            Element eCVV2 = Request.this.body.createElement("CVV2");
            eCVV2.appendChild(Request.this.body.createTextNode(cvv2));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eCVV2);
            return this;
        }

        public Builder setResponseFormat(String responseFormat){
            Element eResponseFormat = Request.this.body.createElement("ResponseFormat");
            eResponseFormat.appendChild(Request.this.body.createTextNode(responseFormat));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(eResponseFormat);
            return this;
        }

        public Builder setRequestElement(String tag, String value){
            Element element = Request.this.body.createElement(tag);
            element.appendChild(Request.this.body.createTextNode(value));
            NodeList tmp = Request.this.body.getElementsByTagName("Request");
            tmp.item(0).appendChild(element);
            return this;
        }

        public Builder setOrderElement(String tag, String value){
            Element element = Request.this.body.createElement(tag);
            element.appendChild(Request.this.body.createTextNode(value));
            NodeList tmp = Request.this.body.getElementsByTagName("Order");
            tmp.item(0).appendChild(element);
            return this;
        }

        /*
        * Operation - above order block +
        * Language - above order block +
        * Merchant - order block +
        * Amount - order block +
        * Currency - order block +
        * Description - order block +
        * OrderID - order block +
        * SessionID - below order block +
        * PAN - below order block +
        * ExpDate - below order block +
        * EncodedPAReq - below order block +
        * PARes - below order block +
        * CVV2 - below order block +
        * ResponseFormat - below order block +
        * authData -mb
        * */

        public Request build(){
            return Request.this;
        }
    }

}
