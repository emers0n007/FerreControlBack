package com.crud.product.repository;

import com.crud.product.model.Users;

import java.util.List;

public interface IUserRepository {
    public List<Users> findAll();
    public int save(Users users);
    public  int update(Users users);
    public int deleteByName(String name_user);
}
