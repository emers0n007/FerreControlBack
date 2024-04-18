package com.crud.product.model;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    Boolean seccess;
    String message;
    T data;
}
