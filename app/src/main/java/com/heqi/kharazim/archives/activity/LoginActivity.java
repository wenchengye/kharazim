package com.heqi.kharazim.archives.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.activity.PendingNavigateActivity;
import com.heqi.kharazim.archives.fragment.LoginFragment;
import com.heqi.kharazim.archives.fragment.RegisterFragment;

/**
 * Created by overspark on 2017/3/16.
 */

public class LoginActivity extends PendingNavigateActivity {

  private LoginFragment loginFragment = new LoginFragment();
  private RegisterFragment registerFragment = new RegisterFragment();
  private Fragment current;

  private LoginFragment.LoginFragmentListener loginFragmentListener =
      new LoginFragment.LoginFragmentListener() {
        @Override
        public void onLoginFinished() {
          navigate(new Runnable() {
            @Override
            public void run() {
              handleLoginFinished();
            }
          });
        }

        @Override
        public void onGotoRegister() {
          setCurrentFragment(registerFragment);
        }

        @Override
        public void onForgetPassword() {

        }
      };

  private RegisterFragment.RegisterFragmentListener registerFragmentListener =
      new RegisterFragment.RegisterFragmentListener() {
        @Override
        public void onRegisterFinished() {
          if (current instanceof RegisterFragment) {
            setCurrentFragment(loginFragment);
          }
        }
      };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);
    this.loginFragment.setListener(loginFragmentListener);
    this.registerFragment.setListener(registerFragmentListener);

    setCurrentFragment(this.loginFragment);

    findViewById(R.id.fragments_container).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyBoard(v.getWindowToken());
      }
    });
  }

  @Override
  public void onBackPressed() {
    if (current instanceof RegisterFragment) {
      setCurrentFragment(loginFragment);
    } else {
      super.onBackPressed();
    }
  }

  private void setCurrentFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, fragment)
        .commit();
    this.current = fragment;
  }

  private void handleLoginFinished() {
    NavigationManager.navigateToHome(KharazimApplication.getAppContext());
    finish();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    KharazimApplication.getThirdPlatform().onActivityResult(requestCode, resultCode, data);
  }

  private void hideSoftKeyBoard(IBinder token) {
    if (token != null) {
      InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      im.hideSoftInputFromWindow(token,
          InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}
