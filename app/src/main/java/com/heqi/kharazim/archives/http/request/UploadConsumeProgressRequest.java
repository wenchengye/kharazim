package com.heqi.kharazim.archives.http.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.rpc.GsonFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by overspark on 2017/4/6.
 */

public class UploadConsumeProgressRequest
    extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_CONSUME_PROGRESS_DIRECTORY = "usr/plan/progress";
  private static final String GET_PARAMS_KEY_DAILY_ID = "dailyid";
  private static final String GET_PARAMS_KEY_USER_PLAN_ID = "userplanid";
  private static final String POST_PARAMS_KEY_PROGRESS_LIST = "progresslist";

  private ConsumeCourseRecord record;

  public UploadConsumeProgressRequest(Response.Listener<ArchivesCommonResult> listener,
                                      Response.ErrorListener errorListener,
                                      String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  public void setRecord(ConsumeCourseRecord record) {
    this.record = record;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_CONSUME_PROGRESS_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.record != null) {
      if (!TextUtils.isEmpty(this.record.getDailyId())) {
        params.put(GET_PARAMS_KEY_DAILY_ID, this.record.getDailyId());
      }

      if (!TextUtils.isEmpty(this.record.getUserplanid())) {
        params.put(GET_PARAMS_KEY_USER_PLAN_ID, this.record.getUserplanid());
      }
    }
  }

  @Override
  protected Map<String, String> getParams() throws AuthFailureError {
    Map<String, String> params = super.getParams();
    if (params == null) {
      params = new HashMap<>();
    }

    if (this.record != null && this.record.getProgresslist() != null) {
      params.put(POST_PARAMS_KEY_PROGRESS_LIST,
          GsonFactory.getGson().toJson(this.record.getProgresslist()));
    }

    return params;
  }
}
