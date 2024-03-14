package com.acer.main.model.folder;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.tool.FolderTool;

public class ProjectFolder implements Folder {

    public static final String FOLDER_NAME = "Project";

    private static String FILTER_FILE = "java";

    private StringBuilder tomcatPathStringBuilder = new StringBuilder();

    @Override
    public <T> String create(String parentFolderPath, T projectFile) {
        String message = "";
        ProjectFile pf = (ProjectFile) projectFile;
        //將.java檔以外的檔案建立到out/專案名稱的資料夾中
        if (!pf.getFileType().equals(FILTER_FILE)) {
            //輸出檔案的路徑(內部對應Tomcat路徑)
            String targetPath = parentFolderPath + "\\" + pf.getProjectName() + pf.getTomcatPath();
            //建立out資料夾及內部資料
            message = FolderTool.createOutputFolder(pf, targetPath);
            //紀錄Tomcat路徑
            saveFileInfo(pf);
        }
        return message;
    }

    private void saveFileInfo(ProjectFile pf) {
        tomcatPathStringBuilder.append(pf.getTomcatPath() + "\n");
    }

    public String getTomcatPathTxt() {
        return this.tomcatPathStringBuilder.toString();
    }
}
