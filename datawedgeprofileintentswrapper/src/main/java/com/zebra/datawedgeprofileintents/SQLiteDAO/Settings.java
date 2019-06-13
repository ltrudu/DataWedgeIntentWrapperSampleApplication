package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.os.Bundle;

public class Settings {

    public static final String COL__ID = "_id";
    public static final String COL_PARAM_ID = "param_id";
    public static final String COL_PARAM_VALUE = "param_value";

    private Integer m_id;
    private String mparam_id;
    private String mparam_value;

    public Settings() {
    }

    public Settings(Integer _id, String param_id, String param_value) {
        this.m_id = _id;
        this.mparam_id = param_id;
        this.mparam_value = param_value;
    }

    public Integer get_id() {
        return m_id;
    }

    public void set_id(Integer _id) {
        this.m_id = _id;
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
        b.putString(COL_PARAM_ID, this.mparam_id);
        b.putString(COL_PARAM_VALUE, this.mparam_value);
        return b;
    }

    public Settings(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mparam_id = b.getString(COL_PARAM_ID);
            this.mparam_value = b.getString(COL_PARAM_VALUE);
        }
    }

    @Override
    public String toString() {
        return "Settings{" +
            " m_id=" + m_id +
            ", mparam_id='" + mparam_id + '\'' +
            ", mparam_value='" + mparam_value + '\'' +
            '}';
    }


}
