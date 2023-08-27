package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

/**
 * Todo.
 */
@Entity
@Table(name = "todo")
@Data
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "importance")
  private Integer importance;

  @Column(name = "urgency")
  private Integer urgency;

  @Column(name = "deadline")
  private Date deadline;

  @Column(name = "done")
  private String done;
}
