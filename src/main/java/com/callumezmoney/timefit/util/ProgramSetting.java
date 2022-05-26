package com.callumezmoney.timefit.util;

public enum ProgramSetting {
    FREQUENCY,
    WEEKLY;

    ProgramSetting(){}

    public String value(){
        switch(this){
            case FREQUENCY:
                return "frequency";
            case WEEKLY:
                return "weekly";
        }
        return null;
    }

    public static ProgramSetting fromValue(String value){
        switch(value){
            case "frequency":
                return FREQUENCY;
            case "weekly":
                return WEEKLY;
        }
        return null;
    }
}
