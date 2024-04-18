package com.crud.product.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String name_user;
    private String password;
}
