package com.crud.product.repository;

import com.crud.product.model.Buy;
import com.crud.product.model.Product;
import com.crud.product.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class SaleRepository implements ISaleRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<Sale> findAll() {
        String SQL = "SELECT b.*, bd.id_product as product_detail_id, bd.quantity as quantity_detail, p.* " +
                "FROM Sale b " +
                "JOIN Sale_Detail bd ON b.is_sale = bd.is_sale " +
                "JOIN Product p ON bd.id_product = p.id_product";

        Map<Integer, Sale> saleMap = new HashMap<>();
        jdbcTemplate.query(SQL, rs -> {
            int id_buy = rs.getInt("id_buy");
            Sale sale = saleMap.get(id_buy);
            if (sale == null) {
                sale = new Sale();
                sale.setId_sale(id_buy);
                sale.setId_supplier(rs.getInt("id_supplier"));
                sale.setSale_date(rs.getDate("sale_date"));
                sale.setTotal_price(rs.getFloat("total_price"));
                sale.setSaleDetail(new ArrayList<>());
                saleMap.put(id_buy, sale);
            }
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));
            product.setStock(rs.getInt("quantity_detail"));
            // Set other properties of the product as needed
            sale.getSaleDetail().add(product);
        });
        return new ArrayList<>(saleMap.values());
    }

    @Override
    public int save(Sale sale) {
        int aux = 0;
        // Insert into Buy table
        String buyInsertSQL = "INSERT INTO Sale (id_sale, id_supplier, sale_date, total_price) VALUES (?, ?, ?, ?)";
        aux = jdbcTemplate.update(buyInsertSQL, sale.getId_sale(), sale.getId_supplier(), sale.getSale_date(), sale.getTotal_price());

        // Insert into Buy_Detail table for each product in buyDetail list
        String buyDetailInsertSQL = "INSERT INTO Sale_Detail (id_sale, id_product, quantity, price) VALUES (?, ?, ?, ?)";
        for (Product product : sale.getSaleDetail()) {
            aux = jdbcTemplate.update(buyDetailInsertSQL, sale.getId_sale(), product.getId_product(), product.getStock(), (product.getPrice_buy()*product.getStock()));
        }

        String searchProductSQL = "SELECT stock FROM Product WHERE id_product=?";
        String updateStockProductSQL = "UPDATE Product SET stock=? WHERE id_product=?";
        for (Product product : sale.getSaleDetail()) {
            // Buscar el stock actual del producto
            Integer currentStock = jdbcTemplate.queryForObject(searchProductSQL, Integer.class, product.getId_product());
            if (currentStock != null) {
                // Calcular el nuevo stock (restar la cantidad comprada)
                int newStock = currentStock - product.getStock();
                // Actualizar el stock en la base de datos
                jdbcTemplate.update(updateStockProductSQL, newStock, product.getId_product());
            } else {
                // Manejar el caso en el que no se encuentre el producto
                // Aquí puedes lanzar una excepción, registrar un mensaje de error, etc.
            }
        }

        return aux;
    }

    @Override
    public Sale findSaleById(int id_sale) {
        String SQL = "SELECT b.*, bd.id_product as product_detail_id, bd.quantity as quantity_detail, p.* " +
                "FROM Sale b " +
                "JOIN Sale_Datail bd ON b.id_sale = bd.id_sale " +
                "JOIN Product p ON bd.id_product = p.id_product " +
                "WHERE b.id_sale = ?";

        return jdbcTemplate.query(SQL, new Object[]{id_sale}, rs -> {
            Sale sale = null;
            ArrayList<Product> saleDetail = new ArrayList<>();
            while (rs.next()) {
                if (sale == null) {
                    sale = new Sale();
                    sale.setId_sale(rs.getInt("id_sale"));
                    sale.setId_supplier(rs.getInt("id_supplier"));
                    sale.setSale_date(rs.getDate("purchase_date"));
                    sale.setTotal_price(rs.getFloat("total_price"));
                }
                Product product = new Product();
                product.setId_product(rs.getInt("id_product"));
                product.setName(rs.getString("name"));
                product.setPrice_buy(rs.getFloat("price_buy"));
                product.setPrice_buy(rs.getFloat("price_sale"));
                product.setStock(rs.getInt("quantity_detail"));
                // Set other properties of the product as needed
                saleDetail.add(product);
            }
            if (sale != null) {
                sale.setSaleDetail(saleDetail);
            }
            return sale;
        });
    }
}
