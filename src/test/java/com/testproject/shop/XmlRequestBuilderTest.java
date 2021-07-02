package com.testproject.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class XmlRequestBuilderTest {

    @Test
    @DisplayName("Test of XML request builder")
    public void testRepresentXmlDocAsString() throws Exception {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation("Operation");
        builder.setLanguage("Language");
        builder.setMerchant("Merchant");
        builder.setAmount("Amount");
        builder.setCurrency("Currency");
        builder.setDescription("Description");
        builder.setOrderId("OrderId");
        builder.setSessionId("SessionId");
        builder.setPAN("PAN");
        builder.setExpDate("ExpDate");
        builder.setEncodedPAReq("EncodedPAReq");
        builder.setPARes("PARes");
        builder.setCVV2("CVV2");
        builder.setResponseFormat("ResponseFormat");

        String xmlRequestExample = Utils.representXmlDocAsString(builder.build().getBody());
        assertEquals("<TKKPG><Request><Order><Merchant>Merchant</Merchant><Amount>Amount</Amount>" +
                         "<Currency>Currency</Currency><Description>Description</Description><OrderID>OrderId</OrderID></Order>" +
                        "<Operation>Operation</Operation><Language>Language</Language><SessionID>SessionId</SessionID><PAN>PAN</PAN>" +
                        "<ExpDate>ExpDate</ExpDate><EncodedPAReq>EncodedPAReq</EncodedPAReq><PARes>PARes</PARes><CVV2>CVV2</CVV2>" +
                        "<ResponseFormat>ResponseFormat</ResponseFormat></Request></TKKPG>", xmlRequestExample);
    }

}
