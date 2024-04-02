package com.crud.product.repository;

import com.crud.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Product> findAll() {
        String SQL = "SELECT * FROM product";
        System.out.println(jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Product.class)));
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Product.class));
    }

    @Override
    public int save(Product product) {
        String SQL = "INSERT INTO product VALUES(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{product.getId_product(), product.getName(), product.getStock(),product.getPrice_buy(),product.getPrice_sale(),product.getId_supplier(),product.getStatus()});
    }

    @Override
    public int update(Product product) {
        String SQL = "UPDATE product SET name=?,stock=?,price_buy=?,price_sale=?,id_supplier=? WHERE id_product =?";
        return jdbcTemplate.update(SQL,new Object[]{product.getName(), product.getStock(), product.getPrice_buy(), product.getPrice_sale(),product.getId_supplier(),product.getId_product()});
    }

    @Override
    public int deleteById(int id) {
        String SQL ="UPDATE product SET status=0 WHERE id_product =?";
        return jdbcTemplate.update(SQL,new Object[]{id});
    }
}
