package com.cnb.projects.shuffledex.task;

import android.os.AsyncTask;

import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.List;

/**
 * Created by vntcanb on 07/10/2015.
 */
public class ReadSkillDatabase extends AsyncTask<String, Void, List<Skill>> {

    private final Callback mCallback;

    public ReadSkillDatabase(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        Utils.isLoading = true;
        super.onPreExecute();
    }

    @Override
    protected List<Skill> doInBackground(String... params) {
        List<Skill> list;

        list = Skill.selectAll();

        if ((params != null) && (params.length >= 1)) {
            final String optionId = params[0];

            String skillName = null;


            if (params.length >= 2) {
                skillName = params[1];
            }

            switch (optionId) {
                case Utils.SEARCH_ALL_SKILLS:
                    list = Skill.selectSkillFromSearch(skillName);
                    break;
                case Utils.SEARCH_SKILL_FRAGMENT:
                    list = Skill.selectSkillFromFragment(skillName);
                    break;
                default:
                    case Utils.ALL_SKILLS_DRAWER:
                     list = Skill.selectAll();
                    break;
            }
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<Skill> models) {
        Utils.isLoading = false;
        super.onPostExecute(models);
        if (mCallback != null) {
            mCallback.onSkillListLoaded(models);
        }
    }

    public interface Callback {
        void onSkillListLoaded(List<Skill> list);
    }
}
