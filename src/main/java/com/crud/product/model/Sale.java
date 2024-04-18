package com.crud.product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
public class Sale {
    private Integer id_sale;
    private Integer id_supplier;
    private Date sale_date;
    private Float total_price;
    private ArrayList<Product> saleDetail;
}
