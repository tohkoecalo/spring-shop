package com.testproject.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UtilsTests {

    @Test
    @DisplayName("Test of special characters encoding")
    public void testEncodeSymbols() throws Exception {
        assertEquals("%3CTEST%3E", Utils.urlEncodeSymbols("<TEST>"));
        assertEquals("%3C%2FTEST%3E", Utils.urlEncodeSymbols("</TEST>"));
    }

    @Test
    @DisplayName("Test of getAuthToken function")
    public void testGetAuthToken() throws Exception {
        assertEquals("82B430C35EFFA80F75F89E0A860665E088D0B97A8BA92D527E2AB31A26ACF023", Utils.getAuthToken("<TEST>", "TEST", "TEST"));
    }

    @Test
    @DisplayName("Test of sha256 function")
    public void testSha256() throws Exception {
        assertEquals("94EE059335E587E501CC4BF90613E0814F00A7B08BC7C648FD865A2AF6A22CC2", Utils.sha256("TEST"));
    }

    @Test
    @DisplayName("Test of representXmlDocAsString function")
    public void testRepresentXmlDocAsString() throws Exception {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CREATE_ORDER.getValue());
        builder.setAmount("100");
        assertEquals("<TKKPG><Request><Order><Amount>100</Amount></Order><Operation>" + XmlRequest.Operation.CREATE_ORDER.getValue() + "</Operation></Request></TKKPG>",
                Utils.representXmlDocAsString(builder.build().getBody()));
    }

    @Test
    @DisplayName("Test of representXmlDocAsString function")
    public void testLoadXmlDocFromString() throws Exception {
        XmlRequest.Builder builder = XmlRequest.newBuilder();
        builder.setOperation(XmlRequest.Operation.CREATE_ORDER.getValue());
        builder.setAmount("100");
        assertEquals(Utils.representXmlDocAsString(builder.build().getBody()),
                Utils.representXmlDocAsString(Utils.loadXmlDocFromString("<TKKPG><Request><Order><Amount>100</Amount></Order><Operation>" + XmlRequest.Operation.CREATE_ORDER.getValue() + "</Operation></Request></TKKPG>")));
    }

}
