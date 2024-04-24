package com.crud.product.repository;

import com.crud.product.model.Buy;
import com.crud.product.model.Mark;
import com.crud.product.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MarkRepository implements IMarkRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Mark> findAll() {
        String SQL = "SELECT * FROM mark";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Mark.class));
    }

    @Override
    public int save(Mark mark) {
        String SQL = "INSERT INTO Mark VALUES(?,?)";
        return jdbcTemplate.update(SQL, new Object[]{mark.getId_mark(),mark.getName_mark()});
    }
}
