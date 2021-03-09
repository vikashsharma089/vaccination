package com.covid.vaccine.covidvac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String getWelcomePage(Model model){

         System.out.println("Welcome to java ");
        return "welcome";
    }
}
