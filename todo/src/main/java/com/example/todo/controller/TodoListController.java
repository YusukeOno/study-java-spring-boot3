package com.example.todo.controller;

import com.example.todo.dao.TodoDaoImpl;
import com.example.todo.entity.Todo;
import com.example.todo.form.TodoData;
import com.example.todo.form.TodoQuery;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequiredArgsConstructor
public class TodoListController {
  private final TodoRepository todoRepository;
  private final TodoService todoService;
  private final HttpSession session;

  @PersistenceContext
  private EntityManager entityManager;
  TodoDaoImpl todoDaoImpl;

  /**
   * init.
   */
  @PostConstruct
  public void init() {
    todoDaoImpl = new TodoDaoImpl(entityManager);
  }

  /**
   * showTodoList.
   *
   * @param mv       ModelAndView
   * @param pageable Pageable
   * @return ModelAndView
   */
  @GetMapping("/todo")
  public ModelAndView showTodoList(ModelAndView mv,
      @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable) {
    // 一覧を検索して表示する
    mv.setViewName("todoList");
    Page<Todo> todoPage = todoRepository.findAll(pageable);
    mv.addObject("todoQuery", new TodoQuery());
    mv.addObject("todoPage", todoPage);
    mv.addObject("todoList", todoPage.getContent());
    session.setAttribute("todoQuery", new TodoQuery());
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
   * queryTodo.
   *
   * @param todoQuery TodoQuery
   * @param result    BindingResult
   * @param pageable  Pageable
   * @param mv        ModelAndView
   * @return ModelAndView
   */
  @PostMapping("/todo/query")
  public ModelAndView queryTodo(
      @ModelAttribute TodoQuery todoQuery,
      BindingResult result,
      @PageableDefault(page = 0, size = 5) Pageable pageable,
      ModelAndView mv) {
    mv.setViewName("todoList");
    // List<Todo> todoList = null;
    Page<Todo> todoPage = null;
    if (todoService.isValid(todoQuery, result)) {
      // エラーがなければ検索
      // todoList = todoService.doQuery(todoQuery); //
      // todoList = todoDaoImpl.findByJpql(todoQuery); // JPQL
      // todoList = todoDaoImpl.findByCriteria(todoQuery); // Criteria
      todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable);

      // 入力された検索条件をsessionに保存
      session.setAttribute("todoQuery", todoQuery);
      mv.addObject("todoPage", todoPage);
      mv.addObject("todoList", todoPage.getContent());
    } else {
      // エラーがあった場合検索
      mv.addObject("todoPage", null);
      mv.addObject("todoList", null);
    }

    return mv;
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
