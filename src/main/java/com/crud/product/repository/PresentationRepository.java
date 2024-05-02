package com.crud.product.repository;


import com.crud.product.model.Mark;
import com.crud.product.model.Presentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PresentationRepository implements IPresentationRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int save(Presentation presentation) {
        String SQL = "INSERT INTO Presentation VALUES(?,?)";
        return jdbcTemplate.update(SQL, new Object[]{presentation.getName_presentation(),presentation.getDescription_presentation()});
    }

    @Override
    public int findTop(){
        String SQL = "SELECT IDENT_CURRENT('Presentation')";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }
}
