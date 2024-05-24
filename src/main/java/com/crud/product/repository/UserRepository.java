package com.crud.product.repository;

import com.crud.product.model.Supplier;
import com.crud.product.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Date;
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
        String SQL = "SELECT * FROM users WHERE status!='ELIMINADO'";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Users.class));
    }

    @Override
    public int save(Users user) {
        passwordEncoder = new BCryptPasswordEncoder();
        String SQL = "INSERT INTO Users (name_user, password, name, role, status) VALUES (?, ?, ?, ?, ?)";
        String status = "INACTIVO";
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        return jdbcTemplate.update(SQL, user.getName_user(), hashedPassword, user.getName(), user.getRole(), status);
    }

    @Override
    public int update(Users users) {
        return 0;
    }

    @Override
    public int deleteByName(String name_user) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String newName = name_user + currentDateTime;
        String delete = "ELIMINADO";
        String SQL ="UPDATE Users SET status=? WHERE name_user =?";
        return jdbcTemplate.update(SQL,new Object[]{newName,delete,name_user});
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

    @Override
    public int activateUser(String name_user) {
        String acitvated = "ACTIVO";
        String SQL ="UPDATE Users SET status=? WHERE name_user =?";
        return jdbcTemplate.update(SQL,new Object[]{acitvated,name_user});
    }

    @Override
    public int inactivateUser(String name_user) {
        String acitvated = "INACTIVO";
        String SQL ="UPDATE Users SET status=? WHERE name_user =?";
        return jdbcTemplate.update(SQL,new Object[]{acitvated,name_user});
    }
}
