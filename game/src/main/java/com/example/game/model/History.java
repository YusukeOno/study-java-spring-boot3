package com.example.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * History.
 */
@AllArgsConstructor
@Getter
public class History {
  private int seq;
  private int yourAnswer;
  private String result;
}
