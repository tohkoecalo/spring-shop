package com.testproject.shop;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

public class CommunicationHandler {
    public CommunicationHandler(){}

    public Map<String, String> provideRequest(String url, RequestParameter... params) {
        Map<String, String> responseDetails = null;
        try {
            responseDetails = getResponseDataAsMap(sendRequest(url, params));//Utils.representXmlDocAsString(body)
        } catch (Exception e){
            e.printStackTrace();
        }
        return responseDetails;
    }

    private String sendRequest(String url, RequestParameter... params) { //Methods sends only HTTP POST request
        StringBuilder body = new StringBuilder();
        for (RequestParameter param : params){
            body.append(Utils.escapeSymbols(param.getKey())).append("=").append(Utils.escapeSymbols(param.getValue())).append("&");
        }
        body.deleteCharAt(body.length() - 1);
        try {
            URL rUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) rUrl.openConnection();
            connection.setRequestMethod(HttpMethod.POST.toString());
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED.toString());
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

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

    private Map<String, String> getResponseDataAsMap(String xmlResponse) {
        Map<String, String> result = new HashMap<>();
        Document xmlDoc = Utils.loadXmlDocFromString(xmlResponse);
        DocumentTraversal traversal = (DocumentTraversal) xmlDoc;
        NodeIterator iterator = traversal.createNodeIterator(xmlDoc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
        for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
            String tag = ((Element) n).getTagName();
            String content = getFirstLevelTextContent(n).replaceAll("[ \n]", "");
            if (content.length() > 0) {
                result.put(tag, getFirstLevelTextContent(n));
            }
        }
        return result;
    }

    private String getFirstLevelTextContent(Node node) { //Extracts first tag content from node
        NodeList list = node.getChildNodes();
        StringBuilder textContent = new StringBuilder();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
                textContent.append(child.getTextContent());
        }
        return textContent.toString();
    }
}
