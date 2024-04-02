package com.crud.product.controller;

import com.crud.product.model.Product;
import com.crud.product.model.ServiceResponse;
import com.crud.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @GetMapping("/list/product")
    public ResponseEntity<List<Product>> findAll(){
        var listProducts = iProductService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @PostMapping("/save/product")
    public ResponseEntity<ServiceResponse> save(@RequestBody Product product){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iProductService.save(product);
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/update/product")
    public ResponseEntity<ServiceResponse> update(@RequestBody Product product){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iProductService.update(product);
        if(result==1){
            serviceResponse.setMessage("Modificado Con Exito");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/delete/product/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iProductService.deleteById(id);
        if(result==1){
            serviceResponse.setMessage("Eliminado Con Exito");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
