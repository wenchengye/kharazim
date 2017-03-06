package com.heqi.kharazim.explore.http.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class PlanListRequest extends AbstractKharazimHttpRequest<PlanListInfo> {

  private static final String PLAN_LIST_DIRECTORY = "plan/list";
  private static final String GET_PARAMS_KEY_PAGE_NUMBER = "pageNo";
  private static final String GET_PARAMS_KEY_PAGE_SIZE = "pageSize";

  private Integer pageNumber;
  private Integer pageSize;

  public PlanListRequest(Response.Listener<PlanListInfo> listener,
                         Response.ErrorListener errorListener) {
    super(PlanListInfo.class, listener, errorListener);
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return PLAN_LIST_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (pageNumber != null) {
      params.put(GET_PARAMS_KEY_PAGE_NUMBER, String.valueOf(pageNumber));
    }

    if (pageSize != null) {
      params.put(GET_PARAMS_KEY_PAGE_SIZE, String.valueOf(pageSize));
    }
  }

  @Override
  protected Response<PlanListInfo> parseNetworkResponse(NetworkResponse response) {
    Response<PlanListInfo> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null) {
      Const.redirectKharazimModel(ret.result);
    }
    return ret;
  }
}
