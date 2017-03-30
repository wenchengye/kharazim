package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;

/**
 * Created by overspark on 2017/3/6.
 */

public class ExploreCircleProgressView extends View {

  private static final int VALUE_DEFAULT_MIN = 0;
  private static final int VALUE_DEFAULT_MAX = 100;
  private static final float VALUE_DEFAULT_PROGRESS = 0.f;
  private static final float PROGRESS_START_ANGLE = -90.f;

  private static final float PROGRESS_THICKNESS_PROPORTION = 0.052f;
  private static final float MINOR_FONT_SIZE_PROPORTION = 0.113f;
  private static final float MAJOR_FONT_SIZE_PROPORTION = 0.199f;
  private static final float INTERVAL_1_PROPORTION = 0.153f;

  private String progressText;
  private String maxText;
  private String upperMinorText;
  private String lowerMinorText;
  private int min;
  private int max;
  private float progress;
  private String majorText;

  private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint majorTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint minorTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private RectF viewRectF = new RectF();

  private int size;
  private int progressThickness;
  private int minorFontSize;
  private int majorFontSize;
  private Point majorStartPoint = new Point();
  private Point upperMinorStartPoint = new Point();
  private Point lowerMinorStartPoint = new Point();

  public ExploreCircleProgressView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
  }

  private static int validate2PxInteger(float value) {
    return Math.max(Math.round(value), 1);
  }

  private static String combineProgressText(String progressText, String maxText) {
    return String.format(
        KharazimApplication.getAppContext().getString(R.string.explore_circle_progress_text_format),
        TextUtils.isEmpty(progressText) ? "" : progressText,
        TextUtils.isEmpty(maxText) ? "" : maxText);
  }

  private void initView(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(
        attrs,
        R.styleable.ExploreCircleProgressView,
        0, 0);

    try {
      progressText = typedArray.getString(R.styleable.ExploreCircleProgressView_progressText);
      maxText = typedArray.getString(R.styleable.ExploreCircleProgressView_maxText);
      upperMinorText = typedArray.getString(R.styleable.ExploreCircleProgressView_upperMinorText);
      lowerMinorText = typedArray.getString(R.styleable.ExploreCircleProgressView_lowerMinorText);
      min = typedArray.getInt(R.styleable.ExploreCircleProgressView_min, VALUE_DEFAULT_MIN);
      max = typedArray.getInt(R.styleable.ExploreCircleProgressView_max, VALUE_DEFAULT_MAX);
      progress = typedArray.getFloat(R.styleable.ExploreCircleProgressView_progress,
          VALUE_DEFAULT_PROGRESS);
      majorText = combineProgressText(progressText, maxText);

    } finally {
      typedArray.recycle();
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      backgroundPaint.setColor(context.getColor(R.color.text_color_gray_light));
    } else {
      backgroundPaint.setColor(context.getResources().getColor(R.color.text_color_gray_light));
    }
    backgroundPaint.setStyle(Paint.Style.STROKE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      foregroundPaint.setColor(context.getColor(R.color.kharazim_design_color));
    } else {
      foregroundPaint.setColor(context.getResources().getColor(R.color.kharazim_design_color));
    }
    foregroundPaint.setStyle(Paint.Style.STROKE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      majorTextPaint.setColor(context.getColor(R.color.kharazim_design_color));
    } else {
      majorTextPaint.setColor(context.getResources().getColor(R.color.kharazim_design_color));
    }
    majorTextPaint.setTextAlign(Paint.Align.LEFT);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      minorTextPaint.setColor(context.getColor(R.color.text_color_gray_light));
    } else {
      minorTextPaint.setColor(context.getResources().getColor(R.color.text_color_gray_light));
    }
    minorTextPaint.setTextAlign(Paint.Align.LEFT);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
    final int viewSize = Math.min(width, height);
    setMeasuredDimension(viewSize, viewSize);

    progressThickness = validate2PxInteger(viewSize * PROGRESS_THICKNESS_PROPORTION);
    minorFontSize = validate2PxInteger(viewSize * MINOR_FONT_SIZE_PROPORTION);
    majorFontSize = validate2PxInteger(viewSize * MAJOR_FONT_SIZE_PROPORTION);
    int interval1Size = validate2PxInteger(viewSize * INTERVAL_1_PROPORTION);
    this.size = viewSize;

    backgroundPaint.setStrokeWidth(progressThickness);
    foregroundPaint.setStrokeWidth(progressThickness);
    majorTextPaint.setTextSize(majorFontSize);
    minorTextPaint.setTextSize(minorFontSize);

    majorStartPoint.y = (size + majorFontSize) / 2;
    upperMinorStartPoint.y = progressThickness + interval1Size + minorFontSize;
    lowerMinorStartPoint.y = size - progressThickness - interval1Size;

    viewRectF.set(progressThickness / 2, progressThickness / 2,
        viewSize - progressThickness / 2, viewSize - progressThickness / 2);

    resetStartPointX(majorText, majorStartPoint, majorTextPaint);
    resetStartPointX(upperMinorText, upperMinorStartPoint, minorTextPaint);
    resetStartPointX(lowerMinorText, lowerMinorStartPoint, minorTextPaint);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawOval(viewRectF, backgroundPaint);
    float angle = 360 * (progress - min) / (max - min);
    canvas.drawArc(viewRectF, PROGRESS_START_ANGLE, angle, false, foregroundPaint);

    drawText(majorText, majorStartPoint, majorTextPaint, canvas);
    drawText(upperMinorText, upperMinorStartPoint, minorTextPaint, canvas);
    drawText(lowerMinorText, lowerMinorStartPoint, minorTextPaint, canvas);
  }

  private void resetStartPointX(String text, Point startPoint, Paint paint) {
    if (TextUtils.isEmpty(text)) return;

    float textWidth = paint.measureText(text);
    startPoint.x = Math.max(Math.round((this.size - textWidth) / 2), 0);
  }

  private void drawText(String text, Point startPoint, Paint paint, Canvas canvas) {
    if (TextUtils.isEmpty(text)) return;

    canvas.drawText(text, startPoint.x, startPoint.y, paint);
  }

  public void setMaxText(String maxText) {
    this.maxText = maxText;

    majorText = combineProgressText(progressText, maxText);
    resetStartPointX(majorText, majorStartPoint, majorTextPaint);
    invalidate();
  }

  public void setProgressText(String progressText) {
    this.progressText = progressText;

    majorText = combineProgressText(progressText, maxText);
    resetStartPointX(majorText, majorStartPoint, majorTextPaint);
    invalidate();
  }

  public void setUpperMinorText(String upperMinorText) {
    this.upperMinorText = upperMinorText;
    resetStartPointX(upperMinorText, upperMinorStartPoint, minorTextPaint);
    invalidate();
  }

  public void setLowerMinorText(String lowerMinorText) {
    this.lowerMinorText = lowerMinorText;
    resetStartPointX(lowerMinorText, lowerMinorStartPoint, minorTextPaint);
    invalidate();
  }

  public void setMin(int min) {
    this.min = min;
    invalidate();
  }

  public void setMax(int max) {
    this.max = max;
    invalidate();
  }

  public void setProgress(float progress) {
    this.progress = progress;
    invalidate();
  }
}
