package com.crud.product.repository;

import com.crud.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BuyRepository implements IBuyRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IMarkRepository iMarkRepository;

    @Autowired
    private IPresentationRepository iPresentationRepository;

    @Autowired
    private ISupplierRepository iSupplierRepository;


    @Override
    public List<Buy> findAll() {
        String SQL = "SELECT b.*, bd.id_product as product_detail_id, bd.quantity as quantity_detail, p.* " +
                "FROM Buy b " +
                "JOIN Buy_Detail bd ON b.id_buy = bd.id_buy " +
                "JOIN Product p ON bd.id_product = p.id_product";

        Map<String, Buy> buyMap = new HashMap<>();
        jdbcTemplate.query(SQL, rs -> {
            String id_buy = rs.getString("id_buy");
            Buy buy = buyMap.get(id_buy);
            if (buy == null) {
                buy = new Buy();
                buy.setId_buy(id_buy);
                buy.setId_supplier(rs.getInt("id_supplier"));
                buy.setPurchase_date(rs.getDate("purchase_date"));
                buy.setTotal_price(rs.getFloat("total_price"));
                buy.setName_user(rs.getString("name_user"));
                buy.setBuyDetail(new ArrayList<>());
                buyMap.put(id_buy, buy);
            }
            Product product = new Product();
            product.setId_product(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setPrice_buy(rs.getFloat("price_buy"));
            product.setPrice_sale(rs.getFloat("price_sale"));
            product.setStock(rs.getInt("stock"));
            product.setQuantity(rs.getInt("quantity_detail"));
            product.setStatus(rs.getInt("status"));

            Mark mark = new Mark();
            mark.setId_mark(rs.getInt("id_mark"));
            product.setMark(iMarkRepository.findById(mark));

            Presentation presentation =  new Presentation();
            presentation.setId_presentation(rs.getInt("id_presentation"));
            product.setPresentation(iPresentationRepository.findById(presentation));

            Supplier supplier = new Supplier();
            supplier.setId_supplier(rs.getInt("id_supplier"));
            product.setSupplier(iSupplierRepository.findById(supplier));
            // Set other properties of the product as needed
            buy.getBuyDetail().add(product);
        });
        return new ArrayList<>(buyMap.values());
    }

    @Override
    public int save(Buy buy) {
        int aux = 0;
        // Insert into Buy table
        String buyInsertSQL = "INSERT INTO Buy (id_buy, id_supplier, purchase_date, total_price,name_user) VALUES (?, ?, ?, ?,?)";
        aux = jdbcTemplate.update(buyInsertSQL, buy.getId_buy(), buy.getId_supplier(), buy.getPurchase_date(), buy.getTotal_price(),buy.getName_user());

        // Insert into Buy_Detail table for each product in buyDetail list
        String buyDetailInsertSQL = "INSERT INTO Buy_Detail (id_buy, id_product, quantity) VALUES (?, ?, ?)";
        for (Product product : buy.getBuyDetail()) {
            aux = jdbcTemplate.update(buyDetailInsertSQL, buy.getId_buy(), product.getId_product(), product.getQuantity());
        }

        String searchProductSQL = "SELECT quantity FROM Product WHERE id_product=?";
        String updateStockProductSQL = "UPDATE Product SET quantity=? WHERE id_product=?";
        for (Product product : buy.getBuyDetail()) {
            // Buscar el stock actual del producto
            Integer currentStock = jdbcTemplate.queryForObject(searchProductSQL, Integer.class, product.getId_product());
            if (currentStock != null) {
                // Calcular el nuevo stock (restar la cantidad comprada)
                int newStock = currentStock + product.getQuantity();
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
    public Buy findBuyById(String id_buy) {
        String SQL = "SELECT b.*, bd.id_product as product_detail_id, bd.quantity as quantity_detail, p.* " +
                "FROM Buy b " +
                "JOIN Buy_Detail bd ON b.id_buy = bd.id_buy " +
                "JOIN Product p ON bd.id_product = p.id_product " +
                "WHERE b.id_buy = ?";

        return jdbcTemplate.query(SQL, new Object[]{id_buy}, rs -> {
            Buy buy = null;
            List<Product> buyDetail = new ArrayList<>();
            while (rs.next()) {
                if (buy == null) {
                    buy = new Buy();
                    buy.setId_buy(rs.getString("id_buy"));
                    buy.setId_supplier(rs.getInt("id_supplier"));
                    buy.setPurchase_date(rs.getDate("purchase_date"));
                    buy.setTotal_price(rs.getFloat("total_price"));
                }
                Product product = new Product();
                product.setId_product(rs.getInt("id_product"));
                product.setName(rs.getString("name"));
                product.setPrice_buy(rs.getFloat("price_buy"));
                product.setPrice_buy(rs.getFloat("price_sale"));
                product.setStock(rs.getInt("quantity_detail"));
                // Set other properties of the product as needed
                buyDetail.add(product);
            }
            if (buy != null) {
                buy.setBuyDetail(buyDetail);
            }
            return buy;
        });
    }
}
