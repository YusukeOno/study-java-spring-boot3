package com.example.todo.dao;

import com.example.todo.common.Utils;
import com.example.todo.entity.Todo;
import com.example.todo.entity.Todo_;
import com.example.todo.form.TodoQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * TodoDaoImpl.
 */
@AllArgsConstructor
public class TodoDaoImpl implements TodoDao {
  private final EntityManager entityManager;

  /**
   * JPQLによる検索.
   */
  @Override
  public List<Todo> findByJpql(TodoQuery todoQuery) {
    StringBuilder sb = new StringBuilder();
    sb.append("select t from Todo t where 1 = 1");

    List<Object> params = new ArrayList<>();
    int pos = 0;

    // 件名
    if (todoQuery.getTitle().length() > 0) {
      sb.append(" and t.title like ?" + (++pos));
      params.add("%" + todoQuery.getTitle() + "%");
    }

    // 重要度
    if (todoQuery.getImportance() != -1) {
      sb.append(" and t.importance = ?" + (++pos));
      params.add(todoQuery.getImportance());
    }

    // 緊急度
    if (todoQuery.getUrgency() != -1) {
      sb.append(" and t.urgency = ?" + (++pos));
      params.add(todoQuery.getUrgency());
    }

    // 期限：開始〜
    if (!todoQuery.getDeadlineFrom().equals("")) {
      sb.append(" and t.deadline >= ?" + (++pos));
      params.add(Utils.str2date(todoQuery.getDeadlineFrom()));
    }

    // 〜権限：終了で検索
    if (!todoQuery.getDeadlineTo().equals("")) {
      sb.append(" and t.deadline <= ?" + (++pos));
      params.add(Utils.str2date(todoQuery.getDeadlineTo()));
    }

    // 完了
    if (todoQuery.getDone() != null && todoQuery.getDone().equals("Y")) {
      sb.append(" and t.done = ?" + (++pos));
      params.add(todoQuery.getDone());
    }

    // order by
    sb.append(" order by id");

    Query query = entityManager.createQuery(sb.toString());
    for (int i = 0; i < params.size(); i++) {
      query = query.setParameter(i + 1, params.get(i));
    }

    @SuppressWarnings("unchecked")
    List<Todo> list = query.getResultList();

    return list;
  }

  /**
   * Criteria APIによる検索.
   */
  @Override
  public List<Todo> findByCriteria(TodoQuery todoQuery) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Todo> query = builder.createQuery(Todo.class);
    Root<Todo> root = query.from(Todo.class);
    List<Predicate> predicates = new ArrayList<>();

    // 件名
    String title = "";
    if (todoQuery.getTitle().length() > 0) {
      title = "%" + todoQuery.getTitle() + "%";
    } else {
      title = "%";
    }
    predicates.add(builder.like(root.get(Todo_.TITLE), title));

    // 重要度
    if (todoQuery.getImportance() != -1) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.IMPORTANCE), todoQuery.getImportance())));
    }

    // 緊急度
    if (todoQuery.getUrgency() != -1) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.URGENCY), todoQuery.getUrgency())));
    }

    // 期限：開始〜
    if (!todoQuery.getDeadlineFrom().equals("")) {
      predicates.add(
          builder.and(
              builder.greaterThanOrEqualTo(
                  root.get(Todo_.DEADLINE), Utils.str2date(todoQuery.getDeadlineFrom()))));
    }

    // 〜期限：終了で検索
    if (!todoQuery.getDeadlineTo().equals("")) {
      predicates.add(
          builder.and(
              builder.lessThanOrEqualTo(
                  root.get(Todo_.DEADLINE), Utils.str2date(todoQuery.getDeadlineTo()))));
    }

    // 完了
    if (todoQuery.getDone() != null && todoQuery.getDone().equals("Y")) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.DONE), todoQuery.getDone())));
    }

    // SELECT作成
    Predicate[] predArray = new Predicate[predicates.size()];
    predicates.toArray(predArray);
    query = query.select(root).where(predArray).orderBy(builder.asc(root.get(Todo_.id)));

    // 検索
    List<Todo> list = entityManager.createQuery(query).getResultList();
    return list;
  }

  /**
   * findByCriteria.
   */
  @Override
  public Page<Todo> findByCriteria(TodoQuery todoQuery, Pageable pageable) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Todo> query = builder.createQuery(Todo.class);
    Root<Todo> root = query.from(Todo.class);
    List<Predicate> predicates = new ArrayList<>();

    // 件名
    String title = "";
    if (todoQuery.getTitle().length() > 0) {
      title = "%" + todoQuery.getTitle() + "%";
    } else {
      title = "%";
    }
    predicates.add(builder.like(root.get(Todo_.TITLE), title));

    // 重要度
    if (todoQuery.getImportance() != -1) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.IMPORTANCE), todoQuery.getImportance())));
    }

    // 緊急度
    if (todoQuery.getUrgency() != -1) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.URGENCY), todoQuery.getUrgency())));
    }

    // 期限：開始〜
    if (!todoQuery.getDeadlineFrom().equals("")) {
      predicates.add(
          builder.and(
              builder.greaterThanOrEqualTo(
                  root.get(Todo_.DEADLINE), Utils.str2date(todoQuery.getDeadlineFrom()))));
    }

    // 〜期限：終了で検索
    if (!todoQuery.getDeadlineTo().equals("")) {
      predicates.add(
          builder.and(
              builder.lessThanOrEqualTo(
                  root.get(Todo_.DEADLINE), Utils.str2date(todoQuery.getDeadlineTo()))));
    }

    // 完了
    if (todoQuery.getDone() != null && todoQuery.getDone().equals("Y")) {
      predicates.add(
          builder.and(
              builder.equal(
                  root.get(Todo_.DONE), todoQuery.getDone())));
    }

    // SELECT作成
    Predicate[] predArray = new Predicate[predicates.size()];
    predicates.toArray(predArray);
    query = query.select(root).where(predArray).orderBy(builder.asc(root.get(Todo_.id)));

    // クエリ作成
    TypedQuery<Todo> typedQuery = entityManager.createQuery(query);
    // 該当レコード数取得
    int totalRows = typedQuery.getResultList().size();
    // 先頭レコードの位置設定
    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    // １ページ当たりの件数
    typedQuery.setMaxResults(pageable.getPageSize());
    Page<Todo> page = new PageImpl<>(typedQuery.getResultList(), pageable, totalRows);

    return page;
  }
}
