package com.cnb.projects.shuffledex.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.task.ReadSkillDatabase;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.List;


/**
 * Created by vntcanb on 16/10/2015.
 */
public class SearchSkillFragment extends Fragment implements ReadSkillDatabase.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.skill_item_fragment, null);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search_widget).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle args = getArguments();

        if (args != null) {
            final String skillName = args.getString(Utils.SKILL_OBJECT);
            new ReadSkillDatabase(this).execute(Utils.SEARCH_SKILL_FRAGMENT, skillName);
        }
    }

    public void initComponents(View content, Skill skill) {
        TextView textSkillName = (TextView) content.findViewById(R.id.tv_skill_name_fragment);
        TextView textDescription = (TextView) content.findViewById(R.id.description_text_view_dialog);
        TextView textDescription2 = (TextView) content.findViewById(R.id.description2_text_view_dialog);
        TextView textMatch3 = (TextView) content.findViewById(R.id.skill_match_3);
        TextView textMatch4 = (TextView) content.findViewById(R.id.skill_match_4);
        TextView textMatch5 = (TextView) content.findViewById(R.id.skill_match_5);
        TextView textMega = (TextView) content.findViewById(R.id.megacandy_text_view_dialog);
        ImageView candyView = (ImageView) content.findViewById(R.id.candy_image_view_dialog);
        LinearLayout linearLayout = (LinearLayout) content.findViewById(R.id.matching_layout);

        textSkillName.setText(skill.getSkill());
        textDescription.setText(skill.getDescription());
        textDescription2.setText(skill.getDescription2());
        textMatch3.setText(skill.getActivationMatch3());
        textMatch4.setText(skill.getActivationMatch4());
        textMatch5.setText(skill.getActivationMatch5());

        if (skill.getMegaCandy() >= 1) {
            textMega.setVisibility(View.VISIBLE);
            candyView.setVisibility(View.VISIBLE);
            textMega.setText(String.valueOf(skill.getMegaCandy()));
        } else {
            textMega.setVisibility(View.GONE);
            candyView.setVisibility(View.GONE);
        }

        if (skill.getSkill().toLowerCase().endsWith("mega power")) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSkillListLoaded(List<Skill> list) {
        initComponents(getView(), list.get(0));
    }


}
