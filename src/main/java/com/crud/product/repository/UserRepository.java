package com.crud.product.repository;

import com.crud.product.model.Supplier;
import com.crud.product.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Users> findAll() {
        String SQL = "SELECT * FROM users";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Users.class));
    }

    @Override
    public int save(Users user) {
        passwordEncoder = new BCryptPasswordEncoder();
        String SQL = "INSERT INTO Users (name_user, password, name, role) VALUES (?, ?, ?, ?)";
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        return jdbcTemplate.update(SQL, user.getName_user(), hashedPassword, user.getName(), user.getRole());
    }

    @Override
    public int update(Users users) {
        return 0;
    }

    @Override
    public int deleteByName(String name_user) {
        String SQL ="DELETE FROM Users WHERE name_user =?";
        return jdbcTemplate.update(SQL,new Object[]{name_user});
    }

    @Override
    public Users findByName(String name_user) {
        String SQL = "SELECT * FROM users WHERE name_user = ?";
        try {

            return jdbcTemplate.queryForObject(SQL, new Object[]{name_user}, new BeanPropertyRowMapper<>(Users.class));
        } catch (EmptyResultDataAccessException e) {
            // Maneja el caso cuando no se encuentra ningún resultado
            return null;
        }
    }
}
