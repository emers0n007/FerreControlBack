package com.crud.product.service;

import com.crud.product.model.Buy;

import java.util.List;

public interface IBuyService {
    public List<Buy> findAll();
    public int save(Buy buy);

    public Buy findBuyById(int id_buy);
}
