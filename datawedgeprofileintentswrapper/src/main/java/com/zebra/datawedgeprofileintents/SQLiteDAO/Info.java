package com.zebra.datawedgeprofileintents.sqlitedao;

import android.os.Bundle;
import java.util.Date;

public class Info {

    public static final String COL_PARAM_ID = "param_id";
    public static final String COL_PARAM_VALUE = "param_value";

    private String mparam_id;
    private String mparam_value;

    public Info() {
    }

    public Info(String param_id, String param_value) {
        this.mparam_id = param_id;
        this.mparam_value = param_value;
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
        b.putString(COL_PARAM_ID, this.mparam_id);
        b.putString(COL_PARAM_VALUE, this.mparam_value);
        return b;
    }

    public Info(Bundle b) {
        if (b != null) {
            this.mparam_id = b.getString(COL_PARAM_ID);
            this.mparam_value = b.getString(COL_PARAM_VALUE);
        }
    }

    @Override
    public String toString() {
        return "Info{" +
            " mparam_id='" + mparam_id + '\'' +
            ", mparam_value='" + mparam_value + '\'' +
            '}';
    }


}
