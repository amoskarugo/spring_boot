package com.example.app.app2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {


    @GetMapping("/app")
    public String hello(){
        return "Hello amos!";
    }
}
