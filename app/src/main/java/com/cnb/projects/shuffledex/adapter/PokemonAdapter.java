package com.cnb.projects.shuffledex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vntcanb on 29/09/2015.
 */
public class PokemonAdapter extends BaseAdapter {

    private ArrayList<Pokemon> mPokemons = new ArrayList<>();
    private LayoutInflater mInflater;


    public PokemonAdapter(List<Pokemon> pokemon, Context context) {
        mInflater = LayoutInflater.from(context);

        if (pokemon != null) {
            mPokemons = new ArrayList<Pokemon>(pokemon);
        } else {
            mPokemons = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mPokemons.size();
    }

    @Override
    public Pokemon getItem(int position) {
        return mPokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pokemon_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mPokemonID.setText("#" + getItem(position).getPokemonID());

        int imageFaceId = convertView.getResources().getIdentifier(getItem(position).getImageName(), "drawable", convertView.getContext().getPackageName());
        int imageTypeId = convertView.getResources().getIdentifier(getItem(position).getElement(), "drawable", convertView.getContext().getPackageName());

        holder.mTextViewName.setText(getItem(position).getPokemonName());
        holder.mTextViewPower.setText("Power: " + getItem(position).getBasePower());


        if(getItem(position).getSkill().contains("Mega Power")){
            holder.mTextViewSkill.setText("Mega Power");
        }else{
        holder.mTextViewSkill.setText(getItem(position).getSkill());
        }

        holder.mImageViewFace.setImageResource(imageFaceId);
        holder.mImageViewType.setImageResource(imageTypeId);

        if(getItem(position).isCaptured()){
            if(getItem(position).getPokemonName().contains("Mega")){
                 holder.mImageViewPokeball.setImageResource(R.drawable.ic_mega_stone);
            } else {
                holder.mImageViewPokeball.setImageResource(R.drawable.ic_pokeball_true_icon);
            }
            holder.mImageViewPokeball.setVisibility(View.VISIBLE);

        }else{
            holder.mImageViewPokeball.setVisibility(View.INVISIBLE);
        }


        holder.mImageViewGrade.setImageResource(R.drawable.ic_s_true);

        if(!getItem(position).hasGrade()){
            holder.mImageViewGrade.setVisibility(View.INVISIBLE);
        }else{
            holder.mImageViewGrade.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void removeItem(int position) {
        mPokemons.remove(position);
        notifyDataSetChanged();
    }
    
    private static class ViewHolder{
        ImageView mImageViewFace;
        ImageView mImageViewType;
        ImageView mImageViewPokeball;
        TextView mTextViewName;
        TextView mTextViewPower;
        TextView mTextViewSkill;
        TextView mPokemonID;
        ImageView mImageViewGrade;
        
        public ViewHolder(View view){
            mTextViewName = (TextView) view.findViewById(R.id.pokemon_text_view);
            mTextViewSkill = (TextView) view.findViewById(R.id.pokemonskill_text_view);
            mImageViewFace = (ImageView) view.findViewById(R.id.portrait_view);
            mImageViewType = (ImageView) view.findViewById(R.id.type_view);
            mTextViewPower = (TextView) view.findViewById(R.id.power_text_view);
            mImageViewPokeball = (ImageView) view.findViewById(R.id.capture_view);
            mPokemonID = (TextView) view.findViewById(R.id.id_text_view);
            mImageViewGrade = (ImageView) view.findViewById(R.id.grade_view);
        }
    }
}
