package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.os.Bundle;

public class Token_Selection {

    public static final String COL__ID = "_id";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_PLUGIN_ID = "plugin_id";
    public static final String COL_NAME = "name";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_ENABLED = "enabled";
    public static final String COL_ALLDEVICES = "alldevices";

    private Integer m_id;
    private Integer mprofile_id;
    private String mplugin_id;
    private String mname;
    private Integer mpriority;
    private String menabled;
    private String malldevices;

    public Token_Selection() {
    }

    public Token_Selection(Integer _id, Integer profile_id, String plugin_id, String name, Integer priority, String enabled, String alldevices) {
        this.m_id = _id;
        this.mprofile_id = profile_id;
        this.mplugin_id = plugin_id;
        this.mname = name;
        this.mpriority = priority;
        this.menabled = enabled;
        this.malldevices = alldevices;
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

    public String getplugin_id() {
        return mplugin_id;
    }

    public void setplugin_id(String plugin_id) {
        this.mplugin_id = plugin_id;
    }

    public String getname() {
        return mname;
    }

    public void setname(String name) {
        this.mname = name;
    }

    public Integer getpriority() {
        return mpriority;
    }

    public void setpriority(Integer priority) {
        this.mpriority = priority;
    }

    public String getenabled() {
        return menabled;
    }

    public void setenabled(String enabled) {
        this.menabled = enabled;
    }

    public String getalldevices() {
        return malldevices;
    }

    public void setalldevices(String alldevices) {
        this.malldevices = alldevices;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putInt(COL_PROFILE_ID, this.mprofile_id);
        b.putString(COL_PLUGIN_ID, this.mplugin_id);
        b.putString(COL_NAME, this.mname);
        b.putInt(COL_PRIORITY, this.mpriority);
        b.putString(COL_ENABLED, this.menabled);
        b.putString(COL_ALLDEVICES, this.malldevices);
        return b;
    }

    public Token_Selection(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mprofile_id = b.getInt(COL_PROFILE_ID);
            this.mplugin_id = b.getString(COL_PLUGIN_ID);
            this.mname = b.getString(COL_NAME);
            this.mpriority = b.getInt(COL_PRIORITY);
            this.menabled = b.getString(COL_ENABLED);
            this.malldevices = b.getString(COL_ALLDEVICES);
        }
    }

    @Override
    public String toString() {
        return "Token_Selection{" +
            " m_id=" + m_id +
            ", mprofile_id=" + mprofile_id +
            ", mplugin_id='" + mplugin_id + '\'' +
            ", mname='" + mname + '\'' +
            ", mpriority=" + mpriority +
            ", menabled='" + menabled + '\'' +
            ", malldevices='" + malldevices + '\'' +
            '}';
    }


}
