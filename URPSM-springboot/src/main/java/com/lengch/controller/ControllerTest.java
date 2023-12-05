package com.lengch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Lengchenghao
 * @create : 2023-11-03-10:22
 */

@RestController
public class ControllerTest {


    @GetMapping("/hello")
    public String TestHello(){
        return "Hello URPSM!";
    }




}
