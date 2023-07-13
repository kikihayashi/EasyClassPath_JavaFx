package com.acer.main.model.console;

import com.acer.main.model.folder.Folder;
import com.acer.main.model.folder.ProjectFolder;

import java.util.Map;
import com.acer.main.model.tool.*;

public class TxtConsole extends Console {

    private Map<String, Folder> folderMap;

    private String outputFolder;

    public TxtConsole(Map<String, Folder> folderMap, String outputFolder) {
        this.folderMap = folderMap;
        this.outputFolder = outputFolder;
    }

    @Override
    public String getMessage() {
        return getDate() + createTxtFile(outputFolder) + "\n";
    }

    //產生Txt檔到新的母資料夾
    private String createTxtFile(String outputFolder) {
        try {
            ProjectFolder projectFolder = (ProjectFolder) folderMap.get(ProjectFolder.FOLDER_NAME);
            return TextTool.createTxtFile(outputFolder, projectFolder.getTomcatPathTxt());
        } catch (NullPointerException e) {
            return "[ERROR] 發生錯誤，txt檔建立失敗！" + e;
        }
    }
}
