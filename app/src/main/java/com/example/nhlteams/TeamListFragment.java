package com.example.nhlteams;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mTeamRecyclerView;
    private TeamAdapter mAdapter;
    private boolean mSubtitleVisible;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onTeamSelected(Team team);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        mTeamRecyclerView = (RecyclerView) view
                .findViewById(R.id.team_recycler_view);
        mTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_team_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_team:
                Team team = new Team();
                TeamLab.get(getActivity()).addTeam(team);
                updateUI();
                mCallbacks.onTeamSelected(team);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        TeamLab teamLab = TeamLab.get(getActivity());
        int teamCount = teamLab.getTeams().size();
        String subtitle = getString(R.string.subtitle_format, teamCount);

        if(!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        TeamLab teamLab = TeamLab.get(getActivity());
        List<Team> teams = teamLab.getTeams();

        if (mAdapter == null){
            mAdapter = new TeamAdapter(teams);
            mTeamRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class TeamHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Team mTeam;
        private TextView mNameTextView;
        private TextView mCaptainTextView;
        private TextView mRecordTextView;
        private ImageView mSolvedImageView;

        public TeamHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_team, parent, false));
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.team_name);
            mCaptainTextView = (TextView) itemView.findViewById(R.id.team_captain);
            mRecordTextView = (TextView) itemView.findViewById(R.id.team_record);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.team_icon);
        }

        public void bind(Team team) {
            mTeam = team;
            mNameTextView.setText(mTeam.getName());
            mCaptainTextView.setText(mTeam.getCaptain());
            mRecordTextView.setText(mTeam.getRecord());
            mSolvedImageView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onTeamSelected(mTeam);
        }
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamHolder> {
        private List<Team> mTeams;

        public TeamAdapter(List<Team> teams) {
            mTeams = teams;
        }

        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TeamHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TeamHolder holder, int position){
            Team team = mTeams.get(position);
            holder.bind(team);
        }

        @Override
        public int getItemCount() {
            return mTeams.size();
        }
    }
}
