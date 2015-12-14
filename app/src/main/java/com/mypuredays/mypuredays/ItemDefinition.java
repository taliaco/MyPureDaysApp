package com.mypuredays.mypuredays;

/**
 * Created by yael on 02/12/2015.
 */
public class ItemDefinition {

    private String name;
    private String value;

    public ItemDefinition(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getvalue() {
        return value;
    }

    public void setvalue(String value) {
        this.value = value;
    }
}
