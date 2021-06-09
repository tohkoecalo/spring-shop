package com.testproject.shop;

import java.util.Map;

public class OperationProvider {
    private static final String LANGUAGE = "RU";
    private static final String MERCHANT = "POS_1";
    private static final String CURRENCY = "643";
    private static final String RESPONSE_FORMAT = "TKKPG";

    private String amount;
    private String orderId;
    private String sessionId;
    private String pan;
    private String expDate;
    private String cvv2;
    private String pareq;

    private OperationProvider(){
        this.amount = "";
        this.orderId = "0";
        this.sessionId = "0";
        this.pan = "";
        this.expDate = "";
        this.cvv2 = "";
        this.pareq = "";
    }

    private static class CommunicatorHolder{
        private final static OperationProvider instance = new OperationProvider();
    }

    public static OperationProvider getInstance(){
        return OperationProvider.CommunicatorHolder.instance;
    }

    public String createOrder(String amount){//need auth data
        this.amount = amount;
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

    public boolean check3ds(String orderId, String sessionId, String pan, String expDate, String cvv2) {
        this.pan = pan;
        this.expDate = expDate;
        this.cvv2 = cvv2;

        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(this.pan);
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

    public String getPAReqForm() {
        RequestBody.Builder builder = RequestBody.newBuilder();
        builder.setOperation(RequestBody.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(pan);

        builder.setExpDate(expDate);
        this.expDate = expDate;
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
        builder.setPAN(pan);
        builder.setExpDate(expDate);
        builder.setCVV2(cvv2);
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
        builder.setPAN(pan);
        builder.setExpDate(expDate);
        builder.setCVV2(cvv2);
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