package com.testproject.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/create")
    public @ResponseBody String createOrderAndGetId(@RequestBody Order order) {
        try {
            Map<String, String> response = provider.createOrder(order);
            return provider.getOrderId(response);
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
            return provider.isCard3dsEnrolled(response);
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
            String[] formData = provider.getPaReqFormData(response);
            return "{ url:" + formData[0] + ", pareq:" + formData[1] + " }";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/order/after_issuer")
    public @ResponseBody String processRespAndGetOrderStatus(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.processPARes(orderId, "test");
            return provider.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/purchase", consumes = "application/json")
    public @ResponseBody String purchaseAndGetOrderStatus(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.purchase(orderId);
            return provider.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException();
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public class ServerErrorException extends RuntimeException {}
}