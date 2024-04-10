package com.crud.product.repository;

import com.crud.product.model.Product;
import com.crud.product.model.Supplier;
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
        String SQL = "SELECT p.*, s.id_supplier as supplier_id, s.name as supplier_name, s.phone as supplier_phone, s.email as supplier_email " +
                "FROM product p " +
                "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                "WHERE p.status = 1";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setStock(rs.getInt("stock"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));

            Supplier supplier = new Supplier();
            supplier.setId_supplier(rs.getInt("supplier_id"));
            supplier.setName(rs.getString("supplier_name"));
            supplier.setPhone(rs.getString("supplier_phone"));
            supplier.setEmail(rs.getString("supplier_email"));

            product.setSupplier(supplier);
            product.setStatus(rs.getInt("status"));

            return product;
        });
    }
    @Override
    public int save(Product product) {
        String SQL = "INSERT INTO product VALUES(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{product.getId_product(), product.getName(), product.getStock(),product.getPrice_buy(),product.getPrice_sale(),product.getSupplier().getId_supplier(),product.getStatus()});
    }

    @Override
    public int update(Product product) {
        String SQL = "UPDATE product SET name=?,stock=?,price_buy=?,price_sale=?,id_supplier=? WHERE id_product =?";
        return jdbcTemplate.update(SQL,new Object[]{product.getName(), product.getStock(), product.getPrice_buy(), product.getPrice_sale(),product.getSupplier().getId_supplier(),product.getId_product()});
    }

    @Override
    public int deleteById(int id) {
        String SQL ="UPDATE product SET status=0 WHERE id_product =?";
        return jdbcTemplate.update(SQL,new Object[]{id});
    }
}
