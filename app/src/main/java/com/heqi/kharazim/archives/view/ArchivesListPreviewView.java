package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/19.
 */

public class ArchivesListPreviewView extends RelativeLayout {

  public ArchivesListPreviewView(Context context) {
    super(context);
  }

  public ArchivesListPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ArchivesListPreviewView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public static ArchivesListPreviewView newInstance(ViewGroup viewGroup) {
    return (ArchivesListPreviewView) ViewUtils.newInstance(viewGroup,
        R.layout.archives_preview_view);
  }

}
