package com.crud.product.service;

import com.crud.product.model.Product;
import com.crud.product.model.Supplier;
import com.crud.product.repository.IProductRepository;
import com.crud.product.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService implements ISupplierService{

    @Autowired
    private ISupplierRepository iSupplierRepository;
    @Override
    public List<Supplier> findAll() {
        List<Supplier> list;
        try {
            list = iSupplierRepository.findAll();
        }catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Supplier supplier) {
        int row;
        try {
            row = iSupplierRepository.save(supplier);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int update(Supplier supplier) {
        int row;
        try {
            row = iSupplierRepository.update(supplier);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int deleteById(int id) {
        int row;
        try {
            row = iSupplierRepository.deleteById(id);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }
}
