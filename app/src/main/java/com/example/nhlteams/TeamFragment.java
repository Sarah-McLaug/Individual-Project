package com.example.nhlteams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TeamFragment extends Fragment {
    private static final String ARG_TEAM_ID = "team_id";

    private Team mTeam;
    private EditText mNameField;
    private EditText mCaptainField;
    private EditText mWinsField;
    private EditText mLossesField;
    private EditText mTiesField;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onTeamUpdated(Team team);
    }

    public static TeamFragment newInstance(UUID teamID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEAM_ID, teamID);

        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(args);
        return fragment;
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
        UUID teamId = (UUID) getArguments().getSerializable(ARG_TEAM_ID);
        mTeam = TeamLab.get(getActivity()).getTeam(teamId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_team, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_delete_team:
                UUID teamId = (UUID) getArguments().getSerializable(ARG_TEAM_ID);
                TeamLab teamLab = TeamLab.get(getActivity());
                mTeam = teamLab.getTeam(teamId);
                teamLab.deleteTeam(mTeam);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TeamLab.get(getActivity()).updateTeam(mTeam);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team, container, false);

        mNameField = (EditText) v.findViewById(R.id.team_name);
        mNameField.setText(mTeam.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mTeam.setName(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCaptainField = (EditText) v.findViewById(R.id.captain_name);
        mCaptainField.setText(mTeam.getCaptain());
        mCaptainField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mTeam.setCaptain(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWinsField = (EditText) v.findViewById(R.id.wins);
        mWinsField.setText(String.valueOf(mTeam.getWins()));
        mWinsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().isEmpty()) {
                    mTeam.setWins(Integer.parseInt(s.toString()));
                    mTeam.setRecord();
                    updateTeam();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLossesField = (EditText) v.findViewById(R.id.losses);
        mLossesField.setText(String.valueOf(mTeam.getLosses()));
        mLossesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().isEmpty()) {
                    mTeam.setLosses(Integer.parseInt(s.toString()));
                    mTeam.setRecord();
                    updateTeam();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTiesField = (EditText) v.findViewById(R.id.ties);
        mTiesField.setText(String.valueOf(mTeam.getTies()));
        mTiesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().isEmpty()) {
                    mTeam.setTies(Integer.parseInt(s.toString()));
                    mTeam.setRecord();
                    updateTeam();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    private void updateTeam() {
        TeamLab.get(getActivity()).updateTeam(mTeam);
        mCallbacks.onTeamUpdated(mTeam);
    }
}
