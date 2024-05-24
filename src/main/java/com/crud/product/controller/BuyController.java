package com.crud.product.controller;

import com.crud.product.model.*;
import com.crud.product.service.IBuyService;
import com.crud.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class BuyController {
    @Autowired
    private IBuyService iBuyService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/list/buy")
    public ResponseEntity<List<Buy>> findAll(@RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                var listProducts = iBuyService.findAll();
                return new ResponseEntity<>(listProducts, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

    }
    @PostMapping("/save/buy")
    public ResponseEntity<ServiceResponse> save(@RequestBody Buy buy,@RequestHeader(value = "name_user", required = false) String authenticated){
        //System.out.println(authenticated.toString());
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                ServiceResponse serviceResponse =  new ServiceResponse();
                int result = iBuyService.save(buy);
                if(result==1){
                    serviceResponse.setMessage("Agregado Con Exito");
                    serviceResponse.setSuccess(true);
                }else{
                    serviceResponse.setMessage("No se agrego correctamente");
                    serviceResponse.setSuccess(false);
                }
                return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }


        }
    @PostMapping("/search/buy/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String id,@RequestHeader(value = "name_user", required = false) String authenticated) {
        if (authenticated == null) {
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        } else {
            Users user = iUserService.findByName(authenticated);
            if (user != null && user.getStatus().compareToIgnoreCase("ACTIVO") == 0) {
                ServiceResponse serviceResponse = new ServiceResponse();
                Buy result = iBuyService.findBuyById(id);
                if (result != null) {
                    serviceResponse.setMessage("Eliminado Con Exito");
                    serviceResponse.setSuccess(true);
                    serviceResponse.setData(result);
                } else {
                    serviceResponse.setMessage("No se elimino correctamente");
                    serviceResponse.setSuccess(false);
                }
                return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }


    }


}
