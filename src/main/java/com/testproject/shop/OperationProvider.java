package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Scope("singleton")
public class OperationProvider {
    private static final String LANGUAGE = "RU";
    private static final String MERCHANT = "POS_1";
    private static final String CURRENCY = "643";
    private static final String RESPONSE_FORMAT = "TKKPG";

    private String amount;
    private String orderId;
    private String sessionId;
    private Card card;
    private String pareq;

    public OperationProvider(){
        this.amount = "";
        this.orderId = "0";
        this.sessionId = "0";
        this.card = new Card();
        this.pareq = "";
    }

    /*private static class CommunicatorHolder{
        private final static OperationProvider instance = new OperationProvider();
    }

    public static OperationProvider getInstance(){
        return OperationProvider.CommunicatorHolder.instance;
    }*/

    public String createOrder(Order order){//need auth data
        this.amount = order.getAmount();
        this.card = order.getCard();

        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.CREATE_ORDER.getValue());
        builder.setLanguage(LANGUAGE);
        builder.setMerchant(MERCHANT);
        builder.setAmount(this.amount);
        builder.setCurrency(CURRENCY);
        //builder.setDescription(description);
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = ch.provideRequest(rq.getBody());

        if (Utils.isResponseSuccess(responseDetails)) {
            orderId = responseDetails.get("OrderID");
            sessionId = responseDetails.get("SessionID");
        }
        return orderId;
    }

    public boolean check3ds(String orderId, String sessionId) {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(this.card.getNumber());
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = ch.provideRequest(rq.getBody());

        if (Utils.isResponseSuccess(responseDetails)) {
            if (responseDetails.get("enrolled").equals("Y")){
                return true;
            }
        }
        return false;
    }

    public String getPAReqForm() {// нужен урл редирект на сайт магазина
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(this.card.getNumber());
        builder.setExpDate(this.card.getExpMonth() + "/" + this.card.getExpYear());
        builder.setEncodedPAReq("true");
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = ch.provideRequest(rq.getBody());

        String result = "";
        if (Utils.isResponseSuccess(responseDetails)) {
            result = responseDetails.get("url");
            pareq = responseDetails.get("pareq");
        }
        return result;
    }

    public String processPARes() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.PROCESS_PARES.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPARes(pareq);
        builder.setPAN(this.card.getNumber());
        builder.setExpDate(this.card.getExpMonth() + "/" + this.card.getExpYear());
        builder.setCVV2(this.card.getCvv());
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = ch.provideRequest(rq.getBody());

        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }

    public String purchase() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.PURCHASE.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setAmount(amount);
        builder.setCurrency(CURRENCY);
        builder.setPAN(this.card.getNumber());
        builder.setExpDate(this.card.getExpMonth() + "/" + this.card.getExpYear());
        builder.setCVV2(this.card.getCvv());
        builder.setResponseFormat(RESPONSE_FORMAT);
        RequestBody rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = ch.provideRequest(rq.getBody());

        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }
}