package com.crud.product.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Buy {
    private String id_buy;
    private Integer id_supplier;
    private Date purchase_date;
    private Float total_price;
    private String name_user;
    private List<Product> buyDetail;
}
