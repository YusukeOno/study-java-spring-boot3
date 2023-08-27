package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * TodoListController.
 */
@Controller
@AllArgsConstructor
public class TodoListController {
  private final TodoRepository todoRepository;

  /**
   * showTodoList.
   *
   * @param mv ModelAndView
   * @return ModelAndView
   */
  @GetMapping("/todo")
  public ModelAndView showTodoList(ModelAndView mv) {
    // 一覧を検索して表示する
    mv.setViewName("todoList");
    List<Todo> todoList = todoRepository.findAll();
    mv.addObject("todoList", todoList);

    return mv;

  }
}
