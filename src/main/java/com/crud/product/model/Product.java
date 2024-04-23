package com.crud.product.model;

import lombok.Data;

@Data
public class Product {
    private Integer id_product;
    private String name;
    private Integer stock;
    private Float price_buy;
    private Float price_sale;
    private Supplier supplier;
    private String presentation;
    private Integer description_presentation;
    private Mark mark;
    private Integer status;
}
