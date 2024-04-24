package com.crud.product.controller;

import com.crud.product.model.Mark;
import com.crud.product.service.IBuyService;
import com.crud.product.service.IMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
