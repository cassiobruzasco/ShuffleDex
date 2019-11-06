package com.cnb.projects.shuffledex.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.ApplicationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Cássio on 13-Dec-15.
 */
public class UpdateSkill extends AsyncTask<Void, Void, Void> {

    private InputStream inputStream;
    private BufferedReader reader;
    private String line = "";
    private static final String CHAR_SET = "utf-8";

    public UpdateSkill() {}

    @Override
    protected Void doInBackground(Void... params) {
        /**
         * Import raw/skills.csv and fill Skill Table
         */
        try {
            inputStream = ApplicationData.getAppContext().getResources().openRawResource(R.raw.skills);
            reader = new BufferedReader(new InputStreamReader(inputStream, CHAR_SET));

            while ((line = reader.readLine()) != null) {
                String[] sequence = line.split(",");

                Skill skill = new Skill();
                skill.setSkill(sequence[0]);
                skill.setDescription(sequence[1]);
                skill.setActivationMatch3(sequence[2]);
                skill.setActivationMatch4(sequence[3]);
                skill.setActivationMatch5(sequence[4]);
                skill.setMegaCandy(Integer.valueOf(sequence[5]));
                skill.setDescription2(sequence[6]);
                skill.save();

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
