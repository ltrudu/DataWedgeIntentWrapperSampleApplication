package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.os.Bundle;

public class Plugin_Process_Bdf {

    public static final String COL__ID = "_id";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_PLUGIN_ID = "plugin_id";
    public static final String COL_PARAM_ID = "param_id";
    public static final String COL_PARAM_VALUE = "param_value";

    private Integer m_id;
    private Integer mprofile_id;
    private String mplugin_id;
    private String mparam_id;
    private String mparam_value;

    public Plugin_Process_Bdf() {
    }

    public Plugin_Process_Bdf(Integer _id, Integer profile_id, String plugin_id, String param_id, String param_value) {
        this.m_id = _id;
        this.mprofile_id = profile_id;
        this.mplugin_id = plugin_id;
        this.mparam_id = param_id;
        this.mparam_value = param_value;
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

    public String getparam_id() {
        return mparam_id;
    }

    public void setparam_id(String param_id) {
        this.mparam_id = param_id;
    }

    public String getparam_value() {
        return mparam_value;
    }

    public void setparam_value(String param_value) {
        this.mparam_value = param_value;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putInt(COL_PROFILE_ID, this.mprofile_id);
        b.putString(COL_PLUGIN_ID, this.mplugin_id);
        b.putString(COL_PARAM_ID, this.mparam_id);
        b.putString(COL_PARAM_VALUE, this.mparam_value);
        return b;
    }

    public Plugin_Process_Bdf(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mprofile_id = b.getInt(COL_PROFILE_ID);
            this.mplugin_id = b.getString(COL_PLUGIN_ID);
            this.mparam_id = b.getString(COL_PARAM_ID);
            this.mparam_value = b.getString(COL_PARAM_VALUE);
        }
    }

    @Override
    public String toString() {
        return "Plugin_Process_Bdf{" +
            " m_id=" + m_id +
            ", mprofile_id=" + mprofile_id +
            ", mplugin_id='" + mplugin_id + '\'' +
            ", mparam_id='" + mparam_id + '\'' +
            ", mparam_value='" + mparam_value + '\'' +
            '}';
    }


}
