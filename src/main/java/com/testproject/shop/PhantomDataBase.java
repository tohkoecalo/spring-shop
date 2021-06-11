package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class PhantomDataBase {
    private enum PRICE {
        First(1.99), Second(1.19), Third(0.69), Fourth(2.29);

        private double value;

        PRICE(double value){
            this.value = value;
        }

        public double getValue(){ return value; }
    }

    public PhantomDataBase(){}

    /*private static class InstanceHolder{
        private final static PhantomDataBase instance = new PhantomDataBase();
    }*/

    private static class Cart{
        private static List<Product> cart = new ArrayList<>();
        private static void addToCart(Product product){
            cart.add(product);
        }
        private static void clearCart() {
            cart.clear();
        }
    }

    /*public static PhantomDataBase getInstance(){
        return InstanceHolder.instance;
    }*/

    public List<Product> getCatalog(){
        List<Product> content = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            content.add(new Product(i, "Product #" + i, "Description of Product #" + i, PRICE.values()[i - 1].getValue()));
        }
        return content;
    }

    public List<Product> getCartItem(){
        return Cart.cart;
    }

    public void addToCart(Product product){
        Cart.addToCart(product);
    }

    public void clearCart(){
        Cart.clearCart();
    }
}
