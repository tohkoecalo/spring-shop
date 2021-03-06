package com.testproject.shop;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

public class Utils {

    public static String urlEncodeSymbols(String sourceStr) throws UnsupportedEncodingException { //Encodes special symbols to url view
        return URLEncoder.encode(sourceStr, StandardCharsets.UTF_8.toString());
    }

    public static String urlDecodeSymbols(String sourceStr) throws UnsupportedEncodingException { //Decodes special symbols to url view
        return URLDecoder.decode(sourceStr, StandardCharsets.UTF_8.toString());
    }

    public static String decodeFromBase64(String sourceStr){
        byte[] decodedBytes = Base64.getDecoder().decode(sourceStr);
        return new String(decodedBytes);
    }

    public static String representXmlDocAsString(Document document) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }

    public static Document loadXmlDocFromString(String xml) throws Exception { //Converts XML string to Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    public static String getAuthToken(String xmlRequest, String merchant, String password) throws Exception {
        return sha256(sha256(xmlRequest) + "/" + sha256(merchant + "/" + password));
    }

    public static String sha256(String original) throws NoSuchAlgorithmException { //Found on StackOverflow, chosen because does not use any external libs
        byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
        byte[] hexChars = new byte[hash.length * 2];
        for (int j = 0; j < hash.length; j++) {
            int v = hash[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8).toUpperCase();
    }

    public static boolean isResponseSuccess(Map<String, String> response){
        return response.get("Status").equals("00");
    }
}
