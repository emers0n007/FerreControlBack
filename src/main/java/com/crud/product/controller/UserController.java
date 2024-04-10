package com.crud.product.controller;

import com.crud.product.model.Users;
import com.crud.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/login")
    public ResponseEntity<List<Users>> findAll(){
        var listUsers = iUserService.findAll();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }
}
