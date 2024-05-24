package com.crud.product.controller;

import com.crud.product.model.Product;
import com.crud.product.model.ServiceResponse;
import com.crud.product.model.Supplier;
import com.crud.product.model.Users;
import com.crud.product.service.IProductService;
import com.crud.product.service.ISupplierService;
import com.crud.product.service.IUserService;
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

    @Autowired
    private IUserService iUserService;

    @GetMapping("/list/supplier")
    public ResponseEntity<List<Supplier>> findAll(@RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                var listProducts = iSupplierService.findAll();
                return new ResponseEntity<>(listProducts, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }



    }

    @PostMapping("/save/supplier")
    public ResponseEntity<ServiceResponse> save(@RequestBody Supplier supplier,@RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
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
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }




    }

    @PostMapping("/update/supplier")
    public ResponseEntity<ServiceResponse> update(@RequestBody Supplier supplier,@RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
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
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @GetMapping("/delete/supplier/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id,@RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
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
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }




    }
}
