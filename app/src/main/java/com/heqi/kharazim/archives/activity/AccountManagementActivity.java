package com.heqi.kharazim.archives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.fragment.AccountManagementFragment;
import com.heqi.kharazim.archives.fragment.StandardBindFragment;

/**
 * Created by overspark on 2017/5/22.
 */

public class AccountManagementActivity extends FragmentActivity {

  private StandardBindFragment bindFragment = null;
  private AccountManagementFragment accountManagementFragment = null;
  private StandardBindFragment.StandardBindFragmentListener bindFragmentListener =
      new StandardBindFragment.StandardBindFragmentListener() {
        @Override
        public void onBack() {
          if (bindFragment != null) {
            getSupportFragmentManager().beginTransaction()
                .remove(bindFragment)
                .commit();
          }
        }

        @Override
        public void onBind() {
          if (bindFragment != null) {
            getSupportFragmentManager().beginTransaction()
                .remove(bindFragment)
                .commit();
          }
          if (accountManagementFragment != null) {
            accountManagementFragment.update();
          }
        }
      };
  private AccountManagementFragment.AccountManagementFragmentListener accountManagementListener =
      new AccountManagementFragment.AccountManagementFragmentListener() {
        @Override
        public void onBack() {
          finish();
        }

        @Override
        public void onBindPhone() {
          if (bindFragment != null) {
            getSupportFragmentManager().beginTransaction()
                .remove(bindFragment)
                .commit();
          }

          bindFragment = new StandardBindFragment();
          bindFragment.setListener(bindFragmentListener);
          bindFragment.setType(ArchivesService.LoginFlag.PHONE_FLAG);
          getSupportFragmentManager().beginTransaction()
              .add(R.id.fragments_container, bindFragment)
              .commit();
        }

        @Override
        public void onBindMail() {
          if (bindFragment != null) {
            getSupportFragmentManager().beginTransaction()
                .remove(bindFragment)
                .commit();
          }

          bindFragment = new StandardBindFragment();
          bindFragment.setListener(bindFragmentListener);
          bindFragment.setType(ArchivesService.LoginFlag.MAIL_FLAG);
          getSupportFragmentManager().beginTransaction()
              .add(R.id.fragments_container, bindFragment)
              .commit();
        }

        @Override
        public void onResetPassword() {

        }
      };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.common_activity_container);

    accountManagementFragment = new AccountManagementFragment();
    accountManagementFragment.setListener(accountManagementListener);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, accountManagementFragment)
        .commit();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    KharazimApplication.getThirdPlatform().onActivityResult(requestCode, resultCode, data);
  }

}
