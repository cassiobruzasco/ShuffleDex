package com.cnb.projects.shuffledex.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.utils.ApplicationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Cássio on 13-Dec-15.
 */
public class UpdatePokemon extends AsyncTask<Void, Void, Void> {

    private InputStream inputStream;
    private BufferedReader reader;
    private String line = "";
    private static final String CHAR_SET = "utf-8";

    public UpdatePokemon() {
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            inputStream = ApplicationData.getAppContext().getResources().openRawResource(R.raw.pokemonlist3);
            reader = new BufferedReader(new InputStreamReader(inputStream, CHAR_SET));

            while ((line = reader.readLine()) != null){
                String[] sequence = line.split(",");

                Pokemon pokemon = new Pokemon();
                pokemon.setPokemonID(Integer.valueOf(sequence[0]));
                pokemon.setPokemonName(sequence[1]);
                pokemon.setElement(sequence[2]);
                pokemon.setStage(sequence[3]);
                pokemon.setBasePower(Integer.valueOf(sequence[4]));
                pokemon.setSkill(sequence[5]);
                pokemon.setCaptureRate(sequence[6]);
                pokemon.setImageName(sequence[7]);
                pokemon.save();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
