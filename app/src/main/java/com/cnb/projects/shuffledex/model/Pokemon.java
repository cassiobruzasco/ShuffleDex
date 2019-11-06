package com.cnb.projects.shuffledex.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.List;

/**
 * Created by vntcanb on 23/09/2015.
 */
@Table(name = "POKEMON")
public class Pokemon extends Model implements Parcelable {

    public static final String POKEMON_ID = "POKEMON_ID";
    public static final String NAME = "NAME";
    public static final String ELEMENT = "ELEMENT";
    public static final String STAGE = "STAGE";
    public static final String BASE_POWER = "BASE_POWER";
    public static final String SKILL = "SKILL";
    public static final String CAPTURE_RATE = "CAPTURE_RATE";
    public static final String IMAGE_NAME = "IMAGE_NAME";
    public static final String CAPTURED = "CAPTURED";
    public static final String GRADE = "GRADE";

    @Column(name = POKEMON_ID)
    private int mPokemonID;

    @Column(name = NAME)
    private String mPokemonName;

    @Column(name = ELEMENT)
    private String mElement;

    @Column(name = STAGE)
    private String mStage;

    @Column(name = BASE_POWER)
    private int mBasePower;

    @Column(name = SKILL)
    private String mSkill;

    @Column(name = CAPTURE_RATE)
    private String mCaptureRate;

    @Column(name = IMAGE_NAME)
    private String mImageName;

    @Column(name = CAPTURED)
    private boolean mCaptured;

    @Column (name = GRADE)
    private boolean mGrade;

    public Pokemon() {
        super();
    }

    /**
     * Start of all Database Queries
     */

    //return a list of all pokemon
    public static List selectAll() {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).execute();
    }

    //return a list of all pokemon that is on Main Stage
    public static List selectMainStage() {
        return new Select().from(Pokemon.class).orderBy(STAGE).where("STAGE like 'Main%'").execute();
    }

    //return a list of all pokemon that is on Expert Stage
    public static List selectExpertStages() {
        return new Select().from(Pokemon.class).orderBy(STAGE).where("STAGE like 'Expert%'").execute();
    }

    //return a list of all pokemon that is on Special Stage
    public static List selectSpecialStage() {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("STAGE like 'Special%'").execute();
    }

    //return a list of all pokemon on Main Stage that has the letters typed on search.
    public static List selectMainFromSearch(final String pokename) {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("STAGE like 'Main%' and NAME like '%" + pokename + "%'").execute();
    }

    //return a list of all pokemon on Expert Stage that has the letters typed on search.
    public static List selectExpertFromSearch(final String pokename) {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("STAGE like 'Expert%' and NAME like '%" + pokename + "%'").execute();
    }

    //return a list of all pokemon on Main Stage that has the letters typed on search.
    public static List selectSpecialFromSearch(final String pokename) {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("STAGE like 'Special%' and NAME like '%" + pokename + "%'").execute();
    }

    //return a list of all pokemon that has the letters typed on search.
    public static List selectNameFromSearch(final String pokename) {
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("NAME like '%" + pokename + "%'").execute();
    }

    //return a list of all Captured Pokemon
    public static List selectCaptured(){
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("CAPTURED = 1").execute();
    }

    //return a list of all Captured Pokemon that has the letters typed on search
    public static List<Pokemon> selectCapturedFromSearch(final String pokename){
        return new Select().from(Pokemon.class).orderBy(POKEMON_ID).where("CAPTURED = 1 and NAME like '%" + pokename + "%'").execute();
    }

    //return a list of all Pokemon on All Pokemon tab sorted by 'Power'
    public static List<Pokemon> selectAllPokemonSortPower(){
        return new Select().from(Pokemon.class).orderBy("BASE_POWER DESC").execute();
    }

    //return count of captured pokemon
    public static int selectCounterCaptured(){
        return new Select().from(Pokemon.class).where("CAPTURED = 1 and NAME not like 'Mega%'").count();
    }
    //return count of s grade
    public static int selectCounterS(){
        return new Select().from(Pokemon.class).where("GRADE = 1 and STAGE like 'Main%'").count();
    }
    //return count of missing s grade
    public static int selectMissingS(){
        return new Select().from(Pokemon.class).where("GRADE = 0 and STAGE like 'Main%'").count();
    }

    public static List<Pokemon> checkPokemonUpdate(){
        return new Select().from(Pokemon.class).where("IMAGE_NAME = 'ic_landorus2'").execute();
    }

    public static List<Pokemon> sortPokemon(final String sortTypes){
        if(Utils.DRAWER_OPTIONS==0){
            return new Select().from(Pokemon.class).where(sortTypes).execute();
        } else if(Utils.DRAWER_OPTIONS>=1 && Utils.DRAWER_OPTIONS<=3){
            return new Select().from(Pokemon.class).orderBy(STAGE).where(sortTypes).execute();
        } else {
            return new Select().from(Pokemon.class).where(sortTypes).execute();
        }
    }

    /**
     * End of Database Queries.
     */

    public int getPokemonID() {
        return mPokemonID;
    }

    public void setPokemonID(int mPokemonID) {
        this.mPokemonID = mPokemonID;
    }

    public String getPokemonName() {
        return mPokemonName;
    }

    public void setPokemonName(String mPokemonName) {
        this.mPokemonName = mPokemonName;
    }

    public String getElement() {
        return mElement;
    }

    public void setElement(String mElement) {
        this.mElement = mElement;
    }

    public String getStage() {
        return mStage;
    }

    public void setStage(String mStage) {
        this.mStage = mStage;
    }

    public int getBasePower() {
        return mBasePower;
    }

    public void setBasePower(int mBasePower) {
        this.mBasePower = mBasePower;
    }

    public String getSkill() {
        return mSkill;
    }

    public void setSkill(String mSkill) {
        this.mSkill = mSkill;
    }

    public String getCaptureRate() {
        return mCaptureRate;
    }

    public void setCaptureRate(String mCaptureRate) {
        this.mCaptureRate = mCaptureRate;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String mImageName) {
        this.mImageName = mImageName;
    }

    public boolean isCaptured() {
        return mCaptured;
    }

    public void setCaptured(boolean mCaptured) {
        this.mCaptured = mCaptured;
    }

    public boolean hasGrade() { return mGrade; }

    public void setGrade(boolean mGrade) { this.mGrade = mGrade; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPokemonID);
        dest.writeString(this.mPokemonName);
        dest.writeString(this.mElement);
        dest.writeString(this.mStage);
        dest.writeInt(this.mBasePower);
        dest.writeString(this.mSkill);
        dest.writeString(this.mCaptureRate);
    }

    protected Pokemon(Parcel in) {
        this.mPokemonID = in.readInt();
        this.mPokemonName = in.readString();
        this.mElement = in.readString();
        this.mStage = in.readString();
        this.mBasePower = in.readInt();
        this.mSkill = in.readString();
        this.mCaptureRate = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
