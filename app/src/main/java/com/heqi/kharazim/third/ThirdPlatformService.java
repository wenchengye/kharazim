package com.heqi.kharazim.third;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by overspark on 2017/4/18.
 */

public interface ThirdPlatformService {

  void init(Context context);

  void wechatLogin(ThirdPlatformTaskCallback callback);

  void wechatRelogin(ThirdPlatformTaskCallback callback);

  void onReceiveWechatLoginResponse(SendAuth.Resp resp);

  void onActivityResult(int requestCode, int resultCode, Intent data);

  String getWechatAccessToken();

  String getWechatOpenId();

  void qqLogin(Activity activity, ThirdPlatformTaskCallback callback);

  String getQQAccessToken();

  String getQQOpenId();

  void weiboLogin(Activity activity, ThirdPlatformTaskCallback callback);

  String getWeiboAccessToken();

  String getWeiboOpenId();

  interface ThirdPlatformTaskCallback {

    void onTaskSuccess();

    void onTaskFailed();
  }
}
