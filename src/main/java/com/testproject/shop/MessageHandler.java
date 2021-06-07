package com.testproject.shop;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {
    private static final String URL = "10.77.201.18:5556/execpwd";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String CONTENT_TYPE_PROPERTY = "Content-Type";
    private static final String CONTENT_TYPE_PROPERTY_VALUE = "text/xml";
    private static final String SHA_256_ALGORITHM = "SHA-256";
    private static final String TLS_ALGORITHM = "TLS";
    private static final String CHARSET_UTF_8 = "utf-8";

    private String sha256(String original){//String xmlRequest, String merchant, String password
        byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            byte[] hash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
            byte[] hexChars = new byte[hash.length * 2];
            for (int j = 0; j < hash.length; j++) {
                int v = hash[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars, StandardCharsets.UTF_8).toUpperCase();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    public String sendRequest(String body){
        try {
            SSLContext sc = SSLContext.getInstance(TLS_ALGORITHM);
            sc.init(null, null, null);
            URL rUrl = new URL(URL);
            HttpsURLConnection connection = (HttpsURLConnection) rUrl.openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setRequestMethod(HTTP_METHOD_POST);
            connection.setDoOutput(true);
            connection.setRequestProperty(CONTENT_TYPE_PROPERTY, CONTENT_TYPE_PROPERTY_VALUE);

            OutputStream os = connection.getOutputStream();
            byte[] input = body.getBytes(CHARSET_UTF_8);
            os.write(input, 0, input.length);
            DataOutputStream wr = null;
            wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            BufferedReader reader = null;
            StringBuilder stringBuilder;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Map<String, String> rsContent(String xmlResponse){
        Map<String, String> result = new HashMap<>();
        Document xmlDoc = null;//fooooo
        try {
            xmlDoc = loadXMLFromString(xmlResponse);
        } catch (Exception e){
            e.printStackTrace();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return null;
    }

    public static Document loadXMLFromString(String xml) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
