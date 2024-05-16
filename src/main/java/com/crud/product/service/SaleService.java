package com.crud.product.service;

import com.crud.product.model.Buy;
import com.crud.product.model.Sale;
import com.crud.product.repository.IBuyRepository;
import com.crud.product.repository.ISaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaleService implements ISaleService{
    @Autowired
    private ISaleRepository iSaleRepository;

    @Override
    public List<Sale> findAll() {
        List<Sale> list;
        try {
            list = iSaleRepository.findAll();
        }catch (Exception ex){

            throw ex;
        }
        return list;
    }

    @Override
    public int save(Sale sale) {
        int row;
        try {
            row = iSaleRepository.save(sale);
        }catch (Exception ex){
            System.out.println("Error" + ex.getMessage());
            throw ex;
        }
        return row;
    }

    @Override
    public Sale findSaleById(String id_sale) {
        Sale sale;
        try {
            sale = iSaleRepository.findSaleById(id_sale);
        }catch (Exception ex){
            throw ex;
        }
        return sale;
    }
}
