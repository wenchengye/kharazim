package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.heqi.kharazim.config.Const;

/**
 * Created by overspark on 2017/4/13.
 */

public class ArchivesUserAimView extends RelativeLayout {

  public ArchivesUserAimView(Context context) {
    super(context);
  }

  public ArchivesUserAimView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesUserAimView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setData(Const.Aim aim) {

  }

  public Const.Aim getData() {
    return null;
  }

  public void setSelected(boolean selected) {

  }

  public boolean getSelected() {
    return false;
  }
}
