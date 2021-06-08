package com.testproject.shop;

public class Request {
    public static enum Operation {
        CREATE_ORDER("CreateOrder"), CHECK_3DS_ENROLLED("#Check3DSEnrolled"), GET_PAREQ_FORM("#GetPAReqForm"),
        PROCESS_PARES("ProcessPARes"), PURCHASE("Purchase");

        private String value;
        Operation(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
    }

    private String body;

    private Request(){}

    public String getBody(){
        return body;
    }

    public static Builder newBuilder() {
        return new Request().new Builder();
    }

    public class Builder{
        private Builder(){}

        public Builder setAny(){//need different for any single property in xml body
            //
            return this;
        }
        /*
        * Operation - above order block
        * Language - above order block
        * Merchant - order block
        * Amount - order block
        * Currency - order block
        * Description - order block
        * OrderID - order block
        * SessionID - below order block
        * PAN - below order block
        * ExpDate - below order block
        * EncodedPAReq - below order block
        * PARes - below order block
        * CVV2 - below order block
        * ResponseFormat - below order block
        * */

        public Request build(){
            return Request.this;
        }
    }

}
