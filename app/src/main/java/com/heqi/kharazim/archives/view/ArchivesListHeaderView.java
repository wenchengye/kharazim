package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/19.
 */

public class ArchivesListHeaderView extends TextView {

  public ArchivesListHeaderView(Context context) {
    super(context);
  }

  public ArchivesListHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesListHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesListHeaderView newInstance(ViewGroup parent) {
    return (ArchivesListHeaderView) ViewUtils.newInstance(parent,
        R.layout.archives_list_header_card);
  }

}
