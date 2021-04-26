package com.testproject.shop;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called Repository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findAll();
    void deleteById(int id);
}
