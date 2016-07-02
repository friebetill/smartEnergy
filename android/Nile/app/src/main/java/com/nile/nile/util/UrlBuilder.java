package com.nile.nile.util;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.net.URL;

/**
 * Created by Felix on 02.07.16.
 */
public class UrlBuilder {

    private final String hereMapsApiAppId = "BdJCV1gAjGDkP2dixWRu";
    private final String hereMapsApiAppToken = "MV0PdJIEhcOYveK3eILRUA";


    public String constructApiUrl(String street, int houseNumber, int zipCode, String city) {
        street = street.replace("ß", "ss");
        String[] streetParts = street.split(" ");
        String apiStreet = "";
        for (int i = 0; i < streetParts.length; i++) {
            apiStreet += streetParts[i] + "%20";
        }
        return "?searchtext=" + apiStreet + houseNumber + "%20" + zipCode + "%20" + city +
                "&app_id=" + hereMapsApiAppId + "&app_code=" + hereMapsApiAppToken + "&gen=8";
    }

}
