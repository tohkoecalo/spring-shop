package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Scope("singleton")
public class ResponseFieldsExtractor {
    public ResponseFieldsExtractor(){}

    public String getOrderId(Map<String, String> responseDetails){
        return responseDetails.get("OrderID");
    }

    public boolean isCard3dsEnrolled(Map<String, String> responseDetails){
        if (responseDetails.containsKey("enrolled")){
            return responseDetails.get("enrolled").equals("Y");
        }
        return false;
    }

    public String[] getPaReqFormData(Map<String, String> responseDetails) throws Exception {
        String[] result = new String[2];
        result[0] = responseDetails.get("url");
        result[1] = responseDetails.get("pareq");
        return result;
    }

    String getOrderStatus(Map<String, String> responseDetails) {
        return responseDetails.get("OrderStatus");
    }
}
