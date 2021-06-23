package com.testproject.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RestController {

    @Autowired
    PhantomDataBase db;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return db.getCatalog();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/products", consumes = "application/json")
    public @ResponseBody void addProductToCart(@RequestBody Product product) {
        db.addToCart(product);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/cart")
    public @ResponseBody Iterable<Product> getCartItems() {
        return db.getCartItem();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path="/cart")
    public @ResponseBody void clearCart() {
        db.clearCart();
    }

    @Autowired
    OperationProvider provider;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/create")
    public @ResponseBody String createOrder(@RequestBody Order order) {
        provider.createOrder(order);
        String redirectDest = "/order/purchase";
        if (provider.check3ds()){
            String[] redirectData = provider.getPAReqForm();
            redirectDest = redirectData[0];
            //post on acs with pareq
        }
        return redirectDest;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/after_issuer")
    public @ResponseBody String processResp() {
        return provider.processPARes();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/purchase", consumes = "application/json")
    public @ResponseBody String purchase() {
        return provider.purchase();
    }
}