package com.tameem.SpringBoot55.service;

import com.tameem.SpringBoot55.model.Student;
import com.tameem.SpringBoot55.repository.studentrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Studentservice {

@Autowired
    private studentrepo repo;




public void  saveStudent(Student s)
{
    repo.save(s);
}


public List<Student>getAllStudent(){

    return  repo.findAll();
}


public  Student findById(int id){
    return  repo.findById(id).get();
}
}
