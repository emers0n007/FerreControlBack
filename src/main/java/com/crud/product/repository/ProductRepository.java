package com.crud.product.repository;

import com.crud.product.model.Mark;
import com.crud.product.model.Presentation;
import com.crud.product.model.Product;
import com.crud.product.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Product> findAll() {
        String SQL = "SELECT p.*, s.id_supplier as supplier_id, s.name as supplier_name, s.phone as supplier_phone, s.email as supplier_email, m.id_mark as id_mark, m.name_mark as name_mark, pre.id_presentation as id_presentation, pre.name_presentation, pre.description_presentation as  description_presentation " +
                "FROM product p " +
                "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                "JOIN mark m ON p.id_mark = m.id_mark " +
                "JOIN presentation pre ON p.id_presentation = pre.id_presentation " +
                "WHERE p.status = 1";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setStock(rs.getInt("stock"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));
            product.setQuantity(rs.getInt("quantity"));

            Supplier supplier = new Supplier();
            supplier.setId_supplier(rs.getInt("supplier_id"));
            supplier.setName(rs.getString("supplier_name"));
            supplier.setPhone(rs.getString("supplier_phone"));
            supplier.setEmail(rs.getString("supplier_email"));

            product.setSupplier(supplier);

            Mark mark = new Mark();
            mark.setId_mark(rs.getInt("id_mark"));
            mark.setName_mark(rs.getString("name_mark"));

            product.setMark(mark);

            Presentation presentation =  new Presentation();
            presentation.setId_presentation(rs.getInt("id_presentation"));
            presentation.setName_presentation(rs.getString("name_presentation"));
            presentation.setDescription_presentation(rs.getInt("description_presentation"));
            product.setPresentation(presentation);

            product.setStatus(rs.getInt("status"));

            return product;
        });
    }
    @Override
    public int save(Product product) {
        String SQL = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{product.getId_product(), product.getName(), product.getStock(),product.getPrice_buy(),product.getPrice_sale(),product.getSupplier().getId_supplier(),product.getStatus(), product.getMark().getId_mark(), product.getPresentation().getId_presentation(), product.getQuantity()});
    }

    @Override
    public Product findProduct(int id){
        String SQL = "SELECT p.*, s.id_supplier as supplier_id, s.name as supplier_name, s.phone as supplier_phone, s.email as supplier_email, m.id_mark as id_mark, m.name_mark as name_mark, pre.id_presentation as id_presentation, pre.name_presentation, pre.description_presentation as  description_presentation " +
                "FROM product p " +
                "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                "JOIN mark m ON p.id_mark = m.id_mark " +
                "JOIN presentation pre ON p.id_presentation = pre.id_presentation " +
                "WHERE p.id_product = ?";

        return jdbcTemplate.queryForObject(SQL, new Object[]{id},(rs, rowNum) -> {
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setStock(rs.getInt("stock"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));
            product.setQuantity(rs.getInt("quantity"));

            Supplier supplier = new Supplier();
            supplier.setId_supplier(rs.getInt("supplier_id"));
            supplier.setName(rs.getString("supplier_name"));
            supplier.setPhone(rs.getString("supplier_phone"));
            supplier.setEmail(rs.getString("supplier_email"));

            product.setSupplier(supplier);

            Mark mark = new Mark();
            mark.setId_mark(rs.getInt("id_mark"));
            mark.setName_mark(rs.getString("name_mark"));

            product.setMark(mark);

            Presentation presentation =  new Presentation();
            presentation.setId_presentation(rs.getInt("id_presentation"));
            presentation.setName_presentation(rs.getString("name_presentation"));
            presentation.setDescription_presentation(rs.getInt("description_presentation"));
            product.setPresentation(presentation);

            product.setStatus(rs.getInt("status"));

            return product;
        });
    }
    @Override
    public int update(Product product) {
        System.out.println(product.toString());
        Product productCurrent = findProduct(product.getId_product());
        System.out.println(productCurrent.toString());
        String SQL = "UPDATE product SET name=?,stock=?,price_buy=?,price_sale=?,id_supplier=? WHERE id_product =?";
        String SQL2 = "UPDATE mark SET name_mark = ? WHERE id_mark = ?";
        String SQL3 = "UPDATE presentation SET name_presentation = ?, description_presentation  = ? WHERE id_presentation = ?";
        jdbcTemplate.update(SQL2,new Object[]{product.getMark().getName_mark(), productCurrent.getId_product()});
        jdbcTemplate.update(SQL3,new Object[]{product.getPresentation().getName_presentation(),product.getPresentation().getDescription_presentation(),productCurrent.getPresentation().getId_presentation()});
        return jdbcTemplate.update(SQL,new Object[]{product.getName(), product.getStock(), product.getPrice_buy(), product.getPrice_sale(),product.getSupplier().getId_supplier(), product.getId_product()});
    }

    @Override
    public int deleteById(int id) {
        String SQL ="UPDATE product SET status=0 WHERE id_product =?";
        return jdbcTemplate.update(SQL,new Object[]{id});
    }

    @Override
    public List<Product> findProductLowStock() {
        String SQL = "SELECT p.*, s.id_supplier as supplier_id, s.name as supplier_name, s.phone as supplier_phone, s.email as supplier_email, m.id_mark as id_mark, m.name_mark as name_mark, pre.id_presentation as id_presentation, pre.name_presentation, pre.description_presentation as  description_presentation " +
                "FROM product p " +
                "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                "JOIN mark m ON p.id_mark = m.id_mark " +
                "JOIN presentation pre ON p.id_presentation = pre.id_presentation " +
                "WHERE p.quantity <= p.stock AND p.status = 1";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setStock(rs.getInt("stock"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));
            product.setQuantity(rs.getInt("quantity"));

            Supplier supplier = new Supplier();
            supplier.setId_supplier(rs.getInt("supplier_id"));
            supplier.setName(rs.getString("supplier_name"));
            supplier.setPhone(rs.getString("supplier_phone"));
            supplier.setEmail(rs.getString("supplier_email"));

            product.setSupplier(supplier);

            Mark mark = new Mark();
            mark.setId_mark(rs.getInt("id_mark"));
            mark.setName_mark(rs.getString("name_mark"));

            product.setMark(mark);

            Presentation presentation =  new Presentation();
            presentation.setId_presentation(rs.getInt("id_presentation"));
            presentation.setName_presentation(rs.getString("name_presentation"));
            presentation.setDescription_presentation(rs.getInt("description_presentation"));
            product.setPresentation(presentation);

            product.setStatus(rs.getInt("status"));

            return product;
        });
    }
}
