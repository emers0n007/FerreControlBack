package com.crud.product.repository;

import com.crud.product.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierRepository implements ISupplierRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Supplier> findAll() {
        String SQL = "SELECT * FROM supplier";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Supplier.class));
    }

    @Override
    public int save(Supplier supplier) {
        String SQL = "INSERT INTO Supplier VALUES(?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{supplier.getId_supplier(),supplier.getName(),supplier.getPhone(), supplier.getEmail()});
    }

    @Override
    public int update(Supplier supplier) {
        String SQL = "UPDATE Supplier SET name=?,phone=?,email=? WHERE id_supplier =?";
        return jdbcTemplate.update(SQL,new Object[]{supplier.getName(), supplier.getPhone(),supplier.getEmail(),supplier.getId_supplier()});

    }

    @Override
    public int deleteById(int id) {
        String SQL ="UPDATE Supplier SET status=0 WHERE id_supplier =?";
        return jdbcTemplate.update(SQL,new Object[]{id});
    }
}
