package com.project.fdelivery_bus.classes;

public class validations {

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
//Minimum eight characters, at least one letter and one number
    public static boolean isValidPassword(String password) {
        String regex ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        return password.matches(regex);
    }
}
