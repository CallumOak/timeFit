package com.callumezmoney.timefit.util;

import org.springframework.core.env.Environment;

public class MapperUtils {
    public static Long getIdFromURI(String uri, Environment environment){
        String idString = uri.replace(environment.getProperty("callumezmoney.app.webapiprefix.program"), "");
        return Long.parseLong(idString);
    }
}
