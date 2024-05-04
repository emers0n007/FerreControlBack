package com.crud.product.controller;

import com.crud.product.model.Buy;
import com.crud.product.model.Sale;
import com.crud.product.model.ServiceResponse;
import com.crud.product.service.IBuyService;
import com.crud.product.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class SaleController {
    @Autowired
    private ISaleService iSaleService;

    @GetMapping("/list/sale")
    public ResponseEntity<List<Sale>> findAll(){
        var listProducts = iSaleService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }
    @PostMapping("/save/sale")
    public ResponseEntity<ServiceResponse> save(@RequestBody Sale sale){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iSaleService.save(sale);
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
            serviceResponse.setSuccess(true);
        }else{
            serviceResponse.setMessage("No se agrego correctamente");
            serviceResponse.setSuccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
    @GetMapping("/search/sale/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id){
        ServiceResponse serviceResponse =  new ServiceResponse();
        Sale result = iSaleService.findSaleById(id);
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
