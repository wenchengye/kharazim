package com.heqi.kharazim.third;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.heqi.base.utils.Preferences;
import com.heqi.base.utils.SharePrefSubmitor;
import com.heqi.kharazim.third.http.WechatGetAccessTokenRequest;
import com.heqi.kharazim.third.http.WechatRefreshAccessTokenRequest;
import com.heqi.kharazim.third.modal.WechatGetAccessTokenResult;
import com.heqi.rpc.RpcHelper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by overspark on 2017/4/18.
 */

public class ThirdPlatformServiceImpl implements ThirdPlatformService {

  private static class Const {

    public static final String THIRD_PLATFORM_PREFERENCE_NAME = "kharazim_third";
    public static final String PREFERENCE_KEY_WECHAT_REFRESH_TOKEN =
        "kharazim_wechat_refresh_token";


    public static final String WECHAT_APPID = "wx2b15c26fc7d771c4";
    public static final String WECHAT_APP_SECRET = "9ad31fac163333ece88598fabde9c0ea";
    public static final String WECHAT_SCOPE_BASE = "snsapi_base";
    public static final String WECHAT_SCOPE_USER_INFO = "snsapi_userinfo";
    public static final String WECHAT_SCOPE_SPLITOR = ",";

    public static final String QQ_APPID = "1105927530";
    public static final String QQ_SCOPE = "get_user_info";
    public static final String QQ_JSON_KEY_OPEN_ID = "openid";
    public static final String QQ_JSON_KEY_ACCESS_TOKEN = "access_token";

    public static final String WEIBO_APPID = "4129381655";
    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String WEIBO_SCOPE = "email,direct_messages_read,direct_messages_write";

    private Const() {
    }
  }

  private Context context;
  private Preferences thirdPreferences;

  private IWXAPI wxapi;
  private String wxRefreshToken;
  private String wxAccessToken;
  private String wxOpenId;
  private ThirdPlatformTaskCallback wxLoginCallback;

  private Tencent tencent;
  private String qqAccessToken;
  private String qqOpenId;
  private QQLoginListener qqLoginListener;
  private ThirdPlatformTaskCallback qqLoginCallback;

  private SsoHandler ssoHandler;
  private Oauth2AccessToken weiboAccessToken;
  private ThirdPlatformTaskCallback weiboLoginCallback;

  @Override
  public void init(Context context) {
    this.context = context;
    thirdPreferences = Preferences.getById(this.context, Const.THIRD_PLATFORM_PREFERENCE_NAME);


    wxapi = WXAPIFactory.createWXAPI(context, Const.WECHAT_APPID, true);
    wxapi.registerApp(Const.WECHAT_APPID);
    wxRefreshToken = thirdPreferences.getString(Const.PREFERENCE_KEY_WECHAT_REFRESH_TOKEN, null);

    tencent = Tencent.createInstance(Const.QQ_APPID, context);

  }

  @Override
  public void wechatLogin(final ThirdPlatformTaskCallback callback) {

    wxLoginCallback = callback;

    final SendAuth.Req req = new SendAuth.Req();
    req.scope = Const.WECHAT_SCOPE_USER_INFO;
    req.state = "none";
    wxapi.sendReq(req);
  }

  @Override
  public void wechatRelogin(ThirdPlatformTaskCallback callback) {

    if (wxRefreshToken == null) {
      if (callback != null) {
        callback.onTaskFailed();
      }
      return;
    }

    wxLoginCallback = callback;

    Response.Listener<WechatGetAccessTokenResult> successListener =
        new WechatHttpLoginSuccessListener();
    Response.ErrorListener errorListener = new WechatHttpLoginFailedListener();

    WechatRefreshAccessTokenRequest req =
        new WechatRefreshAccessTokenRequest(successListener, errorListener);
    req.setAppid(Const.WECHAT_APPID);
    req.setRefreshToken(wxRefreshToken);
    RpcHelper.getInstance(context).executeRequestAsync(req);
  }

  @Override
  public void onReceiveWechatLoginResponse(SendAuth.Resp resp) {
    if (resp.errCode == SendAuth.Resp.ErrCode.ERR_OK) {
      Response.Listener<WechatGetAccessTokenResult> successListener =
          new WechatHttpLoginSuccessListener();
      Response.ErrorListener errorListener =
          new WechatHttpLoginFailedListener();

      WechatGetAccessTokenRequest request =
          new WechatGetAccessTokenRequest(successListener, errorListener);
      request.setAppid(Const.WECHAT_APPID);
      request.setSecret(Const.WECHAT_APP_SECRET);
      request.setCode(resp.code);
      RpcHelper.getInstance(context).executeRequestAsync(request);
    } else {
      if (wxLoginCallback != null) {
        wxLoginCallback.onTaskFailed();
      }
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (ssoHandler != null) {
      ssoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    Tencent.onActivityResultData(requestCode,resultCode,data,qqLoginListener);
  }

  @Override
  public String getWechatAccessToken() {
    return wxAccessToken;
  }

  @Override
  public String getWechatOpenId() {
    return wxOpenId;
  }

  @Override
  public void qqLogin(Activity activity, ThirdPlatformTaskCallback callback) {
    qqLoginCallback = callback;

    qqLoginListener = new QQLoginListener();
    tencent.login(activity, Const.QQ_SCOPE, qqLoginListener);
  }

  @Override
  public String getQQAccessToken() {
    return qqAccessToken;
  }

  @Override
  public String getQQOpenId() {
    return qqOpenId;
  }

  @Override
  public void weiboLogin(Activity activity, ThirdPlatformTaskCallback callback) {

    weiboLoginCallback = callback;

    AuthInfo authInfo = new AuthInfo(activity, Const.WEIBO_APPID, Const.WEIBO_REDIRECT_URL,
        Const.WEIBO_SCOPE);
    ssoHandler = new SsoHandler(activity, authInfo);
    ssoHandler.authorize(new WeboAuthListener());
  }

  @Override
  public String getWeiboAccessToken() {
    return weiboAccessToken != null ? weiboAccessToken.getToken() : null;
  }

  @Override
  public String getWeiboOpenId() {
    return weiboAccessToken != null ? weiboAccessToken.getUid() : null;
  }

  private class WechatHttpLoginSuccessListener implements
      Response.Listener<WechatGetAccessTokenResult> {
    @Override
    public void onResponse(WechatGetAccessTokenResult response) {

      if (!TextUtils.isEmpty(response.getAccess_token())) {
        wxAccessToken = response.getAccess_token();
        wxRefreshToken = response.getRefresh_token();
        wxOpenId = response.getOpenid();

        SharePrefSubmitor.submit(thirdPreferences.edit()
            .putString(Const.PREFERENCE_KEY_WECHAT_REFRESH_TOKEN, wxRefreshToken));

        if (wxLoginCallback != null) {
          wxLoginCallback.onTaskSuccess();
        }
      } else {
        if (wxLoginCallback != null) {
          wxLoginCallback.onTaskFailed();
        }
      }
    }
  }

  private class QQLoginListener implements IUiListener {
    @Override
    public void onComplete(Object o) {
      if (o instanceof JSONObject && ((JSONObject)o).has(Const.QQ_JSON_KEY_ACCESS_TOKEN)) {
        JSONObject json = (JSONObject) o;
        qqAccessToken = json.optString(Const.QQ_JSON_KEY_ACCESS_TOKEN);
        qqOpenId = json.optString(Const.QQ_JSON_KEY_OPEN_ID);
        qqLoginCallback.onTaskSuccess();
      } else {
        qqLoginCallback.onTaskFailed();
      }

    }

    @Override
    public void onError(UiError uiError) {
      if (qqLoginCallback != null) {
        qqLoginCallback.onTaskFailed();
      }
    }

    @Override
    public void onCancel() {
      if (qqLoginCallback != null) {
        qqLoginCallback.onTaskFailed();
      }
    }
  }

  private class WechatHttpLoginFailedListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
      if (wxLoginCallback != null) {
        wxLoginCallback.onTaskFailed();
      }
    }
  }

  private class WeboAuthListener implements WeiboAuthListener {

    @Override
    public void onComplete(Bundle values) {
      Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
      //从这里获取用户输入的 电话号码信息
      if (accessToken.isSessionValid()) {
        weiboAccessToken = accessToken;
        if (weiboLoginCallback != null) {
          weiboLoginCallback.onTaskSuccess();
        }
      } else {
        if (weiboLoginCallback != null) {
          weiboLoginCallback.onTaskFailed();
        }
      }
    }

    @Override
    public void onCancel() {
      if (weiboLoginCallback != null) {
        weiboLoginCallback.onTaskFailed();
      }
    }

    @Override
    public void onWeiboException(WeiboException e) {
      if (weiboLoginCallback != null) {
        weiboLoginCallback.onTaskFailed();
      }
    }
  }
}
