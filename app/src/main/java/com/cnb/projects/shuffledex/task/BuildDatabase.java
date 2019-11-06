package com.cnb.projects.shuffledex.task;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.ApplicationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Cássio on 19-Dec-15.
 */
public class BuildDatabase {

    public static InputStream inputStream;
    public static BufferedReader reader;
    public static String line = "";
    private static final String CHAR_SET = "utf-8";

    public static void readSkills() {
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
    }

    public static void readPokemon() {
        /**
         * Import raw/pokemonlist.csv and fill Pokemon Table;
         */
        try {
            inputStream = ApplicationData.getAppContext().getResources().openRawResource(R.raw.pokemonlist);
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
    }

}
