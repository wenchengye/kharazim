package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/19.
 */

public class ArchivesListAddMoreView extends FrameLayout {

  public ArchivesListAddMoreView(Context context) {
    super(context);
  }

  public ArchivesListAddMoreView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesListAddMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesListAddMoreView newInstance(ViewGroup viewGroup) {
    return (ArchivesListAddMoreView) ViewUtils.newInstance(viewGroup,
        R.layout.archives_add_more_view);
  }

}
