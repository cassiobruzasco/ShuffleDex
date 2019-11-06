package com.cnb.projects.shuffledex.activity.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.adapter.PokemonAdapter;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.task.CountDatabase;
import com.cnb.projects.shuffledex.task.ReadDatabase;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.List;

/**
 * Created by vntcanb on 04/11/2015.
 */
public class OverallDialog {

    TextView mFirstText;
    TextView mSecondText;
    TextView mCounterCaptured;
    TextView mCounterS;
    TextView mMissingS;
    LinearLayout mFirstLinear;
    LinearLayout mSecondLinear;
    ProgressBar mProgressBar;

    public void callAboutDialog(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.overall_information, null);

        mFirstLinear = (LinearLayout) content.findViewById(R.id.first_ll);
        mSecondLinear = (LinearLayout) content.findViewById(R.id.second_ll);
        mFirstText = (TextView) content.findViewById(R.id.captured_tv);
        mSecondText = (TextView) content.findViewById(R.id.s_grade_tv);
        mProgressBar = (ProgressBar) content.findViewById(R.id.progress_bar);
        mMissingS = (TextView) content.findViewById(R.id.counter_s_missing);

        mFirstLinear.setVisibility(View.GONE);
        mSecondLinear.setVisibility(View.GONE);
        mFirstText.setVisibility(View.GONE);
        mSecondText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        mCounterCaptured = (TextView) content.findViewById(R.id.counter_captured);
        mCounterS = (TextView) content.findViewById(R.id.counter_s_grade);

        new CountDatabase(new CountDatabase.CountDataListener() {
            @Override
            public void onCounterFinished(int counter) {
                mCounterCaptured.setText(String.valueOf(counter));
            }
        }).execute(0);

        new CountDatabase(new CountDatabase.CountDataListener() {
            @Override
            public void onCounterFinished(int counter) {
                mCounterS.setText(String.valueOf(counter));
            }
        }).execute(1);

        new CountDatabase(new CountDatabase.CountDataListener() {
            @Override
            public void onCounterFinished(int counter) {
                mFirstLinear.setVisibility(View.VISIBLE);
                mSecondLinear.setVisibility(View.VISIBLE);
                mFirstText.setVisibility(View.VISIBLE);
                mSecondText.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mMissingS.setText(String.valueOf(counter));
            }
        }).execute(2);


        new AlertDialogWrapper.Builder(context)
                .setView(content)
                .setPositiveButton("OK", null)
                .setTitle(R.string.overall_dialog)
                .show();
    }
}
