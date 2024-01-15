package com.tameem.SpringBoot55.repository;

import com.tameem.SpringBoot55.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Productrepo extends JpaRepository<Product,Integer>{


}
