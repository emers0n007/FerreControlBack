package com.crud.product.service;

import com.crud.product.model.Mark;
import com.crud.product.model.Supplier;
import com.crud.product.repository.IMarkRepository;
import com.crud.product.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MarkService implements IMarkService{
    @Autowired
    private IMarkRepository iMarkRepository;
    @Override
    public List<Mark> findAll() {
        List<Mark> list;
        try {
            list = iMarkRepository.findAll();
        }catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Mark mark) {
        int row = 0;
        try {
            List<Mark> marks = findAll();
            Boolean exist = false;
            for (Mark newMark : marks) {
                if(mark.getName_mark().compareToIgnoreCase(newMark.getName_mark())==0){
                    exist = true;
                }
            }
            if(!exist){
                row = iMarkRepository.save(mark);
            }
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }
}
