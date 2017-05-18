package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/4/13.
 */

public class UploadHealthConditionRequest
    extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_HEALTH_CONDITION_DIRECTORY = "usr/bodyupdate";
  private static final String GET_PARAMS_KEY_HEIGHT = "stature";
  private static final String GET_PARAMS_KEY_WEIGHT = "weight";
  private static final String GET_PARAMS_KEY_HEART_RATE = "heartrate";
  private static final String GET_PARAMS_KEY_PRESSURE_HIGH = "pressurehigh";
  private static final String GET_PARAMS_KEY_PRESSURE_LOW = "pressurelow";
  private static final String GET_PARAMS_KEY_OTHER_DEC = "otherdec";

  private Integer height;
  private Integer weight;
  private Integer heartRate;
  private Integer pressureHigh;
  private Integer pressureLow;
  private String otherDec;

  public UploadHealthConditionRequest(Response.Listener<ArchivesCommonResult> listener,
                                      Response.ErrorListener errorListener, String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (height != null) {
      params.put(GET_PARAMS_KEY_HEIGHT, String.valueOf(height));
    }

    if (weight != null) {
      params.put(GET_PARAMS_KEY_WEIGHT, String.valueOf(weight));
    }

    if (heartRate != null) {
      params.put(GET_PARAMS_KEY_HEART_RATE, String.valueOf(heartRate));
    }

    if (pressureHigh != null) {
      params.put(GET_PARAMS_KEY_PRESSURE_HIGH, String.valueOf(pressureHigh));
    }

    if (pressureLow != null) {
      params.put(GET_PARAMS_KEY_PRESSURE_LOW, String.valueOf(pressureLow));
    }

    if (otherDec != null) {
      params.put(GET_PARAMS_KEY_OTHER_DEC, otherDec);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_HEALTH_CONDITION_DIRECTORY;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public void setHeartRate(Integer heartRate) {
    this.heartRate = heartRate;
  }

  public void setPressureHigh(Integer pressureHigh) {
    this.pressureHigh = pressureHigh;
  }

  public void setPressureLow(Integer pressureLow) {
    this.pressureLow = pressureLow;
  }

  public void setOtherDec(String otherDec) {
    this.otherDec = otherDec;
  }
}
