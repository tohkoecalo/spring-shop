package com.testproject.shop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
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

    public static String tmp_test = "<TKKPG><Request><Operation>CreateOrder</Operation><Language>RU</Language>" +
            "<Order><Merchant>POS_1</Merchant><Amount>2500</Amount><Currency>643</Currency><Description>Test</Description>" +
            "</Order></Request></TKKPG>";

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

    private static String getFirstLevelTextContent(Node node) {
        NodeList list = node.getChildNodes();
        StringBuilder textContent = new StringBuilder();
        for (int i = 0; i < list.getLength(); ++i) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
                textContent.append(child.getTextContent());
        }
        return textContent.toString();
    }

    public Map<String, String> getRsContent(String xmlResponse){
        Map<String, String> result = new HashMap<>();
        Document xmlDoc = loadXMLFromString(xmlResponse);
        DocumentTraversal traversal = (DocumentTraversal) xmlDoc;
        NodeIterator iterator = traversal.createNodeIterator(xmlDoc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
        for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
            String tag = ((Element) n).getTagName();
            if (getFirstLevelTextContent(n).length() > 0){
                result.put(tag, getFirstLevelTextContent(n));
            }
        }
        return result;
    }

    public static Document loadXMLFromString(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//foo
    }
}
