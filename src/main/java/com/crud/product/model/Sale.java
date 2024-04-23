package com.crud.product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
public class Sale {
    private Integer id_sale;
    private Date sale_date;
    private Float total_price;
    private String name_user;
    private ArrayList<Product> saleDetail;
}
