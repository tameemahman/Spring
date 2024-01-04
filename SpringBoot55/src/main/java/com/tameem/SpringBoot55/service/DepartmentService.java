package com.tameem.SpringBoot55.service;

import com.tameem.SpringBoot55.model.Department;
import com.tameem.SpringBoot55.repository.IDepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentRepo repo;

    public void  save (Department d){

        repo.save(d);
    }
public List<Department> getAllDepartment(){

        return repo.findAll();
}

public  void  deleteDepartment(int id){repo.deleteById(id);}

    public  Department editDepartment(int id){return repo.findById(id).get();}
}
