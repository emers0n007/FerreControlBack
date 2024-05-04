package com.crud.product.controller;

import com.crud.product.model.Product;
import com.crud.product.model.ServiceResponse;
import com.crud.product.model.Supplier;
import com.crud.product.service.IProductService;
import com.crud.product.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class SupplierController {
    @Autowired
    private ISupplierService iSupplierService;

    @GetMapping("/list/supplier")
    public ResponseEntity<List<Supplier>> findAll(){
        var listProducts = iSupplierService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @PostMapping("/save/supplier")
    public ResponseEntity<ServiceResponse> save(@RequestBody Supplier supplier){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iSupplierService.save(supplier);
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
            serviceResponse.setSuccess(true);
        }else{
            serviceResponse.setMessage("No se agrego Correctamente");
            serviceResponse.setSuccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/update/supplier")
    public ResponseEntity<ServiceResponse> update(@RequestBody Supplier supplier){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iSupplierService.update(supplier);
        if(result==1){
            serviceResponse.setMessage("Modificado Con Exito");
            serviceResponse.setSuccess(true);
        }else{
            serviceResponse.setMessage("No se modifico Correctamente");
            serviceResponse.setSuccess(false);
        }

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/delete/supplier/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iSupplierService.deleteById(id);
        if(result==1){
            serviceResponse.setMessage("Eliminado Con Exito");
            serviceResponse.setSuccess(true);
        }else{
            serviceResponse.setMessage("No se elimino Correctamente");
            serviceResponse.setSuccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
