package com.callumezmoney.timefit.util;

import org.springframework.core.env.Environment;

public class MapperUtils {
    public static Long getIdFromURI(String uri, Environment environment, String type){
        String idString = uri.replace(environment.getProperty("callumezmoney.app.webapiprefix." + type) + "/", "");
        return Long.parseLong(idString);
    }
}
