package com.heqi.kharazim.activity;

import com.heqi.kharazim.R;

/**
 * Created by overspark on 2017/3/21.
 */

public enum VerticalType {

  Explore {
    @Override
    public int getIconRes() {
      return R.drawable.icon_home_navigate_explore;
    }

    @Override
    public int getIconSelectedRes() {
      return R.drawable.icon_home_navigate_explore_pressed;
    }
  },

  Archives {
    public int getIconRes() {
      return R.drawable.icon_home_navigate_archives;
    }

    @Override
    public int getIconSelectedRes() {
      return R.drawable.icon_home_navigate_archives_pressed;
    }
  },

  Settings {
    @Override
    public int getIconRes() {
      return R.drawable.icon_home_navigate_settings;
    }

    @Override
    public int getIconSelectedRes() {
      return R.drawable.icon_home_navigate_settings_pressed;
    }
  };

  public abstract int getIconRes();

  public abstract int getIconSelectedRes();
}
