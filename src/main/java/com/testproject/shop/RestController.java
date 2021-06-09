package com.testproject.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RestController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return PhantomDataBase.getInstance().getCatalog();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/products", consumes = "application/json")
    public @ResponseBody void addProductToCart(@RequestBody Product product) {
        PhantomDataBase.getInstance().addToCart(product);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/cart")
    public @ResponseBody Iterable<Product> getCartItems() {
        return PhantomDataBase.getInstance().getCartItem();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path="/cart")
    public @ResponseBody void clearCart() {
        PhantomDataBase.getInstance().clearCart();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/create")
    public @ResponseBody void createOrder(@RequestParam(value = "Amount") String amount) {
        OperationProvider.getInstance().createOrder(amount);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/order/purchase", consumes = "application/json")
    public @ResponseBody void purchase() {//Need card reqs
        /*String status;
        if (Communicator.getInstance().check3ds()){
            String redirectUrl = Communicator.getInstance().getPAReqForm();
            status = Communicator.getInstance().processPARes();
        } else {
            status = Communicator.getInstance().purchase();
        }*/
    }
}