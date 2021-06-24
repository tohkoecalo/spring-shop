package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.transform.TransformerException;
import java.util.Map;

@Service
@Scope("singleton")
public class OperationProvider {
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

    public void createOrder(Order order){
        this.order = order;
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.CREATE_ORDER.getValue());
        builder.setLanguage(LANGUAGE);
        builder.setMerchant(MERCHANT);
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setDescription("TEST");
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestBody.RequestParameter xmlRequestParam = rq.new RequestParameter("xmlRequest", "");
        RequestBody.RequestParameter authDataParam = rq.new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            this.order.setOrderId(responseDetails.get("OrderID"));
            this.order.setSessionId(responseDetails.get("SessionID"));
        }
    }

    public boolean check3ds() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPAN(order.getCard().getNumber());
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestBody.RequestParameter xmlRequestParam = rq.new RequestParameter("xmlRequest", "");
        RequestBody.RequestParameter authDataParam = rq.new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.escapeSymbols(Utils.representXmlDocAsString(rq.getBody())));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            if (responseDetails.get("enrolled").equals("Y")){
                return true;
            }
        }
        return false;
    }

    public String[] getPAReqForm() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getOrderId());
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setEncodedPAReq("true");
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestBody.RequestParameter xmlRequestParam = rq.new RequestParameter("xmlRequest", "");
        RequestBody.RequestParameter authDataParam = rq.new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(authDataParam, xmlRequestParam);
        String result[] = new String[2];
        if (Utils.isResponseSuccess(responseDetails)) {
            result[0] = responseDetails.get("url");
            result[1] = responseDetails.get("pareq");
        }
        return result;
    }

    public String processPARes() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.PROCESS_PARES.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getOrderId());
        builder.setPARes(pareq);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setCVV2(order.getCard().getCvv());
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestBody.RequestParameter xmlRequestParam = rq.new RequestParameter("xmlRequest", "");
        RequestBody.RequestParameter authDataParam = rq.new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.escapeSymbols(Utils.representXmlDocAsString(rq.getBody())));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }

    public String purchase() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.PURCHASE.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpYear() + order.getCard().getExpMonth());
        builder.setCVV2(order.getCard().getCvv());
        builder.setResponseFormat(RESPONSE_FORMAT);
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestBody.RequestParameter xmlRequestParam = rq.new RequestParameter("xmlRequest", "");
        RequestBody.RequestParameter authDataParam = rq.new RequestParameter("authData", "");
        try {
            xmlRequestParam.setValue(Utils.escapeSymbols(Utils.representXmlDocAsString(rq.getBody())));
            authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));
        } catch (TransformerException e){
            e.printStackTrace();
        }

        Map<String, String> responseDetails = ch.provideRequest(authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }

    public void redirectToIssuer(String url, String pareq){
        //todo
    }
}