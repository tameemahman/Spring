package com.tameem.SpringBoot55.service;

import com.tameem.SpringBoot55.model.Category;
import com.tameem.SpringBoot55.repository.Categoryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private Categoryrepo repo;

    public void  save (Category d){

        repo.save(d);
    }
public List<Category> getAllDepartment(){

        return repo.findAll();
}

public  void  deleteDepartment(int id){repo.deleteById(id);}

    public Category editDepartment(int id){return repo.findById(id).get();}
}
