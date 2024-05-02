package com.crud.product.repository;

import com.crud.product.model.Buy;
import com.crud.product.model.Mark;

import java.util.List;

public interface IMarkRepository {
    public List<Mark> findAll();
    public int save(Mark mark);

    public Mark findTop(Mark mark);
}
