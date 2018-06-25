package com.teachme.teachmeclient.Models;

import java.util.*;

/**
 * Created by andyshon on 16.01.2018.
 */

public class GlobalConstants {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    public static final String BACKEND_URL = "https://teachmetest.azurewebsites.net/";
    public static final String URL_SCHEME = "teachmeclient";


    public enum UserRole {
        STUDENT(0),
        TEACHER(1),
        ADMIN(2);

        private int value;
        private static Map map = new HashMap<>();

        UserRole(int value) {
            this.value = value;
        }

        static {
            for (UserRole userRole : UserRole.values()) {
                map.put(userRole.value, userRole);
            }
        }

        public static UserRole valueOf(int userRole) {
            return (UserRole) map.get(userRole);
        }

        public int getValue() {
            return value;
        }
    }
}

