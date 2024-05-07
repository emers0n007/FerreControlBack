package com.crud.product.controller;

import com.crud.product.model.LoginRequest;
import com.crud.product.model.ServiceResponse;
import com.crud.product.model.Users;
import com.crud.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserService iUserService;

    private PasswordEncoder passwordEncoder ;

    @PostMapping("/login")
    public ResponseEntity<Users> findAll(@RequestBody LoginRequest loginRequest){
        passwordEncoder =  new BCryptPasswordEncoder();
        var listUsers = iUserService.findAll();
        Users user = new Users();
        for (Users users:listUsers) {
            if(users.getName_user().compareToIgnoreCase(loginRequest.getName_user())==0){
                if (passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
                    user = users;
                    break;
                }
            }
        }
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Contraseña incorrecta
        }
    }

    @PostMapping("/save/user")
    public ResponseEntity<ServiceResponse> saveUser(@RequestBody Users users){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iUserService.save(users);
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
            serviceResponse.setSuccess(true);
        }else{
            serviceResponse.setMessage("No se agrego Correctamente");
            serviceResponse.setSuccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
