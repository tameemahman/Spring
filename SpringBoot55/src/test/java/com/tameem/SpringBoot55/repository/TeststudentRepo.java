package com.tameem.SpringBoot55.repository;

import com.tameem.SpringBoot55.model.Student;
import org.junit.jupiter.api.Test;

public class TeststudentRepo {


    public Student student;
    @Test

    public  void testAddStudent(){
        student=new Student();
        student.setRoll(10);
        student.setName("Tameem");
        student.setMail("tameem@gmail.com");



    }
}
