package com.heqi.kharazim.config;

/**
 * Created by overspark on 2016/11/19.
 */

public class Const {

  // common
  public static final long SECOND = 1000L;
  public static final long MINTUE = SECOND * 60;
  public static final long HOUR = MINTUE * 60;
  public static final long DAY = HOUR * 24;

  // http
  //public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  public static final String KHARAZIM_SERVER = "https://manager.heal361.com/";
  public static final String EMAIL_KEY_WORD = "@";
  public static final int KHARAZIM_FETCHER_SECOND_PAGE_NUMBER = 2;

  private Const() {
  }

  public static class TabId {
    public static final String NONE = "";

    private TabId() {
    }
  }
}
