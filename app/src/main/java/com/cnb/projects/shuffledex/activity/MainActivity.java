package com.cnb.projects.shuffledex.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.activeandroid.query.Delete;
import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.activity.fragment.AboutDialog;
import com.cnb.projects.shuffledex.activity.fragment.DonationActivity;
import com.cnb.projects.shuffledex.activity.fragment.LicenseDialog;
import com.cnb.projects.shuffledex.activity.fragment.OverallDialog;
import com.cnb.projects.shuffledex.activity.fragment.PokemonListFragment;
import com.cnb.projects.shuffledex.adapter.PokemonAdapter;
import com.cnb.projects.shuffledex.adapter.SkillAdapter;
import com.cnb.projects.shuffledex.model.Pokemon;
import com.cnb.projects.shuffledex.model.Skill;
import com.cnb.projects.shuffledex.task.FillDatabase;
import com.cnb.projects.shuffledex.task.ReadDatabase;
import com.cnb.projects.shuffledex.task.ReadSkillDatabase;
import com.cnb.projects.shuffledex.task.UpdatePokemon;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ReadDatabase.Callback, ReadSkillDatabase.Callback, NavigationView.OnNavigationItemSelectedListener,
        PokemonListFragment.SkillListener, View.OnClickListener {

    private ArrayList<Pokemon> mList;
    private ArrayList<Skill> mSkillList;
    private SearchView mSearchView;
    private String mPokemonFilter = "";
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    public static boolean mHasSkillUpdate = true;
    public static boolean mHasPokemon = true;
    private ImageView mImgBug;       private ImageView mImgDark;
    private ImageView mImgElectric;  private ImageView mImgSteel;
    private ImageView mImgFight;     private ImageView mImgFire;
    private ImageView mImgFlying;    private ImageView mImgFairy;
    private ImageView mImgIce;       private ImageView mImgWater;
    private ImageView mImgGrass;     private ImageView mImgGround;
    private ImageView mImgPoison;    private ImageView mImgPsychic;
    private ImageView mImgNormal;    private ImageView mImgGhost;
    private ImageView mImgDragon;    private ImageView mImgRock;
    private ImageView mSrank;        private ImageView mSrankMissing;
    private String mSelectHelper = "";
    private AlertDialogWrapper.Builder builder;
    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        importDatabase();
        new ReadDatabase(this).execute();
        showAllPokemons();
    }

    private void initComponents() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mToggle);
        mToggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
                hideKeyboard();
            }
        });
    }

    private void importDatabase() {

        if (Pokemon.selectAll().isEmpty()) {
            mHasPokemon = false;
            new FillDatabase(MainActivity.this).execute();
        }

        if (Skill.checkSkillUpdate().size() == 0){
            new Delete().from(Skill.class).execute();
            mHasSkillUpdate = false;
            new FillDatabase(MainActivity.this).execute();
        }

        if(Pokemon.checkPokemonUpdate().size() == 0) {
            new UpdatePokemon().execute();}
    }

    private void showAllPokemons() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Utils.POKEMON_LIST, mList);
        PokemonListFragment pokemonListFragment = new PokemonListFragment();
        pokemonListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container,
                pokemonListFragment, Utils.ALL_POKEMONS).addToBackStack(Utils.BACK_STACK).commit();
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchActionBar = menu.findItem(R.id.search_widget);

        /**
         * Initialize searchView components
         */

        mSearchView = (SearchView) MenuItemCompat.getActionView(searchActionBar);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint("Search by name");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPokemonFilter = query;
                loadPokemon();
                hideKeyboard();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPokemonFilter = newText;
                loadPokemon();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            LicenseDialog.callLicenseDialog(this);
        }

        if (id == R.id.action_sort) {
            callSortDialog(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListLoaded(List<Pokemon> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        PokemonListFragment fragment = getFragment();
        if (fragment != null) {
            PokemonAdapter pokemonAdapter = new PokemonAdapter(list, this);
            fragment.updateList(this, pokemonAdapter);
        }
    }

    @Override
    public void onSkillListLoaded(List<Skill> list) {
        mSkillList = new ArrayList<>(list);
        PokemonListFragment fragment = getFragment();
        if (fragment != null) {
            SkillAdapter skillAdapter = new SkillAdapter(list, this);
            fragment.updateList(this, skillAdapter);
        }
    }

    public PokemonListFragment getFragment() {
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag(Utils.ALL_POKEMONS);

        if (fragment != null) {
            return (PokemonListFragment) fragment;
        }

        return null;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();

        hideKeyboard();

        PokemonListFragment frag = getFragment();
        if (frag != null && !frag.isAdded()) {
            showAllPokemons();
        }

        switch (id) {
            case R.id.donation:
                Intent i = new Intent(this, DonationActivity.class);
                startActivity(i);
                break;
            case R.id.all_pokemons:
                Utils.DRAWER_OPTIONS = 0;
                new ReadDatabase(this).execute(Utils.ALL_POKENONS_DRAWER);
                break;
            case R.id.main_stages:
                Utils.DRAWER_OPTIONS = 1;
                new ReadDatabase(this).execute(Utils.MAIN_STAGES_DRAWER);
                break;
            case R.id.expert_stages:
                Utils.DRAWER_OPTIONS = 2;
                new ReadDatabase(this).execute(Utils.EXPERT_STAGES_DRAWER);
                break;
            case R.id.special_stages:
                Utils.DRAWER_OPTIONS = 3;
                new ReadDatabase(this).execute(Utils.SPECIAL_STAGES_DRAWER);
                break;
            case R.id.captured:
                Utils.DRAWER_OPTIONS = 4;
                new ReadDatabase(this).execute(Utils.CAPTURED_DRAWER);
                break;
            case R.id.skill:
                Utils.DRAWER_OPTIONS = 5;
                new ReadSkillDatabase(this).execute(Utils.ALL_SKILLS_DRAWER);
                break;
            case R.id.about:
                AboutDialog.callAboutDialog(this);
                break;
            case R.id.overall:
                new OverallDialog().callAboutDialog(this);
                break;
            default:
                return false;
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!Utils.isLoading) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }, 700);
        }
        return true;
    }

    private void loadPokemon() {
        switch (Utils.DRAWER_OPTIONS) {
            case 0:
                new ReadDatabase(this).execute(Utils.SEARCH_ALL_POKE_NAME, mPokemonFilter);
                break;
            case 1:
                new ReadDatabase(this).execute(Utils.SEARCH_MAIN_POKE_NAME, mPokemonFilter);
                break;
            case 2:
                new ReadDatabase(this).execute(Utils.SEARCH_EXPERT_POKE_NAME, mPokemonFilter);
                break;
            case 3:
                new ReadDatabase(this).execute(Utils.SEARCH_SPECIAL_POKE_NAME, mPokemonFilter);
                break;
            case 4:
                new ReadDatabase(this).execute(Utils.SEARCH_CAPTURED, mPokemonFilter);
                break;
            case 5:
                new ReadSkillDatabase(this).execute(Utils.SEARCH_ALL_SKILLS, mPokemonFilter);
                break;
        }
    }

    @Override
    public void onSkillSelect(String skillName) {
        mPokemonFilter = skillName;
        new ReadSkillDatabase(this).execute(Utils.SEARCH_SKILL_FRAGMENT, skillName);
    }

    @Override
    public void onBackPressed() {
        if (Utils.DRAWER_OPTIONS == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to close the application?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (Utils.DRAWER_OPTIONS == 5) {
            Utils.DRAWER_OPTIONS = 0;
            showAllPokemons();
        } else {
            Utils.DRAWER_OPTIONS = 0;
            new ReadDatabase(this).execute(Utils.ALL_POKENONS_DRAWER);
        }
    }

    public void callSortDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View content = inflater.inflate(R.layout.sortby_dialog, null);

        mImgBug = (ImageView)content.findViewById(R.id.type_bug);
        mImgDark = (ImageView)content.findViewById(R.id.type_dark);
        mImgDragon = (ImageView)content.findViewById(R.id.type_dragon);
        mImgElectric = (ImageView)content.findViewById(R.id.type_electric);
        mImgFairy = (ImageView)content.findViewById(R.id.type_fairy);
        mImgFight = (ImageView)content.findViewById(R.id.type_fight);
        mImgFire = (ImageView)content.findViewById(R.id.type_fire);
        mImgFlying = (ImageView)content.findViewById(R.id.type_flying);
        mImgGhost = (ImageView)content.findViewById(R.id.type_ghost);
        mImgGrass = (ImageView)content.findViewById(R.id.type_grass);
        mImgGround = (ImageView)content.findViewById(R.id.type_ground);
        mImgIce = (ImageView)content.findViewById(R.id.type_ice);
        mImgNormal = (ImageView)content.findViewById(R.id.type_normal);
        mImgPoison = (ImageView)content.findViewById(R.id.type_poison);
        mImgPsychic = (ImageView)content.findViewById(R.id.type_psychic);
        mImgRock = (ImageView)content.findViewById(R.id.type_rock);
        mImgSteel = (ImageView)content.findViewById(R.id.type_steel);
        mImgWater = (ImageView)content.findViewById(R.id.type_water);
        mSrank = (ImageView)content.findViewById(R.id.s_rank_filter);
        mSrankMissing = (ImageView)content.findViewById(R.id.missing_s_filter);

        mImgBug.setOnClickListener(this);            mImgDark.setOnClickListener(this);
        mImgDragon.setOnClickListener(this);    mImgElectric.setOnClickListener(this);
        mImgFairy.setOnClickListener(this);     mImgFight.setOnClickListener(this);
        mImgFire.setOnClickListener(this);      mImgFlying.setOnClickListener(this);
        mImgGhost.setOnClickListener(this);     mImgGrass.setOnClickListener(this);
        mImgGround.setOnClickListener(this);    mImgIce.setOnClickListener(this);
        mImgNormal.setOnClickListener(this);    mImgPoison.setOnClickListener(this);
        mImgPsychic.setOnClickListener(this);   mImgRock.setOnClickListener(this);
        mImgSteel.setOnClickListener(this);     mImgWater.setOnClickListener(this);
        mSrank.setOnClickListener(this);        mSrankMissing.setOnClickListener(this);


        dialog = new MaterialDialog.Builder(this)
                .title("Sort By")
                .positiveText("OK")
                .customView(content,false)
                .show();

    }

    private void callSortExecute(){
        new ReadDatabase(this).execute(Utils.SORT_BY, mSelectHelper);
        mSelectHelper = "";
    }

    private void okAndDismissType(String type){
        sortByOptions(type);
        callSortExecute();
        dialog.dismiss();
    }

    private void okAndDismissRank(int value){
        sortByS(value);
        callSortExecute();
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        final String type;
        final int value;

        switch (v.getId()){
            case R.id.type_bug:
                type = "type_bug";
                okAndDismissType(type);
                break;
            case R.id.type_dark:
                type = "type_dark";
                okAndDismissType(type);
                break;
            case R.id.type_dragon:
                type = "type_dragon";
                okAndDismissType(type);
                break;
            case R.id.type_electric:
                type = "type_electr";
                okAndDismissType(type);
                break;
            case R.id.type_fairy:
                type = "type_fairy";
                okAndDismissType(type);
                break;
            case R.id.type_fight:
                type = "type_fight";
                okAndDismissType(type);
                break;
            case R.id.type_fire:
                type = "type_fire";
                okAndDismissType(type);
                break;
            case R.id.type_flying:
                type = "type_flying";
                okAndDismissType(type);
                break;
            case R.id.type_ghost:
                type = "type_ghost";
                okAndDismissType(type);
                break;
            case R.id.type_grass:
                type = "type_grass";
                okAndDismissType(type);
                break;
            case R.id.type_ground:
                type = "type_ground";
                okAndDismissType(type);
                break;
            case R.id.type_ice:
                type = "type_ice";
                okAndDismissType(type);
                break;
            case R.id.type_normal:
                type = "type_normal";
                okAndDismissType(type);
                break;
            case R.id.type_poison:
                type = "type_poison";
                okAndDismissType(type);
                break;
            case R.id.type_rock:
                type = "type_rock";
                okAndDismissType(type);
                break;
            case R.id.type_psychic:
                type = "type_psychic";
                okAndDismissType(type);
                break;
            case R.id.type_steel:
                type = "type_steel";
                okAndDismissType(type);
                break;
            case R.id.type_water:
                type = "type_water";
                okAndDismissType(type);
                break;
            case R.id.s_rank_filter:
                value = 1;
                okAndDismissRank(value);
                break;
            case R.id.missing_s_filter:
                value = 0;
                okAndDismissRank(value);
                break;
        }
    }

    private void sortByOptions(String type){
        StringBuilder select = new StringBuilder();

        if(Utils.DRAWER_OPTIONS == 0){

            select.append("ELEMENT like '" + type + "'");
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 1){

            select.append("STAGE like 'Main%' and ELEMENT like '" + type + "'");
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 2){

            select.append("STAGE like 'Expert%' and ELEMENT like '" + type + "'");
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 3){

            select.append("STAGE like 'Special%' and ELEMENT like '" + type + "'");
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 4) {

            select.append("CAPTURED = 1 and ELEMENT like '" + type + "'");
            mSelectHelper = select.toString();

        }
    }

    private void sortByS(int value){
        StringBuilder select = new StringBuilder();

        if(Utils.DRAWER_OPTIONS == 0){

            select.append("GRADE = " + value);
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 1){

            select.append("STAGE like 'Main%' and GRADE = "+ value);
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 2){

            select.append("STAGE like 'Expert%' and GRADE = " + value);
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 3){

            select.append("STAGE like 'Special%' and GRADE = " + value);
            mSelectHelper = select.toString();

        } else if(Utils.DRAWER_OPTIONS == 4) {

            select.append("CAPTURED = 1 and GRADE = " + value);
            mSelectHelper = select.toString();

        }
    }

}
