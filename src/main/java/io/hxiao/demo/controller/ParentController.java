package io.hxiao.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parent")
public class ParentController {

    @GetMapping
    public String hello() {
        return "hello";
    }

}
