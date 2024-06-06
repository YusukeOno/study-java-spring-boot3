package com.example.todo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * UtilsTest.
 */
public class UtilsTest {

  @Test
  @DisplayName("StringからDateに変換するテスト")
  void testStr2date() {
    Date expected = Date.from(
        LocalDate.parse(
            "2023-11-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(
                ZoneId.systemDefault())
            .toInstant());

    Date actual = Utils.str2date("2023-11-22");
    assertEquals(expected, actual, "ok");
  }
}
