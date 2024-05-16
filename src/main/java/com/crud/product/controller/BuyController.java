package com.crud.product.controller;

import com.crud.product.model.Buy;
import com.crud.product.model.Product;
import com.crud.product.model.ServiceResponse;
import com.crud.product.service.IBuyService;
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

    @GetMapping("/list/buy")
    public ResponseEntity<List<Buy>> findAll(){
        var listProducts = iBuyService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }
    @PostMapping("/save/buy")
    public ResponseEntity<ServiceResponse> save(@RequestBody Buy buy){
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
    }
    @GetMapping("/search/buy/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String id){
        ServiceResponse serviceResponse =  new ServiceResponse();
        Buy result = iBuyService.findBuyById(id);
        if(result!=null){
            serviceResponse.setMessage("Eliminado Con Exito");
            serviceResponse.setSuccess(true);
            serviceResponse.setData(result);
        }else{
            serviceResponse.setMessage("No se elimino correctamente");
            serviceResponse.setSuccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
