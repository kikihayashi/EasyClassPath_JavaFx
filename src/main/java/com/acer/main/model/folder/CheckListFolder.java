package com.acer.main.model.folder;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.tool.FolderTool;

public class CheckListFolder implements Folder {

    public static final String FOLDER_NAME = "CheckList";

    private static String FILTER_FILE = "java";

    private static StringBuilder tomcatPathWithProjectNameStringBuilder = new StringBuilder();
    private static StringBuilder fileSizeStringBuilder = new StringBuilder();
    private static StringBuilder lastModifiedTimeStringBuilder = new StringBuilder();
    @Override
    public <T> String create(String parentFolderPath, T projectFile) {
        //建立CheckList資料夾
        String message = FolderTool.createCheckListFolder(parentFolderPath + "\\" + CheckListFolder.FOLDER_NAME);

        ProjectFile pf = (ProjectFile) projectFile;
        //將.java檔以外的檔案，列入以下紀錄中
        if (!pf.getFileType().equals(FILTER_FILE)) {
            //紀錄檔案詳細資訊
            saveFileInfo(pf);
        }
        return message;
    }

    private void saveFileInfo(ProjectFile pf) {
        tomcatPathWithProjectNameStringBuilder.append(pf.getProjectName() + pf.getTomcatPath() + "\n");
        fileSizeStringBuilder.append(pf.getFileSizeByteNumberOnly() + "\n");
        lastModifiedTimeStringBuilder.append(pf.getLastModifiedRocDate() + "\n");
    }

    public String getTomcatPathWithProjectNameTxt() {
        return this.tomcatPathWithProjectNameStringBuilder.toString().replaceAll("\\\\","/");
    }

    public String getFileSizeTxt() {
        return this.fileSizeStringBuilder.toString();
    }

    public String getLastModifiedTimeTxt() {
        return this.lastModifiedTimeStringBuilder.toString();
    }
}
