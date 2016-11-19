package com.heqi.kharazim.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsListView;


import com.heqi.base.utils.SystemUtil;
import com.heqi.image.view.AsyncImageView;


/**
 * Some useful methods for views.
 * 
 * @author liuchunyu@wandoujia.com (Chunyu Liu)
 */
public class ViewUtils {

  private static final int MAX_SMOOTH_SCROLL_POSITION = 5;
  private static final String REDIRECT_URL_PREFIX = "url=";
  private static final String ELLIPSIS = "...";
  private static final int TOAST_LENGTH = 25;
  private static final long TOAST_TIME = 500L;

  private ViewUtils() {}

  /**
   * Changes the size of a view.
   * 
   * @param view the view to change size
   * @param width the new width
   * @param height the new height
   */
  public static void setViewSize(View view, int width, int height) {
    LayoutParams layoutParams = view.getLayoutParams();
    layoutParams.width = width;
    layoutParams.height = height;
    view.setLayoutParams(layoutParams);
  }

  /**
   * Resumes asynchronous loading of all {@link AsyncImageView}s in activity.
   * 
   * @param activity activity
   */
  public static void resumeAsyncImagesLoading(Activity activity) {
    View rootView = activity.findViewById(android.R.id.content);
    processAsyncImagesLoadingInternal(rootView, true);
  }

  /**
   * Pauses asynchronous loading of all {@link AsyncImageView}s in activity.
   * 
   * @param activity activity
   */
  public static void pauseAsyncImagesLoading(Activity activity) {
    View rootView = activity.findViewById(android.R.id.content);
    processAsyncImagesLoadingInternal(rootView, false);
  }

  /**
   * Resumes asynchronous loading of all {@link AsyncImageView}s in fragment.
   * 
   * @param fragment fragment
   */
  public static void resumeAsyncImagesLoading(Fragment fragment) {
    View rootView = fragment.getView();
    processAsyncImagesLoadingInternal(rootView, true);
  }

  /**
   * Pauses asynchronous loading of all {@link AsyncImageView}s in fragment.
   * 
   * @param fragment fragment
   */
  public static void pauseAsyncImagesLoading(Fragment fragment) {
    View rootView = fragment.getView();
    processAsyncImagesLoadingInternal(rootView, false);
  }

  private static void processAsyncImagesLoadingInternal(View view, boolean pause) {
    if (view instanceof AsyncImageView) {
      AsyncImageView imageView = (AsyncImageView) view;
      if (pause && imageView.getStatus() != Status.FINISHED) {
        imageView.resumeLoading();
      } else if (imageView.getStatus() != Status.FINISHED) {
        imageView.pauseLoading();
      }
    } else if (view instanceof ViewGroup) {
      ViewGroup parent = (ViewGroup) view;
      for (int i = 0; i < parent.getChildCount(); ++i) {
        View child = parent.getChildAt(i);
        processAsyncImagesLoadingInternal(child, pause);
      }
    }
  }

  /**
   * Sets the background of view.
   * 
   * @param view view
   * @param background background drawable
   */
  @SuppressWarnings("deprecation")
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static void setBackground(View view, Drawable background) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      view.setBackground(background);
    } else {
      view.setBackgroundDrawable(background);
    }
  }

  /**
   * Creates a view.
   * 
   * @param parent parent view
   * @param resId resource id
   * @return view
   */
  public static View newInstance(ViewGroup parent, int resId) {
    return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
  }

  /**
   * Creates a view.
   * 
   * @param context context
   * @param resId resource id
   * @return view
   */
  public static View newInstance(Context context, int resId) {
    return LayoutInflater.from(context).inflate(resId, null);
  }

  /**
   * get a String of which with a Drawable in front.
   * 
   * @param textSize the size of text
   * @param text the content of text
   * @param drawable the drawable you want to put in front of text
   * @return String with drawable
   */
  public static CharSequence getDrawableTextSpan(final int textSize,
      String text, final Drawable drawable) {
    SpannableStringBuilder sb = new SpannableStringBuilder(" ");
    sb.append(text);
    DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan() {
      @Override
      public Drawable getDrawable() {
        float height = textSize;
        float width =
            drawable.getIntrinsicWidth() * height / drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, (int) width, (int) height);
        return drawable;
      }
    };
    sb.setSpan(drawableSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    return sb;
  }

  public static boolean isViewAttachedToDecorView(View view) {
    if (!(view.getContext() instanceof Activity)) {
      return true;
    }
    View decorView = ((Activity) view.getContext()).getWindow().getDecorView();
    if (view == decorView) {
      return true;
    }
    if (view.getWindowToken() != null && view.getWindowToken() != decorView.getWindowToken()) {
      // The view is not in the same window with activity. It's probably in a Dialog.
      return true;
    }
    ViewParent parent = view.getParent();
    while (parent != null) {
      if (parent == decorView) {
        return true;
      }
      parent = parent.getParent();
    }
    return false;
  }

  /**
   * Scroll the specific AbsListView to top.
   * 
   * @param listView the specific list view
   * @return true if scrollToTop is handled, false otherwise
   */
  @TargetApi(Build.VERSION_CODES.FROYO)
  public static boolean scrollToTop(AbsListView listView) {
    if (listView.getFirstVisiblePosition() == 0) {
      View firstChild = listView.getChildAt(0);
      if (firstChild == null) {
        return false;
      }
      if (firstChild.getTop() == listView.getPaddingTop()) {
        return false;
      }
    }
    if (SystemUtil.aboveApiLevel(Build.VERSION_CODES.FROYO)) {
      // This is used to stop the previous scroll runnable
      listView.smoothScrollBy(0, 0);
      if (listView.getFirstVisiblePosition() <= MAX_SMOOTH_SCROLL_POSITION) {
        listView.smoothScrollToPosition(0);
      } else {
        listView.setSelection(0);
      }
    } else {
      listView.setSelection(0);
    }
    return true;
  }

//  /**
//   * Find the current child view for the specific ViewPager, which means the whole child view is
//   * in the visible window.
//   *
//   * @param viewPager the specific ViewPager
//   * @return the current child view
//   */
//  public static View findCurrentChildView(ViewPager viewPager) {
//    PagerAdapter adapter = viewPager.getAdapter();
//    if (adapter != null && adapter instanceof TabFragmentPagerAdapter) {
//      Fragment f = ((TabFragmentPagerAdapter) adapter).getCurrentFragment();
//      return f == null ? null : f.getView();
//    } else {
//      int leftEdge = viewPager.getScrollX();
//      int rightEdge = leftEdge + viewPager.getWidth();
//      for (int i = 0; i < viewPager.getChildCount(); ++i) {
//        View child = viewPager.getChildAt(i);
//        // visible
//        int childLeft = child.getLeft();
//        int childRight = child.getRight();
//        if (childLeft >= leftEdge && childRight <= rightEdge) {
//          return child;
//        }
//      }
//      return null;
//    }
//  }
//
//  /**
//   *
//   * @param fragment
//   * @return given fragment is child of {@link TabHostFragment}
//   */
//  public static boolean fragmentIsInCommonViewPager(Fragment fragment) {
//    if (fragment.getActivity() != null && !fragment.getActivity().isFinishing()) {
//      Fragment parentFragment = fragment.getParentFragment();
//      return parentFragment instanceof TabHostFragment;
//    }
//    return false;
//  }
//
//  /**
//   * ViewPager's current page contains the given view
//   *
//   * @param v
//   * @return null: not in view pager
//   *         true: view in ViewPager's current page
//   *         true: view not in ViewPager's current page
//   */
//  public static Boolean isViewInViewPagerCurrentPage(View v) {
//    ViewParent viewParent = v.getParent();
//    while (viewParent != null && viewParent instanceof ViewGroup) {
//      if (viewParent instanceof ViewPager) {
//        return findCurrentChildView((ViewPager) viewParent) == v;
//      }
//      v = (View) viewParent;
//      viewParent = v.getParent();
//    }
//    return null;
//  }

}
