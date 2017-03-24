package com.heqi.kharazim.archives;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.heqi.base.utils.Preferences;
import com.heqi.base.utils.SharePrefSubmitor;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.archives.http.request.AddPlanRequest;
import com.heqi.kharazim.archives.http.request.HealthConditionRequest;
import com.heqi.kharazim.archives.http.request.LoginRequest;
import com.heqi.kharazim.archives.http.request.ReloginRequest;
import com.heqi.kharazim.archives.http.request.UserProfileRequest;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.HealthConditionResult;
import com.heqi.kharazim.archives.model.LoginResult;
import com.heqi.kharazim.archives.model.ReloginResult;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.archives.model.UserProfileResult;
import com.heqi.kharazim.utils.KharazimUtils;
import com.heqi.rpc.RpcHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/3/13.
 */

public class ArchivesServiceImpl implements ArchivesService {

  private final Context context;
  private final Preferences archivesPreferences;
  private final List<WeakReference<ArchivesObserver>> observers = new ArrayList<>();
  private int state;
  private int originState;
  private String lastUserId = null;
  private String currentUserId = null;
  private Bundle currentUserBundle = null;
  private String accessToken = null;

  public ArchivesServiceImpl(Context context) {
    this.context = context;
    archivesPreferences = Preferences.getById(this.context, Const.ARCHIVES_PREFERENCE_NAME);
    this.state = State.OFFLINE;
    this.originState = State.OFFLINE;
    this.lastUserId = archivesPreferences.getString(Const.PREFERENCE_KEY_LAST_USER_ID_STRING, null);
  }

  private static String getUserBundleKey(String userId) {
    return Const.PREFERENCE_KEY_USER_ARCHIVE_BUNDLE_PREFIX + userId;
  }

  @Override
  public synchronized int getState() {
    return state;
  }

  private synchronized void setState(int state) {
    this.state = state;
  }

  @Override
  public void addObserver(ArchivesObserver observer) {
    synchronized (this.observers) {
      for (int i = 0; i < observers.size(); ++i) {
        ArchivesObserver ref = observers.get(i).get();

        if (ref != null && ref == observer) return;

      }

      observers.add(new WeakReference<>(observer));
    }
  }

  @Override
  public void removeObserver(ArchivesObserver observer) {
    synchronized (this.observers) {
      for (int i = 0; i < observers.size(); ++i) {
        ArchivesObserver ref = observers.get(i).get();

        if (ref != null && ref == observer) {
          observers.remove(i);
          break;
        }
      }
    }
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
              resetState2Origin();
            }

            if (callback != null) {
              callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
            }
          }
        };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        resetState2Origin();

        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    ReloginRequest request = new ReloginRequest(successListener, errorListener);
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
          resetState2Origin();
        }

        if (callback != null) {
          callback.onTaskSuccess(response.getRet_code(), response.getRet_msg());
        }
      }
    };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        resetState2Origin();

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

  private void handleLogin(final String userId, final String accessToken) {
    synchronized (this) {
      setState(State.ONLINE);

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

    notifyObservers(new NotifyRunnable() {
      @Override
      public void notify(ArchivesObserver observer) {
        observer.onLogin(userId);
      }
    });
  }

  @Override
  public boolean updateCurrentUserProfile(final ArchivesTaskCallback callback) {
    if (getState() != State.ONLINE) return false;

    final String userIdRef;
    final String accessTokenRef;
    synchronized (this) {
      userIdRef = this.currentUserId;
      accessTokenRef = this.accessToken;
    }

    final Response.Listener<UserProfileResult> successListener =
        new Response.Listener<UserProfileResult>() {
          @Override
          public void onResponse(final UserProfileResult response) {

            if (KharazimUtils.isRetCodeOK(response.getRet_code())) {
              boolean notify = false;
              synchronized (ArchivesServiceImpl.this) {
                if (userIdRef != null
                    && userIdRef.equals(currentUserId)) {
                  currentUserBundle.putSerializable(ArchivesServiceImpl.Const.BUNDLE_KEY_USER_PROFILE_OBJECT,
                      response.getData_src());

                  String bundleKey = getUserBundleKey(currentUserId);

                  SharePrefSubmitor.submit(archivesPreferences.edit()
                      .putBundle(bundleKey, currentUserBundle));
                  notify = true;
                }
              }

              if (notify) {
                notifyObservers(new NotifyRunnable() {
                  @Override
                  public void notify(ArchivesObserver observer) {
                    observer.onUserProfileUpdated(userIdRef, response.getData_src());
                  }
                });
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
        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    UserProfileRequest request = new UserProfileRequest(successListener,
        errorListener, accessTokenRef);
    RpcHelper.getInstance(this.context).executeRequestAsync(request);

    return true;
  }

  @Override
  public boolean updateCurrentHealthCondition(final ArchivesTaskCallback callback) {
    if (getState() != State.ONLINE) return false;

    final String userIdRef;
    final String accessTokenRef;
    synchronized (this) {
      userIdRef = this.currentUserId;
      accessTokenRef = this.accessToken;
    }

    final Response.Listener<HealthConditionResult> successListener =
        new Response.Listener<HealthConditionResult>() {
          @Override
          public void onResponse(final HealthConditionResult response) {
            if (KharazimUtils.isRetCodeOK(response.getRet_code())) {
              boolean notify = false;
              synchronized (ArchivesServiceImpl.this) {
                if (userIdRef != null
                    && userIdRef.equals(currentUserId)) {
                  currentUserBundle.putSerializable(
                      ArchivesServiceImpl.Const.BUNLDE_KEY_USER_HEALTH_CONDITION_OBJECT,
                      response.getData_src());

                  String bundleKey = getUserBundleKey(currentUserId);

                  SharePrefSubmitor.submit(archivesPreferences.edit()
                      .putBundle(bundleKey, currentUserBundle));
                  notify = true;
                }
              }

              if (notify) {
                notifyObservers(new NotifyRunnable() {
                  @Override
                  public void notify(ArchivesObserver observer) {
                    observer.onHealthConditionUpdated(userIdRef, response.getData_src());
                  }
                });
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
        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    HealthConditionRequest request = new HealthConditionRequest(successListener,
        errorListener, accessTokenRef);
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
  public boolean addPlan(final String planId, final ArchivesTaskCallback callback) {
    if (getState() != State.ONLINE) return false;

    final String userIdRef;
    final String accessTokenRef;
    synchronized (this) {
      userIdRef = this.currentUserId;
      accessTokenRef = this.accessToken;
    }

    final Response.Listener<ArchivesCommonResult> successListener =
        new Response.Listener<ArchivesCommonResult>() {
          @Override
          public void onResponse(final ArchivesCommonResult response) {
            if (KharazimUtils.isRetCodeOK(response.getRet_code())) {
              boolean notify = false;
              synchronized (ArchivesServiceImpl.this) {
                if (userIdRef != null
                    && userIdRef.equals(currentUserId)) {
                  notify = true;
                }
              }

              if (notify) {
                notifyObservers(new NotifyRunnable() {
                  @Override
                  public void notify(ArchivesObserver observer) {
                    observer.onAddPlan(userIdRef, planId);
                  }
                });
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
        if (callback != null) {
          callback.onTaskFailed();
        }
      }
    };

    AddPlanRequest request = new AddPlanRequest(successListener, errorListener, accessTokenRef);
    request.setPlanId(planId);
    RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(request);

    return true;
  }

  @Override
  public boolean removePlan(String planId, ArchivesTaskCallback callback) {
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

  private void notifyObservers(NotifyRunnable runnable) {
    synchronized (this.observers) {
      for (WeakReference<ArchivesObserver> observer : observers) {
        ArchivesObserver ref = observer.get();
        if (ref != null) {
          runnable.notify(ref);
        }
      }
    }
  }

  private synchronized void resetState2Origin() {
    this.state = this.originState;
  }

  private boolean switchState2Logining() {
    synchronized (this) {
      if (this.state == State.LOGINING) return false;
      this.originState = this.state;
      this.state = State.LOGINING;
    }
    return true;
  }

  private interface NotifyRunnable {
    void notify(ArchivesObserver observer);
  }

  private static class Const {

    public static final String ARCHIVES_PREFERENCE_NAME = "kharazim_archives";

    public static final String PREFERENCE_KEY_USER_ARCHIVE_BUNDLE_PREFIX = "kharazim_user_archive_";

    public static final String PREFERENCE_KEY_LAST_USER_ID_STRING = "kharazim_last_user_id";

    public static final String BUNDLE_KEY_USER_ID_STRING = "kharazim_user_id";

    public static final String BUNDLE_KEY_USER_PROFILE_OBJECT = "kharazim_user_profile";

    public static final String BUNLDE_KEY_USER_HEALTH_CONDITION_OBJECT =
        "kharazim_user_health_condition";

    private Const() {
    }
  }
}