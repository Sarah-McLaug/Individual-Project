package com.example.nhlteams;

import android.content.Intent;

import androidx.fragment.app.Fragment;

public class TeamListActivity extends SingleFragmentActivity
    implements TeamListFragment.Callbacks, TeamFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new TeamListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onTeamSelected(Team team) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = TeamPagerActivity.newIntent(this, team.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = TeamFragment.newInstance(team.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onTeamUpdated(Team team) {
        TeamListFragment listFragment = (TeamListFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
