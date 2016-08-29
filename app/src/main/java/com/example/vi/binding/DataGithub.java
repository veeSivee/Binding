package com.example.vi.binding;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taufiqotulfaidah on 8/26/16.
 */
public class DataGithub {

    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName();
    }
}
