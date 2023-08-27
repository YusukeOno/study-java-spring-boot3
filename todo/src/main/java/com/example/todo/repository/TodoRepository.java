package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TodoRepository.
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
