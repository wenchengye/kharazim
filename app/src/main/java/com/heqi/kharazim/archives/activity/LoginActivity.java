package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.fragment.LoginFragment;
import com.heqi.kharazim.archives.fragment.RegisterFragment;
import com.heqi.kharazim.explore.activity.ExploreActivity;

/**
 * Created by overspark on 2017/3/16.
 */

public class LoginActivity extends FragmentActivity {

  private LoginFragment loginFragment = new LoginFragment();
  private RegisterFragment registerFragment = new RegisterFragment();
  private Fragment current;
  private boolean pendingLoginFinished = false;
  private boolean paused = false;

  private LoginFragment.LoginFragmentListener loginFragmentListener =
      new LoginFragment.LoginFragmentListener() {
    @Override
    public void onLoginFinished() {
      if (paused) pendingLoginFinished = true;
      else handleLoginFinished();

    }

    @Override
    public void onGotoRegister() {
      setCurrentFragment(registerFragment);
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
  }

  private void setCurrentFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, fragment)
        .commit();
    this.current = fragment;
  }

  @Override
  protected void onPause() {
    super.onPause();
    this.paused = true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    this.paused = false;

    if (this.pendingLoginFinished) {
      handleLoginFinished();
    }
  }

  private void handleLoginFinished() {
    this.pendingLoginFinished = false;

    ExploreActivity.launchActivity(KharazimApplication.getAppContext());
  }
}
