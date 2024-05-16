package com.crud.product.repository;

import com.crud.product.model.Buy;
import com.crud.product.model.Sale;

import java.util.List;

public interface ISaleRepository {
    public List<Sale> findAll();
    public int save(Sale sale);

    public Sale findSaleById(String id_Sale);
}
