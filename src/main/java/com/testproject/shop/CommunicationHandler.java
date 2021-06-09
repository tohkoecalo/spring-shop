package com.testproject.shop;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

public class CommunicationHandler {
    private static final String URL = "10.77.201.18:5556/execpwd";
    private static final String MERCHANT = "REPLACE_ME";
    private static final String PASSWORD = "REPLACE_ME";

    public String sendRequest(String body) { //Methods sends only HTTP POST request on constant url
        try {
            URL rUrl = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) rUrl.openConnection();
            connection.setRequestMethod(HttpMethod.POST.toString());
            connection.setDoOutput(true);
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML.toString());

            OutputStream os = connection.getOutputStream();
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
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

    public Map<String, String> getResponseDataAsMap(String xmlResponse) { //Represents response content as map
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
