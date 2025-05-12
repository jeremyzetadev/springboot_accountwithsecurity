

package com.vikingbank.rest.objects;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.io.Serializable;


@JsonSerialize

public class UserSetting implements Serializable {

    // dark / light

    private String theme;

    // small medium large

    private String fontSize;


    public String getTheme() {

        return theme;

    }


    public void setTheme(String theme) {

        this.theme = theme;

    }


    public String getFontSize() {

        return fontSize;

    }


    public void setFontSize(String fontSize) {

        this.fontSize = fontSize;

    }

}
