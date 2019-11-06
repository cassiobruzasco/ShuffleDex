package com.cnb.projects.shuffledex.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by vntcanb on 24/09/2015.
 */
@Table(name = "SKILL")
public class Skill extends Model implements Parcelable {

    public static final String SKILL = "SKILL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DESCRIPTION_2 = "DESCRIPTION_2";
    public static final String ACTIVATION_MATCH_3 = "ACTIVATION_MATCH_3";
    public static final String ACTIVATION_MATCH_4 = "ACTIVATION_MATCH_4";
    public static final String ACTIVATION_MATCH_5 = "ACTIVATION_MATCH_5";
    public static final String MEGA_CANDY = "MEGA_CANDY";

    @Column (name = SKILL)
    private String mSkill;

    @Column (name = DESCRIPTION)
    private String mDescription;

    @Column (name = ACTIVATION_MATCH_3)
    private String mActivationMatch3;

    @Column (name = ACTIVATION_MATCH_4)
    private String mActivationMatch4;

    @Column (name = ACTIVATION_MATCH_5)
    private String mActivationMatch5;

    @Column (name = MEGA_CANDY)
    private int mMegaCandy;

    @Column (name = DESCRIPTION_2)
    private String mDescription_2;

    public static List selectAll() {
        return new Select().from(Skill.class).orderBy(SKILL).execute();
    }

    public static List selectSkillFromSearch(final String skillName) {
        return new Select().from(Skill.class).orderBy(SKILL).where("SKILL like '%" + skillName + "%'").execute();
    }

    public static List selectSkillFromFragment(final String skillName) {
        return new Select().from(Skill.class).orderBy(SKILL).where("SKILL = '" + skillName + "'").execute();
    }

    public static List checkSkillUpdate(){
        return new Select().from(Skill.class).where("SKILL like 'Sceptile Mega Power'").execute();
    }

    public Skill() {
        super();
    }

    public Skill(String mSkill, String mDescription, String mActivationMatch3,
                 String mActivationMatch4, String mActivationMatch5,
                 int mMegaCandy, String mDescription_2) {
        super();
        this.mSkill = mSkill;
        this.mDescription = mDescription;
        this.mActivationMatch3 = mActivationMatch3;
        this.mActivationMatch4 = mActivationMatch4;
        this.mActivationMatch5 = mActivationMatch5;
        this.mMegaCandy = mMegaCandy;
        this.mDescription_2 = mDescription_2;
    }

    public String getSkill() {
        return mSkill;
    }

    public void setSkill(String mSkill) {
        this.mSkill = mSkill;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getActivationMatch3() {
        return mActivationMatch3;
    }

    public void setActivationMatch3(String mActivationMatch3) {
        this.mActivationMatch3 = mActivationMatch3;
    }

    public String getActivationMatch4() {
        return mActivationMatch4;
    }

    public void setActivationMatch4(String mActivationMatch4) {
        this.mActivationMatch4 = mActivationMatch4;
    }

    public String getActivationMatch5() {
        return mActivationMatch5;
    }

    public void setActivationMatch5(String mActivationMatch5) {
        this.mActivationMatch5 = mActivationMatch5;
    }

    public int getMegaCandy() {
        return mMegaCandy;
    }

    public void setMegaCandy(int mMegaCandy) {
        this.mMegaCandy = mMegaCandy;
    }

    public String getDescription2() {
        return mDescription_2;
    }

    public void setDescription2(String mDescription_2) {
        this.mDescription_2 = mDescription_2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSkill);
        dest.writeString(this.mDescription);
        dest.writeString(this.mActivationMatch3);
        dest.writeString(this.mActivationMatch4);
        dest.writeString(this.mActivationMatch5);
        dest.writeInt(this.mMegaCandy);
        dest.writeString(this.mDescription_2);
    }

    protected Skill(Parcel in) {
        this.mSkill = in.readString();
        this.mDescription = in.readString();
        this.mActivationMatch3 = in.readString();
        this.mActivationMatch4 = in.readString();
        this.mActivationMatch5 = in.readString();
        this.mMegaCandy = in.readInt();
        this.mDescription_2 = in.readString();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        public Skill createFromParcel(Parcel source) {
            return new Skill(source);
        }

        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };
}
