package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.UserStatsResult;

import java.util.Map;

/**
 * Created by overspark on 2017/3/25.
 */

public class UserStatsRequest extends AbstractKharazimArchivesHttpRequest<UserStatsResult> {

  private static final String USER_STATS_DIRECTORY = "usr/plan/count";
  private static final String GET_PARAMS_KEY_SCALE = "datatype";
  private UserStatsScale scale = UserStatsScale.ALL;

  public UserStatsRequest(Response.Listener<UserStatsResult> listener,
                          Response.ErrorListener errorListener,
                          String accessToken) {
    super(UserStatsResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return USER_STATS_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    params.put(GET_PARAMS_KEY_SCALE, String.valueOf(scale.getValue()));
  }

  public void setScale(UserStatsScale scale) {
    this.scale = scale;
  }

  public enum UserStatsScale {
    DAY(1), WEEK(2), MONTH(3), ALL(4);


    private final int value;

    private UserStatsScale(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }
}
