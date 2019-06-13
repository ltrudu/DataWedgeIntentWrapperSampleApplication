package com.zebra.datawedgeprofileintents.sqlitedao;

import android.os.Bundle;
import java.util.Date;

public class Applist {

    public static final String COL__ID = "_id";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_ACTIVITY = "activity";
    public static final String COL_PACKAGENAME = "packagename";

    private Integer m_id;
    private Integer mprofile_id;
    private String mactivity;
    private String mpackagename;

    public Applist() {
    }

    public Applist(Integer _id, Integer profile_id, String activity, String packagename) {
        this.m_id = _id;
        this.mprofile_id = profile_id;
        this.mactivity = activity;
        this.mpackagename = packagename;
    }

    public Integer get_id() {
        return m_id;
    }

    public void set_id(Integer _id) {
        this.m_id = _id;
    }

    public Integer getprofile_id() {
        return mprofile_id;
    }

    public void setprofile_id(Integer profile_id) {
        this.mprofile_id = profile_id;
    }

    public String getactivity() {
        return mactivity;
    }

    public void setactivity(String activity) {
        this.mactivity = activity;
    }

    public String getpackagename() {
        return mpackagename;
    }

    public void setpackagename(String packagename) {
        this.mpackagename = packagename;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putInt(COL_PROFILE_ID, this.mprofile_id);
        b.putString(COL_ACTIVITY, this.mactivity);
        b.putString(COL_PACKAGENAME, this.mpackagename);
        return b;
    }

    public Applist(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mprofile_id = b.getInt(COL_PROFILE_ID);
            this.mactivity = b.getString(COL_ACTIVITY);
            this.mpackagename = b.getString(COL_PACKAGENAME);
        }
    }

    @Override
    public String toString() {
        return "Applist{" +
            " m_id=" + m_id +
            ", mprofile_id=" + mprofile_id +
            ", mactivity='" + mactivity + '\'' +
            ", mpackagename='" + mpackagename + '\'' +
            '}';
    }


}
