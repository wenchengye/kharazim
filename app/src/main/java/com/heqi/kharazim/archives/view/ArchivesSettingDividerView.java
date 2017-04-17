package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/4/17.
 */

public class ArchivesSettingDividerView extends View {
  public ArchivesSettingDividerView(Context context) {
    super(context);
  }

  public ArchivesSettingDividerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesSettingDividerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesSettingDividerView newInstance(ViewGroup parent) {
    return (ArchivesSettingDividerView) ViewUtils.newInstance(parent,
        R.layout.archives_setting_divider_card);
  }


}
