package com.heqi.kharazim.explore.http;

import android.text.TextUtils;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class CourseDetailWithDailyIdRequest extends AbstractKharazimHttpRequest<CourseQueryResult> {

  private static final String COURSE_DETAIL_WITH_DAILY_DIRECTORY = "plan/dailycourse";
  private static final String GET_PARAMS_KEY_DAILY_ID = "dailyid";

  private String dailyId;

  public CourseDetailWithDailyIdRequest(Response.Listener<CourseQueryResult> listener,
                                        Response.ErrorListener errorListener) {
    super(CourseQueryResult.class, listener, errorListener);
  }

  public String getDailyId() {
    return dailyId;
  }

  public void setDailyId(String dailyId) {
    this.dailyId = dailyId;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return COURSE_DETAIL_WITH_DAILY_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (!TextUtils.isEmpty(dailyId)) {
      params.put(GET_PARAMS_KEY_DAILY_ID, dailyId);
    }
  }
}
