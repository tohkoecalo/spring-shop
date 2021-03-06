package com.testproject.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RestController {

    @RequestMapping(value = { "/", "/catalog", "/cart", "/form", "/status", "/order/after_issuer" })
    public String index() {
        return "index.html";
    }

    @Autowired
    PhantomDataBase db;

    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return db.getCatalog();
    }

    @Autowired
    OperationProvider provider;

    @Autowired
    ResponseFieldsExtractor extractor;

    @PostMapping(path="/order/create")
    public @ResponseBody String createOrderAndGetId(@RequestBody Order order) {
        try {
            Map<String, String> response = provider.createOrder(order);
            return extractor.getOrderId(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping(path="/order/check3ds")
    public @ResponseBody boolean isCard3dsEnrolled(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.check3ds(orderId);
            return extractor.isCard3dsEnrolled(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping(path="/order/getpareq")
    public @ResponseBody String getOrderPareqData(@RequestParam String orderId) {
        try {
            Map<String, String> response = provider.getPAReqForm(orderId);
            String[] formData = extractor.getPaReqFormData(response);
            return "{ \"url\":\"" + formData[0] + "\", \"pareq\":\"" + formData[1] + "\" }";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/order/after_issuer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String processResp(HttpServletResponse result, @RequestBody String acsResponse) {
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
            result.sendRedirect("http://localhost:8081/status");
            return extractor.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping(path="/order/purchase")
    public @ResponseBody String purchase(HttpServletResponse result, @RequestParam String orderId) {
        try {
            Map<String, String> response = provider.purchase(orderId);
            result.sendRedirect("http://localhost:8081/status");
            return extractor.getOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping(path="/order/getStatus")
    public @ResponseBody String getOrderStatus(@RequestParam String orderId) {
        try {
            return provider.getOrderStatus(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public class ServerErrorException extends RuntimeException {
        public ServerErrorException(String errorMessage){
            super(errorMessage);
        }
    }
}