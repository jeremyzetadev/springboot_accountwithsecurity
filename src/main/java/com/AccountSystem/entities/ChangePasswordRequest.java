

package com.vikingbank.entities;


public class ChangePasswordRequest {

    private String currentPassword;

    private String newPassword;

    private String confirmNewPassword;


    public ChangePasswordRequest() {

    }


    public String getCurrentPassword() {

        return currentPassword;

    }



    public String getNewPassword() {

        return newPassword;

    }



    public String getConfirmNewPassword() {

        return confirmNewPassword;

    }


}
