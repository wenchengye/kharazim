package com.heqi.kharazim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.heqi.kharazim.archives.fragment.ArchivesFragment;
import com.heqi.kharazim.archives.fragment.SettingsFragment;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.explore.fragment.PlanListFragment;
import com.heqi.kharazim.view.HomeNavigateView;

/**
 * Created by overspark on 2017/3/20.
 */

public class HomeActivity extends FragmentActivity {

  private HomeNavigateView navigateView;
  private HomeNavigateView.OnVerticalTypeSelectedListener naviListener =
      new HomeNavigateView.OnVerticalTypeSelectedListener() {
    @Override
    public void onVerticalTypeSelected(VerticalType type) {
      navigateToVertical(type);
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    handleIntent(getIntent());
  }

  private void initView() {

  }

  private void handleIntent(Intent intent) {
    if (intent == null) return;

    VerticalType type;
    if (!intent.hasExtra(intent.getStringExtra(Intents.EXTRA_VERTICAL_TYPE))) {
      type = VerticalType.Explore;
    } else {
      try {
        type = VerticalType.valueOf(intent.getStringExtra(Intents.EXTRA_VERTICAL_TYPE));
      } catch (IllegalArgumentException e) {
        type = VerticalType.Explore;
      }
    }
  }

  private void navigateToVertical(VerticalType type) {
    if (type != null) {
      navigateView.selectVerticalType(type);
    }
  }

  private void navigateToVerticalInternal(VerticalType type) {
    if (type == null) return;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    Fragment currentFragment = fm.findFragmentById(android.R.id.content);
    switch (type) {
      case Explore:
        if (currentFragment instanceof PlanListFragment) {
          return;
        } else {
          PlanListFragment planListFragment = new PlanListFragment();
          ft.replace(android.R.id.content, planListFragment);
        }
        break;
      case Archives:
        if (currentFragment instanceof ArchivesFragment) {
          return;
        } else {
          ArchivesFragment archivesFragment = new ArchivesFragment();
          ft.replace(android.R.id.content, archivesFragment);
        }
        break;
      case Settings:
        if (currentFragment instanceof SettingsFragment) {
          return;
        } else {
          SettingsFragment settingsFragment = new SettingsFragment();
          ft.replace(android.R.id.content, settingsFragment);
        }
        break;
      default:
        break;
    }
    ft.commitAllowingStateLoss();
  }
}
