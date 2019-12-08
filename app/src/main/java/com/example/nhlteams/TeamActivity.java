package com.example.nhlteams;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TeamActivity extends SingleFragmentActivity {

    private static final String EXTRA_TEAM_ID =
            "com.example.nhlteams.team_id";

    public static Intent newIntent(Context packageContext, UUID teamID) {
        Intent intent = new Intent(packageContext, TeamActivity.class);
        intent.putExtra(EXTRA_TEAM_ID, teamID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID teamId = (UUID) getIntent().getSerializableExtra(EXTRA_TEAM_ID);
        return TeamFragment.newInstance(teamId);
    }
}
