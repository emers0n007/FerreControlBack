package com.crud.product.controller;

import com.crud.product.model.Mark;
import com.crud.product.model.ServiceResponse;
import com.crud.product.model.Supplier;
import com.crud.product.service.IBuyService;
import com.crud.product.service.IMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("FerreControl")
@CrossOrigin("*")
public class MarkController {

    @Autowired
    private IMarkService iMarkService;

    @GetMapping("/list/mark")
    public ResponseEntity<List<Mark>> findAllMark(){
        var listProducts = iMarkService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @PostMapping("/save/mark")
    public ResponseEntity<ServiceResponse> save(@RequestBody Mark mark){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iMarkService.save(mark);
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
            serviceResponse.setSeccess(true);
        }else{
            serviceResponse.setMessage("No se agrego Correctamente");
            serviceResponse.setSeccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
