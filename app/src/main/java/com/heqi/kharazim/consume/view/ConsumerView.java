package com.heqi.kharazim.consume.view;

import com.heqi.kharazim.consume.core.api.State;
import com.heqi.kharazim.explore.model.ActionDetailInfo;

/**
 * Created by overspark on 2017/3/2.
 */

public interface ConsumerView {

  void initContent(ActionDetailInfo action, int progress, int duration, int repeatIndex,
                   int repeatSum, State state);

  void setAction(ActionDetailInfo action);

  void setProgress(int progress, int duration);

  void setRepeat(int repeatIndex, int repeatSum);

  void setState(State state);

  void showGuideView();

  void showNormalView();

  void setExploreConsumerViewListener(ExploreConsumerViewListener listener);

  interface ExploreConsumerViewListener {

    void onPreviousPressed();

    void onNextPressed();

    void onPlayPressed();

    void onInterpretationPressed();

    void onExitPressed();

    void onSkipGuidePressed();
  }
}
