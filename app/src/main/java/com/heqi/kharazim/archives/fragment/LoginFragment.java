package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/3/16.
 */

public class LoginFragment extends AsyncLoadFragment {

  private EditText userIdEditText;
  private EditText passwordEditText;
  private TextView loginBtn;
  private TextView registerBtn;
  private ImageView wechatLoginBtn;
  private ImageView weiboLoginBtn;
  private ImageView qqLoginBtn;
  private LoginFragmentListener listener;

  public void setListener(LoginFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
  }

  private void initView(View contentView) {
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
  }

  private void standardLogin() {
    String userId = userIdEditText.getText().toString().trim();
    String password = userIdEditText.getText().toString().trim();

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

    ArchivesService.ArchivesTaskCallback loginListener = new ArchivesService.ArchivesTaskCallback() {
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
        KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
            R.string.archives_login_failed_hint_text));
      }
    };

    boolean ret = KharazimApplication.getArchives().login(userId, password, loginListener);

    if (!ret) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_in_login_progress_hint_text));
    }
  }

  private void wechatLogin() {

  }

  private void weiboLogin() {

  }

  private void qqLogin() {

  }

  @Override
  protected int getLayoutResId() {
    return 0;
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
  }

}
