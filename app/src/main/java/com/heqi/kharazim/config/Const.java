package com.heqi.kharazim.config;

/**
 * Created by overspark on 2016/11/19.
 */

public class Const {

  public static class TabId {
    public static final String NONE = "";

    private TabId() {}
  }

  // http
  public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";

  public static String getKharazimResource(final String resourceUrl) {
    return resourceUrl.startsWith("http://") ? resourceUrl : KHARAZIM_SERVER + resourceUrl;
  }

  private Const() {
  }
}
