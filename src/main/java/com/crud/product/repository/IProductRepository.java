package com.crud.product.repository;

import com.crud.product.model.Product;

import java.util.List;

public interface IProductRepository {
    public List<Product> findAll();
    public int save(Product product);
    public  int update(Product product);
    public int deleteById(int id);

    public List<Product> findProductLowStock();

}
