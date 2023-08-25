package com.example.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Hello5Controller.
 */
@Controller
public class Hello5Controller {
  /**
   * sayHello.
   *
   * @param name Name
   * @param mv   ModelAndView
   * @return ModelAndView
   */
  @GetMapping("/hello5")
  public ModelAndView sayHello(@RequestParam("name") String name, ModelAndView mv) {
    mv.setViewName("Hello");
    mv.addObject("name", name);
    return mv;
  }
}
