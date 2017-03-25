package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2016/11/25.
 */

public class ExplorePlanDetailCourseListView extends RelativeLayout {

  private static int COURSE_VIEW_INDEX_NONE = -1;

  private TextView previewTitleTv;
  private TextView explainTitleTv;
  private LinearLayout coursesListLayout;
  private LinearLayout explainListLayout;
  private ExploreActionImageList actionImageList;

  private PlanDetailInfo planDetailInfo;
  private PlanLiteInfo planLiteInfo;
  private int currentOpenIndex = COURSE_VIEW_INDEX_NONE;

  public ExplorePlanDetailCourseListView(Context context) {
    super(context);
  }

  public ExplorePlanDetailCourseListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExplorePlanDetailCourseListView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExplorePlanDetailCourseListView newInstance(ViewGroup parent) {
    return (ExplorePlanDetailCourseListView) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_courses_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    previewTitleTv = (TextView) findViewById(R.id.courses_preview_title_tv);
    explainTitleTv = (TextView) findViewById(R.id.courses_explain_title_tv);
    coursesListLayout = (LinearLayout) findViewById(R.id.courses_list_layout);
    explainListLayout = (LinearLayout) findViewById(R.id.explain_list_layout);

    previewTitleTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        enablePreview();
      }
    });

    explainTitleTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        enableExplain();
      }
    });

    enablePreview();
  }

  public void setData(PlanDetailInfo detail, PlanLiteInfo lite, boolean showFinish) {

    if (detail != null && detail != this.planDetailInfo) {
      this.planDetailInfo = detail;

      if (this.coursesListLayout != null) {
        this.coursesListLayout.removeAllViews();

        for (int i = 0; i < this.planDetailInfo.getData_info().size(); ++i) {
          ExplorePlanDetailCourseView view =
              ExplorePlanDetailCourseView.newInstance(this.coursesListLayout);
          view.setData(this.planDetailInfo.getData_info().get(i), i, showFinish);
          final int indexRef = i;
          view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              onClickCourseView(indexRef);
            }
          });
          this.coursesListLayout.addView(view);
        }
      }
    }

    if (lite != null && lite != this.planLiteInfo) {
      this.planLiteInfo = lite;

      if (this.explainListLayout != null) {
        this.explainListLayout.removeAllViews();

        if (this.planLiteInfo.getPlandeclist() != null) {
          for (int i = 0; i < this.planLiteInfo.getPlandeclist().size(); ++i) {
            ExplorePlanDetailExplainView view =
                ExplorePlanDetailExplainView.newInstance(this.explainListLayout);
            view.setData(this.planLiteInfo.getPlandeclist().get(i));
          }
        }
      }
    }
  }

  private void onClickCourseView(int index) {
    if (index == currentOpenIndex) {
      closeCurrentOpen();
    } else {
      setCurrentOpen(index);
    }
  }

  private void closeCurrentOpen() {
    if (currentOpenIndex == COURSE_VIEW_INDEX_NONE) return;

    if (coursesListLayout == null) return;

    coursesListLayout.removeView(actionImageList);
    currentOpenIndex = COURSE_VIEW_INDEX_NONE;
  }

  private void setCurrentOpen(int index) {
    if (currentOpenIndex == index || index < 0
        || index >= planDetailInfo.getData_info().size()) {
      return;
    }

    if (coursesListLayout == null) return;

    closeCurrentOpen();

    if (actionImageList == null) {
      actionImageList = ExploreActionImageList.newInstance(this);
    }

    coursesListLayout.addView(actionImageList, index + 1);
    coursesListLayout.requestLayout();
    actionImageList.setData(
        planDetailInfo.getData_info().get(index).getPlanDailyActDtoList());

    currentOpenIndex = index;
  }


  public void enablePreview() {
    if (coursesListLayout != null) {
      coursesListLayout.setVisibility(View.VISIBLE);
    }

    if (explainListLayout != null) {
      explainListLayout.setVisibility(View.GONE);
    }

    if (previewTitleTv != null) {
      setTitleViewEnable(previewTitleTv);
    }

    if (explainTitleTv != null) {
      setTitleViewDisable(explainTitleTv);
    }
  }

  public void enableExplain() {
    if (coursesListLayout != null) {
      coursesListLayout.setVisibility(View.GONE);
    }

    if (explainListLayout != null) {
      explainListLayout.setVisibility(View.VISIBLE);
    }

    if (previewTitleTv != null) {
      setTitleViewDisable(previewTitleTv);
    }

    if (explainTitleTv != null) {
      setTitleViewEnable(explainTitleTv);
    }
  }

  private void setTitleViewEnable(@NonNull TextView textView) {
    textView.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
    textView.setBackgroundColor(getContext().getResources().getColor(R.color.bg_color_white));
    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
        R.drawable.icon_explore_courses_title_indicator);
  }

  private void setTitleViewDisable(@NonNull TextView textView) {
    textView.setTextColor(getContext().getResources().getColor(R.color.text_color_gray_light));
    textView.setBackgroundColor(getContext().getResources().getColor(R.color.bg_color_gray));
    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
  }
}
