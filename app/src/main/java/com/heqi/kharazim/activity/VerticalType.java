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
  },

  Archives {
    public int getIconRes() {
      return R.drawable.icon_home_navigate_archives;
    }
  },

  Settings {
    @Override
    public int getIconRes() {
      return R.drawable.icon_home_navigate_settings;
    }
  };

  public abstract int getIconRes();
}
