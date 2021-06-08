package com.testproject.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}