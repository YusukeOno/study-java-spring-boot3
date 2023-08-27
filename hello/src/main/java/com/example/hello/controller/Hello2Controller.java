package com.example.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello2Controller.
 */
@RestController
public class Hello2Controller {
  @GetMapping("/hello2")
  public String sayHello(@RequestParam("name") String name) {
    return "Hello, world!" + "こんにちわ" + name + "さん!";
  }
}
