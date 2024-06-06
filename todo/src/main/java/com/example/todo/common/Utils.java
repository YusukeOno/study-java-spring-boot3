package com.example.todo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils.
 */
public class Utils {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * str2date.
   *
   * @param s DateString
   * @return Date
   */
  public static Date str2date(final String s) {
    long ms = 0;
    try {
      ms = sdf.parse(s).getTime();
    } catch (final ParseException e) {
      e.printStackTrace();
    }
    return new Date(ms);
  }

}
