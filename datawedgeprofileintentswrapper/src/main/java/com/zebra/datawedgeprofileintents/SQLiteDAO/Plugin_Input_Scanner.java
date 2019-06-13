package com.zebra.datawedgeprofileintents.sqlitedao;

import android.os.Bundle;
import java.util.Date;

public class Plugin_Input_Scanner {

    public static final String COL__ID = "_id";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_PLUGIN_ID = "plugin_id";
    public static final String COL_DEVICE_ID = "device_id";
    public static final String COL_PARAM_ID = "param_id";
    public static final String COL_PARAM_VALUE = "param_value";
    public static final String COL_SCANNER_TYPE = "scanner_type";
    public static final String COL_DISPLAY_NAME = "display_name";
    public static final String COL_VALUE_NAME = "value_name";

    private Integer m_id;
    private Integer mprofile_id;
    private String mplugin_id;
    private String mdevice_id;
    private String mparam_id;
    private String mparam_value;
    private String mscanner_type;
    private String mdisplay_name;
    private String mvalue_name;

    public Plugin_Input_Scanner() {
    }

    public Plugin_Input_Scanner(Integer _id, Integer profile_id, String plugin_id, String device_id, String param_id, String param_value, String scanner_type, String display_name, String value_name) {
        this.m_id = _id;
        this.mprofile_id = profile_id;
        this.mplugin_id = plugin_id;
        this.mdevice_id = device_id;
        this.mparam_id = param_id;
        this.mparam_value = param_value;
        this.mscanner_type = scanner_type;
        this.mdisplay_name = display_name;
        this.mvalue_name = value_name;
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

    public String getdevice_id() {
        return mdevice_id;
    }

    public void setdevice_id(String device_id) {
        this.mdevice_id = device_id;
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

    public String getscanner_type() {
        return mscanner_type;
    }

    public void setscanner_type(String scanner_type) {
        this.mscanner_type = scanner_type;
    }

    public String getdisplay_name() {
        return mdisplay_name;
    }

    public void setdisplay_name(String display_name) {
        this.mdisplay_name = display_name;
    }

    public String getvalue_name() {
        return mvalue_name;
    }

    public void setvalue_name(String value_name) {
        this.mvalue_name = value_name;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putInt(COL_PROFILE_ID, this.mprofile_id);
        b.putString(COL_PLUGIN_ID, this.mplugin_id);
        b.putString(COL_DEVICE_ID, this.mdevice_id);
        b.putString(COL_PARAM_ID, this.mparam_id);
        b.putString(COL_PARAM_VALUE, this.mparam_value);
        b.putString(COL_SCANNER_TYPE, this.mscanner_type);
        b.putString(COL_DISPLAY_NAME, this.mdisplay_name);
        b.putString(COL_VALUE_NAME, this.mvalue_name);
        return b;
    }

    public Plugin_Input_Scanner(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mprofile_id = b.getInt(COL_PROFILE_ID);
            this.mplugin_id = b.getString(COL_PLUGIN_ID);
            this.mdevice_id = b.getString(COL_DEVICE_ID);
            this.mparam_id = b.getString(COL_PARAM_ID);
            this.mparam_value = b.getString(COL_PARAM_VALUE);
            this.mscanner_type = b.getString(COL_SCANNER_TYPE);
            this.mdisplay_name = b.getString(COL_DISPLAY_NAME);
            this.mvalue_name = b.getString(COL_VALUE_NAME);
        }
    }

    @Override
    public String toString() {
        return "Plugin_Input_Scanner{" +
            " m_id=" + m_id +
            ", mprofile_id=" + mprofile_id +
            ", mplugin_id='" + mplugin_id + '\'' +
            ", mdevice_id='" + mdevice_id + '\'' +
            ", mparam_id='" + mparam_id + '\'' +
            ", mparam_value='" + mparam_value + '\'' +
            ", mscanner_type='" + mscanner_type + '\'' +
            ", mdisplay_name='" + mdisplay_name + '\'' +
            ", mvalue_name='" + mvalue_name + '\'' +
            '}';
    }


}
