package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.view.ArchivesEditTextView;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.third.ThirdPlatformService;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by overspark on 2017/3/16.
 */

public class LoginFragment extends AsyncLoadFragment {

  private ArchivesEditTextView userIdEditText;
  private ArchivesEditTextView passwordEditText;
  private View loginBtn;
  private View registerBtn;
  private View wechatLoginBtn;
  private View weiboLoginBtn;
  private View qqLoginBtn;
  private View forgetPasswordBtn;
  private LoginFragmentListener listener;

  private SsoHandler ssoHandler;

  private static void showLoginOnGoingToast() {
    KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
        R.string.archives_in_login_progress_hint_text));
  }

  private static void showLoginFailedToast() {
    KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
        R.string.archives_login_failed_hint_text));
  }

  public void setListener(LoginFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
  }

  private void initView(View contentView) {
    if (contentView == null) return;

    userIdEditText = (ArchivesEditTextView)
        contentView.findViewById(R.id.archives_login_user_input_et);
    passwordEditText = (ArchivesEditTextView)
        contentView.findViewById(R.id.archives_login_password_input_et);
    loginBtn = contentView.findViewById(R.id.archives_login_action_login_btn);
    registerBtn = contentView.findViewById(R.id.archives_login_action_register_btn);
    wechatLoginBtn = contentView.findViewById(R.id.archives_login_wechat_iv);
    weiboLoginBtn = contentView.findViewById(R.id.archives_login_weibo_iv);
    qqLoginBtn = contentView.findViewById(R.id.archives_login_qq_iv);
    forgetPasswordBtn = contentView.findViewById(R.id.archives_login_forget_password_button);
  }

  private void initListeners() {
    if (loginBtn != null) {
      loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          standardLogin();
        }
      });
    }

    if (registerBtn != null) {
      registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onGotoRegister();
          }
        }
      });
    }

    if (wechatLoginBtn != null) {
      wechatLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          wechatLogin();
        }
      });
    }

    if (qqLoginBtn != null) {
      qqLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          qqLogin();
        }
      });
    }

    if (weiboLoginBtn != null) {
      weiboLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          weiboLogin();
        }
      });
    }

    if (forgetPasswordBtn != null) {
      forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onForgetPassword();
          }
        }
      });

      if (passwordEditText != null) {
        passwordEditText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            forgetPasswordBtn.setVisibility(TextUtils.isEmpty(s) ? View.VISIBLE : View.GONE);
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
        });
      }
    }
  }

  private void standardLogin() {
    String userId = userIdEditText.getText().trim();
    String password = passwordEditText.getText().trim();

    if (TextUtils.isEmpty(userId)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_id_hint_text));
      return;
    }

    if (TextUtils.isEmpty(password)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_password_hint_text));
      return;
    }

    LoginTaskListener loginListener = new LoginTaskListener();

    boolean ret = KharazimApplication.getArchives().login(userId, password, loginListener);

    if (ret) {
      showLoginOnGoingToast();
    }
  }

  private void wechatLogin() {
    KharazimApplication.getThirdPlatform().wechatLogin(getActivity(),
        new ThirdPlatformService.ThirdPlatformTaskCallback() {
          @Override
          public void onTaskSuccess() {
            LoginTaskListener loginListener = new LoginTaskListener();

            boolean ret = KharazimApplication.getArchives().thirdLogin(
                KharazimApplication.getThirdPlatform().getWechatOpenId(),
                Const.LoginType.Wechat.getValue(), loginListener);

            if (ret) {
              showLoginOnGoingToast();
            }
          }

          @Override
          public void onTaskFailed() {
            showLoginFailedToast();
          }
        });
  }

  private void weiboLogin() {
    KharazimApplication.getThirdPlatform().weiboLogin(getActivity(),
        new ThirdPlatformService.ThirdPlatformTaskCallback() {
          @Override
          public void onTaskSuccess() {
            LoginTaskListener loginListener = new LoginTaskListener();

            boolean ret = KharazimApplication.getArchives().thirdLogin(
                KharazimApplication.getThirdPlatform().getWeiboOpenId(),
                Const.LoginType.Weibo.getValue(), loginListener);

            if (ret) {
              showLoginOnGoingToast();
            }
          }

          @Override
          public void onTaskFailed() {
            showLoginFailedToast();
          }
        });
  }

  private void qqLogin() {
    KharazimApplication.getThirdPlatform().qqLogin(getActivity(),
        new ThirdPlatformService.ThirdPlatformTaskCallback() {
          @Override
          public void onTaskSuccess() {
            LoginTaskListener loginListener = new LoginTaskListener();

            boolean ret = KharazimApplication.getArchives().thirdLogin(
                KharazimApplication.getThirdPlatform().getQQOpenId(),
                Const.LoginType.QQ.getValue(), loginListener);

            if (ret) {
              showLoginOnGoingToast();
            }
          }

          @Override
          public void onTaskFailed() {
            showLoginFailedToast();
          }
        });
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_login_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {
    // is not used
  }

  @Override
  protected void onStartLoading() {
    // is not used
  }

  @Override
  protected boolean needToLoadData() {
    return false;
  }

  public interface LoginFragmentListener {

    void onLoginFinished();

    void onGotoRegister();

    void onForgetPassword();
  }

  private class LoginTaskListener implements ArchivesService.ArchivesTaskCallback {

    @Override
    public void onTaskSuccess(int code, String msg) {
      if (KharazimUtils.isRetCodeOK(code)) {
        if (listener != null) {
          listener.onLoginFinished();
        }
      } else {
        if (TextUtils.isEmpty(msg)) {
          KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
              R.string.archives_login_failed_hint_text));
        } else {
          KharazimUtils.showToast(msg);
        }
      }
    }

    @Override
    public void onTaskFailed() {
      showLoginFailedToast();
    }
  }

}
