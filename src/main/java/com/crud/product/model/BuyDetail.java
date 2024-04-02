package com.crud.product.model;

import lombok.Data;

@Data
public class BuyDetail {
    private Integer id_buy_detail;
    private Integer id_buy;
    private Integer id_product;
    private Integer quantity;
    private Float price;
}
