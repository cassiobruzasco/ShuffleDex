package com.cnb.projects.shuffledex.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.activity.MainActivity;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.ApplicationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by vntcanb on 24/09/2015.
 */
public class FillDatabase extends AsyncTask<Void, Void, Void>{

    private ProgressDialog mDialog;

    public FillDatabase (Context context){
        mDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
            mDialog.setCancelable(false);
            mDialog.setMessage("Fetching data...");
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setProgress(0);
            mDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (!MainActivity.mHasPokemon){

            BuildDatabase.readPokemon();

            MainActivity.mHasPokemon = true;

        } else if (!MainActivity.mHasSkillUpdate){

            BuildDatabase.readSkills();

            MainActivity.mHasSkillUpdate = true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDialog.dismiss();
    }
}
