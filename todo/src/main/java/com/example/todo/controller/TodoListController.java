package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.form.TodoData;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * TodoListController.
 */
@Controller
@AllArgsConstructor
public class TodoListController {
  private final TodoRepository todoRepository;
  private final TodoService todoService;

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

  /**
   * createTodo.
   *
   * @param mv ModelAndView
   * @return ModelAndView
   */
  @GetMapping("/todo/create")
  public ModelAndView createTodo(ModelAndView mv) {
    mv.setViewName("todoForm");
    mv.addObject("todoData", new TodoData());
    return mv;
  }

  /**
   * createTodo.<br />
   * Todo入力画面で[登録]ボタンを押下された時
   *
   * @param todoData TodoData
   * @param result   BindingResult
   * @param mv       ModelAndView
   * @return ModelAndView
   */
  @PostMapping("/todo/create")
  public ModelAndView createTodo(
      @ModelAttribute @Validated TodoData todoData, BindingResult result, ModelAndView mv) {

    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);

    if (!result.hasErrors() && isValid) {
      // エラーなし
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return showTodoList(mv);
    }

    mv.setViewName("todoForm");
    return mv;
  }

  /**
   * cancel.
   *
   * @return redirect
   */
  @PostMapping("/todo/cancel")
  public String cancel() {
    return "redirect:/todo";
  }
}
