package com.cnb.projects.shuffledex.utils;

/**
 * Created by vntcanb on 23/09/2015.
 */
public class Utils {

    public static final String POKEMON_LIST = "list";
    public static final String SKILL_OBJECT = "SKILL_OBJECT";
    public static final String SKILL_LIST = "SKILL_LIST";
    public static final String ALL_POKEMONS = "ALL_POKEMONS";
    public static final String RESULT_SKILL = "RESULT_SKILL";
    public static final String BACK_STACK = "BACK_STACK";
    public static int SELECT_OPTION = 0;
    public static final String TOTAL_CAPTURED = "TOTAL_CAPUTRED";
    public static final String TOTAL_S_GRADE = "TOTAL_S_GRADE";

    /**
     * Drawer options
     */
    public static final String ALL_POKENONS_DRAWER = "ALL_POKEMONS_DRAWER";
    public static final String MAIN_STAGES_DRAWER = "MAIN_STAGES";
    public static final String EXPERT_STAGES_DRAWER = "EXPERT_STAGES_DRAWER";
    public static final String SPECIAL_STAGES_DRAWER = "SPECIAL_STAGES_DRAWER";
    public static final String CAPTURED_DRAWER = "CAPTURED_DRAWER";
    public static final String ALL_SKILLS_DRAWER = "ALL_SKILLS";
    public static final String ABOUT_DRAWER = "ABOUT_DRAWER";

    /**
     * Search options - Pokemon
     * Load a different query for each displayed list.
     */
    public static final String SEARCH_ALL_POKE_NAME = "SEARCH_ALL_POKE_NAME";
    public static final String SEARCH_MAIN_POKE_NAME = "SEARCH_MAIN_POKE_NAME";
    public static final String SEARCH_EXPERT_POKE_NAME = "SEARCH_EXPERT_POKE_NAME";
    public static final String SEARCH_SPECIAL_POKE_NAME = "SEARCH_SPECIAL_POKE_NAME";
    public static final String SEARCH_CAPTURED = "SEARCH_CAPTURED";
    public static final String SEARCH_SKILL_FRAGMENT = "SEARCH_SKILL_FRAGMENT";
    public static int DRAWER_OPTIONS = 0;

    /**
     * Sort Options
     */
    public static final String SORT_BY_POWER = "SORT_BY_POWER";
    public static final String SORT_BY = "SORT_BY";

    public static int getDrawerOptions(){
        return DRAWER_OPTIONS;
    }

    /**
     * Search options - Skill
     */
    public static final String SEARCH_ALL_SKILLS = "SEARCH_ALL_SKILLS";

    /**
     * Save list position
     */
    public static int LIST_SAVED_POSITION = 0;
    /**
     * Loading
     */
    public static boolean isLoading = false;
    /**
     * Sort saved options
     */
    public static int SORT_OPTIONS = 0;
}
