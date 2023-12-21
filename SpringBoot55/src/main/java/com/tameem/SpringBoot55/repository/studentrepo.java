package com.tameem.SpringBoot55.repository;

import com.tameem.SpringBoot55.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface studentrepo extends JpaRepository<Student , Integer>{


}
