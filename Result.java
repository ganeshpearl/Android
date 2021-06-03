package com.timemoneywaste.flames;

//It is pojo class . this method is called encapsulation
//It help to set and get the values in recycler view
//Seriralizable help to avoid woobler when scrolloing down veryfast

import java.io.Serializable;



public class Result implements Serializable {

////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
}