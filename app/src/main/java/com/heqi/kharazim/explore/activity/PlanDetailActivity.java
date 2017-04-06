package com.heqi.kharazim.explore.activity;

import android.os.Bundle;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.activity.PendingNavigateActivity;
import com.heqi.kharazim.explore.fragment.PlanDetailFragment;
import com.heqi.kharazim.explore.model.PlanLiteInfo;

/**
 * Created by overspark on 2016/12/21.
 */

public class PlanDetailActivity extends PendingNavigateActivity {

  PlanDetailFragment.PlanDetailFragmentListener planDetailFragmentListener =
      new PlanDetailFragment.PlanDetailFragmentListener() {
        @Override
        public void onAddPlan(final PlanLiteInfo planLiteInfo) {
          planLiteInfo.setMyplan(true);
          navigate(new Runnable() {
            @Override
            public void run() {
              NavigationManager.navigateToPlanDetail(KharazimApplication.getAppContext(),
                  planLiteInfo);
              finish();
            }
          });
        }

        @Override
        public void onConsumePlan(final String courseId, final String userPlanId) {
          navigate(new Runnable() {
            @Override
            public void run() {
              NavigationManager.navigateToConsume(PlanDetailActivity.this, courseId, userPlanId);
            }
          });
        }

        @Override
        public void onBack() {
          finish();
        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    PlanDetailFragment planListFragment = new PlanDetailFragment();
    planListFragment.setListener(planDetailFragmentListener);
    planListFragment.setArguments(getIntent().getExtras());
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, planListFragment)
        .commit();
  }
}
