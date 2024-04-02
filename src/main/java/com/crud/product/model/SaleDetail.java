package com.crud.product.model;

import lombok.Data;

@Data
public class SaleDetail {
    private Integer id_sale_detail;
    private Integer id_sale;
    private Integer id_product;
    private Integer quantity;
    private Float price;
}
