package com.acer.main.model.config;

public enum Property {
    CONFIG_FILE("config.properties"),
    OUTPUT_FOLDER_NAME("OUTPUT_FOLDER_NAME"),
    OUTPUT_TXT_NAME("OUTPUT_TXT_NAME"),
    ECLIPSE("ECLIPSE"),
    INTELLIJ_IDEA("INTELLIJ_IDEA"),
    ECLIPSE_PATH("DEFAULT_ECLIPSE_SEARCH_PATH"),
    INTELLIJ_IDEA_PATH("DEFAULT_INTELLIJ_IDEA_SEARCH_PATH");

    private String key;

    public String getKey() {
        return key;
    }

    Property(String key) {
        this.key = key;
    }

}
