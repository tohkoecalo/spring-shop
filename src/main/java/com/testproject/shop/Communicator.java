package com.testproject.shop;

import java.util.Map;

public class Communicator { //need constants mb singleton
    private String amount;
    private String orderId;
    private String sessionId;
    private String pan;
    private String expDate;
    private String cvv2;
    private String pareq;

    public Communicator(){
        this.amount = "";
        this.orderId = "0";
        this.sessionId = "0";
        this.pan = "";
        this.expDate = "";
        this.cvv2 = "";
        this.pareq = "";
    }

    public String createOrder(String amount, String description){//need auth data
        this.amount = amount;
        Request.Builder builder = Request.newBuilder();
        builder.setOperation(Request.Operation.CREATE_ORDER.getValue());
        builder.setLanguage("RU");
        builder.setMerchant("POS_1");
        builder.setAmount(this.amount);
        builder.setCurrency("643");
        builder.setDescription(description);
        Request rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = null;
        try {
            responseDetails = ch.getResponseDataAsMap(ch.sendRequest(Utils.representXmlDocAsString(rq.getBody())));
        } catch (Exception e){
            e.printStackTrace();
        }

        if (responseDetails.get("Status").equals("00")) {
            orderId = responseDetails.get("OrderID");
            sessionId = responseDetails.get("SessionID");
        }
        return orderId;
    }

    public boolean check3ds(String orderId, String sessionId, String pan, String expDate, String cvv2) {
        this.pan = pan;
        this.expDate = expDate;
        this.cvv2 = cvv2;

        Request.Builder builder = Request.newBuilder();
        builder.setOperation(Request.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant("POS_1");
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(this.pan);
        Request rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = null;
        try {
            responseDetails = ch.getResponseDataAsMap(ch.sendRequest(Utils.representXmlDocAsString(rq.getBody())));
        } catch (Exception e){
            e.printStackTrace();
        }

        if (responseDetails.get("Status").equals("00")) {
            if (responseDetails.get("enrolled").equals("Y")){
                return true;
            }
        }
        return false;
    }

    public String getPAReqForm() {
        Request.Builder builder = Request.newBuilder();
        builder.setOperation(Request.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant("POS_1");
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPAN(pan);

        builder.setExpDate(expDate);
        this.expDate = expDate;
        builder.setEncodedPAReq("true");
        Request rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = null;
        try {
            responseDetails = ch.getResponseDataAsMap(ch.sendRequest(Utils.representXmlDocAsString(rq.getBody())));
        } catch (Exception e){
            e.printStackTrace();
        }

        String result = "";
        if (responseDetails.get("Status").equals("00")) {
            result = responseDetails.get("url");
            pareq = responseDetails.get("pareq");
        }
        return result;
    }

    public String processPARes() {
        Request.Builder builder = Request.newBuilder();
        builder.setOperation(Request.Operation.PROCESS_PARES.getValue());
        builder.setMerchant("POS_1");
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setPARes(pareq);
        builder.setPAN(pan);
        builder.setExpDate(expDate);
        builder.setCVV2(cvv2);
        Request rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = null;
        try {
            responseDetails = ch.getResponseDataAsMap(ch.sendRequest(Utils.representXmlDocAsString(rq.getBody())));
        } catch (Exception e){
            e.printStackTrace();
        }

        if (responseDetails.get("Status").equals("00")) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }

    public String purchase() {
        Request.Builder builder = Request.newBuilder();
        builder.setOperation(Request.Operation.PURCHASE.getValue());
        builder.setMerchant("POS_1");
        builder.setOrderId(orderId);
        builder.setSessionId(sessionId);
        builder.setAmount(amount);
        builder.setCurrency("643");
        builder.setPAN(pan);
        builder.setExpDate(expDate);
        builder.setCVV2(cvv2);
        builder.setResponseFormat("TKKPG");
        Request rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        Map<String, String> responseDetails = null;
        try {
            responseDetails = ch.getResponseDataAsMap(ch.sendRequest(Utils.representXmlDocAsString(rq.getBody())));
        } catch (Exception e){
            e.printStackTrace();
        }

        if (responseDetails.get("Status").equals("00")) {
            return responseDetails.get("OrderStatus");
        }
        return "Process went wrong";
    }
}
