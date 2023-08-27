package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.form.TodoData;
import com.example.todo.form.TodoQuery;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final HttpSession session;

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
    mv.addObject("todoQuery", new TodoQuery());
    return mv;
  }

  /**
   * todoById.
   *
   * @param id todo.id
   * @param mv ModelAndView
   * @return ModelAndView
   */
  @GetMapping("/todo/{id}")
  public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
    mv.setViewName("todoForm");
    Todo todo = todoRepository.findById(id).get();
    mv.addObject("todoData", todo);
    session.setAttribute("mode", "update");
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
    session.setAttribute("mode", "create");
    return mv;
  }

  /**
   * queryTodo.
   *
   * @param todoQuery TodoQuery
   * @param result    BindingResult
   * @param mv        ModelAndView
   * @return ModelAndView
   */
  @PostMapping("/todo/query")
  public ModelAndView queryTodo(
      @ModelAttribute TodoQuery todoQuery, BindingResult result, ModelAndView mv) {
    mv.setViewName("todoList");
    List<Todo> todoList = null;
    if (todoService.isValid(todoQuery, result)) {
      // エラーがなければ検索
      todoList = todoService.doQuery(todoQuery);
    }
    mv.addObject("todoList", todoList);
    return mv;
  }

  /**
   * createTodo.<br />
   * Todo入力画面で[登録]ボタンを押下された時
   *
   * @param todoData TodoData
   * @param result   BindingResult
   * @param model    ModelAndView
   * @return ModelAndView
   */
  @PostMapping("/todo/create")
  public String createTodo(
      @ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {

    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);
    if (!result.hasErrors() && isValid) {
      // エラーなし
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return "redirect:/todo";
    }

    // エラーあり
    return "todoForm";
  }

  /**
   * updateTodo.
   *
   * @param todoData TodoData
   * @param result   BindingResult
   * @param model    Model
   * @return redirect
   */
  @PostMapping("/todo/update")
  public String updateTodo(
      @ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {

    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);
    if (!result.hasErrors() && isValid) {
      // エラー無し
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return "redirect:/todo";
    }

    // エラーあり
    return "todoForm";
  }

  /**
   * deleteTodo.
   *
   * @param todoData TodoData
   * @return redirect
   */
  @PostMapping("/todo/delete")
  public String deleteTodo(@ModelAttribute TodoData todoData) {
    todoRepository.deleteById(todoData.getId());
    return "redirect:/todo";
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
