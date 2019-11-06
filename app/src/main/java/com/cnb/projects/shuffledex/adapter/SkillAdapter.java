package com.cnb.projects.shuffledex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vntcanb on 06/10/2015.
 */
public class SkillAdapter extends BaseAdapter {

    private ArrayList<Skill> mSkill = new ArrayList<>();
    private LayoutInflater mInflater;
    private TextView mSkillName;
    private ImageView mImageView;

    public SkillAdapter(List<Skill> skill, Context context) {
        mInflater = LayoutInflater.from(context);

        if (skill != null) {
            mSkill = new ArrayList<Skill>(skill);
        } else {
            mSkill = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mSkill.size();
    }

    @Override
    public Skill getItem(int position) {
        return mSkill.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.skill_list_item, null);

        mSkillName = (TextView)view.findViewById(R.id.skill_name);
        mImageView = (ImageView)view.findViewById(R.id.more_image_view);

        mSkillName.setText(getItem(position).getSkill());
        if(getCount()>1){
            mImageView.setVisibility(View.GONE);
        }else {
            mImageView.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
