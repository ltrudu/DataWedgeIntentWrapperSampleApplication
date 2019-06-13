package com.zebra.datawedgeprofileintents.sqlitedao;

import android.os.Bundle;
import java.util.Date;

public class Plugins {

    public static final String COL__ID = "_id";
    public static final String COL_PLUGIN_ID = "plugin_id";

    private Integer m_id;
    private String mplugin_id;

    public Plugins() {
    }

    public Plugins(Integer _id, String plugin_id) {
        this.m_id = _id;
        this.mplugin_id = plugin_id;
    }

    public Integer get_id() {
        return m_id;
    }

    public void set_id(Integer _id) {
        this.m_id = _id;
    }

    public String getplugin_id() {
        return mplugin_id;
    }

    public void setplugin_id(String plugin_id) {
        this.mplugin_id = plugin_id;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putString(COL_PLUGIN_ID, this.mplugin_id);
        return b;
    }

    public Plugins(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mplugin_id = b.getString(COL_PLUGIN_ID);
        }
    }

    @Override
    public String toString() {
        return "Plugins{" +
            " m_id=" + m_id +
            ", mplugin_id='" + mplugin_id + '\'' +
            '}';
    }


}
