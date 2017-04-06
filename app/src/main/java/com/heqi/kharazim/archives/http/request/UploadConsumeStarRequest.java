package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/4/6.
 */

public class UploadConsumeStarRequest extends
    AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_CONSUME_STAR_DIRECTORY = "usr/plan/updatescore";
  private static final String GET_PARAMS_KEY_USER_PLAN_ID = "userplanid";
  private static final String GET_PRARMS_KEY_DAILY_ID = "dailyid";
  private static final String GET_PRARMS_KEY_COURSE_ID = "courseid";
  private static final String GET_PRARMS_KEY_DATE = "usedate";
  private static final String GET_PARAMS_KEY_STAR = "score";

  private String userPlanId;
  private String dailyId;
  private String courseId;
  private String date;
  private int star;

  public UploadConsumeStarRequest(Response.Listener<ArchivesCommonResult> listener,
                                  Response.ErrorListener errorListener,
                                  String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_CONSUME_STAR_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.userPlanId != null) {
      params.put(GET_PARAMS_KEY_USER_PLAN_ID, userPlanId);
    }

    if (this.dailyId != null) {
      params.put(GET_PRARMS_KEY_DAILY_ID, dailyId);
    }

    if (this.courseId != null) {
      params.put(GET_PRARMS_KEY_COURSE_ID, courseId);
    }

    if (this.date != null) {
      params.put(GET_PRARMS_KEY_DATE, date);
    }

    params.put(GET_PARAMS_KEY_STAR, String.valueOf(star));
  }

  public void setUserPlanId(String userPlanId) {
    this.userPlanId = userPlanId;
  }

  public void setDailyId(String dailyId) {
    this.dailyId = dailyId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setStar(int star) {
    this.star = star;
  }
}
