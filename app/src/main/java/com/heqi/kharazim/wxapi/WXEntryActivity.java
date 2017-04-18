package com.heqi.kharazim.wxapi;

import android.app.Activity;
import android.widget.Toast;

import com.heqi.kharazim.KharazimApplication;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

  @Override
  public void onReq(BaseReq baseReq) {
    Toast.makeText(this, baseReq.toString(), Toast.LENGTH_LONG).show();
  }

  @Override
  public void onResp(BaseResp baseResp) {
    if (baseResp instanceof SendAuth.Resp && KharazimApplication.getThirdPlatform() != null) {
      KharazimApplication.getThirdPlatform().onReceiveWechatLoginResponse(
          (SendAuth.Resp) baseResp);
    }
  }
}