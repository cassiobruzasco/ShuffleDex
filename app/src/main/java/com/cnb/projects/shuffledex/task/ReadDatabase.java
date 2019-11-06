package com.cnb.projects.shuffledex.task;

import android.os.AsyncTask;

import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.List;

/**
 * Created by vntcanb on 30/09/2015.
 */
public class ReadDatabase extends AsyncTask<String, Void, List<Pokemon>> {

    private final Callback mCallback;


    public ReadDatabase(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        Utils.isLoading = true;
        super.onPreExecute();
    }

    @Override
    protected List<Pokemon> doInBackground(String... params) {
        List<Pokemon> list;

        list = Pokemon.selectAll();

        if ((params != null) && (params.length >= 1)) {
            final String optionId = params[0];

            String pokename = null;
            String sortTypes = null;
            String order = null;


            if (params.length >= 2) {
                pokename = params[1];
                sortTypes = params[1];
            }

            switch (optionId) {
                case Utils.MAIN_STAGES_DRAWER:
                    list = Pokemon.selectMainStage();
                    break;
                case Utils.EXPERT_STAGES_DRAWER:
                    list = Pokemon.selectExpertStages();
                    break;
                case Utils.SPECIAL_STAGES_DRAWER:
                    list = Pokemon.selectSpecialStage();
                    break;
                case Utils.CAPTURED_DRAWER:
                    list = Pokemon.selectCaptured();
                    break;
                case Utils.SEARCH_ALL_SKILLS:
                    if (pokename != null) {
                        list = Skill.selectSkillFromSearch(pokename);
                    }
                    break;
                case Utils.SEARCH_ALL_POKE_NAME:
                    if (pokename != null) {
                        list = Pokemon.selectNameFromSearch(pokename);
                    }
                    break;
                case Utils.SEARCH_MAIN_POKE_NAME:
                    if (pokename != null) {
                        list = Pokemon.selectMainFromSearch(pokename);
                    }
                    break;
                case Utils.SEARCH_EXPERT_POKE_NAME:
                    if (pokename != null) {
                        list = Pokemon.selectExpertFromSearch(pokename);
                    }
                    break;
                case Utils.SEARCH_SPECIAL_POKE_NAME:
                    if (pokename != null) {
                        list = Pokemon.selectSpecialFromSearch(pokename);
                    }
                    break;
                case Utils.SEARCH_CAPTURED:
                    if (pokename != null) {
                        list = Pokemon.selectCapturedFromSearch(pokename);
                    }
                    break;
                case Utils.SORT_BY_POWER:
                    list = Pokemon.selectAllPokemonSortPower();
                    break;
                case Utils.SORT_BY:
                    list = Pokemon.sortPokemon(sortTypes);
                    break;
                default:
                case Utils.ALL_POKENONS_DRAWER:
                    list = Pokemon.selectAll();
                    break;
            }
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<Pokemon> models) {
        Utils.isLoading = false;
        super.onPostExecute(models);
        if (mCallback != null) {
            mCallback.onListLoaded(models);
        }
    }

    public interface Callback {
        void onListLoaded(List<Pokemon> list);
    }
}
