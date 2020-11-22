package com.pf.scoringsalud.stepCounter;

public class GlobalVariables {
    private static boolean isServiceActive = false;
    public static boolean getIsServiceActive(){
        return isServiceActive;
    }
    public static void setIsServiceActive(boolean bool ){
        isServiceActive = bool;
    }
}
