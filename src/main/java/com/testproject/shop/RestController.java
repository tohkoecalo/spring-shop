package com.testproject.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class RestController {

    @Autowired
    PhantomDataBase db;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return db.getCatalog();
    }

    @Autowired
    OperationProvider provider;

    @Autowired
    ResponseFieldsExtractor extractor;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/create")
    public @ResponseBody String createOrderAndGetId(@RequestBody Order order) {
        try {
            Map<String, String> response = provider.createOrder(order);
            return extractor.getOrderId(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/order/check3ds")
    public @ResponseBody boolean isCard3dsEnrolled(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.check3ds(orderId);
            return extractor.isCard3dsEnrolled(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/order/getpareq")
    public @ResponseBody String getOrderPareqData(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.getPAReqForm(orderId);
            String[] formData = extractor.getPaReqFormData(response);
            return "{ \"url\":\"" + formData[0] + "\", \"pareq\":\"" + formData[1] + "\" }";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/order/after_issuer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String processRespAndGetOrderStatus(@RequestBody String acsResponse) {
        try {
            acsResponse = Utils.urlDecodeSymbols(acsResponse);
            String[] parameters = acsResponse.split("&");
            String orderId = "";
            String pares = "";
            if (parameters[0].contains("PaRes")){
                pares = parameters[0].split("=")[1];
                orderId = Utils.decodeFromBase64(parameters[1].split("=")[1]);
            } else {
                pares = parameters[1].split("=")[1];
                orderId = Utils.decodeFromBase64(parameters[0].split("=")[1]);
            }
            Map<String, String> response = provider.processPARes(orderId, pares);
            return extractor.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/purchase")
    public @ResponseBody String purchaseAndGetOrderStatus(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.purchase(orderId);
            return extractor.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public class ServerErrorException extends RuntimeException {}
}