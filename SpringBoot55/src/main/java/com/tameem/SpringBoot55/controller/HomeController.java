package com.tameem.SpringBoot55.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller


public class HomeController {

    @GetMapping( "/")

     public String home(){

        return "home";
    }
    @GetMapping(value = {"/mypage", "/abc"})
    public  String mypage(){

        return "mypage";
    }

}
