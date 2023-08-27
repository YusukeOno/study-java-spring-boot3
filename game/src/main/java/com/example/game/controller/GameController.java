package com.example.game.controller;

import com.example.game.model.History;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * GameController.
 */
@Controller
public class GameController {

  @Autowired
  HttpSession session;

  /**
   * index.
   *
   * @return view
   */
  @GetMapping("/")
  public String index() {
    session.invalidate();

    // Create an answer and store it in the session
    Random rdn = new Random();
    int answer = rdn.nextInt(100) + 1;
    session.setAttribute("answer", answer);
    System.out.println("answer=" + answer);
    return "game";
  }

  /**
   * challenge.
   *
   * @param number Number
   * @param mv     ModelAndView
   * @return ModelAndView
   */
  @PostMapping("/challenge")
  public ModelAndView challenge(@RequestParam("number") int number, ModelAndView mv) {
    // Get answers from session
    int answer = (Integer) session.getAttribute("answer");

    // Get user's answer history
    @SuppressWarnings("unchecked")
    List<History> histories = (List<History>) session.getAttribute("histories");
    if (histories == null) {
      histories = new ArrayList<>();
      session.setAttribute("histories", histories);
    }
    // Judgment, additional answer history
    if (answer < number) {
      histories.add(new History(histories.size() + 1, number, "Much smaller."));
    } else if (answer == number) {
      histories.add(new History(histories.size() + 1, number, "Correct answer."));
    } else {
      histories.add(new History(histories.size() + 1, number, "Much larger."));
    }

    mv.setViewName("game");
    mv.addObject("histories", histories);

    return mv;
  }
}
