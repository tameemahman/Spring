package com.tameem.SpringBoot55.repository;

import com.tameem.SpringBoot55.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Categoryrepo extends JpaRepository<Category,Integer> {
}
