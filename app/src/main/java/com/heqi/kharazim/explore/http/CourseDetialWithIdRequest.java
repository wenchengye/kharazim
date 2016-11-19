package com.heqi.kharazim.explore.http;

import android.text.TextUtils;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class CourseDetialWithIdRequest extends AbstractKharazimHttpRequest<CourseQueryResult> {

  private static final String COURSE_DETAIL_WITH_ID_DIRECTORY = "course/details";
  private static final String GET_PARAMS_KEY_ID = "id";

  private String id;

  public CourseDetialWithIdRequest(Response.Listener<CourseQueryResult> listener,
                                   Response.ErrorListener errorListener) {
    super(CourseQueryResult.class, listener, errorListener);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return COURSE_DETAIL_WITH_ID_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (!TextUtils.isEmpty(id)) {
      params.put(GET_PARAMS_KEY_ID, id);
    }
  }
}
