package com.crud.product.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Buy {
    private Integer id_buy;
    private Integer id_supplier;
    private Date purchase_date;
    private Float total_price;
    private List<Product> buyDetail;
}
