package com.crud.product.model;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    Boolean success;
    String message;
    T data;
}
