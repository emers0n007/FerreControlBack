package com.crud.product.service;

import com.crud.product.model.Buy;
import com.crud.product.model.Product;
import com.crud.product.repository.IBuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BuyService implements IBuyService{
    @Autowired
    private IBuyRepository iBuyRepository;

    @Override
    public List<Buy> findAll() {
        List<Buy> list;
        try {
            list = iBuyRepository.findAll();
        }catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Buy buy) {
        int row;
        try {
            row = iBuyRepository.save(buy);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public Buy findBuyById(int id_buy) {
        Buy buy;
        try {
            buy = iBuyRepository.findBuyById(id_buy);
        }catch (Exception ex){
            throw ex;
        }
        return buy;
    }
}
