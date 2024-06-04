package jp.co.nss.ojt2024.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    /**
     * /helloリクエストの処理
     * 
     * @param model Model
     * @return helloworld.htmlのパス
     */
    @GetMapping("/hello")
    public String getHello(Model model) {
        model.addAttribute("helloworld", "Hello, World");
        return "helloworld";
    }
}
