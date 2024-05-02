package com.crud.product.service;

import com.crud.product.model.Presentation;
import com.crud.product.repository.IMarkRepository;
import com.crud.product.repository.IPresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentationService implements IPresentationService{

    @Autowired
    private IPresentationRepository iPresentationRepository;
    @Override
    public int save(Presentation presentation) {
        int row;
        try {
            row = iPresentationRepository.save(presentation);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int findTop() {
        int row;
        try {
            row = iPresentationRepository.findTop();
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }
}
