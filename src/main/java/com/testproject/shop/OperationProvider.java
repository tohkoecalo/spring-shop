package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("singleton")
public class OperationProvider {
    private static final String TWGP_URL = "http://10.77.201.18:5556/execpwd";
    private static final String LANGUAGE = "RU";
    private static final String MERCHANT = "POS_1";
    private static final String PASSWORD = "12345";
    private static final String CURRENCY = "840";

    private Map<String, Order> orders;

    public OperationProvider(){
        this.orders = new HashMap<>();
    }

    public String getOrderStatus(String orderId){
        return orders.get(orderId).getOrderStatus();
    }

    public Map<String, String> createOrder(Order order) throws Exception {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CREATE_ORDER.getValue());
        builder.setLanguage(LANGUAGE);
        builder.setOrderType("Purchase");
        builder.setMerchant(MERCHANT);
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setDescription("Test description");
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter(RequestParameter.XML_REQUEST_PARAMETER_KEY, "");
        RequestParameter authDataParam = new RequestParameter(RequestParameter.AUTH_DATA_PARAMETER_KEY, "");
        xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
        authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            order.setOrderId(responseDetails.get("OrderID"));
            order.setSessionId(responseDetails.get("SessionID"));
            orders.put(order.getOrderId(), order);
            return responseDetails;
        } else {
            throw new OperationProviderException("CreateOrder operation went wrong with status: " + responseDetails.get("Status"));
        }
    }

    public Map<String, String> check3ds(String orderId) throws Exception {
        Order order = orders.get(orderId);
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CHECK_3DS_ENROLLED.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPAN(order.getCard().getNumber());
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter(RequestParameter.XML_REQUEST_PARAMETER_KEY, "");
        RequestParameter authDataParam = new RequestParameter(RequestParameter.AUTH_DATA_PARAMETER_KEY, "");
        xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
        authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));


        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails;
        } else {
            throw new OperationProviderException("Check3dsEnrolled operation went wrong with status: " + responseDetails.get("Status"));
        }
    }

    public Map<String, String> getPAReqForm(String orderId) throws Exception {
        Order order = orders.get(orderId);
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.GET_PAREQ_FORM.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpiry());
        builder.setEncodedPAReq("true");
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter(RequestParameter.XML_REQUEST_PARAMETER_KEY, "");
        RequestParameter authDataParam = new RequestParameter(RequestParameter.AUTH_DATA_PARAMETER_KEY, "");
        xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
        authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            return responseDetails;
        } else {
            throw new OperationProviderException("GetPAReqForm operation went wrong with status: " + responseDetails.get("Status"));
        }
    }

    public Map<String, String> processPARes(String orderId, String pares) throws Exception {
        Order order = orders.get(orderId);
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.PROCESS_PARES.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setPARes(pares);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpiry());
        builder.setCVV2(order.getCard().getCvv());
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter(RequestParameter.XML_REQUEST_PARAMETER_KEY, "");
        RequestParameter authDataParam = new RequestParameter(RequestParameter.AUTH_DATA_PARAMETER_KEY, "");
        xmlRequestParam.setValue(Utils.representXmlDocAsString(rq.getBody()));
        authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            order.setOrderStatus(responseDetails.get("OrderStatus"));
            return responseDetails;
        } else {
            throw new OperationProviderException("ProcessPARes operation went wrong with status: " + responseDetails.get("Status"));
        }
    }

    public Map<String, String> purchase(String orderId) throws Exception {
        Order order = orders.get(orderId);
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.PURCHASE.getValue());
        builder.setMerchant(MERCHANT);
        builder.setOrderId(order.getOrderId());
        builder.setSessionId(order.getSessionId());
        builder.setAmount(order.getAmount());
        builder.setCurrency(CURRENCY);
        builder.setPAN(order.getCard().getNumber());
        builder.setExpDate(order.getCard().getExpiry());
        builder.setCVV2(order.getCard().getCvv());
        builder.setResponseFormat("TKKPG");
        XmlRequest rq = builder.build();

        CommunicationHandler ch = new CommunicationHandler();
        RequestParameter xmlRequestParam = new RequestParameter(RequestParameter.XML_REQUEST_PARAMETER_KEY, "");
        RequestParameter authDataParam = new RequestParameter(RequestParameter.AUTH_DATA_PARAMETER_KEY, "");
        xmlRequestParam.setValue(Utils.urlEncodeSymbols(Utils.representXmlDocAsString(rq.getBody())));
        authDataParam.setValue(Utils.getAuthToken(Utils.representXmlDocAsString(rq.getBody()), MERCHANT, PASSWORD));

        Map<String, String> responseDetails = ch.provideRequest(TWGP_URL, authDataParam, xmlRequestParam);
        if (Utils.isResponseSuccess(responseDetails)) {
            order.setOrderStatus(responseDetails.get("OrderStatus"));
            return responseDetails;
        } else {
            throw new OperationProviderException("Purchase operation went wrong with status: " + responseDetails.get("Status"));
        }
    }

    private class OperationProviderException extends Exception {
        public OperationProviderException(String errorMessage) {
            super(errorMessage);
        }
    }
}