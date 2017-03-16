package com.heqi.kharazim.archives.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.base.utils.Base64;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.config.Const;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class UploadHeadImageRequest extends
    AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_HEAD_IMAGE_DIRECTORY = "usr/headimg";
  private static final String GET_PARAMS_KEY_IMAGE = "image";

  private byte[] image;

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
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);
    if (this.image != null) {
      params.put(GET_PARAMS_KEY_IMAGE, Base64.encodeToString(image, Base64.DEFAULT));
    }
  }

  @Override
  protected Response<ArchivesCommonResult> parseNetworkResponse(NetworkResponse response) {
    Response<ArchivesCommonResult> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null && ret.result.getRet_msg() != null) {
      ret.result.setRet_msg(Const.getKharazimResource(ret.result.getRet_msg()));
    }
    return ret;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }
}
