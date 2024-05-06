package com.crud.product.repository;


import com.crud.product.model.Supplier;

import java.util.List;

public interface ISupplierRepository {
    public List<Supplier> findAll();
    public int save(Supplier supplier);
    public  int update(Supplier supplier);
    public int deleteById(int id);

    public Supplier findById(Supplier supplier);
}
