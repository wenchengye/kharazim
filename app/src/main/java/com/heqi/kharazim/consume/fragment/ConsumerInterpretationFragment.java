package com.heqi.kharazim.consume.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.http.request.AcupointDetailRequest;
import com.heqi.kharazim.explore.model.AcupointDetailInfo;
import com.heqi.kharazim.explore.model.AcupointQueryResult;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.ui.fragment.async.NetworkAsyncLoadFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2017/4/1.
 */

public class ConsumerInterpretationFragment extends NetworkAsyncLoadFragment<AcupointQueryResult> {

  private View exitBtn;
  private AsyncImageView interpretationIv;
  private TextView interpretationTv;
  private VideoView interpretationVv;
  private TextView indexTv;
  private View forwardBtn;
  private View backwardBtn;

  private List<PlanDetailInfo.PlanActionInfo> dataList;
  private int index;

  private ConsumerInterpretationFragmentListener listener;

  private static String generateInterperataionText(AcupointDetailInfo info) {
    StringBuilder sb = new StringBuilder();

    if (info != null) {
      String endl = KharazimApplication.getAppContext().getString(R.string.endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_name))
          .append(info.getAcupointname()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_position))
          .append(info.getPosition()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_bodypart))
          .append(info.getBodyparts()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_meridian))
          .append(info.getMeridian()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_technique))
          .append(info.getTechnique()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_operation))
          .append(info.getOperation()).append(endl).append(endl);

      sb.append(KharazimApplication.getAppContext().getString(
          R.string.consumer_interpretation_label_acupoint_function))
          .append(info.getFunction()).append(endl).append(endl);
    }

    return sb.toString();
  }

  public void setData(List<PlanDetailInfo.PlanActionInfo> data, int index) {
    this.dataList = data;
    this.index = index;

    requestLoad();
    invalidateIndexView();
    invalidateVideoView();
  }

  public void forward() {
    if (this.dataList != null && index + 1 >= 0 && index + 1 < this.dataList.size()) {
      this.index++;

      requestLoad();
      invalidateIndexView();
      invalidateVideoView();
    }
  }

  public void backward() {
    if (this.dataList != null && index - 1 >= 0 && index - 1 < this.dataList.size()) {
      this.index--;

      requestLoad();
      invalidateIndexView();
      invalidateVideoView();
    }
  }

  public void release() {
    if (this.interpretationVv != null) {
      interpretationVv.stopPlayback();
    }
  }

  private void invalidateIndexView() {
    if (this.indexTv != null) {
      this.indexTv.setText(String.format(
          KharazimApplication.getAppContext().getString(
              R.string.explore_circle_progress_text_format),
          String.valueOf(this.index + 1),
          String.valueOf(this.dataList != null ? this.dataList.size() : 0)));
    }
  }

  private void invalidateVideoView() {
    if (this.interpretationVv != null) {
      this.interpretationVv.stopPlayback();

      String source = null;
      if (this.dataList != null && index >= 0 && index < this.dataList.size()
          && this.dataList.get(index) != null) {
        source = this.dataList.get(index).getActvediofile();
      }

      if (!TextUtils.isEmpty(source)) {
        this.interpretationVv.setVideoURI(Uri.parse(source));
        this.interpretationVv.start();
      }
    }
  }

  public void setListener(ConsumerInterpretationFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {

    initViews(contentView);
    initListeners();
  }

  private void initViews(View contentView) {

    if (contentView != null) {

      exitBtn = contentView.findViewById(R.id.consumer_interpretation_exit_btn);
      interpretationIv = (AsyncImageView) contentView.findViewById(R.id.consumer_interpretation_iv);
      interpretationTv = (TextView)
          contentView.findViewById(R.id.consumer_interpretation_content_tv);
      interpretationVv = (VideoView) contentView.findViewById(R.id.consumer_interpretation_vv);
      indexTv = (TextView) contentView.findViewById(R.id.consumer_interpretation_index_tv);
      forwardBtn = contentView.findViewById(R.id.consumer_interpretation_forward_btn);
      backwardBtn = contentView.findViewById(R.id.consumer_interpretation_backward_btn);

      contentView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
      });

    }

    invalidateIndexView();
    invalidateVideoView();
  }

  private void initListeners() {
    if (exitBtn != null) {
      exitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onExitPressed();
          }
        }
      });
    }

    if (forwardBtn != null) {
      forwardBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          forward();
        }
      });
    }

    if (backwardBtn != null) {
      backwardBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          backward();
        }
      });
    }
  }

  @Override
  protected void applyData(AcupointQueryResult data) {

    AcupointDetailInfo info = data != null ? data.getData_info() : null;

    if (info != null) {
      if (this.interpretationIv != null) {
        this.interpretationIv.loadNetworkImage(info.getPositionimg(),
            R.drawable.icon_consume_interpretation_image_place_holder);

        this.interpretationTv.setText(generateInterperataionText(info));
      }
    }

  }

  @Override
  protected Request<AcupointQueryResult> newRequest(Response.Listener<AcupointQueryResult> listener,
                                                    Response.ErrorListener errorListener) {

    if (dataList != null && index >= 0 && index < dataList.size() && dataList.get(index) != null) {
      AcupointDetailRequest request = new AcupointDetailRequest(listener, errorListener);
      request.setId(dataList.get(index).getAcupointid());
      return request;
    }
    return null;
  }

  @Override
  protected void showLoadingTipsView() {
  }

  @Override
  protected void hideLoadingTipsView() {
  }

  @Override
  protected void showFetchFailedTipsView(ExecutionException e) {
  }

  @Override
  protected void hideFetchFailedTipsView() {
  }

  @Override
  protected void onNoFetchResult() {
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.consumer_interpretation_fragment_layout;
  }

  public interface ConsumerInterpretationFragmentListener {
    void onExitPressed();
  }
}
