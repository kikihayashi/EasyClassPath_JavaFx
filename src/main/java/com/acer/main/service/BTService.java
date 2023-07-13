package com.acer.main.service;

import com.acer.main.model.config.ConfigManager;
import com.acer.main.model.config.Property;

public class BTService {
    public static String searchPath;

    public static Property IDE = Property.INTELLIJ_IDEA;//預設Intellij IDEA

    public static final String OUTPUT_FOLDER = System.getProperty("user.dir") + ConfigManager.getPropertyValue(Property.OUTPUT_FOLDER_NAME);

    //取得專案名稱
    protected String getProjectName() {
        int index = searchPath.lastIndexOf("\\");
        return searchPath.substring(index + 1);
    }

    //設置IDE
    public static void setIDE(int ideType) {
        switch (ideType) {
            case 0 -> IDE = Property.INTELLIJ_IDEA;
            case 1 -> IDE = Property.ECLIPSE;
            default -> throw new IllegalArgumentException();
        }
    }

    //取得預設搜尋路徑
    public static String getDefaultPath() {
        switch (IDE) {
            case INTELLIJ_IDEA -> {
                return ConfigManager.getPropertyValue(Property.INTELLIJ_IDEA_PATH);
            }
            case ECLIPSE -> {
                return ConfigManager.getPropertyValue(Property.ECLIPSE_PATH);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
