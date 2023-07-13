package com.acer.main.model.folder;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.tool.FolderTool;

public class SourceCodeFolder extends FolderTool implements Folder {

    public static final String FOLDER_NAME = "SourceCode";

    private static String FILTER_FILE = "class";

    private static int type = 0;

    public SourceCodeFolder(int typeIndex) {
        this.type = typeIndex;
    }

    @Override
    public <T> String create(String parentFolderPath, T projectFile) {
        String message = "";
        ProjectFile pf = (ProjectFile) projectFile;
        //將.class檔以外的檔案建立到out/SourceCode的資料夾中
        if (!pf.getFileType().equals(FILTER_FILE)) {
            //輸出原始檔案的路徑
            String targetPath = parentFolderPath + "\\" + FOLDER_NAME + "\\" + getFolderType(pf);
            //建立out資料夾及內部資料
            message = FolderTool.createOutputFolder(pf, targetPath);
        }
        return message;
    }

    private String getFolderType(ProjectFile pf) {
        if (this.type == 0) {
            //輸出原始檔案的路徑(同一資料夾)
            return pf.getFileName();
        } else {
            //輸出原始檔案的路徑(原專案路徑)
            return pf.getSourceCodePath();
        }
    }
}
