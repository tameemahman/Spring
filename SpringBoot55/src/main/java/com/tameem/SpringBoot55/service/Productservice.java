package com.tameem.SpringBoot55.service;

import com.tameem.SpringBoot55.model.Product;
import com.tameem.SpringBoot55.repository.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Productservice {

@Autowired
    private Productrepo repo;




public void  saveProduct(Product s)
{
    repo.save(s);
}


public List<Product>getAllProduct(){

    return  repo.findAll();
}


public Product findById(int id){
    return  repo.findById(id).get();
}
}
