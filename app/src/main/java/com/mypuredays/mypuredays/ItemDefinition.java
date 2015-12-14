package com.mypuredays.mypuredays;

/**
 * Created by yael on 02/12/2015.
 */
public class ItemDefinition {

    private String name;
    private Constants.DEF_TYPE value;

    public ItemDefinition(String name, Constants.DEF_TYPE value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constants.DEF_TYPE getvalue() {
        return value;
    }

    public void setvalue(Constants.DEF_TYPE value) {
        this.value = value;
    }
}
