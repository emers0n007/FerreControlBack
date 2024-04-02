package com.crud.product.service;

import com.crud.product.model.Supplier;

import java.util.List;

public interface ISupplierService {
    public List<Supplier> findAll();
    public int save(Supplier supplier);
    public  int update(Supplier supplier);
    public int deleteById(int id);
}
