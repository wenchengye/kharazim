package com.heqi.kharazim.archives.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class UploadHeadImageRequest extends
    AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_HEAD_IMAGE_DIRECTORY = "usr/headimg";
  private static final String POST_PARAMS_KEY_IMAGE = "image";

  private String image;

  public UploadHeadImageRequest(Response.Listener<ArchivesCommonResult> listener,
                                Response.ErrorListener errorListener,
                                String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_HEAD_IMAGE_DIRECTORY;
  }

  @Override
  protected Map<String, String> getParams() throws AuthFailureError {
    Map<String, String> params = super.getParams();
    if (params == null) {
      params = new HashMap<>();
    }

    params.put(POST_PARAMS_KEY_IMAGE, image);
    return params;
  }

  @Override
  protected Response<ArchivesCommonResult> parseNetworkResponse(NetworkResponse response) {
    Response<ArchivesCommonResult> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null && ret.result.getRet_msg() != null) {
      ret.result.setRet_msg(KharazimUtils.getKharazimResource(ret.result.getRet_msg()));
    }
    return ret;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
