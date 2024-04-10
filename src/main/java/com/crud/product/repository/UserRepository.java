package com.crud.product.repository;

import com.crud.product.model.Supplier;
import com.crud.product.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Users> findAll() {
        String SQL = "SELECT * FROM users";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Users.class));
    }

    @Override
    public int save(Users users) {
        return 0;
    }

    @Override
    public int update(Users users) {
        return 0;
    }

    @Override
    public int deleteByName(String name_user) {
        return 0;
    }
}
