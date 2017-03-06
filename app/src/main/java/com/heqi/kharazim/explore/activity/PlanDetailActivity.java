package com.heqi.kharazim.explore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.explore.fragment.PlanDetailFragment;
import com.heqi.kharazim.explore.model.PlanLiteInfo;

/**
 * Created by overspark on 2016/12/21.
 */

public class PlanDetailActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    PlanDetailFragment planListFragment = new PlanDetailFragment();
    planListFragment.setArguments(getIntent().getExtras());
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, planListFragment)
        .commit();
  }

  public static void launchActivity(Context context, PlanLiteInfo planLiteInfo) {
    Intent intent = new Intent(context, PlanDetailActivity.class);
    intent.putExtra(Intents.EXTRA_PLAN_LITE_INFO, planLiteInfo);
    context.startActivity(intent);
  }
}
