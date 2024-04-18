package com.crud.product.repository;

import com.crud.product.model.Buy;
import com.crud.product.model.Product;

import java.util.List;

public interface IBuyRepository {
    public List<Buy> findAll();
    public int save(Buy buy);

    public Buy findBuyById(int id_buy);
}
