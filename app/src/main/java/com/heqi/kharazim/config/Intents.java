package com.heqi.kharazim.config;

/**
 * Created by overspark on 2016/11/19.
 */

public class Intents {

  public static final String EXTRA_LIST_STATE = "kharazim.intent.extra.LIST_STATE";

  /**
   * explore
   */
  public static final String EXTRA_PLAN_LITE_INFO = "kharazim.intent.extra.PLAN_LITE_INFO";

  public static final String EXTRA_COURSE_DETAIL_TYPE = "kharazim.intent.extra.COURSE_DETAIL_TYPE";

  public static final int EXTRA_VALUE_COURSE_DETAIL_TYPE_DAILY_ID = 0;
  public static final int EXTRA_VALUE_COURSE_DETAIL_TYPE_INFO = 1;

  public static final String EXTRA_COURSE_DETAIL_DAILY_ID =
      "kharazim.intent.extra.COURSE_DETAIL_DAILY_ID";

  public static final String EXTRA_COURSE_DETAIL_INFO = "kharazim.intent.extra.COURSE_DETAIL_INFO";

  public static final String ACTION_CONSUMER_PLAY = "kharazim.intent.action.CONSUMER_PLAY";

  private Intents() {
  }
}
