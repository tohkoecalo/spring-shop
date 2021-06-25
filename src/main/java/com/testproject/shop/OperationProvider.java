package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.transform.TransformerException;
import java.util.Base64;
import java.util.Map;

@Service
@Scope("singleton")
public class OperationProvider {
    private static final String TWGP_URL = "http://10.77.201.18:5556/execpwd";
    private static final String LANGUAGE = "RU";
    private static final String MERCHANT = "POS_1";
    private static final String PASSWORD = "12345";
    private static final String CURRENCY = "643";
    private static final String RESPONSE_FORMAT = "TKKPG";

    private Order order;
    private String pareq;

    public OperationProvider(){
        this.order = new Order();
        this.pareq = "";
    }

    public String createOrder(Order order){
        this.order = order;
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CREATE_ORDER.getValue());
        builder.setLanguage(LANGUAGE);
        builder.setMerchant(MERCHANT);
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setDescription("TEST");
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter("xmlRequest", "");
        RequestParameter authDataParam = new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        String status = "Create order process went wrong";
        if (Utils.isResponseSuccess(responseDetails)) {
            this.order.setOrderId(responseDetails.get("OrderID"));
            this.order.setSessionId(responseDetails.get("SessionID"));
            status = "Success";
        }
        return status;
    }

    public boolean check3ds() {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPAN(order.getCard().getNumber());
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter("xmlRequest", "");
        RequestParameter authDataParam = new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            if (responseDetails.get("enrolled").equals("Y")){
                return true;
            }
        }
        return false;
    }

    public String[] getPAReqForm() {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setEncodedPAReq("true");
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter("xmlRequest", "");
        RequestParameter authDataParam = new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        String result[] = new String[2];
        if (Utils.isResponseSuccess(responseDetails)) {
            result[0] = responseDetails.get("url");
            result[1] = responseDetails.get("pareq");
            pareq = responseDetails.get("pareq");
        }
        return result;
    }

    public void redirectToIssuer(String url){
        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter mdParam = new RequestParameter("MD", Base64.getEncoder().encodeToString(order.getOrderId().getBytes()));
        RequestParameter termUrlParam = new RequestParameter("TermUrl", "http://localhost:3000/order/after_issuer");
        RequestParameter pareqParam = new RequestParameter("pareq", pareq);

        Map<String, String> responseDetails = ch.provideRequest(url, mdParam, termUrlParam, pareqParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            String status = responseDetails.get("OrderStatus");
        }
    }

    public String processPARes(String pares) {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.PROCESS_PARES.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPARes(pares);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setCVV2(order.getCard().getCvv());
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter("xmlRequest", "");
        RequestParameter authDataParam = new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.escapeSymbols(Utils.representXmlDocAsString(rq.getBody())));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }

    public String purchase() {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.PURCHASE.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setCVV2(order.getCard().getCvv());
        builder.setResponseFormat(RESPONSE_FORMAT);
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter("xmlRequest", "");
        RequestParameter authDataParam = new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.escapeSymbols(Utils.representXmlDocAsString(rq.getBody())));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }
}