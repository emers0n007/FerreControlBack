package com.crud.product.service;

import com.crud.product.model.Mark;

import java.util.List;

public interface IMarkService {
    public List<Mark> findAll();
    public int save(Mark mark);
    public Mark findTop(Mark mark);
}
