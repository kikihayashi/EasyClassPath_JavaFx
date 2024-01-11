package com.acer.main.model.folder;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.tool.FolderTool;

public class SourceCodeTheSameFolder extends FolderTool implements Folder {

    public static final String FOLDER_NAME = "SourceCodeTheSame";

    private static String FILTER_FILE = "class";

    @Override
    public <T> String create(String parentFolderPath, T projectFile) {
        return copyToSourceCode(parentFolderPath, (ProjectFile) projectFile,  FOLDER_NAME);
    }

    //將.class檔以外的檔案建立到out/SourceCode的資料夾中
    private String copyToSourceCode(String parentFolderPath, ProjectFile pf,  String folderName) {
        String message = "";
        if (!pf.getFileType().equals(FILTER_FILE)) {
            //輸出原始檔案的路徑
            String targetPath = parentFolderPath + "\\" + folderName + "\\" + getFolderType(pf);
            //建立out資料夾及內部資料
            message = FolderTool.createOutputFolder(pf, targetPath);
        }
        return message;
    }

    private String getFolderType(ProjectFile pf) {
        return pf.getFileName();
    }
}
