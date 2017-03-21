package com.heqi.kharazim.activity;

/**
 * Created by overspark on 2017/3/21.
 */

public enum VerticalType {

  Explore {
    @Override
    public int getIconRes() {
      return 0;
    }
  },

  Archives {
    public int getIconRes() {
      return 0;
    }
  },

  Settings {
    @Override
    public int getIconRes() {
      return 0;
    }
  };

  public abstract int getIconRes();
}
