package com.ams.util;

public class ValidationConstants {

    public static final String PASSWORD_REGEX_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static  final String PINCODE_REGEX_PATTERN= "^[1-9][0-9]{5}$";

    public static final String FIRST_NAME_VALIDATION_MESSAGE = "First Name should contain at least 2 characters";
    public static final String LAST_NAME_VALIDATION_MESSAGE = "Last Name should contain at least 2 characters";
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password must contain at least one digit [0-9].\n" +
            "Password must contain at least one lowercase Latin character [a-z].\n" +
            "Password must contain at least one uppercase Latin character [A-Z].\n" +
            "Password must contain at least one special character like ! @ # & ( ).\n" +
            "Password must contain a length of at least 8 characters and a maximum of 20 characters.";

}
