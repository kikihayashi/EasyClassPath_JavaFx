package com.acer.main.model.console;

import com.acer.main.model.config.ConfigManager;
import com.acer.main.model.config.Property;
import com.acer.main.model.folder.CheckListFolder;
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
            CheckListFolder checkListFolder = (CheckListFolder) folderMap.get(CheckListFolder.FOLDER_NAME);

            String projectFolderMessage = createTxtFileAndReturnMessage(outputFolder, projectFolder);
            String checkListFolderMessage = createTxtFileAndReturnMessage(outputFolder + "\\" + CheckListFolder.FOLDER_NAME, checkListFolder);
            return projectFolderMessage + "\n" + checkListFolderMessage;
        } catch (NullPointerException e) {
            return "[ERROR] 發生錯誤，txt檔建立失敗！" + e;
        }
    }

    private String createTxtFileAndReturnMessage(String outputFolder, ProjectFolder projectFolder) {
        String RESULT_TXT = ConfigManager.getPropertyValue(Property.OUTPUT_TXT_NAME);
        String RESULT_TXT_MESSAGE = TextTool.createTxtFile(outputFolder, projectFolder.getTomcatPathTxt(), RESULT_TXT);
        return RESULT_TXT_MESSAGE;
    }

    private String createTxtFileAndReturnMessage(String outputFolder, CheckListFolder checkListFolder) {
        String PATH_TXT = ConfigManager.getPropertyValue(Property.PATH_TXT_NAME);
        String FILE_SIZE_TXT = ConfigManager.getPropertyValue(Property.FILE_SIZE_TXT_NAME);
        String LAST_MODIFIED_DATE_TXT = ConfigManager.getPropertyValue(Property.LAST_MODIFIED_DATE_TXT_NAME);

        String PATH_TXT_MESSAGE = TextTool.createTxtFile(outputFolder, checkListFolder.getTomcatPathWithProjectNameTxt(), PATH_TXT);
        String FILE_SIZE_MESSAGE = TextTool.createTxtFile(outputFolder, checkListFolder.getFileSizeTxt(), FILE_SIZE_TXT);
        String LAST_MODIFIED_DATE_TXT_MESSAGE = TextTool.createTxtFile(outputFolder, checkListFolder.getLastModifiedTimeTxt(), LAST_MODIFIED_DATE_TXT);
        return PATH_TXT_MESSAGE + "\n" + FILE_SIZE_MESSAGE + "\n" + LAST_MODIFIED_DATE_TXT_MESSAGE;
    }
}
