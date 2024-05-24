package com.crud.product.controller;

import com.crud.product.model.*;
import com.crud.product.service.IMarkService;
import com.crud.product.service.IPresentationService;
import com.crud.product.service.IProductService;
import com.crud.product.service.IUserService;
import org.apache.catalina.User;
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

    @Autowired
    private IUserService iUserService;

    @GetMapping("/list/product")
    public ResponseEntity<List<Product>> findAll(@RequestHeader(value = "name_user", required = false) String authenticated){
        //System.out.println(authenticated.getName_user());
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                var listProducts = iProductService.findAll();
                return new ResponseEntity<>(listProducts, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @GetMapping("/low/product")
    public ResponseEntity<List<Product>> findProductLowStock(@RequestHeader(value = "name_user", required = false) String authenticated){
        //System.out.println(authenticated.getName_user());
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                var listProducts = iProductService.findProductLowStock();
                return new ResponseEntity<>(listProducts, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }


    @PostMapping("/save/product")
    public ResponseEntity<ServiceResponse> save(@RequestBody Product product,@RequestHeader(value = "name_user",required = false) String authenticated){
        //System.out.println(authenticated.getName_user());
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                ServiceResponse serviceResponse = new ServiceResponse();
                try {
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
                    if(result == 1){
                        serviceResponse.setMessage("Agregado Con Éxito");
                        serviceResponse.setSuccess(true);
                    } else {
                        serviceResponse.setMessage("No se agregó correctamente");
                        serviceResponse.setSuccess(false);
                    }
                } catch (Exception ex) {
                    // Captura de excepciones y envío de mensaje de error al frontend
                    serviceResponse.setMessage( ex.getMessage());
                    serviceResponse.setSuccess(false);
                }
                return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }


    @PostMapping("/update/product")
    public ResponseEntity<ServiceResponse> update(@RequestBody Product product,@RequestHeader(value = "name_user", required = false) String authenticated){

        //System.out.println(authenticated.getName_user());
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){

                ServiceResponse serviceResponse =  new ServiceResponse();
                int result = iProductService.update(product);
                if(result==1){
                    serviceResponse.setMessage("Modificado Con Exito");
                    serviceResponse.setSuccess(true);
                }else{
                    serviceResponse.setMessage("No se modifico correctamente");
                    serviceResponse.setSuccess(false);
                }
                return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }



}

    @GetMapping("/delete/product/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id, @RequestHeader(value = "name_user", required = false) String authenticated){
        if(authenticated==null){
            System.out.println("Error");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Retorna una respuesta de error 401
        }else {
            Users user = iUserService.findByName(authenticated);
            if(user!=null && user.getStatus().compareToIgnoreCase("ACTIVO")==0){
                ServiceResponse serviceResponse =  new ServiceResponse();
                int result = iProductService.deleteById(id);
                if(result==1){
                    serviceResponse.setMessage("Eliminado Con Exito");
                    serviceResponse.setSuccess(true);
                }else{
                    serviceResponse.setMessage("No se elimino correctamente");
                    serviceResponse.setSuccess(false);
                }
                return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }




    }
}
