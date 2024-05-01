package com.crud.product.repository;


import com.crud.product.model.Presentation;
import org.springframework.beans.factory.annotation.Autowired;
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
}
