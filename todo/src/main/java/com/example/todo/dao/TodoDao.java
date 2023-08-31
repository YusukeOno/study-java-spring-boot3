package com.example.todo.dao;

import com.example.todo.entity.Todo;
import com.example.todo.form.TodoQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TodoDao.
 */
public interface TodoDao {
  // JPQLによる検索
  List<Todo> findByJpql(TodoQuery todoQuery);

  // Criteria APIによる検索
  List<Todo> findByCriteria(TodoQuery todoQuery);

  // Criteria APIによる検索
  Page<Todo> findByCriteria(TodoQuery todoQuery, Pageable pageable);
}
