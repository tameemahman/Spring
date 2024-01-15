package com.tameem.SpringBoot55.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller


public class HomeController {

    @GetMapping( "/")

     public String home(){

        return "home";
    }
//    @GetMapping( "/page")
//
//     public String shopPage(){
//
//        return "shopPage";
//    }

    @GetMapping( "/shop")

    public String shop(){

        return "shop";
    }

    @GetMapping( "/feature")

    public String feature(){

        return "featuring";
    }
    @GetMapping(value = {"/mypage", "/abc"})
    public  String mypage(){

        return "mypage";
    }

}
