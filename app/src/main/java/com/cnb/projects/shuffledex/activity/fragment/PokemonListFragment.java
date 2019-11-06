package com.cnb.projects.shuffledex.activity.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.adapter.PokemonAdapter;
import com.cnb.projects.shuffledex.adapter.SkillAdapter;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.ArrayList;

/**
 * Created by vntcanb on 30/09/2015.
 */
public class PokemonListFragment extends Fragment {


    private ListView mListView;
    private ArrayList<Pokemon> mPokemonList;
    private ArrayList<Skill> mSkillList;
    private SkillListener mListener;
    private LinearLayout mLinearLayout;
    private ImageView mPokeballView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            mPokemonList = args.getParcelableArrayList(Utils.POKEMON_LIST);
            mSkillList = args.getParcelableArrayList(Utils.SKILL_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_list_header, null);
        mListView = (ListView) view.findViewById(R.id.pokemon_list_view);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.list_linear_layout);


        ListAdapter adapter = new PokemonAdapter(mPokemonList, view.getContext());
        updateList(view.getContext(), adapter);

        mListView.setEmptyView(view.findViewById(android.R.id.empty));
        mPokeballView = (ImageView) view.findViewById(R.id.capture_view);

        return view;
    }

    public void updateList(final Context context, final ListAdapter adapter) {
        setHeaderVisibility(!(adapter instanceof SkillAdapter));

        mListView.setAdapter(adapter);
        showDialog(context);

    }

    public void showDialog(final Context context) {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final ListAdapter adapter = mListView.getAdapter();
                final LayoutInflater inflater = LayoutInflater.from(view.getContext());


                if ((adapter != null) && (adapter instanceof PokemonAdapter)) {

                    View content = inflater.inflate(R.layout.pokemon_dialog, null);

                    final Pokemon pkmn = ((PokemonAdapter) adapter).getItem(position);

                    ImageView imageFace = (ImageView) content.findViewById(R.id.pokemon_face_dialog);
                    ImageView imageType = (ImageView) content.findViewById(R.id.type_img_view_dialog);
                    TextView textPower = (TextView) content.findViewById(R.id.power_text_view_dialog);
                    TextView textSkill = (TextView) content.findViewById(R.id.skill_text_view_dialog);
                    TextView textCapture = (TextView) content.findViewById(R.id.capture_text_view_dialog);
                    TextView textStage = (TextView) content.findViewById(R.id.stage_text_view_dialog);
                    TextView textID = (TextView) content.findViewById(R.id.dialog_pokemonid_tv);

                    textID.setText("ID: #" + String.valueOf(pkmn.getPokemonID()));

                    final ImageButton captureButton = (ImageButton) content.findViewById(R.id.button_captured);
                    final ImageButton gradeButton = (ImageButton) content.findViewById(R.id.grade_button);
                    final ImageButton skillButton = (ImageButton) content.findViewById(R.id.skill_button_dialog);

                    int imageFaceId = getResources().getIdentifier(pkmn.getImageName(), "drawable", context.getPackageName());
                    int imageTypeId = getResources().getIdentifier(pkmn.getElement(), "drawable", context.getPackageName());
                    imageFace.setImageResource(imageFaceId);
                    imageType.setImageResource(imageTypeId);

                    textStage.setText(pkmn.getStage());
                    textPower.setText("Power: " + String.valueOf(pkmn.getBasePower()));
                    if (pkmn.getSkill().contains("Mega Power")) {
                        textSkill.setText("Mega Power");
                    } else {
                        textSkill.setText(pkmn.getSkill());
                    }
                    if (pkmn.getCaptureRate().equalsIgnoreCase("---") || pkmn.getCaptureRate().equalsIgnoreCase("Unknown")) {
                        textCapture.setVisibility(View.INVISIBLE);
                    } else {
                        textCapture.setVisibility(View.VISIBLE);
                        textCapture.setText("Capture Rate: " + pkmn.getCaptureRate());
                    }

                    if (pkmn.isCaptured()) {
                        if (pkmn.getPokemonName().contains("Mega")) {
                            captureButton.setImageResource(R.drawable.ic_mega_stone);
                        } else {
                            captureButton.setImageResource(R.drawable.ic_pokeball_true_icon);
                        }
                    } else {
                        if (pkmn.getPokemonName().contains("Mega")) {
                            captureButton.setImageResource(R.drawable.ic_mega_stone_false);
                        } else {
                            captureButton.setImageResource(R.drawable.ic_pokeball_false_icon);
                        }

                    }

                    if (!pkmn.hasGrade()) {
                        gradeButton.setImageResource(R.drawable.ic_s_false);
                    } else {
                        gradeButton.setImageResource(R.drawable.ic_s_true);
                    }

                    /**
                     * button to set if the pokemon is capture or not
                     */
                    captureButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pkmn.isCaptured()) {
                                pkmn.setCaptured(false);
                                if (pkmn.getPokemonName().contains("Mega")) {
                                    captureButton.setImageResource(R.drawable.ic_mega_stone_false);
                                } else {
                                    captureButton.setImageResource(R.drawable.ic_pokeball_false_icon);
                                }
                            } else {
                                pkmn.setCaptured(true);
                                if (pkmn.getPokemonName().contains("Mega")) {
                                    captureButton.setImageResource(R.drawable.ic_mega_stone);
                                } else {
                                    captureButton.setImageResource(R.drawable.ic_pokeball_true_icon);
                                }
                            }
                        }
                    });

                    /**
                     * button to set grade
                     */
                    gradeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!pkmn.hasGrade()) {
                                gradeButton.setImageResource(R.drawable.ic_s_true);
                                pkmn.setGrade(true);
                            } else {
                                pkmn.setGrade(false);
                                gradeButton.setImageResource(R.drawable.ic_s_false);
                            }
                        }
                    });

                    /**
                     * Build Alert Dialog Wrapper
                     */

                    final Dialog dialog = new AlertDialogWrapper.Builder(context)
                            .setTitle(pkmn.getPokemonName())
                            .setView(content)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Utils.DRAWER_OPTIONS == 4 && !pkmn.isCaptured()) {
                                        ((PokemonAdapter) adapter).removeItem(position);
                                    }
                                    final int aux = mListView.getFirstVisiblePosition();
                                    updateList(context, adapter);

                                    mListView.setSelection(aux);

                                    pkmn.save();
                                }
                            })
                            .create();
                    dialog.show();

                    /**
                     * search button: search by skill name
                     */
                    skillButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pkmn.save();
                            dialog.dismiss();

                            Utils.LIST_SAVED_POSITION = mListView.getFirstVisiblePosition();

                            Utils.DRAWER_OPTIONS = 5;
                            Bundle bundle = new Bundle();
                            bundle.putString(Utils.SKILL_OBJECT, pkmn.getSkill());
                            SearchSkillFragment skillFragment = new SearchSkillFragment();
                            skillFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.animator.animator_left_in, R.animator.animator_left_out, R.animator.animator_right_in, R.animator.animator_right_out)
                                    .addToBackStack(Utils.BACK_STACK)
                                    .replace(R.id.container, skillFragment, Utils.RESULT_SKILL)
                                    .commit();
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.setSelection(Utils.LIST_SAVED_POSITION);
                                }
                            }, 100);
                        }
                    });

                } else {

                    View content = inflater.inflate(R.layout.skill_dialog, null);

                    final Skill skill = ((SkillAdapter) adapter).getItem(position);

                    TextView textDescription = (TextView) content.findViewById(R.id.description_text_view_dialog);
                    TextView textDescription2 = (TextView) content.findViewById(R.id.description2_text_view_dialog);
                    TextView textMatch3 = (TextView) content.findViewById(R.id.skill_match_3);
                    TextView textMatch4 = (TextView) content.findViewById(R.id.skill_match_4);
                    TextView textMatch5 = (TextView) content.findViewById(R.id.skill_match_5);
                    TextView textMega = (TextView) content.findViewById(R.id.megacandy_text_view_dialog);
                    ImageView candyView = (ImageView) content.findViewById(R.id.candy_image_view_dialog);
                    LinearLayout linearLayout = (LinearLayout) content.findViewById(R.id.matching_layout);

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

                    new AlertDialogWrapper.Builder(context)
                            .setTitle(skill.getSkill())
                            .setView(content)
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SkillListener) {
            mListener = (SkillListener) activity;
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SkillListener {
        void onSkillSelect(String skillName);
    }

    public void setHeaderVisibility(boolean visible) {
        if (visible) {
            mLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (Utils.LIST_SAVED_POSITION != 0) {
            mListView.setSelection(Utils.LIST_SAVED_POSITION);
            Utils.LIST_SAVED_POSITION = 0;
        }
        super.onActivityCreated(savedInstanceState);
    }
}

