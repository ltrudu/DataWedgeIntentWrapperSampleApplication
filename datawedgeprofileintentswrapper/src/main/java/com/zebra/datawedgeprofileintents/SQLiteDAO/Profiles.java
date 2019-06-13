package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.os.Bundle;

public class Profiles {

    public static final String COL__ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_DW_ENABLED = "dw_enabled";
    public static final String COL_ENABLED = "enabled";
    public static final String COL_DELETABLE = "deletable";
    public static final String COL_EDITABLE = "editable";

    private Integer m_id;
    private String mname;
    private String mdw_enabled;
    private String menabled;
    private String mdeletable;
    private String meditable;

    public Profiles() {
    }

    public Profiles(Integer _id, String name, String dw_enabled, String enabled, String deletable, String editable) {
        this.m_id = _id;
        this.mname = name;
        this.mdw_enabled = dw_enabled;
        this.menabled = enabled;
        this.mdeletable = deletable;
        this.meditable = editable;
    }

    public Integer get_id() {
        return m_id;
    }

    public void set_id(Integer _id) {
        this.m_id = _id;
    }

    public String getname() {
        return mname;
    }

    public void setname(String name) {
        this.mname = name;
    }

    public String getdw_enabled() {
        return mdw_enabled;
    }

    public void setdw_enabled(String dw_enabled) {
        this.mdw_enabled = dw_enabled;
    }

    public String getenabled() {
        return menabled;
    }

    public void setenabled(String enabled) {
        this.menabled = enabled;
    }

    public String getdeletable() {
        return mdeletable;
    }

    public void setdeletable(String deletable) {
        this.mdeletable = deletable;
    }

    public String geteditable() {
        return meditable;
    }

    public void seteditable(String editable) {
        this.meditable = editable;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putString(COL_NAME, this.mname);
        b.putString(COL_DW_ENABLED, this.mdw_enabled);
        b.putString(COL_ENABLED, this.menabled);
        b.putString(COL_DELETABLE, this.mdeletable);
        b.putString(COL_EDITABLE, this.meditable);
        return b;
    }

    public Profiles(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mname = b.getString(COL_NAME);
            this.mdw_enabled = b.getString(COL_DW_ENABLED);
            this.menabled = b.getString(COL_ENABLED);
            this.mdeletable = b.getString(COL_DELETABLE);
            this.meditable = b.getString(COL_EDITABLE);
        }
    }

    @Override
    public String toString() {
        return "Profiles{" +
            " m_id=" + m_id +
            ", mname='" + mname + '\'' +
            ", mdw_enabled='" + mdw_enabled + '\'' +
            ", menabled='" + menabled + '\'' +
            ", mdeletable='" + mdeletable + '\'' +
            ", meditable='" + meditable + '\'' +
            '}';
    }


}
