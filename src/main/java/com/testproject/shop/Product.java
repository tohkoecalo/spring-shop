package com.testproject.shop;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private int id;
    private String name;
    private String description;
    private int price;

    public Product(){}

    public Product(int id, String name, String description, int price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getPrice(){
        return price;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
