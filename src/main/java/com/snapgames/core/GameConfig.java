package com.snapgames.core;

import java.util.ResourceBundle;

public class GameConfig {

    public ResourceBundle config;

    public GameConfig() {
    }

    public GameConfig(String file) {
        this();
        config = ResourceBundle.getBundle(file != null ? file : "res.gldemogame");
    }

    public String getString(String key, String defaultIntegerValue) {
        return config.containsKey(key) ? config.getString(key) : defaultIntegerValue;
    }

    public int getInteger(String key, int defaultIntegerValue) {
        return Integer.parseInt(getString(key, "" + defaultIntegerValue));
    }

    public double getDoule(String key, double defaultIntegerValue) {
        return Double.parseDouble(getString(key, "" + defaultIntegerValue));
    }

    public float getFloat(String key, float defaultIntegerValue) {
        return Float.parseFloat(getString(key, "" + defaultIntegerValue));
    }

    public boolean getBoolean(String key, boolean defaultIntegerValue) {
        return Boolean.parseBoolean(getString(key, "" + defaultIntegerValue));
    }

}
