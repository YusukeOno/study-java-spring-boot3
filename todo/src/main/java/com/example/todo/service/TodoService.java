package com.example.todo.service;

import com.example.todo.common.Utils;
import com.example.todo.entity.Todo;
import com.example.todo.form.TodoData;
import com.example.todo.form.TodoQuery;
import com.example.todo.repository.TodoRepository;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * TodoService.
 */
@Service
@AllArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;

  /**
   * isValid.
   *
   * @param todoData todoData
   * @param result   result
   * @return true if validation successful.
   */
  public boolean isValid(TodoData todoData, BindingResult result) {
    boolean ans = true;

    // 件名が全角スペースだけで構成されていたらエラー
    String title = todoData.getTitle();
    if (title != null && !title.equals("")) {
      boolean isAllDoubleSpace = true;
      for (int i = 0; i < title.length(); i++) {
        if (title.charAt(i) != '　') {
          isAllDoubleSpace = false;
          break;
        }
      }
      if (isAllDoubleSpace) {
        FieldError fieldError = new FieldError(result.getObjectName(), "title", "件名が全角スペースです");
        result.addError(fieldError);
        ans = false;
      }
    }

    // 期限日が過去日付ならエラー
    String deadline = todoData.getDeadline();
    if (!deadline.equals("")) {
      LocalDate todyDate = LocalDate.now();
      LocalDate deadlineDate = null;
      try {
        deadlineDate = LocalDate.parse(deadline);
        if (deadlineDate.isBefore(todyDate)) {
          FieldError fieldError = new FieldError(
              result.getObjectName(), "deadline", "期限を設定する時は今日以降にしてください");
          result.addError(fieldError);
          ans = false;
        }
      } catch (DateTimeException e) {
        FieldError fieldError = new FieldError(
            result.getObjectName(), "deadline", "期限を設定する時はyyyy-mm-dd形式で入力してください");
        result.addError(fieldError);
        ans = false;
      }
    }

    return ans;
  }

  /**
   * isValid.
   *
   * @param todoQuery TodoQuery
   * @param result    BindingResult
   * @return true if validation successful.
   */
  public boolean isValid(TodoQuery todoQuery, BindingResult result) {
    boolean ans = true;

    // 期限：開始の形式をチェック
    String date = todoQuery.getDeadlineFrom();
    if (!date.equals("")) {
      try {
        LocalDate.parse(date);
      } catch (DateTimeException e) {
        // parseできない場合
        FieldError fieldError = new FieldError(result.getObjectName(), "deadlineFrom",
            "期限：開始を入力する時はyyyy-mm-dd形式で入力してください");
        result.addError(fieldError);
        ans = false;
      }
    }

    // 期限：終了の形式をチェック
    date = todoQuery.getDeadlineTo();
    if (!date.equals("")) {
      try {
        LocalDate.parse(date);
      } catch (DateTimeException e) {
        // parseできない場合
        FieldError fieldError = new FieldError(result.getObjectName(), "deadlineTo",
            "期限：終了を入力する時はyyyy-mm-dd形式で入力してください");
        result.addError(fieldError);
        ans = false;
      }
    }

    return ans;
  }

  /**
   * doQuery.
   *
   * @param todoQuery TodoQuery
   * @return Todo
   */
  public List<Todo> doQuery(TodoQuery todoQuery) {
    List<Todo> todoList = null;
    if (todoQuery.getTitle().length() > 0) {
      // タイトルで検索
      todoList = todoRepository.findByTitleLike("%" + todoQuery.getTitle() + "%");
    } else if (todoQuery.getImportance() != null && todoQuery.getImportance() != -1) {
      // 重要度で検索
      todoList = todoRepository.findByImportance(todoQuery.getImportance());
    } else if (todoQuery.getUrgency() != null && todoQuery.getUrgency() != -1) {
      // 緊急度で検索
      todoList = todoRepository.findByUrgency(todoQuery.getUrgency());
    } else if (!todoQuery.getDeadlineFrom().equals("") && todoQuery.getDeadlineTo().equals("")) {
      // 期限 開始〜
      todoList = todoRepository.findByDeadlineGreaterThanEqualOrderByDeadlineAsc(
          Utils.str2date(todoQuery.getDeadlineFrom()));

    }
    return null;
  }
}
