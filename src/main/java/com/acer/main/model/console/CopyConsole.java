package com.acer.main.model.console;

import com.acer.main.model.folder.Folder;

import java.util.List;
import java.util.Map;

public class CopyConsole extends Console {

    private List list;

    private Map<String, Folder> folderMap;

    private String outputFolder;

    public CopyConsole(List list, Map<String, Folder> folderMap, String outputFolder) {
        this.list = list;
        this.folderMap = folderMap;
        this.outputFolder = outputFolder;
    }

    @Override
    public String getMessage() {
        return getDate() + createFolderAndExportFile(list, outputFolder) + "\n";
    }

    //產生新的母資料夾，並將找到的檔案複製過去
    private <T> String createFolderAndExportFile(List<T> fileList, String outputFolder) {
        for (T pf : fileList) {
            for (Map.Entry<String, Folder> entry : folderMap.entrySet()) {
                String message = entry.getValue().create(outputFolder, pf);
                if (message.length() > 0) {
                    return message;
                }
            }
        }
        return (fileList.size() > 0) ? "[SUCCESS] 已成功複製檔案！" : "[INFO] 無任何檔案被複製！";
    }
}
