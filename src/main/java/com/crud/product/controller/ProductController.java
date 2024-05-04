package com.crud.product.controller;

import com.crud.product.model.Mark;
import com.crud.product.model.Presentation;
import com.crud.product.model.Product;
import com.crud.product.model.ServiceResponse;
import com.crud.product.service.IMarkService;
import com.crud.product.service.IPresentationService;
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

    @Autowired
    private IPresentationService iPresentationService;

    @Autowired
    private IMarkService iMarkService;

    @GetMapping("/list/product")
    public ResponseEntity<List<Product>> findAll(){
        var listProducts = iProductService.findAll();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @GetMapping("/low/product")
    public ResponseEntity<List<Product>> findProductLowStock(){
        var listProducts = iProductService.findProductLowStock();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }


    @PostMapping("/save/product")
    public ResponseEntity<ServiceResponse> save(@RequestBody Product product){
        ServiceResponse serviceResponse =  new ServiceResponse();
        iPresentationService.save(product.getPresentation());
        int newIDPresentation = iPresentationService.findTop();
        Presentation newPresentation = new Presentation();
        newPresentation.setId_presentation(newIDPresentation);
        newPresentation.setName_presentation(product.getPresentation().getName_presentation());
        newPresentation.setDescription_presentation(product.getPresentation().getDescription_presentation());
        product.setPresentation(newPresentation);

        iMarkService.save(product.getMark());
        Mark newMark = new Mark();
        newMark.setName_mark(product.getMark().getName_mark());
        newMark.setId_mark(iMarkService.findTop(product.getMark()).getId_mark());
        product.setMark(newMark);

        int result = iProductService.save(product);
        //int result = 1;
        if(result==1){
            serviceResponse.setMessage("Agregado Con Exito");
            serviceResponse.setSeccess(true);
        }else{
            serviceResponse.setMessage("No se agrego correctamente");
            serviceResponse.setSeccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/update/product")
    public ResponseEntity<ServiceResponse> update(@RequestBody Product product){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iProductService.update(product);
        if(result==1){
            serviceResponse.setMessage("Modificado Con Exito");
            serviceResponse.setSeccess(true);
        }else{
            serviceResponse.setMessage("No se modifico correctamente");
            serviceResponse.setSeccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/delete/product/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id){
        ServiceResponse serviceResponse =  new ServiceResponse();
        int result = iProductService.deleteById(id);
        if(result==1){
            serviceResponse.setMessage("Eliminado Con Exito");
            serviceResponse.setSeccess(true);
        }else{
            serviceResponse.setMessage("No se elimino correctamente");
            serviceResponse.setSeccess(false);
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
