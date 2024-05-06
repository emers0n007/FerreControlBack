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
public class SaleRepository implements ISaleRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IMarkRepository iMarkRepository;

    @Autowired
    private IPresentationRepository iPresentationRepository;

    @Autowired
    private ISupplierRepository iSupplierRepository;



    @Override
    public List<Sale> findAll() {
        String SQL = "SELECT b.*, bd.id_product as product_detail_id, bd.quantity as quantity_detail, p.* " +
                "FROM Sale b " +
                "JOIN Sale_Detail bd ON b.id_sale = bd.id_sale " +
                "JOIN Product p ON bd.id_product = p.id_product";

        Map<String, Sale> saleMap = new HashMap<>();
        jdbcTemplate.query(SQL, rs -> {
            String id_sale = rs.getString("id_sale");
            Sale sale = saleMap.get(id_sale);
            if (sale == null) {
                sale = new Sale();
                sale.setId_sale(id_sale);
                sale.setSale_date(rs.getDate("sale_date"));
                sale.setTotal_price(rs.getFloat("total_price"));
                sale.setSaleDetail(new ArrayList<>());
                sale.setName_user(rs.getString("name_user"));
                saleMap.put(id_sale, sale);
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
            sale.getSaleDetail().add(product);
        });
        return new ArrayList<>(saleMap.values());
    }

    @Override
    public int save(Sale sale) {
        int aux = 0;
        String buyInsertSQL = "INSERT INTO Sale (id_sale, name_user, sale_date, total_price) VALUES (?, ?, ?, ?)";
        aux = jdbcTemplate.update(buyInsertSQL, sale.getId_sale(), sale.getName_user(), sale.getSale_date(), sale.getTotal_price());

        // Insert into Buy_Detail table for each product in buyDetail list
        String buyDetailInsertSQL = "INSERT INTO Sale_Detail (id_sale, id_product, quantity) VALUES (?, ?, ?)";
        for (Product product : sale.getSaleDetail()) {
            aux = jdbcTemplate.update(buyDetailInsertSQL, sale.getId_sale(), product.getId_product(), product.getQuantity());
        }

        String searchProductSQL = "SELECT quantity FROM Product WHERE id_product=?";
        String updateStockProductSQL = "UPDATE Product SET quantity=? WHERE id_product=?";
        for (Product product : sale.getSaleDetail()) {
            // Buscar el stock actual del producto
            Integer currentStock = jdbcTemplate.queryForObject(searchProductSQL, Integer.class, product.getId_product());
            if (currentStock != null) {
                // Calcular el nuevo stock (restar la cantidad comprada)
                int newStock = currentStock - product.getQuantity();
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
                    sale.setId_sale(rs.getString("id_sale"));
                    sale.setName_user(rs.getString("name_user"));
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
