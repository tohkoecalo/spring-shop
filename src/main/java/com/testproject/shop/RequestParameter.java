package com.testproject.shop;

public class RequestParameter {

    public static final String XML_REQUEST_PARAMETER_KEY = "xmlRequest";
    public static final String AUTH_DATA_PARAMETER_KEY = "authData";

    private String key;
    private String value;

    public RequestParameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}