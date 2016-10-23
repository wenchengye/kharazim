package com.heqi.rpc;

import com.wandoujia.gson.Gson;

/**
 * Created by wenchengye on 16/8/28.
 */
public class GsonFactory {

  private static Gson gson;

  static {
    gson = new Gson();
  }

  private GsonFactory() {}

  /**
   * Gets gson object.
   *
   * @return {@link Gson} object
   */
  public static Gson getGson() {
    return gson;
  }
}
