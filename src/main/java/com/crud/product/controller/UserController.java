package com.crud.product.controller;

import com.crud.product.model.LoginRequest;
import com.crud.product.model.Users;
import com.crud.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @PostMapping("/login")
    public ResponseEntity<Users> findAll(@RequestBody LoginRequest loginRequest){
        var listUsers = iUserService.findAll();
        Users user = new Users();
        for (Users users:listUsers) {
            if(users.getName_user().compareToIgnoreCase(loginRequest.getName_user())==0){
                if(users.getPassword().compareToIgnoreCase(loginRequest.getPassword())==0){
                    user = users;
                }
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
