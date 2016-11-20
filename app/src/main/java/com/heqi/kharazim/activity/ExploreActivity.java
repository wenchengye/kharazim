package com.heqi.kharazim.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.fragment.PlanListFragment;
import com.heqi.kharazim.explore.http.request.PlanListRequest;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.rpc.RpcHelper;

import java.util.List;


public class ExploreActivity extends FragmentActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    PlanListFragment planListFragment = new PlanListFragment();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, planListFragment)
        .commit();
  }
}
