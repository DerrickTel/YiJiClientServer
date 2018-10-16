package com.kirkcola.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public Map hello() {
        return new HashMap<String, String>(){
            {
                put("test","hello");
            }
        };
    }
}