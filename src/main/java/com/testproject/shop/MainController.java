package com.testproject.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return PhantomDataBase.getInstance().getCatalog();
    }

    @PostMapping(path = "/products", consumes = "application/json")
    public @ResponseBody void addProductToCart(@RequestBody Product product) {
        PhantomDataBase.getInstance().addToCart(product);
    }

    @GetMapping(path="/cart")
    public @ResponseBody Iterable<Product> getCartItems() {
        return PhantomDataBase.getInstance().getCartItem();
    }
}