package com.zebra.datawedgeprofileintents.sqlitedao;

import android.os.Bundle;
import java.util.Date;

public class Scanner_Params {

    public static final String COL__ID = "_id";
    public static final String COL_SCANNER_TYPE = "scanner_type";
    public static final String COL_PARAM_ID = "param_id";
    public static final String COL_PARAM_CATEGORY = "param_category";
    public static final String COL_DISPLAY_NAME = "display_name";
    public static final String COL_DEFAULT_VALUE = "default_value";
    public static final String COL_VALUE_NAME = "value_name";
    public static final String COL_VALUE_TYPE = "value_type";
    public static final String COL_VALUE_MIN = "value_min";
    public static final String COL_VALUE_MAX = "value_max";
    public static final String COL_VALUES_DISCRETE_COUNT = "values_discrete_count";
    public static final String COL_VALUES_DISCRETE = "values_discrete";
    public static final String COL_VALUES_DISCRETE_NAMES = "values_discrete_names";

    private Integer m_id;
    private String mscanner_type;
    private String mparam_id;
    private String mparam_category;
    private String mdisplay_name;
    private String mdefault_value;
    private String mvalue_name;
    private String mvalue_type;
    private Integer mvalue_min;
    private Integer mvalue_max;
    private Integer mvalues_discrete_count;
    private String mvalues_discrete;
    private String mvalues_discrete_names;

    public Scanner_Params() {
    }

    public Scanner_Params(Integer _id, String scanner_type, String param_id, String param_category, String display_name, String default_value, String value_name, String value_type, Integer value_min, Integer value_max, Integer values_discrete_count, String values_discrete, String values_discrete_names) {
        this.m_id = _id;
        this.mscanner_type = scanner_type;
        this.mparam_id = param_id;
        this.mparam_category = param_category;
        this.mdisplay_name = display_name;
        this.mdefault_value = default_value;
        this.mvalue_name = value_name;
        this.mvalue_type = value_type;
        this.mvalue_min = value_min;
        this.mvalue_max = value_max;
        this.mvalues_discrete_count = values_discrete_count;
        this.mvalues_discrete = values_discrete;
        this.mvalues_discrete_names = values_discrete_names;
    }

    public Integer get_id() {
        return m_id;
    }

    public void set_id(Integer _id) {
        this.m_id = _id;
    }

    public String getscanner_type() {
        return mscanner_type;
    }

    public void setscanner_type(String scanner_type) {
        this.mscanner_type = scanner_type;
    }

    public String getparam_id() {
        return mparam_id;
    }

    public void setparam_id(String param_id) {
        this.mparam_id = param_id;
    }

    public String getparam_category() {
        return mparam_category;
    }

    public void setparam_category(String param_category) {
        this.mparam_category = param_category;
    }

    public String getdisplay_name() {
        return mdisplay_name;
    }

    public void setdisplay_name(String display_name) {
        this.mdisplay_name = display_name;
    }

    public String getdefault_value() {
        return mdefault_value;
    }

    public void setdefault_value(String default_value) {
        this.mdefault_value = default_value;
    }

    public String getvalue_name() {
        return mvalue_name;
    }

    public void setvalue_name(String value_name) {
        this.mvalue_name = value_name;
    }

    public String getvalue_type() {
        return mvalue_type;
    }

    public void setvalue_type(String value_type) {
        this.mvalue_type = value_type;
    }

    public Integer getvalue_min() {
        return mvalue_min;
    }

    public void setvalue_min(Integer value_min) {
        this.mvalue_min = value_min;
    }

    public Integer getvalue_max() {
        return mvalue_max;
    }

    public void setvalue_max(Integer value_max) {
        this.mvalue_max = value_max;
    }

    public Integer getvalues_discrete_count() {
        return mvalues_discrete_count;
    }

    public void setvalues_discrete_count(Integer values_discrete_count) {
        this.mvalues_discrete_count = values_discrete_count;
    }

    public String getvalues_discrete() {
        return mvalues_discrete;
    }

    public void setvalues_discrete(String values_discrete) {
        this.mvalues_discrete = values_discrete;
    }

    public String getvalues_discrete_names() {
        return mvalues_discrete_names;
    }

    public void setvalues_discrete_names(String values_discrete_names) {
        this.mvalues_discrete_names = values_discrete_names;
    }


    public Bundle toBundle() { 
        Bundle b = new Bundle();
        b.putInt(COL__ID, this.m_id);
        b.putString(COL_SCANNER_TYPE, this.mscanner_type);
        b.putString(COL_PARAM_ID, this.mparam_id);
        b.putString(COL_PARAM_CATEGORY, this.mparam_category);
        b.putString(COL_DISPLAY_NAME, this.mdisplay_name);
        b.putString(COL_DEFAULT_VALUE, this.mdefault_value);
        b.putString(COL_VALUE_NAME, this.mvalue_name);
        b.putString(COL_VALUE_TYPE, this.mvalue_type);
        b.putInt(COL_VALUE_MIN, this.mvalue_min);
        b.putInt(COL_VALUE_MAX, this.mvalue_max);
        b.putInt(COL_VALUES_DISCRETE_COUNT, this.mvalues_discrete_count);
        b.putString(COL_VALUES_DISCRETE, this.mvalues_discrete);
        b.putString(COL_VALUES_DISCRETE_NAMES, this.mvalues_discrete_names);
        return b;
    }

    public Scanner_Params(Bundle b) {
        if (b != null) {
            this.m_id = b.getInt(COL__ID);
            this.mscanner_type = b.getString(COL_SCANNER_TYPE);
            this.mparam_id = b.getString(COL_PARAM_ID);
            this.mparam_category = b.getString(COL_PARAM_CATEGORY);
            this.mdisplay_name = b.getString(COL_DISPLAY_NAME);
            this.mdefault_value = b.getString(COL_DEFAULT_VALUE);
            this.mvalue_name = b.getString(COL_VALUE_NAME);
            this.mvalue_type = b.getString(COL_VALUE_TYPE);
            this.mvalue_min = b.getInt(COL_VALUE_MIN);
            this.mvalue_max = b.getInt(COL_VALUE_MAX);
            this.mvalues_discrete_count = b.getInt(COL_VALUES_DISCRETE_COUNT);
            this.mvalues_discrete = b.getString(COL_VALUES_DISCRETE);
            this.mvalues_discrete_names = b.getString(COL_VALUES_DISCRETE_NAMES);
        }
    }

    @Override
    public String toString() {
        return "Scanner_Params{" +
            " m_id=" + m_id +
            ", mscanner_type='" + mscanner_type + '\'' +
            ", mparam_id='" + mparam_id + '\'' +
            ", mparam_category='" + mparam_category + '\'' +
            ", mdisplay_name='" + mdisplay_name + '\'' +
            ", mdefault_value='" + mdefault_value + '\'' +
            ", mvalue_name='" + mvalue_name + '\'' +
            ", mvalue_type='" + mvalue_type + '\'' +
            ", mvalue_min=" + mvalue_min +
            ", mvalue_max=" + mvalue_max +
            ", mvalues_discrete_count=" + mvalues_discrete_count +
            ", mvalues_discrete='" + mvalues_discrete + '\'' +
            ", mvalues_discrete_names='" + mvalues_discrete_names + '\'' +
            '}';
    }


}
