package com.crud.product.service;

import com.crud.product.model.Buy;
import com.crud.product.model.Sale;

import java.util.List;

public interface ISaleService {
    public List<Sale> findAll();
    public int save(Sale sale);

    public Sale findSaleById(int id_sale);
}
