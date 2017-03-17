package com.heqi.kharazim.archives;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.heqi.base.utils.Preferences;
import com.heqi.base.utils.SharePrefSubmitor;
import com.heqi.kharazim.archives.http.HealthConditionRequest;
import com.heqi.kharazim.archives.http.LoginRequest;
import com.heqi.kharazim.archives.http.ReloginRequest;
import com.heqi.kharazim.archives.http.UserProfileRequest;
import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.HealthConditionResult;
import com.heqi.kharazim.archives.model.LoginResult;
import com.heqi.kharazim.archives.model.ReloginResult;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.archives.model.UserProfileResult;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.utils.KharazimUtils;
import com.heqi.rpc.RpcHelper;

/**
 * Created by overspark on 2017/3/13.
 */

public class ArchivesImpl implements Archives {

  private static class Const {

    public static final String ARCHIVES_PREFERENCE_NAME = "kharazim_archives";

    public static final String PREFERENCE_KEY_USER_ARCHIVE_BUNDLE_PREFIX = "kharazim_user_archive_";

    public static final String PREFERENCE_KEY_LAST_USER_ID_STRING = "kharazim_last_user_id";

    public static final String BUNDLE_KEY_USER_ID_STRING = "kharazim_user_id";

    public static final String BUNDLE_KEY_USER_PROFILE_OBJECT = "kharazim_user_profile";

    public static final String BUNLDE_KEY_USER_HEALTH_CONDITION_OBJECT =
        "kharazim_user_health_condition";

    private Const() {}
  }

  private final Context context;
  private final Preferences archivesPreferences;
  private int state;
  private int originState;
  private String lastUserId = null;
  private String currentUserId = null;
  private Bundle currentUserBundle = null;
  private String accessToken = null;

  public ArchivesImpl(Context context) {
    this.context = context;
    archivesPreferences = Preferences.getById(this.context, Const.ARCHIVES_PREFERENCE_NAME);
    this.state = State.OFFLINE;
    this.originState = State.OFFLINE;
    this.lastUserId = archivesPreferences.getString(Const.PREFERENCE_KEY_LAST_USER_ID_STRING, null);
  }

  @Override
  public int getState() {
    return state;
  }

  @Override
  public boolean relogin(final String userId, final ArchivesTaskCallback callback) {
    if (!switchState2Logining()) return false;

    final Response.Listener<ReloginResult> successListener =
        new Response.Listener<ReloginResult>() {
      @Override
      public void onResponse(ReloginResult response) {
        if (KharazimUtils.isRetCodeOK(response.getRet_code())) {

          handleLogin(userId, response.getAccesstoken());

        } else {
          synchronized (ArchivesImpl.this) {
            ArchivesImpl.this.state = ArchivesImpl.this.originState;
          }
        }

        if (callback != null) {
          callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
        }
      }
    };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        synchronized (ArchivesImpl.this) {
          ArchivesImpl.this.state = ArchivesImpl.this.originState;
        }

        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    ReloginRequest request =  new ReloginRequest(successListener, errorListener);
    request.setToken(userId);
    RpcHelper.getInstance(this.context).executeRequestAsync(request);

    return true;
  }

  @Override
  public boolean login(@NonNull final String id, @NonNull final String password,
                       final ArchivesTaskCallback callback) {
    if (!switchState2Logining()) return false;

    final Response.Listener<LoginResult> successListener = new Response.Listener<LoginResult>() {
      @Override
      public void onResponse(final LoginResult response) {
        if (KharazimUtils.isRetCodeOK(response.getRet_code())) {

          handleLogin(response.getRelogintoken(), response.getAccesstoken());

        } else {
          synchronized (ArchivesImpl.this) {
            ArchivesImpl.this.state = ArchivesImpl.this.originState;
          }
        }

        if (callback != null) {
          callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
        }
      }
    };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        synchronized (ArchivesImpl.this) {
          ArchivesImpl.this.state = ArchivesImpl.this.originState;
        }


        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    LoginRequest request = new LoginRequest(successListener, errorListener);

    if (id.contains(com.heqi.kharazim.config.Const.EMAIL_KEY_WORD)) {
      request.setEmail(id);
    } else {
      request.setPhoneNumber(id);
    }
    request.setLoginPassword(password);

    RpcHelper.getInstance(this.context).executeRequestAsync(request);
    return true;
  }

  private boolean switchState2Logining() {
    synchronized (this) {
      if (this.state == State.LOGINING) return false;
      this.originState = this.state;
      this.state = State.LOGINING;
    }
    return true;
  }

  private void handleLogin(String userId, String accessToken) {
    synchronized (ArchivesImpl.this) {
      ArchivesImpl.this.state = State.ONLINE;
    }

    this.accessToken = accessToken;
    this.lastUserId = userId;
    this.currentUserId = userId;

    String bundleKey = getUserBundleKey(this.currentUserId);
    this.currentUserBundle = this.archivesPreferences.getBundle(bundleKey, new Bundle());
    this.currentUserBundle.putString(Const.BUNDLE_KEY_USER_ID_STRING, this.currentUserId);

    SharePrefSubmitor.submit(this.archivesPreferences.edit()
        .putBundle(bundleKey, this.currentUserBundle)
        .putString(Const.PREFERENCE_KEY_LAST_USER_ID_STRING, this.lastUserId));
  }

  @Override
  public boolean updateCurrentUserProfile(final ArchivesTaskCallback callback) {
    synchronized (this) {
      if (this.state != State.ONLINE) return false;
    }

    final String userIdRef = this.currentUserId;

    final Response.Listener<UserProfileResult> successListener =
        new Response.Listener<UserProfileResult>() {
          @Override
          public void onResponse(final UserProfileResult response) {
            if (KharazimUtils.isRetCodeOK(response.getRet_code())
                && userIdRef != null
                && userIdRef.equals(currentUserId)) {
              currentUserBundle.putSerializable(ArchivesImpl.Const.BUNDLE_KEY_USER_PROFILE_OBJECT,
                  response.getData_src());

              String bundleKey = getUserBundleKey(currentUserId);

              SharePrefSubmitor.submit(archivesPreferences.edit()
                  .putBundle(bundleKey, currentUserBundle));
            }

            if (callback != null) {
              callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
            }
          }
        };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    UserProfileRequest request = new UserProfileRequest(successListener,
        errorListener, this.accessToken);
    RpcHelper.getInstance(this.context).executeRequestAsync(request);

    return true;
  }

  @Override
  public boolean updateCurrentHealthCondition(final ArchivesTaskCallback callback) {
    synchronized (this) {
      if (this.state != State.ONLINE) return false;
    }

    final String userIdRef = this.currentUserId;

    final Response.Listener<HealthConditionResult> successListener =
        new Response.Listener<HealthConditionResult>() {
          @Override
          public void onResponse(final HealthConditionResult response) {
            if (KharazimUtils.isRetCodeOK(response.getRet_code())
                && userIdRef != null
                && userIdRef.equals(currentUserId)) {
              currentUserBundle.putSerializable(
                  ArchivesImpl.Const.BUNLDE_KEY_USER_HEALTH_CONDITION_OBJECT,
                  response.getData_src());

              String bundleKey = getUserBundleKey(currentUserId);

              SharePrefSubmitor.submit(archivesPreferences.edit()
                  .putBundle(bundleKey, currentUserBundle));
            }

            if (callback != null) {
              callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
            }
          }
        };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    HealthConditionRequest request = new HealthConditionRequest(successListener,
        errorListener, this.accessToken);
    RpcHelper.getInstance(this.context).executeRequestAsync(request);

    return true;
  }

  @Override
  public boolean uploadCurrentUserProfile(UserProfile userProfile, ArchivesTaskCallback callback) {
    return false;
  }

  @Override
  public boolean uploadCurrentHealthCondition(HealthCondition healthCondition,
                                              ArchivesTaskCallback callback) {
    return false;
  }

  @Override
  public void logout() {

  }

  @Override
  public String getLastUserId() {
    return this.lastUserId;
  }

  @Override
  public String getCurrentUserId() {
    return this.state == State.ONLINE ? this.currentUserId : null;
  }

  @Override
  public String getCurrentAccessToken() {
    return this.state == State.ONLINE ? this.accessToken : null;
  }

  @Override
  public UserProfile getCurrentUserProfile() {
    return this.state == State.ONLINE
        ? (UserProfile) this.currentUserBundle.getSerializable(Const.BUNDLE_KEY_USER_PROFILE_OBJECT)
        : null;
  }

  @Override
  public HealthCondition getCurrentHealthCondition() {
    return this.state == State.ONLINE
        ? (HealthCondition) this.currentUserBundle.getSerializable(
            Const.BUNLDE_KEY_USER_HEALTH_CONDITION_OBJECT)
        : null;
  }

  private static String getUserBundleKey(String userId) {
    return Const.PREFERENCE_KEY_USER_ARCHIVE_BUNDLE_PREFIX + userId;
  }

  private interface LoginRequestBuilder {
    void build(LoginRequest request);
  }
}