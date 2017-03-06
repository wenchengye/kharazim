package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.consume.api.State;
import com.heqi.kharazim.explore.model.ActionDetailInfo;

/**
 * Created by overspark on 2017/3/3.
 */

public class ExploreConsumerViewImpl extends RelativeLayout implements ExploreConsumerView {

  private ImageView playBtn;
  private ImageView previousBtn;
  private ImageView nextBtn;
  private ImageView exitBtn;
  private TextView interpretationBtn;
  private ExploreCircleProgressView repeatProgress;
  private ExploreCircleProgressView durationProgress;

  private ExploreConsumerViewListener listener;

  private ActionDetailInfo action;
  private State state;

  public ExploreConsumerViewImpl(Context context) {
    super(context);
    init();
  }

  public ExploreConsumerViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ExploreConsumerViewImpl(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    playBtn =  (ImageView) findViewById(R.id.consumer_play_button);
    previousBtn = (ImageView) findViewById(R.id.consumer_backward_button);
    nextBtn = (ImageView) findViewById(R.id.consumer_forward_button);
    interpretationBtn = (TextView) findViewById(R.id.consumer_interpretation_button);
    repeatProgress = (ExploreCircleProgressView) findViewById(R.id.consumer_repeat_progress);
    durationProgress = (ExploreCircleProgressView) findViewById(R.id.consumer_duration_progress);

    initGeneralListeners();
  }

  private void initGeneralListeners() {
    if (exitBtn != null) {
      exitBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onExitPressed();
        }
      });
    }

    if (interpretationBtn != null) {
      interpretationBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onInterpretationPressed();
        }
      });
    }
  }

  private void initContentListeners() {
    if (playBtn != null) {
      playBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onPlayPressed();
        }
      });
    }

    if (previousBtn != null) {
      previousBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onPreviousPressed();
        }
      });
    }

    if (nextBtn != null) {
      nextBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onNextPressed();
        }
      });
    }

    if (repeatProgress != null) {
      repeatProgress.setUpperMinorText(
          getContext().getString(R.string.consumer_progress_upper_text));
      repeatProgress.setLowerMinorText(
          getContext().getString(R.string.consumer_progress_repeat_lower_text));
    }

    if (durationProgress != null) {
      durationProgress.setUpperMinorText(
          getContext().getString(R.string.consumer_progress_upper_text));
      durationProgress.setLowerMinorText(
          getContext().getString(R.string.consumer_progress_duration_lower_text));
    }
  }

  @Override
  public void initContent(ActionDetailInfo action, int progress, int duration, int repeatIndex,
      int repeatSum, State state) {
    setAction(action);
    setProgress(progress, duration);
    setRepeat(repeatIndex, repeatSum);
    setState(state);

    initContentListeners();
  }

  @Override
  public void setAction(ActionDetailInfo action) {
    // Do nothing
  }

  @Override
  public void setProgress(int progress, int duration) {
    if (this.durationProgress != null) {
      this.durationProgress.setProgress(progress);
      this.durationProgress.setMax(duration);
      this.durationProgress.setProgressText(String.valueOf(progress));
      this.durationProgress.setProgressText(String.valueOf(duration));
    }
  }

  @Override
  public void setRepeat(int repeatIndex, int repeatSum) {
    if (this.durationProgress != null) {
      this.durationProgress.setProgress(repeatIndex);
      this.durationProgress.setMax(repeatSum);
      this.durationProgress.setProgressText(String.valueOf(repeatIndex));
      this.durationProgress.setProgressText(String.valueOf(repeatSum));
    }
  }

  @Override
  public void setState(State state) {

  }

  @Override
  public void setExploreConsumerViewListener(ExploreConsumerViewListener listener) {
    this.listener = listener;
  }
}