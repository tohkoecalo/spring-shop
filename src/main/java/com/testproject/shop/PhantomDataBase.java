package com.testproject.shop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class PhantomDataBase {
    private enum PRICE {
        First(25), Second(119), Third(69), Fourth(229);

        private double value;

        PRICE(double value){
            this.value = value;
        }

        public double getValue(){ return value; }
    }

    public PhantomDataBase(){}

    public List<Product> getCatalog(){
        List<Product> content = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            content.add(new Product(i, "Product #" + i, "Description of Product #" + i, PRICE.values()[i - 1].getValue()));
        }
        return content;
    }
}
