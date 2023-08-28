package com.example.todo.dao;

import com.example.todo.entity.Todo;
import com.example.todo.form.TodoQuery;
import java.util.List;

/**
 * TodoDao.
 */
public interface TodoDao {
  // JPQLによる検索
  List<Todo> findByJpql(TodoQuery todoQuery);

  // Criteria APIによる検索
  List<Todo> findByCriteria(TodoQuery todoQuery);
}
