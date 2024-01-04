package com.tameem.SpringBoot55.controller;

import com.tameem.SpringBoot55.model.Student;
import com.tameem.SpringBoot55.service.DepartmentService;
import com.tameem.SpringBoot55.service.Studentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

 @Autowired
    private Studentservice studentSevice;
 @Autowired
    private DepartmentService dService;


 @GetMapping

    public String allStudent(Model m){



     List<Student>studentList=studentSevice.getAllStudent();
     m.addAttribute("studentList", studentList);
     m.addAttribute("title","All Student");
     return "studenthome";

 }

}
