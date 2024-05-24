package com.crud.product.service;

import com.crud.product.model.Users;
import com.crud.product.repository.ISupplierRepository;
import com.crud.product.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository iUserRepository;
    @Override
    public List<Users> findAll() {
        List<Users> list;
        try {
            list = iUserRepository.findAll();
        }catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Users user) {
        int row;
        try {
            row = iUserRepository.save(user);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int update(Users user) {
        int row;
        try {
            row = iUserRepository.update(user);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int deleteByName(String name) {
        int row;
        try {
            row = iUserRepository.deleteByName(name);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public Users findByName(String name_user) {
        Users row;
        try {
            row = iUserRepository.findByName(name_user);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }


    @Override
    public int activateUser(String name) {
        int row;
        try {
            row = iUserRepository.activateUser(name);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }
    @Override
    public int inactivateUser(String name) {
        int row;
        try {
            row = iUserRepository.inactivateUser(name);
        }catch (Exception ex){
            throw ex;
        }
        return row;
    }
}
